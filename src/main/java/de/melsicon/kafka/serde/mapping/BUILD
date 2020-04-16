load("@rules_java//java:defs.bzl", "java_library", "java_plugin")

java_plugin(
    name = "mapstruct_processor",
    processor_class = "org.mapstruct.ap.MappingProcessor",
    deps = ["@maven//:org_mapstruct_mapstruct_processor"],
)

java_library(
    name = "mapstruct",
    exported_plugins = ["mapstruct_processor"],
    javacopts = ["-Amapstruct.suppressGeneratorTimestamp=true"],
    visibility = ["//visibility:public"],
    exports = ["@maven//:org_mapstruct_mapstruct"],
)

java_library(
    name = "mapping",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//toolchain:checker",
        "//toolchain:nullaway",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:org_apache_kafka_kafka_clients",
        "@maven//:org_mapstruct_mapstruct",
    ],
)
