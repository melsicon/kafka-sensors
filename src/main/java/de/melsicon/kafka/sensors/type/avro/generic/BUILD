load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "generic",
    srcs = glob(["*.java"]),
    visibility = [
        "//src/main/java/de/melsicon/kafka/sensors/serialization:__subpackages__",
        "//src/test:__subpackages__",
    ],
    deps = [
        "//src/main/java/de/melsicon/kafka/sensors/type/avro/logicaltypes",
        "//toolchain:checker",
        "//toolchain:dagger",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:org_apache_avro_avro",
    ],
)
