load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "mixin",
    srcs = glob(["*.java"]),
    visibility = [
        "//src/main/java/de/melsicon/kafka/sensors/serialization:__subpackages__",
        "//src/test:__subpackages__",
    ],
    deps = [
        "//src/main/java/de/melsicon/kafka/sensors/model",
        "//toolchain:checker",
        "@maven//:com_fasterxml_jackson_core_jackson_annotations",
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
        "@maven//:com_fasterxml_jackson_datatype_jackson_datatype_guava",
        "@maven//:com_fasterxml_jackson_datatype_jackson_datatype_jdk8",
        "@maven//:com_fasterxml_jackson_datatype_jackson_datatype_jsr310",
        "@maven//:com_google_errorprone_error_prone_annotations",
    ],
)
