load("@rules_java//java:defs.bzl", "java_library", "java_plugin")

java_library(
    name = "protobuf",
    srcs = ["ProtoTypesMapper.java"],
    visibility = ["//visibility:public"],
    deps = [
        "//toolchain:nullaway",
        "@com_google_protobuf//:protobuf_java",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:org_mapstruct_mapstruct_processor",
    ],
)

java_library(
    name = "protobuf-naming",
    srcs = ["ProtobufAccessorNamingStrategy.java"],
    deps = [
        "//src/main/java/de/melsicon/annotation",
        "//toolchain:autoservice",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:org_mapstruct_mapstruct_processor",
    ],
)

java_plugin(
    name = "mapstruct_processor",
    processor_class = "org.mapstruct.ap.MappingProcessor",
    deps = [
        "protobuf-naming",
        "@maven//:org_mapstruct_mapstruct_processor",
    ],
)

java_library(
    name = "mapstruct",
    exported_plugins = ["mapstruct_processor"],
    visibility = ["//visibility:public"],
    exports = ["@maven//:org_mapstruct_mapstruct"],
)
