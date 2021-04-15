load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "serdes",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//src/main/java/de/melsicon/kafka/model",
        "//src/main/java/de/melsicon/kafka/serde",
        "//src/main/java/de/melsicon/kafka/serde/avro",
        "//src/main/java/de/melsicon/kafka/serde/avromapper",
        "//src/main/java/de/melsicon/kafka/serde/confluent",
        "//src/main/java/de/melsicon/kafka/serde/confluentmapper",
        "//src/main/java/de/melsicon/kafka/serde/ion",
        "//src/main/java/de/melsicon/kafka/serde/json",
        "//src/main/java/de/melsicon/kafka/serde/proto",
        "//toolchain:checker",
        "@maven//:com_google_errorprone_error_prone_annotations",
    ],
)
