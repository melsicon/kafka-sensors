load("@rules_java//java:defs.bzl", "java_library", "java_test")

java_library(
    name = "context",
    testonly = True,
    srcs = glob(["*.java"]),
    javacopts = ["-AskipDefs=\\.Dagger"],
    visibility = ["//src/test:__subpackages__"],
    deps = [
        "//src/main/java/de/melsicon/kafka/serde",
        "//src/main/java/de/melsicon/kafka/serde/avro",
        "//src/main/java/de/melsicon/kafka/serde/confluent",
        "//src/main/java/de/melsicon/kafka/serde/ion",
        "//src/main/java/de/melsicon/kafka/serde/json",
        "//src/main/java/de/melsicon/kafka/serde/proto",
        "//toolchain:checker",
        "//toolchain:dagger",
        "//toolchain:truth",
        "@maven//:com_google_guava_guava",
        "@maven//:org_apache_kafka_kafka_clients",
    ],
)
