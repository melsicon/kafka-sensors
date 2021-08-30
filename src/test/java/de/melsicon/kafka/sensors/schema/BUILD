load("@rules_java//java:defs.bzl", "java_library", "java_test")

java_library(
    name = "tests",
    testonly = True,
    srcs = glob(["*.java"]),
    deps = [
        "//src/main/java/de/melsicon/kafka/sensors/type/avro/generic",
        "//src/main/java/de/melsicon/kafka/sensors/type/avro/reflect",
        "//src/main/java/de/melsicon/kafka/sensors/type/avro/specific",
        "//toolchain:checker",
        "//toolchain:truth",
        "@maven//:com_google_guava_guava",
        "@maven//:org_apache_avro_avro",
    ],
)

java_test(
    name = "SchemaTest",
    size = "small",
    runtime_deps = [
        ":tests",
        "@maven//:org_slf4j_slf4j_jdk14",
    ],
)
