load("@rules_java//java:defs.bzl", "java_library", "java_test")

java_library(
    name = "tests",
    testonly = True,
    srcs = glob(["*.java"]),
    deps = [
        "//src/main/avro/de/melsicon/kafka/sensors/avro",
        "//src/main/java/de/melsicon/kafka/sensors/type/confluent/specific",
        "//src/test/java/de/melsicon/kafka/sensors/testutil",
        "//toolchain:checker",
        "//toolchain:truth",
        "@confluent_schema_registry//:kafka-streams-avro-serde",
        "@maven//:org_apache_avro_avro",
        "@maven//:org_apache_kafka_kafka_clients",
    ],
)

java_test(
    name = "SerializationTest",
    size = "small",
    data = ["//conf:logging"],
    jvm_flags = ["-Djava.util.logging.config.file=$(rootpath //conf:logging)"],
    runtime_deps = [
        ":tests",
        "@maven//:org_slf4j_slf4j_jdk14",
    ],
)
