load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "confluentmapper",
    srcs = glob(["*.java"]),
    javacopts = [
        "-Amapstruct.suppressGeneratorTimestamp=true",
        "-AskipDefs=MapperImpl$$",
    ],
    visibility = ["//src/main/java/de/melsicon/kafka/sensors/serde/context:__pkg__"],
    deps = [
        "//src/main/java/de/melsicon/kafka/sensors/model",
        "//src/main/java/de/melsicon/kafka/sensors/serde",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/avromapper",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/confluent_json",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/confluent_reflect",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/generic",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/logicaltypes",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/mapping",
        "//src/main/java/de/melsicon/kafka/sensors/serialization/mapping:mapstruct",
        "//toolchain:checker",
        "//toolchain:dagger",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:javax_inject_javax_inject",
        "@maven//:org_apache_avro_avro",
    ],
)
