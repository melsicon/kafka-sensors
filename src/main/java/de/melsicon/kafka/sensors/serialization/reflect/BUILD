load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "reflect",
    srcs = glob(["*.java"]),
    visibility = [
        "//src/main/java/de/melsicon/kafka/sensors/serialization:__subpackages__",
        "//src/test:__subpackages__",
    ],
    deps = [
        "//src/main/java/de/melsicon/kafka/sensors/serialization/logicaltypes",
        "//toolchain:checker",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:com_google_guava_guava",
        "@maven//:org_apache_avro_avro",
    ],
)
