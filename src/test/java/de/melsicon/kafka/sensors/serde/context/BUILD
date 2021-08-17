load("@rules_java//java:defs.bzl", "java_library", "java_test")

java_library(
    name = "context",
    testonly = True,
    srcs = glob(["*.java"]),
    javacopts = ["-AskipDefs=\\.Dagger"],
    visibility = ["//src/test:__subpackages__"],
    deps = [
        "//src/main/java/de/melsicon/kafka/sensors/serde",
        "//src/main/java/de/melsicon/kafka/sensors/serde/context",
        "//toolchain:checker",
        "//toolchain:dagger",
    ],
)
