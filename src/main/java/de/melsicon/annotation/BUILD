load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "annotation",
    srcs = glob(["*.java"]),
    neverlink = True,
    visibility = ["//visibility:public"],
    deps = [
        "@maven//:com_google_code_findbugs_jsr305",
    ],
)
