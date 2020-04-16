load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "context",
    testonly = True,
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//src/main/java/de/melsicon/kafka/configuration",
        "//src/main/java/de/melsicon/kafka/topology",
        "//toolchain:checker",
        "//toolchain:dagger",
        "//toolchain:nullaway",
        "@maven//:com_salesforce_kafka_test_kafka_junit_core",
        "@maven//:javax_inject_javax_inject",
    ],
)
