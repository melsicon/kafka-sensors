load("@io_bazel_rules_docker//container:container.bzl", "container_image")

NOBODY = 65534

container_image(
    name = "java16_base",
    base = "@java16_jdk//image",
    cmd = None,
    user = "%d" % NOBODY,
    visibility = ["//visibility:public"],
    workdir = "/app/de_melsicon_kafka_sensors",
)

container_image(
    name = "java16_debug_base",
    base = "@java16_jdk//image",
    cmd = None,
    visibility = ["//visibility:public"],
    workdir = "/app/de_melsicon_kafka_sensors",
)
