load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "gson",
    srcs = glob(["*.java"]),
    javacopts = ["-AskipDefs=\\.(Immutable|GsonAdapters)[A-Z][A-Za-z0-9]*$$"],
    visibility = [
        "//src/main/java/de/melsicon/kafka/sensors/serialization:__subpackages__",
        "//src/test:__subpackages__",
    ],
    deps = [
        "//toolchain:checker",
        "//toolchain:immutables",
        "@maven//:com_google_code_gson_gson",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:com_google_flogger_flogger",
        "@maven//:com_google_guava_guava",
        "@maven//:org_immutables_gson",
    ],
)
