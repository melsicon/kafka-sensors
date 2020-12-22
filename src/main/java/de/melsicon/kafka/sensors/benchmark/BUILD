load("@io_bazel_rules_docker//java:image.bzl", "java_image")

java_image(
    name = "benchmark",
    srcs = glob(["*.java"]),
    base = "@java_base//image",
    data = ["//conf"],
    jvm_flags = [
        "--add-opens",
        "java.base/java.io=ALL-UNNAMED",
        "-Djava.util.logging.config.file=conf/logging.properties",
        "-Dorg.apache.avro.fastread=true",
        "-XX:+CrashOnOutOfMemoryError",
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
        "//src/main/java/de/melsicon/kafka/sensors/serdes",
        "//src/main/java/de/melsicon/kafka/serde",
        "//toolchain:jmh",
        "//toolchain:nullaway",
        "@confluent_schema_registry//:kafka-avro-serializer",
        "@confluent_schema_registry//:kafka-schema-registry-client",
        "@confluent_schema_registry//:kafka-schema-serializer",
        "@maven//:com_google_errorprone_error_prone_annotations",
    ],
)
