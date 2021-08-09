load("@rules_java//java:defs.bzl", "java_library", "java_plugin")

java_library(
    name = "protobuf",
    srcs = ["ProtoTypesMapper.java"],
    visibility = ["//src/main/java/de/melsicon/kafka/sensors/serialization:__subpackages__"],
    deps = [
        "//toolchain:checker",
        "@com_google_protobuf//:protobuf_java",
    ],
)

java_library(
    name = "protobuf-naming",
    srcs = ["ProtobufAccessorNamingStrategy.java"],
    deps = [
        "//toolchain:autoservice",
        "//toolchain:checker",
        "@maven//:org_mapstruct_mapstruct_processor",
    ],
)

java_plugin(
    name = "mapstruct_processor",
    processor_class = "org.mapstruct.ap.MappingProcessor",
    deps = [
        "protobuf-naming",
    ],
)

java_library(
    name = "mapstruct",
    exported_plugins = ["mapstruct_processor"],
    visibility = ["//src/main/java/de/melsicon/kafka/sensors/serialization:__subpackages__"],
    exports = ["@maven//:org_mapstruct_mapstruct"],
)
