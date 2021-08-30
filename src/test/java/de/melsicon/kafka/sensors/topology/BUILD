load("@rules_java//java:defs.bzl", "java_library", "java_test")

java_library(
    name = "tests",
    testonly = True,
    srcs = glob(["*.java"]),
    deps = [
        "//src/main/java/de/melsicon/kafka/sensors/configuration",
        "//src/main/java/de/melsicon/kafka/sensors/model",
        "//src/main/java/de/melsicon/kafka/sensors/serde",
        "//src/main/java/de/melsicon/kafka/sensors/topology",
        "//src/test/java/de/melsicon/kafka/sensors/testutil",
        "//src/test/java/de/melsicon/kafka/sensors/topology/context",
        "//toolchain:checker",
        "//toolchain:truth",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:com_google_guava_guava",
        "@maven//:net_mguenther_kafka_kafka_junit",
        "@maven//:org_apache_kafka_kafka_clients",
        "@maven//:org_apache_kafka_kafka_streams",
        "@maven//:org_apache_kafka_kafka_streams_test_utils",
    ],
)

java_test(
    name = "TopologyTest",
    size = "medium",
    data = ["//conf:logging"],
    jvm_flags = ["-Djava.util.logging.config.file=$(rootpath //conf:logging)"],
    runtime_deps = [
        ":tests",
        "@maven//:com_google_flogger_flogger_system_backend",
        "@maven//:org_slf4j_slf4j_jdk14",
    ],
)
