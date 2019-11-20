load("@rules_java//java:defs.bzl", "java_binary")

java_binary(
    name = "sensors",
    srcs = ["Main.java"],
    data = ["//conf"],
    jvm_flags = ["-Djava.util.logging.config.file=conf/logging.properties"],
    main_class = "de.melsicon.kafka.sensors.Main",
    visibility = ["//visibility:public"],
    runtime_deps = [
        "@maven//:com_google_flogger_flogger_system_backend",
        "@maven//:io_helidon_config_helidon_config_yaml",
        "@maven//:org_slf4j_slf4j_jdk14",
    ],
    deps = [
        "//src/main/java/de/melsicon/kafka/startup",
        "//toolchain:nullaway",
        "@maven//:com_google_flogger_flogger",
        "@maven//:info_picocli_picocli",
        "@maven//:io_helidon_config_helidon_config",
    ],
)

java_binary(
    name = "classcheck",
    srcs = ["ClassCheck.java"],
    data = ["//conf"],
    jvm_flags = ["-Djava.util.logging.config.file=conf/logging.properties"],
    main_class = "de.melsicon.kafka.sensors.ClassCheck",
    visibility = ["//visibility:public"],
    runtime_deps = [
        "@maven//:com_google_flogger_flogger_system_backend",
        "@maven//:io_helidon_config_helidon_config_yaml",
        "@maven//:org_slf4j_slf4j_jdk14",
    ],
    deps = [
        "//src/main/avro/de/melsicon/kafka/sensors/avro",
        "//src/main/java/de/melsicon/kafka/model",
        "//src/main/java/de/melsicon/kafka/startup",
        "//toolchain:nullaway",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:com_google_flogger_flogger",
        "@maven//:com_google_guava_guava",
        "@maven//:info_picocli_picocli",
        "@maven//:io_github_classgraph_classgraph",
        "@maven//:io_helidon_config_helidon_config",
        "@maven//:javax_inject_javax_inject",
        "@maven//:org_apache_avro_avro",
    ],
)
