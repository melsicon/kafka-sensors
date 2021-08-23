""" async-profiler dependencies. """

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

def async_profiler_repositories():
    http_archive(
        name = "async_profiler_linux",
        build_file = "//third_party/async_profiler:BUILD.async_profiler.bazel",
        sha256 = "f2b5b6bcd001b55f1ba61b40d4d391155567483b842caf8f4ca207b7d9bfb949",
        strip_prefix = "async-profiler-2.0-linux-x64",
        urls = ["https://github.com/jvm-profiling-tools/async-profiler/releases/download/v2.0/async-profiler-2.0-linux-x64.tar.gz"],
    )

    http_archive(
        name = "async_profiler_linux_aarch64",
        build_file = "//third_party/async_profiler:BUILD.async_profiler.bazel",
        sha256 = "5210f8074c3c1bc41e721a042f4eb4fff4d4852ab8cf3c48a8d541f2b50a196f",
        strip_prefix = "async-profiler-2.0-linux-aarch64",
        urls = ["https://github.com/jvm-profiling-tools/async-profiler/releases/download/v2.0/async-profiler-2.0-linux-aarch64.tar.gz"],
    )

    http_archive(
        name = "async_profiler_macos",
        build_file = "//third_party/async_profiler:BUILD.async_profiler.bazel",
        sha256 = "1b7f8437bd1cdbd0ac120f971fe7cf3102a4624dc4cbfca995598033b6685f47",
        strip_prefix = "async-profiler-2.0-macos-x64",
        urls = ["https://github.com/jvm-profiling-tools/async-profiler/releases/download/v2.0/async-profiler-2.0-macos-x64.tar.gz"],
    )

    http_archive(
        name = "async_profiler_macos_aarch64",
        build_file = "//third_party/async_profiler:BUILD.async_profiler.bazel",
        sha256 = "0c10478c0ed3fa603033a0507d75f67eb0272031154e27b90cdb9062b287419e",
        strip_prefix = "async-profiler-2.1-ea-macos-aarch64",
        urls = ["https://github.com/jvm-profiling-tools/async-profiler/releases/download/v2.1-ea/async-profiler-2.1-ea-macos-aarch64.zip"],
    )
