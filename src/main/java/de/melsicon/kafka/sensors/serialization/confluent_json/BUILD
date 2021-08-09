load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "confluent_json",
    srcs = glob(["*.java"]),
    visibility = ["//src/main/java/de/melsicon/kafka/sensors/serialization:__subpackages__"],
    deps = [
        "//toolchain:checker",
        "@maven//:com_fasterxml_jackson_core_jackson_annotations",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:com_google_guava_guava",
    ],
)
