load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "scala-library",
    neverlink = True,
    exports = ["@maven//:org_scala_lang_scala_library"],
)

java_library(
    name = "confluent",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    exports = [
        "@maven//:org_apache_kafka_kafka_clients",
    ],
    deps = [
        ":scala-library",
        "//src/main/avro/de/melsicon/kafka/sensors/avro",
        "//src/main/java/de/melsicon/kafka/model",
        "//src/main/java/de/melsicon/kafka/sensors/confluent_json",
        "//src/main/java/de/melsicon/kafka/sensors/confluent_reflect",
        "//src/main/java/de/melsicon/kafka/sensors/generic",
        "//src/main/java/de/melsicon/kafka/serde",
        "//src/main/java/de/melsicon/kafka/serde/confluentmapper",
        "//src/main/java/de/melsicon/kafka/serde/mapping",
        "//src/main/java/de/melsicon/kafka/serde/proto",
        "//src/main/proto/melsicon/kafka/sensors/v1:sensorsv1_java_proto",
        "//toolchain:nullaway",
        "@com_google_protobuf//:protobuf_java",
        "@confluent_schema_registry//:kafka-avro-serializer",
        "@confluent_schema_registry//:kafka-json-schema-serializer",
        "@confluent_schema_registry//:kafka-protobuf-serializer",
        "@confluent_schema_registry//:kafka-streams-avro-serde",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:javax_inject_javax_inject",
        "@maven//:org_apache_avro_avro",
        "@maven//:org_apache_kafka_kafka_clients",
    ],
)
