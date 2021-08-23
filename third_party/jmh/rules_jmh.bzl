""" Manage JMH version file. """

def _version_impl(ctx):
    ctx.actions.expand_template(
        template = ctx.file.template,
        output = ctx.outputs.out,
        substitutions = {
            "${buildDate}": "1970/01/01",
            "${project.version}": ctx.attr.version,
        },
    )

version = rule(
    attrs = {
        "template": attr.label(
            mandatory = True,
            allow_single_file = True,
        ),
        "version": attr.string(mandatory = True),
    },
    outputs = {"out": "jmh.properties"},
    implementation = _version_impl,
)
