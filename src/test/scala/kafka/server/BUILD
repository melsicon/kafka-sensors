load("@io_bazel_rules_scala//scala:scala.bzl", "scala_library")

scala_library(
    name = "server",
    testonly = True,
    srcs = glob(["*.scala"]),
    visibility = ["//visibility:public"],
    deps = [
        "@maven//:com_typesafe_scala_logging_scala_logging_2_13",
        "@maven//:org_apache_kafka_kafka_2_13",
        "@maven//:org_apache_kafka_kafka_clients",
        "@maven//:org_apache_kafka_kafka_metadata",
    ],
)
