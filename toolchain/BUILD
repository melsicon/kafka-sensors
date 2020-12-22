load("@bazel_tools//tools/jdk:default_java_toolchain.bzl", "default_java_toolchain")
load("@com_google_dagger//:workspace_defs.bzl", "dagger_rules")
load("@rules_java//java:defs.bzl", "java_library", "java_package_configuration", "java_plugin")

default_java_toolchain(
    name = "toolchain_java11",
    package_configuration = [
        ":package_configuration",
    ],
    source_version = "11",
    target_version = "11",
    visibility = ["//visibility:public"],
)

java_package_configuration(
    name = "package_configuration",
    javacopts = [
        "-Xlint:all,-processing,-cast",
        "-Werror",
        "-Xpkginfo:always",
        "-XepExcludedPaths:.*/bazel-out/.*",
        "-XepDisableWarningsInGeneratedCode",
        "-XepAllDisabledChecksAsWarnings",
        "-Xep:Java7ApiChecker:OFF",
        "-Xep:AndroidJdkLibsChecker:OFF",
        "-Xep:StaticOrDefaultInterfaceMethod:OFF",
        "-Xep:NoFunctionalReturnType:OFF",
        "-Xep:WildcardImport:OFF",
        "-Xep:NullAway:ERROR",
        "-XepOpt:NullAway:AnnotatedPackages=de.melsicon",
        "-XepOpt:NullAway:TreatGeneratedAsUnannotated",
        "-XepOpt:NullAway:CheckOptionalEmptiness",
        "-XepOpt:NullAway:AcknowledgeRestrictiveAnnotations",
        "-XepOpt:NullAway:CustomInitializerAnnotations=org.junit.Before,org.junit.BeforeClass",
        "-XepOpt:NullAway:ExcludedFieldAnnotations=picocli.CommandLine.Option,org.openjdk.jmh.annotations.Param",
    ],
    packages = [":package_group"],
)

package_group(
    name = "package_group",
    packages = [
        "//src/...",
    ],
)

# ---

java_plugin(
    name = "nullaway_processor",
    deps = ["@maven//:com_uber_nullaway_nullaway"],
)

java_library(
    name = "nullaway",
    exported_plugins = ["nullaway_processor"],
    neverlink = True,
    visibility = ["//visibility:public"],
    exports = [
        "@maven//:org_checkerframework_checker_qual",
    ],
)

# ---

java_plugin(
    name = "auto_value_processor",
    processor_class = "com.google.auto.value.processor.AutoValueProcessor",
    deps = ["@maven//:com_google_auto_value_auto_value"],
)

java_plugin(
    name = "auto_oneof_processor",
    processor_class = "com.google.auto.value.processor.AutoOneOfProcessor",
    deps = ["@maven//:com_google_auto_value_auto_value"],
)

java_plugin(
    name = "auto_annotation_processor",
    processor_class = "com.google.auto.value.processor.AutoAnnotationProcessor",
    deps = ["@maven//:com_google_auto_value_auto_value"],
)

java_library(
    name = "autovalue",
    exported_plugins = [
        "auto_value_processor",
        "auto_oneof_processor",
        "auto_annotation_processor",
    ],
    neverlink = True,
    visibility = ["//visibility:public"],
    exports = [
        "@maven//:com_google_auto_value_auto_value_annotations",
        "@maven//:jakarta_annotation_jakarta_annotation_api",
    ],
)

# ---

dagger_rules("@maven")

# ---

java_plugin(
    name = "auto_service_processor",
    processor_class = "com.google.auto.service.processor.AutoServiceProcessor",
    deps = ["@maven//:com_google_auto_service_auto_service"],
)

java_library(
    name = "autoservice",
    exported_plugins = ["auto_service_processor"],
    neverlink = True,
    visibility = ["//visibility:public"],
    exports = ["@maven//:com_google_auto_service_auto_service_annotations"],
)

# ---

java_plugin(
    name = "checker_processor",
    processor_class = "org.checkerframework.checker.nullness.NullnessChecker",
    deps = ["@maven//:org_checkerframework_checker"],
)

java_library(
    name = "checker",
    exported_plugins = ["checker_processor"],
    neverlink = True,
    visibility = ["//visibility:public"],
    exports = ["@maven//:org_checkerframework_checker_qual"],
)

# ---

java_plugin(
    name = "jmh_processor",
    processor_class = "org.openjdk.jmh.generators.BenchmarkProcessor",
    deps = ["@maven//:org_openjdk_jmh_jmh_generator_annprocess"],
)

java_library(
    name = "jmh",
    exported_plugins = ["jmh_processor"],
    visibility = ["//visibility:public"],
    exports = ["@maven//:org_openjdk_jmh_jmh_core"],
)

# ---

java_library(
    name = "truth",
    testonly = True,
    visibility = ["//visibility:public"],
    exports = [
        "@maven//:com_google_truth_extensions_truth_java8_extension",
        "@maven//:com_google_truth_extensions_truth_proto_extension",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
        "@maven//:org_ow2_asm_asm",
    ],
)
