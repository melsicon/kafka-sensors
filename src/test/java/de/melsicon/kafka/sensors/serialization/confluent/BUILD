load("@rules_java//java:defs.bzl", "java_library", "java_test")

java_library(
    name = "tests",
    testonly = True,
    srcs = glob(["*.java"]),
    visibility = ["//src/test:__subpackages__"],
    deps = [
        "//src/main/avro/de/melsicon/kafka/sensors/avro",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/confluent",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/confluent_reflect",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/generic",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/logicaltypes",
        "//src/test/java/de/melsicon/kafka/sensors/testutil",
        "//toolchain:checker",
        "//toolchain:truth",
        "@confluent_schema_registry//:kafka-streams-avro-serde",
        "@maven//:org_apache_avro_avro",
        "@maven//:org_apache_kafka_kafka_clients",
    ],
)

java_test(
    name = "AvroTest",
    size = "small",
    runtime_deps = [
        ":tests",
        "@maven//:org_slf4j_slf4j_jdk14",
    ],
)

java_test(
    name = "GenericTest",
    size = "small",
    runtime_deps = [
        ":tests",
        "@maven//:org_slf4j_slf4j_jdk14",
    ],
)

java_test(
    name = "ReflectTest",
    size = "small",
    runtime_deps = [
        ":tests",
        "@maven//:org_slf4j_slf4j_jdk14",
    ],
)
