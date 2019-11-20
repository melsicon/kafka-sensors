load("//rules_avro:avro.bzl", "avro_java_library")

avro_java_library(
    name = "avro",
    srcs = glob(["*.avsc"]),
    javacopts = ["-XepDisableAllChecks"],
    strings = True,
    visibility = ["//visibility:public"],
)
