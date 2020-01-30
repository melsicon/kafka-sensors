load("@rules_java//java:defs.bzl", "java_library", "java_test")

java_library(
    name = "tests",
    testonly = True,
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//src/main/java/de/melsicon/kafka/model",
        "//src/main/java/de/melsicon/kafka/serde",
        "//src/main/java/de/melsicon/kafka/serde/avromapper",
        "//src/main/java/de/melsicon/kafka/serde/confluent",
        "//src/test/java/de/melsicon/kafka/testutil",
        "//toolchain:nullaway",
        "@maven//:junit_junit",
        "@maven//:org_apache_kafka_kafka_clients",
        "@maven//:org_assertj_assertj_core",
    ],
)

java_test(
    name = "SerializationTest",
    size = "small",
    runtime_deps = [
        ":tests",
        "@maven//:org_slf4j_slf4j_jdk14",
    ],
)
