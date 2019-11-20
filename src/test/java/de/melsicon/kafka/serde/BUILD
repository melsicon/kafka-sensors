load("@rules_java//java:defs.bzl", "java_library", "java_test")

java_library(
    name = "tests",
    srcs = glob(["*.java"]),
    runtime_deps = [
        "@maven//:org_slf4j_slf4j_jdk14",
    ],
    deps = [
        "//src/main/avro/de/melsicon/kafka/sensors/avro",
        "//src/main/java/de/melsicon/kafka/model",
        "//src/main/java/de/melsicon/kafka/serde",
        "//src/main/java/de/melsicon/kafka/serde/avro",
        "//src/main/java/de/melsicon/kafka/serde/json",
        "//src/main/java/de/melsicon/kafka/serde/proto",
        "//src/test/java/de/melsicon/kafka/testutil",
        "//toolchain:nullaway",
        "@confluent_schema_registry//:kafka-avro-serializer",
        "@confluent_schema_registry//:kafka-schema-registry-client",
        "@confluent_schema_registry//:kafka-sensors-avro-serde",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:com_google_guava_guava",
        "@maven//:javax_inject_javax_inject",
        "@maven//:junit_junit",
        "@maven//:org_apache_avro_avro",
        "@maven//:org_apache_kafka_kafka_clients",
        "@maven//:org_apache_kafka_kafka_streams",
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
