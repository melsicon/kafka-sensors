load("@rules_java//java:defs.bzl", "java_library", "java_plugin")

java_plugin(
    name = "mapstruct_processor",
    javacopts = ["-Amapstruct.suppressGeneratorTimestamp=true"],
    processor_class = "org.mapstruct.ap.MappingProcessor",
    deps = ["@maven//:org_mapstruct_mapstruct_processor"],
)

java_library(
    name = "mapstruct",
    exported_plugins = ["mapstruct_processor"],
    visibility = ["//src/main/java/de/melsicon/kafka/sensors/serialization:__subpackages__"],
    exports = ["@maven//:org_mapstruct_mapstruct"],
)

java_library(
    name = "mapping",
    srcs = glob(["*.java"]),
    visibility = ["//src/main/java/de/melsicon/kafka/sensors/serialization:__subpackages__"],
    deps = [
        "//toolchain:checker",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:org_apache_kafka_kafka_clients",
        "@maven//:org_mapstruct_mapstruct",
    ],
)
