load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "proto",
    srcs = glob(["*.java"]),
    javacopts = [
        "-Amapstruct.suppressGeneratorTimestamp=true",
        "-AskipDefs=MapperImpl$$",
    ],
    visibility = ["//src/main/java/de/melsicon/kafka/sensors/serde/context:__pkg__"],
    exports = [
        "@maven//:org_apache_kafka_kafka_clients",
    ],
    deps = [
        "//src/main/java/de/melsicon/kafka/sensors/model",
        "//src/main/java/de/melsicon/kafka/sensors/serde",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/mapping",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/mapping/protobuf",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/mapping/protobuf:mapstruct",
        "//src/main/java/de/melsicon/kafka/sensors/type/proto",
        "//src/main/proto/melsicon/kafka/sensors/v1:sensorsv1_java_proto",
        "//toolchain:checker",
        "//toolchain:dagger",
        "@com_google_protobuf//:protobuf_java",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:org_apache_kafka_kafka_clients",
    ],
)
