load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "startup",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//src/main/java/de/melsicon/kafka/context",
        "//src/main/java/de/melsicon/kafka/lifecycle",
        "//toolchain:nullaway",
        "@maven//:com_google_code_findbugs_jsr305",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:info_picocli_picocli",
        "@maven//:io_helidon_config_helidon_config",
        "@maven//:javax_inject_javax_inject",
    ],
)
