load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "common-utils",
    srcs = glob(["utils/src/main/java/**/*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "@maven//:org_slf4j_slf4j_api",
    ],
)

java_library(
    name = "common-config",
    srcs = glob(["config/src/main/java/**/*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        ":common-utils",
        "@maven//:org_slf4j_slf4j_api",
    ],
)
