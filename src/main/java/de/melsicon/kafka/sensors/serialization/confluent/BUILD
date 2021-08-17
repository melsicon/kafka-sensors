load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "scala-library",
    neverlink = True,
    exports = ["@maven//:org_scala_lang_scala_library"],
)

java_library(
    name = "confluent",
    srcs = glob(["*.java"]),
    visibility = [
        "//src/main/java/de/melsicon/kafka/sensors/serde/context:__pkg__",
        "//src/test:__subpackages__",
    ],
    exports = [
        "@maven//:org_apache_kafka_kafka_clients",
    ],
    deps = [
        ":scala-library",
        "//src/main/avro/de/melsicon/kafka/sensors/avro",
        "//src/main/java/de/melsicon/kafka/sensors/model",
        "//src/main/java/de/melsicon/kafka/sensors/serde",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/confluent_json",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/confluent_reflect",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/generic",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/mapping",
        "//src/main/proto/melsicon/kafka/sensors/v1:sensorsv1_java_proto",
        "//toolchain:checker",
        "//toolchain:dagger",
        "@com_google_protobuf//:protobuf_java",
        "@confluent_schema_registry//:kafka-avro-serializer",
        "@confluent_schema_registry//:kafka-json-schema-serializer",
        "@confluent_schema_registry//:kafka-protobuf-serializer",
        "@confluent_schema_registry//:kafka-streams-avro-serde",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:org_apache_avro_avro",
        "@maven//:org_apache_kafka_kafka_clients",
    ],
)
