load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "logicaltypes",
    srcs = glob(["*.java"]),
    visibility = [
        "//src/main/java/de/melsicon/kafka/sensors:__subpackages__",
        "//src/test:__subpackages__",
    ],
    deps = [
        "//toolchain:checker",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:org_apache_avro_avro",
    ],
)
