load("@io_bazel_rules_docker//java:image.bzl", "java_image")
load("@rules_java//java:defs.bzl", "java_binary", "java_library")
load("//third_party/images:java_base.bzl", "JAVA_BASE")

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

DATA = [
    "//conf",
    "//conf:logging",
]

JVM_FLAGS = [
    "-Djava.util.logging.config.file=$(rootpath //conf:logging)",
    "-Dorg.apache.avro.fastread=true",  # https://issues.apache.org/jira/browse/AVRO-2247
    "-Dorg.apache.avro.specific.use_custom_coders=true",  # https://issues.apache.org/jira/browse/AVRO-2090
    "-XX:+CrashOnOutOfMemoryError",
]

java_library(
    name = "app_library",
    srcs = glob(
        ["*.java"],
        exclude = ["ClassCheck.java"],
    ),
    deps = [
        "//src/main/java/de/melsicon/kafka/sensors/app/context",
        "//src/main/java/de/melsicon/kafka/sensors/lifecycle",
        "//toolchain:checker",
    ] + APP_DEPS,
)

java_binary(
    name = "app",
    data = DATA,
    jvm_flags = JVM_FLAGS,
    main_class = "de.melsicon.kafka.sensors.app.Main",
    visibility = ["//visibility:public"],
    runtime_deps = [
        ":app_library",
    ] + RUNTIME_DEPS,
)

java_image(
    name = "kafka_sensors",
    base = JAVA_BASE,
    data = DATA,
    jvm_flags = JVM_FLAGS,
    main_class = "de.melsicon.kafka.sensors.app.Main",
    visibility = ["//visibility:public"],
    runtime_deps = [
        ":app_library",
    ] + RUNTIME_DEPS,
)

java_binary(
    name = "classcheck",
    srcs = ["ClassCheck.java"],
    data = ["//conf:logging"],
    jvm_flags = ["-Djava.util.logging.config.file=$(rootpath //conf:logging)"],
    main_class = "de.melsicon.kafka.sensors.app.ClassCheck",
    visibility = ["//visibility:public"],
    runtime_deps = [
        ":app_library",
    ] + RUNTIME_DEPS,
    deps = [
        "@maven//:com_google_flogger_flogger",
        "@maven//:com_google_guava_guava",
        "@maven//:io_github_classgraph_classgraph",
    ],
)
