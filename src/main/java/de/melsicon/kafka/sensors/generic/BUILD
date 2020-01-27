load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "generic",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//toolchain:nullaway",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:org_apache_avro_avro",
    ],
)
