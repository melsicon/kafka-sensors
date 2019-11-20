load("@bazel_tools//tools/jdk:default_java_toolchain.bzl", "default_java_toolchain")
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
        "-Xlint:all,-processing,-try,-classfile",
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
        "-XepOpt:NullAway:ExcludedFieldAnnotations=picocli.CommandLine.Option",
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
        "//src/main/java/de/melsicon/annotation",
        "@maven//:com_google_code_findbugs_jsr305",
    ],
)

# ---

java_plugin(
    name = "autovalue_processor",
    processor_class = "com.google.auto.value.processor.AutoValueProcessor",
    deps = ["@maven//:com_google_auto_value_auto_value"],
)

java_library(
    name = "autovalue",
    exported_plugins = ["autovalue_processor"],
    neverlink = True,
    visibility = ["//visibility:public"],
    exports = [
        "@maven//:com_google_auto_value_auto_value_annotations",
        "@maven//:jakarta_annotation_jakarta_annotation_api",
    ],
)

# ---

java_plugin(
    name = "dagger_processor",
    processor_class = "dagger.internal.codegen.ComponentProcessor",
    deps = ["@maven//:com_google_dagger_dagger_compiler"],
)

java_library(
    name = "dagger",
    exported_plugins = ["dagger_processor"],
    visibility = ["//visibility:public"],
    exports = ["@maven//:com_google_dagger_dagger"],
)

# ---

java_plugin(
    name = "mapstruct_processor",
    processor_class = "org.mapstruct.ap.MappingProcessor",
    deps = ["@maven//:org_mapstruct_mapstruct_processor"],
)

java_library(
    name = "mapstruct",
    exported_plugins = ["mapstruct_processor"],
    visibility = ["//visibility:public"],
    exports = ["@maven//:org_mapstruct_mapstruct"],
)

# ---

java_plugin(
    name = "autoservice_processor",
    processor_class = "com.google.auto.service.processor.AutoServiceProcessor",
    deps = ["@maven//:com_google_auto_service_auto_service"],
)

java_library(
    name = "autoservice",
    exported_plugins = ["autoservice_processor"],
    neverlink = True,
    visibility = ["//visibility:public"],
    exports = ["@maven//:com_google_auto_service_auto_service_annotations"],
)
