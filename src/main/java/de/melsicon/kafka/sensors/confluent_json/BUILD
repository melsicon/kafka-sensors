load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "confluent_json",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//toolchain:checker",
        "@maven//:com_fasterxml_jackson_core_jackson_annotations",
        "@maven//:com_google_errorprone_error_prone_annotations",
    ],
)
