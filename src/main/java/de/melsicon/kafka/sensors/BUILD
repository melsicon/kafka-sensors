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
        "//src/main/java/de/melsicon/kafka/startup",
        "//toolchain:nullaway",
        "@maven//:com_google_flogger_flogger",
        "@maven//:io_github_classgraph_classgraph",
    ],
)

java_binary(
    name = "benchmark",
    srcs = glob(["Bench*.java"]),
    data = ["//conf"],
    jvm_flags = [
        "--add-opens",
        "java.base/java.io=ALL-UNNAMED",
        "-Djava.util.logging.config.file=conf/logging.properties",
    ],
    main_class = "org.openjdk.jmh.Main",
    visibility = ["//visibility:public"],
    runtime_deps = [
        "@maven//:com_google_flogger_flogger_system_backend",
        "@maven//:io_helidon_config_helidon_config_yaml",
        "@maven//:org_slf4j_slf4j_jdk14",
    ],
    deps = [
        "//src/main/java/de/melsicon/annotation",
        "//src/main/java/de/melsicon/kafka/model",
        "//src/main/java/de/melsicon/kafka/serde",
        "//src/main/java/de/melsicon/kafka/serde/avro",
        "//src/main/java/de/melsicon/kafka/serde/json",
        "//src/main/java/de/melsicon/kafka/serde/proto",
        "//src/main/java/de/melsicon/kafka/serde/reflect",
        "//toolchain:jmh",
        "//toolchain:nullaway",
        "@confluent_schema_registry//:kafka-avro-serializer",
        "@confluent_schema_registry//:kafka-schema-registry-client",
    ],
)
