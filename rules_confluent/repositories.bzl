load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

def confluent_repositories():
    http_archive(
        name = "confluent_common",
        build_file = "//:rules_confluent/common.bzl",
        sha256 = "435bfa2bc2586afa3bacc36dc4eefcd20581414f2d9802b570609c1c888f37b9",
        strip_prefix = "common-5.4.1",
        urls = ["https://github.com/confluentinc/common/archive/v5.4.1.tar.gz"],
    )
    http_archive(
        name = "confluent_schema_registry",
        build_file = "//:rules_confluent/schema_registry.bzl",
        sha256 = "254cdcbc4bd1c0549e3fa4b4fe617b2dcd61e1aa6e5d9c1f6a74e702524ff701",
        strip_prefix = "schema-registry-5.4.1",
        urls = ["https://github.com/confluentinc/schema-registry/archive/v5.4.1.tar.gz"],
    )

def confluent_repositories_beta():
    http_archive(
        name = "confluent_common",
        build_file = "//:rules_confluent/common.bzl",
        sha256 = "2e5c7ccac3f69e44b064672d4b1d192dfaa5f4f05a7d8ba14985b04eff00806d",
        strip_prefix = "common-6.0.0-beta200305045734",
        urls = ["https://github.com/confluentinc/common/archive/v6.0.0-beta200305045734.tar.gz"],
    )
    http_archive(
        name = "confluent_schema_registry",
        build_file = "//:rules_confluent/schema_registry.bzl",
        sha256 = "2189e77fc73899786c053e65aeeb32d226f1409a271609c48bcc767d1d0de905",
        strip_prefix = "schema-registry-6.0.0-beta200305045734",
        urls = ["https://github.com/confluentinc/schema-registry/archive/v6.0.0-beta200305045734.tar.gz"],
    )
