load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "kafka-schema-registry-client",
    srcs = glob(["client/src/main/java/**/*.java"]),
    resources = glob(["client/src/main/resources/**"]),
    visibility = ["//visibility:public"],
    deps = [
        "@confluent_common//:common-config",
        "@confluent_common//:common-utils",
        "@maven//:com_fasterxml_jackson_core_jackson_annotations",
        "@maven//:com_fasterxml_jackson_core_jackson_core",
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
        "@maven//:com_google_guava_guava",
        "@maven//:io_swagger_swagger_annotations",
        "@maven//:jakarta_validation_jakarta_validation_api",
        "@maven//:jakarta_ws_rs_jakarta_ws_rs_api",
        "@maven//:org_apache_avro_avro",
        "@maven//:org_apache_kafka_kafka_clients",
        "@maven//:org_slf4j_slf4j_api",
    ],
)

java_library(
    name = "kafka-core",
    neverlink = True,
    exports = ["@maven//:org_apache_kafka_kafka_2_12"],
)

java_library(
    name = "kafka-schema-serializer",
    srcs = glob(["schema-serializer/src/main/java/**/*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        ":kafka-core",
        ":kafka-schema-registry-client",
        "@confluent_common//:common-config",
        "@maven//:com_fasterxml_jackson_core_jackson_core",
        "@maven//:org_apache_avro_avro",
        "@maven//:org_apache_kafka_kafka_clients",
    ],
)

java_library(
    name = "kafka-avro-serializer",
    srcs = glob(["avro-serializer/src/main/java/**/*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        ":kafka-core",
        ":kafka-schema-registry-client",
        ":kafka-schema-serializer",
        "@confluent_common//:common-config",
        "@maven//:org_apache_avro_avro",
        "@maven//:org_apache_kafka_kafka_clients",
    ],
)

java_library(
    name = "kafka-streams-avro-serde",
    srcs = glob(["avro-serde/src/main/java/**/*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        ":kafka-avro-serializer",
        ":kafka-schema-registry-client",
        "@maven//:org_apache_avro_avro",
        "@maven//:org_apache_kafka_kafka_clients",
    ],
)

# runtime dependencies of org_everit_json_schema
SCHEMA_DEPS = [
    "@maven//:com_damnhandy_handy_uri_templates",
    "@maven//:com_google_re2j_re2j",
    "@maven//:commons_validator_commons_validator",
    "@maven//:org_json_json",
]

java_library(
    name = "kafka-json-schema-provider",
    srcs = glob(["json-schema-provider/src/main/java/**/*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        ":kafka-schema-registry-client",
        "@maven//:com_fasterxml_jackson_core_jackson_core",
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
        "@maven//:com_fasterxml_jackson_datatype_jackson_datatype_guava",
        "@maven//:com_fasterxml_jackson_datatype_jackson_datatype_jdk8",
        "@maven//:com_fasterxml_jackson_datatype_jackson_datatype_joda",
        "@maven//:com_fasterxml_jackson_datatype_jackson_datatype_jsr310",
        "@maven//:com_fasterxml_jackson_module_jackson_module_parameter_names",
        "@maven//:com_kjetland_mbknor_jackson_jsonschema_2_12",
        "@maven//:org_json_json",
        "@maven//:org_slf4j_slf4j_api",
        "@org_everit_json_schema//jar",
    ],
    runtime_deps = SCHEMA_DEPS,
)

java_library(
    name = "kafka-json-schema-serializer",
    srcs = glob(["json-schema-serializer/src/main/java/**/*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        ":kafka-core",
        ":kafka-json-schema-provider",
        ":kafka-schema-registry-client",
        ":kafka-schema-serializer",
        "@confluent_common//:common-config",
        "@maven//:com_fasterxml_jackson_core_jackson_core",
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
        "@maven//:com_google_guava_guava",
        "@maven//:org_apache_kafka_kafka_clients",
        "@org_everit_json_schema//jar",
    ],
    runtime_deps = SCHEMA_DEPS,
)

java_library(
    name = "kafka-streams-json-schema-serde",
    srcs = glob(["json-schema-serde/src/main/java/**/*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        ":kafka-json-schema-serializer",
        ":kafka-schema-registry-client",
        "@maven//:org_apache_kafka_kafka_clients",
    ],
)

java_library(
    name = "kafka-protobuf-provider",
    srcs = glob(["protobuf-provider/src/main/java/**/*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        ":kafka-schema-registry-client",
        "@com_google_protobuf//:protobuf_java",
        "@com_google_protobuf//:protobuf_java_util",
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
        "@maven//:com_google_guava_guava",
        "@maven//:com_squareup_wire_wire_schema",
        "@maven//:org_apache_kafka_kafka_clients",
        "@maven//:org_jetbrains_kotlin_kotlin_stdlib",
        "@maven//:org_slf4j_slf4j_api",
    ],
)

java_library(
    name = "kafka-protobuf-serializer",
    srcs = glob(["protobuf-serializer/src/main/java/**/*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        ":kafka-core",
        ":kafka-protobuf-provider",
        ":kafka-schema-registry-client",
        ":kafka-schema-serializer",
        "@com_google_protobuf//:protobuf_java",
        "@com_google_protobuf//:protobuf_java_util",
        "@confluent_common//:common-config",
        "@maven//:com_google_guava_guava",
        "@maven//:com_squareup_wire_wire_schema",
        "@maven//:org_apache_kafka_kafka_clients",
    ],
)

java_library(
    name = "kafka-streams-protobuf-serde",
    srcs = glob(["protobuf-serde/src/main/java/**/*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        ":kafka-protobuf-serializer",
        ":kafka-schema-registry-client",
        "@com_google_protobuf//:protobuf_java",
        "@maven//:org_apache_kafka_kafka_clients",
    ],
)
