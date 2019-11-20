load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "serde",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    exports = [
        "//src/main/java/de/melsicon/kafka/model",
        "@maven//:org_apache_kafka_kafka_clients",
    ],
    deps = [
        "//src/main/java/de/melsicon/kafka/model",
        "//toolchain:nullaway",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:org_apache_kafka_kafka_clients",
    ],
)
