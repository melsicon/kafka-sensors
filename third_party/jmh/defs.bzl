""" JMH dependencies. """

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

JMH_ARTIFACTS = [
    "org.apache.commons:commons-math3:3.6.1",
    "net.sf.jopt-simple:jopt-simple:5.0.4",
]

def jmh_repositories():
    http_archive(
        name = "jmh",
        build_file = "//third_party/jmh:BUILD.jmh.bazel",
        sha256 = "ff1a136e454ca5012f4683257fc8258e8c03cdc1dc47a49f76c1c466c57481a8",
        strip_prefix = "jmh-6f151470c8db1d8f2d3cd0e4d1ac24c314b461a4",
        urls = ["https://github.com/openjdk/jmh/archive/6f151470c8db1d8f2d3cd0e4d1ac24c314b461a4.tar.gz"],
    )
