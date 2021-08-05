load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "serde",
    srcs = glob(["*.java"]),
    javacopts = ["-AskipDefs=\\.(Immutable|With)[A-Z][A-Za-z0-9]*(\\.Builder)?$$"],
    visibility = ["//visibility:public"],
    exports = [
        "//src/main/java/de/melsicon/kafka/model",
        "@maven//:org_apache_kafka_kafka_clients",
    ],
    deps = [
        "//src/main/java/de/melsicon/kafka/model",
        "//toolchain:checker",
        "//toolchain:immutables",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:org_apache_kafka_kafka_clients",
    ],
)
