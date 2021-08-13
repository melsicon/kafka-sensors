load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

JMH_ARTIFACTS = [
    "org.apache.commons:commons-math3:3.6.1",
    "net.sf.jopt-simple:jopt-simple:5.0.4",
]

def jmh_repositories():
    http_archive(
        name = "jmh",
        build_file = "//third_party/jmh:build.jmh.bazel",
        sha256 = "8246708df59eac9b815cd3540404338ddadd8272103d2985eee546d1115a861a",
        strip_prefix = "jmh-267d5869884378fa19a126732bbdfcc9516533c1",
        urls = ["https://github.com/eikemeier/jmh/archive/267d5869884378fa19a126732bbdfcc9516533c1.tar.gz"],
    )
