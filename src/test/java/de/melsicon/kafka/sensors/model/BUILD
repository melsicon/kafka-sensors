load("@rules_java//java:defs.bzl", "java_library", "java_test")

java_library(
    name = "tests",
    testonly = True,
    srcs = glob(["*.java"]),
    deps = [
        "//src/main/java/de/melsicon/kafka/sensors/model",
        "//toolchain:checker",
        "//toolchain:truth",
    ],
)

java_test(
    name = "SensorStateTest",
    size = "small",
    runtime_deps = [":tests"],
)
