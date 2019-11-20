load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "context",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//src/main/java/de/melsicon/kafka/configuration",
        "//src/main/java/de/melsicon/kafka/streams",
        "//src/main/java/de/melsicon/kafka/topology",
        "//toolchain:dagger",
        "//toolchain:nullaway",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:com_google_guava_guava",
        "@maven//:com_salesforce_kafka_test_kafka_junit_core",
        "@maven//:io_helidon_config_helidon_config",
        "@maven//:javax_inject_javax_inject",
        "@maven//:org_apache_kafka_kafka_clients",
        "@maven//:org_apache_kafka_kafka_streams_test_utils",
    ],
)
