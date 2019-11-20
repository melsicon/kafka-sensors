load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "topology",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//src/main/avro/de/melsicon/kafka/sensors/avro",
        "//src/main/java/de/melsicon/kafka/model",
        "//src/main/java/de/melsicon/kafka/serde/proto",
        "//toolchain:nullaway",
        "@confluent_schema_registry//:kafka-avro-serializer",
        "@confluent_schema_registry//:kafka-schema-registry-client",
        "@confluent_schema_registry//:kafka-sensors-avro-serde",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:com_google_guava_guava",
        "@maven//:javax_inject_javax_inject",
        "@maven//:org_apache_avro_avro",
        "@maven//:org_apache_kafka_kafka_clients",
        "@maven//:org_apache_kafka_kafka_streams",
    ],
)
