load("@rules_java//java:defs.bzl", "java_library", "java_test")

java_library(
    name = "tests",
    srcs = glob(["*.java"]),
    runtime_deps = [
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
        "@maven//:org_slf4j_slf4j_jdk14",
    ],
    deps = [
        "//src/main/java/de/melsicon/annotation",
        "//src/main/java/de/melsicon/kafka/serde/reflect",
        "//toolchain:nullaway",
        "@maven//:junit_junit",
        "@maven//:org_apache_avro_avro",
        "@maven//:org_assertj_assertj_core",
    ],
)

java_test(
    name = "SerializationTest",
    size = "small",
    runtime_deps = [
        ":tests",
    ],
)
