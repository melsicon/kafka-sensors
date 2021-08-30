load("@rules_java//java:defs.bzl", "java_library", "java_test")

java_library(
    name = "tests",
    testonly = True,
    srcs = glob(["*.java"]),
    deps = [
        "//src/main/java/de/melsicon/kafka/sensors/type/gson",
        "//toolchain:checker",
        "//toolchain:truth",
        "@maven//:com_google_code_gson_gson",
    ],
)

java_test(
    name = "SerializationTest",
    size = "small",
    runtime_deps = [":tests"],
)
