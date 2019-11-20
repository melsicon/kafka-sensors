load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "proto",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    exports = [
        "@maven//:org_apache_kafka_kafka_clients",
    ],
    deps = [
        "//src/main/java/de/melsicon/kafka/model",
        "//src/main/java/de/melsicon/kafka/serde",
        "//src/main/java/de/melsicon/kafka/serde/mapping",
        "//src/main/java/de/melsicon/kafka/serde/mapping/protobuf",
        "//src/main/java/de/melsicon/kafka/serde/mapping/protobuf:mapstruct",
        "//src/main/proto/melsicon/kafka/sensors/v1:streamsv1_java_proto",
        "//toolchain:nullaway",
        "@com_google_protobuf//:protobuf_java",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:javax_inject_javax_inject",
        "@maven//:org_apache_kafka_kafka_clients",
    ],
)
