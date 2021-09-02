load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "ion",
    srcs = glob(["*.java"]),
    visibility = [
        "//src/main/java/de/melsicon/kafka/sensors/serialization:__subpackages__",
        "//src/test:__subpackages__",
    ],
    exports = [
        "@maven//:org_apache_kafka_kafka_clients",
    ],
    deps = [
        "//src/main/java/de/melsicon/kafka/sensors/model",
        "//src/main/java/de/melsicon/kafka/sensors/type/helper",
        "//toolchain:checker",
        "@maven//:com_amazon_ion_ion_java",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:org_apache_kafka_kafka_clients",
    ],
)
