load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "configuration",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//toolchain:autovalue",
        "//toolchain:nullaway",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:com_google_guava_guava",
        "@maven//:io_helidon_config_helidon_config",
        "@maven//:io_helidon_config_helidon_config_object_mapping",
        "@maven//:javax_inject_javax_inject",
    ],
)
