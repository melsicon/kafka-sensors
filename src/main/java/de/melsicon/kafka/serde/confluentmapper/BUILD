load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "confluentmapper",
    srcs = glob(["*.java"]),
    javacopts = ["-Amapstruct.suppressGeneratorTimestamp=true"],
    visibility = ["//visibility:public"],
    deps = [
        "//src/main/java/de/melsicon/kafka/model",
        "//src/main/java/de/melsicon/kafka/sensors/confluent_reflect",
        "//src/main/java/de/melsicon/kafka/sensors/generic",
        "//src/main/java/de/melsicon/kafka/serde",
        "//src/main/java/de/melsicon/kafka/serde/avromapper",
        "//src/main/java/de/melsicon/kafka/serde/mapping",
        "//src/main/java/de/melsicon/kafka/serde/mapping:mapstruct",
        "//toolchain:nullaway",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:javax_inject_javax_inject",
        "@maven//:org_apache_avro_avro",
    ],
)
