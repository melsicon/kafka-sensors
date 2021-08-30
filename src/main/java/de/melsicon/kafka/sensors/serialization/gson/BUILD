load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "gson",
    srcs = glob(["*.java"]),
    javacopts = [
        "-Amapstruct.suppressGeneratorTimestamp=true",
        "-AskipDefs=MapperImpl$$",
    ],
    visibility = [
        "//src/main/java/de/melsicon/kafka/sensors/serde/context:__pkg__",
        "//src/test:__subpackages__",
    ],
    exports = [
        "@maven//:org_apache_kafka_kafka_clients",
    ],
    deps = [
        "//src/main/java/de/melsicon/kafka/sensors/model",
        "//src/main/java/de/melsicon/kafka/sensors/serde",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/mapping",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/mapping:mapstruct",
        "//src/main/java/de/melsicon/kafka/sensors/type/gson",
        "//toolchain:checker",
        "//toolchain:dagger",
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
        "@maven//:com_google_code_gson_gson",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:org_apache_kafka_kafka_clients",
    ],
)
