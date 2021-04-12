load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "context",
    testonly = True,
    srcs = glob(["*.java"]),
    javacopts = ["-AskipDefs=^de.melsicon.kafka.context.Dagger"],
    visibility = ["//visibility:public"],
    deps = [
        "//src/main/java/de/melsicon/kafka/configuration",
        "//src/main/java/de/melsicon/kafka/model",
        "//src/main/java/de/melsicon/kafka/topology",
        "//toolchain:checker",
        "//toolchain:dagger",
        "@maven//:com_salesforce_kafka_test_kafka_junit_core",
        "@maven//:javax_inject_javax_inject",
        "@maven//:org_apache_kafka_kafka_clients",
        "@maven//:org_apache_kafka_kafka_streams",
    ],
)
