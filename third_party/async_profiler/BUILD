load("@bazel_skylib//lib:selects.bzl", "selects")

selects.config_setting_group(
    name = "supports_profiling",
    match_any = [
        "@bazel_tools//src/conditions:darwin_x86_64",
        "@bazel_tools//src/conditions:darwin_arm64",
        "@bazel_tools//src/conditions:linux_x86_64",
        "@bazel_tools//src/conditions:linux_aarch64",
    ],
)

alias(
    name = "async_profiler",
    actual = select(
        {
            "@bazel_tools//src/conditions:darwin_x86_64": "@async_profiler_macos//:async_profiler",
            "@bazel_tools//src/conditions:darwin_arm64": "@async_profiler_macos_aarch64//:async_profiler",
            "@bazel_tools//src/conditions:linux_x86_64": "@async_profiler_linux//:async_profiler",
            "@bazel_tools//src/conditions:linux_aarch64": "@async_profiler_linux_aarch64//:async_profiler",
        },
        no_match_error = "Profiling not supported for host execution environment.",
    ),
    visibility = ["//visibility:public"],
)

alias(
    name = "async_profiler_ext",
    actual = select(
        {
            "@bazel_tools//src/conditions:darwin_x86_64": "@async_profiler_macos//:async_profiler_ext",
            "@bazel_tools//src/conditions:darwin_arm64": "@async_profiler_macos_aarch64//:async_profiler_ext",
            "@bazel_tools//src/conditions:linux_x86_64": "@async_profiler_linux//:async_profiler_ext",
            "@bazel_tools//src/conditions:linux_aarch64": "@async_profiler_linux_aarch64//:async_profiler_ext",
        },
        no_match_error = "Profiling not supported for host execution environment.",
    ),
    visibility = ["//visibility:public"],
)
