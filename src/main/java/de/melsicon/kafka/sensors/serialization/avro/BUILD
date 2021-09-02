load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "avro",
    srcs = glob(["*.java"]),
    visibility = ["//src/main/java/de/melsicon/kafka/sensors/serde/context:__pkg__"],
    exports = [
        "@maven//:org_apache_kafka_kafka_clients",
    ],
    deps = [
        "//src/main/avro/de/melsicon/kafka/sensors/avro",
        "//src/main/java/de/melsicon/kafka/sensors/model",
        "//src/main/java/de/melsicon/kafka/sensors/serde",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/mapping",
        "//src/main/java/de/melsicon/kafka/sensors/type/avro",
        "//src/main/java/de/melsicon/kafka/sensors/type/avro/generic",
        "//src/main/java/de/melsicon/kafka/sensors/type/avro/reflect",
        "//src/main/java/de/melsicon/kafka/sensors/type/avro/specific",
        "//toolchain:checker",
        "//toolchain:dagger",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:org_apache_avro_avro",
        "@maven//:org_apache_kafka_kafka_clients",
    ],
)
