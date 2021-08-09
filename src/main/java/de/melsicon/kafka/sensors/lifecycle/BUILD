load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "lifecycle",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//toolchain:checker",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:com_google_guava_guava",
        "@maven//:javax_inject_javax_inject",
    ],
)
