load("@rules_java//java:defs.bzl", "java_library", "java_test")

java_library(
    name = "tests",
    srcs = glob(["*.java"]),
    runtime_deps = [
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
        "@maven//:org_apache_kafka_kafka_2_12",
        "@maven//:org_slf4j_slf4j_jdk14",
    ],
    deps = [
        "//src/main/java/de/melsicon/kafka/model",
        "//src/main/java/de/melsicon/kafka/serde",
        "//src/main/java/de/melsicon/kafka/serde/avro",
        "//src/main/java/de/melsicon/kafka/serde/json",
        "//src/main/java/de/melsicon/kafka/serde/proto",
        "//src/main/java/de/melsicon/kafka/topology",
        "//src/test/java/de/melsicon/kafka/testutil",
        "//toolchain:nullaway",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:com_salesforce_kafka_test_kafka_junit4",
        "@maven//:com_salesforce_kafka_test_kafka_junit_core",
        "@maven//:junit_junit",
        "@maven//:org_apache_kafka_kafka_clients",
        "@maven//:org_apache_kafka_kafka_streams",
        "@maven//:org_apache_kafka_kafka_streams_test_utils",
        "@maven//:org_assertj_assertj_core",
    ],
)

java_test(
    name = "ProcessorTest",
    size = "small",
    runtime_deps = [
        ":tests",
    ],
)

java_test(
    name = "TopologyTest",
    size = "medium",
    runtime_deps = [
        ":tests",
    ],
)
