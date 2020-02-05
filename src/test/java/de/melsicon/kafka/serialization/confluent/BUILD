load("@rules_java//java:defs.bzl", "java_library", "java_test")

java_library(
    name = "tests",
    testonly = True,
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//src/main/avro/de/melsicon/kafka/sensors/avro",
        "//src/main/java/de/melsicon/kafka/sensors/generic",
        "//src/main/java/de/melsicon/kafka/sensors/reflect",
        "//src/main/java/de/melsicon/kafka/serde/confluent",
        "//src/test/java/de/melsicon/kafka/testutil",
        "//toolchain:nullaway",
        "@confluent_schema_registry//:kafka-streams-avro-serde",
        "@maven//:junit_junit",
        "@maven//:org_apache_avro_avro",
        "@maven//:org_apache_kafka_kafka_clients",
        "@maven//:org_assertj_assertj_core",
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
