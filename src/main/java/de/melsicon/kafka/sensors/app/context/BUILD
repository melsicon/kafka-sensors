load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "context",
    srcs = glob(["*.java"]),
    javacopts = ["-AskipDefs=\\.Dagger"],
    visibility = ["//src/main/java/de/melsicon/kafka/sensors/app:__pkg__"],
    deps = [
        "//src/main/java/de/melsicon/kafka/sensors/configuration",
        "//src/main/java/de/melsicon/kafka/sensors/lifecycle",
        "//src/main/java/de/melsicon/kafka/sensors/serde/context:app_context",
        "//src/main/java/de/melsicon/kafka/sensors/streams",
        "//src/main/java/de/melsicon/kafka/sensors/topology",
        "//toolchain:checker",
        "//toolchain:dagger",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:com_google_guava_guava",
        "@maven//:io_helidon_config_helidon_config",
        "@maven//:javax_inject_javax_inject",
        "@maven//:org_apache_kafka_kafka_clients",
    ],
)
