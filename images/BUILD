load("@io_bazel_rules_docker//container:container.bzl", "container_image")

# From https://github.com/GoogleContainerTools/distroless/blob/master/base/base.bzl
NONROOT = 65532

container_image(
    name = "java11_nonroot",
    base = "@java_base//image",
    user = "%d" % NONROOT,
    visibility = ["//visibility:public"],
)
