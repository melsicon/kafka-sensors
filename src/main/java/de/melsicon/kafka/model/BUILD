load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "model",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//toolchain:autovalue",
        "//toolchain:nullaway",
        "@maven//:com_google_errorprone_error_prone_annotations",
    ],
)
