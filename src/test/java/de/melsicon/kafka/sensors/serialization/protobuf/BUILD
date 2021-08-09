load("@rules_java//java:defs.bzl", "java_library", "java_test")

java_library(
    name = "tests",
    testonly = True,
    srcs = glob(["*.java"]),
    runtime_deps = [
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
    ],
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
    runtime_deps = [
        ":tests",
        "@maven//:org_slf4j_slf4j_jdk14",
    ],
)
