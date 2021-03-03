load("@rules_java//java:defs.bzl", "java_binary", "java_library")
load("@io_bazel_rules_docker//java:image.bzl", "java_image")

APP_DEPS = [
    "@maven//:com_google_errorprone_error_prone_annotations",
    "@maven//:com_google_flogger_flogger",
    "@maven//:info_picocli_picocli",
    "@maven//:io_helidon_config_helidon_config",
]

RUNTIME_DEPS = [
    "@maven//:com_google_flogger_flogger_system_backend",
    "@maven//:io_helidon_config_helidon_config_yaml",
    "@maven//:org_slf4j_slf4j_jdk14",
]

java_library(
    name = "app_library",
    srcs = glob(
        ["*.java"],
        exclude = ["ClassCheck.java"],
    ),
    deps = [
        "//src/main/java/de/melsicon/kafka/context",
        "//src/main/java/de/melsicon/kafka/lifecycle",
        "//toolchain:checker",
        "//toolchain:nullaway",
    ] + APP_DEPS,
)

java_binary(
    name = "app",
    data = ["//conf"],
    jvm_flags = [
        "-Djava.util.logging.config.file=conf/logging.properties",
        "-XX:+CrashOnOutOfMemoryError",
    ],
    main_class = "de.melsicon.kafka.sensors.app.Main",
    visibility = ["//visibility:public"],
    runtime_deps = [
        ":app_library",
    ] + RUNTIME_DEPS,
)

java_image(
    name = "kafka_sensors",
    base = "@java_base//image",
    data = ["//conf"],
    jvm_flags = [
        "-Djava.util.logging.config.file=conf/logging.properties",
        "-XX:+CrashOnOutOfMemoryError",
    ],
    main_class = "de.melsicon.kafka.sensors.app.Main",
    visibility = ["//visibility:public"],
    runtime_deps = [
        ":app_library",
    ] + RUNTIME_DEPS,
)

java_binary(
    name = "classcheck",
    srcs = ["ClassCheck.java"],
    data = ["//conf"],
    jvm_flags = ["-Djava.util.logging.config.file=conf/logging.properties"],
    main_class = "de.melsicon.kafka.sensors.app.ClassCheck",
    visibility = ["//visibility:public"],
    runtime_deps = [
        ":app_library",
    ] + RUNTIME_DEPS,
    deps = [
        "//toolchain:nullaway",
        "@maven//:com_google_flogger_flogger",
        "@maven//:io_github_classgraph_classgraph",
    ],
)
