load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "context",
    srcs = glob(["*.java"]),
    javacopts = ["-AskipDefs=\\.Dagger"],
    visibility = ["//src/main/java/de/melsicon/kafka/sensors/benchmark:__pkg__"],
    deps = [
        "//src/main/java/de/melsicon/kafka/sensors/model",
        "//src/main/java/de/melsicon/kafka/sensors/serde",
        "//src/main/java/de/melsicon/kafka/sensors/serde/context",
        "//toolchain:checker",
        "//toolchain:dagger",
        "@maven//:com_google_errorprone_error_prone_annotations",
    ],
)
