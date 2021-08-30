load("@rules_java//java:defs.bzl", "java_library", "java_test")

java_library(
    name = "tests",
    testonly = True,
    srcs = glob(["*.java"]),
    deps = [
        "//src/main/proto/melsicon/kafka/sensors/v1:sensorsv1_java_proto",
        "//toolchain:checker",
        "//toolchain:truth",
        "@com_google_protobuf//:protobuf_java",
    ],
)

java_test(
    name = "SerializationTest",
    size = "small",
    runtime_deps = [":tests"],
)
