load("@rules_java//java:defs.bzl", "java_library")

def _version_impl(ctx):
    ctx.actions.expand_template(
        template = ctx.file.template,
        output = ctx.outputs.out,
        substitutions = {
            "${project.version}": ctx.attr.version,
            "${buildDate}": "1970/01/01",
        },
    )

version = rule(
    attrs = {
        "version": attr.string(mandatory = True),
        "template": attr.label(
            mandatory = True,
            allow_single_file = True,
        ),
    },
    outputs = {"out": "jmh.properties"},
    implementation = _version_impl,
)
