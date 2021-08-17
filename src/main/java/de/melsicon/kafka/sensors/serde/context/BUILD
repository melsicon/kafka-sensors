load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "context",
    srcs = ["SerDesModule.java"],
    javacopts = ["-AskipDefs=\\.Dagger"],
    visibility = ["//visibility:public"],
    deps = [
        "//src/main/java/de/melsicon/kafka/sensors/serialization/avro",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/avromapper",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/confluent",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/confluentmapper",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/ion",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/json",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/proto",
        "//toolchain:checker",
        "//toolchain:dagger",
    ],
)

java_library(
    name = "app_context",
    srcs = ["AppSerDesModule.java"],
    javacopts = ["-AskipDefs=\\.Dagger"],
    visibility = ["//visibility:public"],
    deps = [
        "//src/main/java/de/melsicon/kafka/sensors/configuration",
        "//src/main/java/de/melsicon/kafka/sensors/model",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/json",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/proto",
        "//toolchain:checker",
        "//toolchain:dagger",
    ],
)
