load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

def confluent_repositories():
    http_archive(
        name = "confluent_common",
        build_file = "//:rules_confluent/common.bzl",
        sha256 = "93cae10a72531bab75df7ce7a414291ac1e571dab3aa515667c38f9a370de632",
        strip_prefix = "common-5.3.1",
        urls = ["https://github.com/confluentinc/common/archive/v5.3.1.tar.gz"],
    )
    http_archive(
        name = "confluent_schema_registry",
        build_file = "//:rules_confluent/schema_registry.bzl",
        sha256 = "e1fc9fffabb26c4e873cc7a2fd6d31336a2e84100028ea6476e8dee869202929",
        strip_prefix = "schema-registry-5.3.1",
        urls = ["https://github.com/confluentinc/schema-registry/archive/v5.3.1.tar.gz"],
    )
