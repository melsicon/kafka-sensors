load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "context",
    testonly = True,
    srcs = glob(["*.java"]),
    javacopts = ["-AskipDefs=\\.Dagger"],
    visibility = ["//src/test:__subpackages__"],
    deps = [
        "//src/main/java/de/melsicon/kafka/sensors/configuration",
        "//src/main/java/de/melsicon/kafka/sensors/model",
        "//src/main/java/de/melsicon/kafka/sensors/serde",
        "//src/main/java/de/melsicon/kafka/sensors/serde/context",
        "//src/main/java/de/melsicon/kafka/sensors/topology",
        "//toolchain:checker",
        "//toolchain:dagger",
        "@maven//:org_apache_kafka_kafka_streams",
    ],
)
