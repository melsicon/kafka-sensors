load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "testutil",
    testonly = True,
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//toolchain:checker",
        "@confluent_schema_registry//:kafka-schema-registry-client",
        "@confluent_schema_registry//:kafka-schema-serializer",
        "@maven//:junit_junit",
        "@maven//:net_mguenther_kafka_kafka_junit",
        "@maven//:org_apache_kafka_kafka_clients",
    ],
)
