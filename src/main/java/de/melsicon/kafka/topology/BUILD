load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "topology",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//src/main/java/de/melsicon/kafka/model",
        "//toolchain:nullaway",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:com_google_guava_guava",
        "@maven//:javax_inject_javax_inject",
        "@maven//:org_apache_kafka_kafka_clients",
        "@maven//:org_apache_kafka_kafka_streams",
    ],
)
