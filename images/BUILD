load("@io_bazel_rules_docker//container:container.bzl", "container_image")

NOBODY = 65534

container_image(
    name = "java16_nonroot",
    base = "@java16_jre//image",
    cmd = None,
    user = "%d" % NOBODY,
    visibility = ["//visibility:public"],
)

container_image(
    name = "java16_debug",
    base = "@java16_jdk//image",
    cmd = None,
    visibility = ["//visibility:public"],
)
