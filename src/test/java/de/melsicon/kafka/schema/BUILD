load("@rules_java//java:defs.bzl", "java_library", "java_test")

java_library(
    name = "tests",
    testonly = True,
    srcs = glob(["*.java"]),
    runtime_deps = [
        "@maven//:org_slf4j_slf4j_jdk14",
    ],
    deps = [
        "//src/main/avro/de/melsicon/kafka/sensors/avro",
        "//src/main/java/de/melsicon/kafka/sensors/generic",
        "//src/main/java/de/melsicon/kafka/sensors/reflect",
        "//toolchain:nullaway",
        "@maven//:com_google_guava_guava",
        "@maven//:junit_junit",
        "@maven//:org_apache_avro_avro",
        "@maven//:org_assertj_assertj_core",
    ],
)

java_test(
    name = "SchemaTest",
    size = "small",
    runtime_deps = [
        ":tests",
    ],
)
