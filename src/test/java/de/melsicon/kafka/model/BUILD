load("@rules_java//java:defs.bzl", "java_library", "java_test")

java_library(
    name = "tests",
    srcs = glob(["*.java"]),
    deps = [
        "//src/main/java/de/melsicon/kafka/model",
        "//toolchain:nullaway",
        "@maven//:junit_junit",
        "@maven//:org_assertj_assertj_core",
    ],
)

java_test(
    name = "SensorStateTest",
    size = "small",
    runtime_deps = [
        ":tests",
    ],
)
