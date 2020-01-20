load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

def confluent_repositories():
    http_archive(
        name = "confluent_common",
        build_file = "//:rules_confluent/common.bzl",
        sha256 = "98bdad07734391feb9f081c7170a279c6a77f52c3ae532f49fa9bb027a7c14ec",
        strip_prefix = "common-5.4.0",
        urls = ["https://github.com/confluentinc/common/archive/v5.4.0.tar.gz"],
    )
    http_archive(
        name = "confluent_schema_registry",
        build_file = "//:rules_confluent/schema_registry.bzl",
        sha256 = "6c2a19a27ee4b36fd1a2baecef36e2abde9b911af26a552a07808b5995caa692",
        strip_prefix = "schema-registry-5.4.0",
        urls = ["https://github.com/confluentinc/schema-registry/archive/v5.4.0.tar.gz"],
    )
