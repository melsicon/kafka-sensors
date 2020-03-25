load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "confluent",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    exports = [
        "@maven//:org_apache_kafka_kafka_clients",
    ],
    deps = [
        "//src/main/avro/de/melsicon/kafka/sensors/avro",
        "//src/main/java/de/melsicon/kafka/model",
        "//src/main/java/de/melsicon/kafka/sensors/confluent_reflect",
        "//src/main/java/de/melsicon/kafka/sensors/generic",
        "//src/main/java/de/melsicon/kafka/serde",
        "//src/main/java/de/melsicon/kafka/serde/avromapper",
        "//src/main/java/de/melsicon/kafka/serde/mapping",
        "//toolchain:nullaway",
        "@confluent_schema_registry//:kafka-avro-serializer",
        "@confluent_schema_registry//:kafka-streams-avro-serde",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:javax_inject_javax_inject",
        "@maven//:org_apache_avro_avro",
        "@maven//:org_apache_kafka_kafka_clients",
    ],
)
