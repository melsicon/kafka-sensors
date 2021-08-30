load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "proto",
    srcs = glob(["*.java"]),
    visibility = [
        "//src/main/java/de/melsicon/kafka/sensors/serialization:__subpackages__",
        "//src/test:__subpackages__",
    ],
    deps = [
        "//toolchain:checker",
        "@com_google_protobuf//:protobuf_java",
        "@confluent_schema_registry//:kafka-protobuf-serializer",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:com_google_guava_guava",
        "@maven//:org_apache_kafka_kafka_clients",
    ],
)
