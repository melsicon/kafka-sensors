load("@rules_java//java:defs.bzl", "java_proto_library")
load("@rules_proto//proto:defs.bzl", "proto_library")

java_proto_library(
    name = "streamsv1_java_proto",
    visibility = ["//visibility:public"],
    deps = [
        ":streamsv1_proto",
    ],
)

proto_library(
    name = "streamsv1_proto",
    srcs = glob(["*.proto"]),
    strip_import_prefix = "/src/main/proto",
    deps = [
        "@com_google_protobuf//:duration_proto",
        "@com_google_protobuf//:timestamp_proto",
    ],
)
