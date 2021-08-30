load("@rules_java//java:defs.bzl", "java_library", "java_test")

java_library(
    name = "tests",
    testonly = True,
    srcs = glob(["*.java"]),
    deps = [
        "//src/main/java/de/melsicon/kafka/sensors/logic",
        "//src/main/java/de/melsicon/kafka/sensors/model",
        "//toolchain:checker",
        "//toolchain:truth",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:com_google_guava_guava",
    ],
)

java_test(
    name = "CalculatorTest",
    size = "small",
    runtime_deps = [":tests"],
)
