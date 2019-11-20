load("@rules_java//java:defs.bzl", "java_library")

# Loosely based on https://github.com/meetup/rules_avro

def _commonprefix(m):
    if not m:
        return ""
    s1 = min(m)
    s2 = max(m)
    chars = []
    for i in range(0, len(s1)):
        chars.append(s1[i])
    for i, c in enumerate(chars):
        if c != s2[i]:
            return s1[:i]
    return s1

def _new_generator_command(ctx, src_dir, gen_dir):
    java_path = ctx.attr._jdk[java_common.JavaRuntimeInfo].java_executable_exec_path
    gen_command = "{java} {options} -cp {tool} {main} compile ".format(
        java = java_path,
        main = "org.apache.avro.tool.Main",  # should be -jar
        options = "-Dorg.apache.avro.specific.use_custom_coders=true",
        tool = ctx.file._avro_tools.path,
    )

    if ctx.attr.strings:
        gen_command += " -string"

    if ctx.attr.encoding:
        gen_command += " -encoding {encoding}".format(
            encoding = ctx.attr.encoding,
        )

    gen_command += " schema {src} {gen_dir}".format(
        src = src_dir,
        gen_dir = gen_dir,
    )

    return gen_command

def _impl(ctx):
    src_dir = _commonprefix(
        [f.dirname for f in ctx.files.srcs],
    )
    gen_dir = "{out}-tmp".format(
        out = ctx.outputs.codegen.path,
    )
    commands = [
        "mkdir -p {gen_dir}".format(gen_dir = gen_dir),
        _new_generator_command(ctx, src_dir, gen_dir),
        # forcing a timestamp for deterministic artifacts
        "find {gen_dir} -exec touch -t 198001010000 {{}} \\;".format(
            gen_dir = gen_dir,
        ),
        "{jar} cMf {output} -C {gen_dir} .".format(
            jar = "%s/bin/jar" % ctx.attr._jdk[java_common.JavaRuntimeInfo].java_home,
            output = ctx.outputs.codegen.path,
            gen_dir = gen_dir,
        ),
    ]

    inputs = ctx.files.srcs + ctx.files._jdk + [
        ctx.file._avro_tools,
    ]

    ctx.actions.run_shell(
        inputs = inputs,
        outputs = [ctx.outputs.codegen],
        command = " && ".join(commands),
        progress_message = "generating avro srcs",
        arguments = [],
    )

    return struct(
        codegen = ctx.outputs.codegen,
    )

avro_gen = rule(
    attrs = {
        "srcs": attr.label_list(
            allow_files = [".avsc"],
        ),
        "strings": attr.bool(),
        "encoding": attr.string(),
        "_jdk": attr.label(
            default = "@bazel_tools//tools/jdk:current_java_runtime",
            providers = [java_common.JavaRuntimeInfo],
        ),
        "_avro_tools": attr.label(
            cfg = "host",
            default = "@maven//:org_apache_avro_avro_tools",
            allow_single_file = True,
        ),
    },
    outputs = {
        "codegen": "%{name}_codegen.srcjar",
    },
    implementation = _impl,
)

def avro_java_library(
        name,
        srcs = [],
        javacopts = None,
        strings = None,
        encoding = None,
        visibility = None):
    avro_gen(
        name = name + "_srcjar",
        srcs = srcs,
        strings = strings,
        encoding = encoding,
        visibility = visibility,
    )
    java_library(
        name = name,
        srcs = [name + "_srcjar"],
        javacopts = javacopts,
        deps = [
            "@maven//:org_apache_avro_avro",
        ],
        visibility = visibility,
    )
