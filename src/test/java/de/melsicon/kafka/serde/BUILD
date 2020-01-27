load("@rules_java//java:defs.bzl", "java_library", "java_test")

java_library(
    name = "tests",
    testonly = True,
    srcs = glob(["*.java"]),
    runtime_deps = [
        "@maven//:org_slf4j_slf4j_jdk14",
    ],
    deps = [
        "//src/main/java/de/melsicon/kafka/model",
        "//src/main/java/de/melsicon/kafka/serde",
        "//src/main/java/de/melsicon/kafka/serde/avro",
        "//src/main/java/de/melsicon/kafka/serde/confluent",
        "//src/main/java/de/melsicon/kafka/serde/json",
        "//src/main/java/de/melsicon/kafka/serde/proto",
        "//src/test/java/de/melsicon/kafka/testutil",
        "//toolchain:nullaway",
        "@maven//:com_google_guava_guava",
        "@maven//:junit_junit",
        "@maven//:org_apache_kafka_kafka_clients",
        "@maven//:org_assertj_assertj_core",
    ],
)

java_test(
    name = "SerdeTest",
    size = "small",
    runtime_deps = [
        ":tests",
    ],
)

java_test(
    name = "SerdeWithDurationTest",
    size = "small",
    runtime_deps = [
        ":tests",
    ],
)
