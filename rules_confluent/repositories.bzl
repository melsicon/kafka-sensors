load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

def confluent_repositories():
    http_archive(
        name = "confluent_common",
        build_file = "//:rules_confluent/common.bzl",
        # sha256 = "4f0aa47bfb92b500e17b57f34b68be8aabf9a6861a56d9c55cdd0d49024bc200",
        # strip_prefix = "common-5.3.2",
        # urls = ["https://github.com/confluentinc/common/archive/v5.3.2.tar.gz"],
        sha256 = "eb3a2521c7062bd823c60f2bb4f63c77269d982ccf6d712aecdbead13540dbf9",
        strip_prefix = "common-1398d9effff9564bc897123b032281b80077ed9b",
        urls = ["https://github.com/confluentinc/common/archive/1398d9effff9564bc897123b032281b80077ed9b.tar.gz"],
    )
    http_archive(
        name = "confluent_schema_registry",
        build_file = "//:rules_confluent/schema_registry.bzl",
        # sha256 = "3e7f48af7b33ac9db2ed0d9d9600ab8e08361b8e19e016503768aba82b3569a1",
        # strip_prefix = "schema-registry-5.3.2",
        # urls = ["https://github.com/confluentinc/schema-registry/archive/v5.3.2.tar.gz"],
        sha256 = "89feeb4edd3ae78de1a396e4d1a71b1e739199bdb33c4b724e7854c6b84421fa",
        strip_prefix = "schema-registry-702286182079583602c64e5ab219721bfe8fe074",
        urls = ["https://github.com/confluentinc/schema-registry/archive/702286182079583602c64e5ab219721bfe8fe074.tar.gz"],
    )
