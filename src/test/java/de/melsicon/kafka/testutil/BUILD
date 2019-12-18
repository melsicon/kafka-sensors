load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "testutil",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//src/main/java/de/melsicon/annotation",
        "//src/main/java/de/melsicon/kafka/model",
        "//toolchain:nullaway",
        "@confluent_schema_registry//:kafka-avro-serializer",
        "@confluent_schema_registry//:kafka-schema-registry-client",
        "@maven//:junit_junit",
        "@maven//:org_apache_kafka_kafka_clients",
    ],
)
