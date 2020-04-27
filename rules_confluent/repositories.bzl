load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")
load("@bazel_tools//tools/build_defs/repo:jvm.bzl", "jvm_maven_import_external")

CONFLUENT_ARTIFACTS = [
    "com.damnhandy:handy-uri-templates:2.1.8",
    "com.google.re2j:re2j:1.3",
    "com.kjetland:mbknor-jackson-jsonschema_2.13:1.0.39",
    "com.squareup.wire:wire-schema:3.2.0",
    "commons-validator:commons-validator:1.6",
    "org.json:json:20190722",
]

def confluent_repositories_common():
    jvm_maven_import_external(
        name = "org_everit_json_schema",
        artifact = "com.github.everit-org.json-schema:org.everit.json.schema:1.12.1",
        artifact_sha256 = "2444eadc87f6ad45b7434435f936bcfa9847fa52158f744b1f038d13d9aaec8e",
        server_urls = [
            "https://jitpack.io/",
        ],
    )

def confluent_repositories():
    confluent_repositories_common()
    http_archive(
        name = "confluent_common",
        build_file = "//:rules_confluent/common.bzl",
        sha256 = "9d45f98e63106920bde8bb53986f974c629ca01f4156f2f16ebf64df71b5fc07",
        strip_prefix = "common-5.5.0",
        urls = ["https://github.com/confluentinc/common/archive/v5.5.0.tar.gz"],
    )
    http_archive(
        name = "confluent_schema_registry",
        build_file = "//:rules_confluent/schema_registry.bzl",
        sha256 = "424156b79b633547a7b41c31ccd49ec5909c867e9b9a2975bb06dd95ad5d1c5a",
        strip_prefix = "schema-registry-5.5.0",
        urls = ["https://github.com/confluentinc/schema-registry/archive/v5.5.0.tar.gz"],
    )

def confluent_repositories_beta():
    confluent_repositories_common()
    http_archive(
        name = "confluent_common",
        build_file = "//:rules_confluent/common.bzl",
        sha256 = "dccb316ccd5b6a349c6d79e60354bfed51cead296902398b5335c19b58f21f46",
        strip_prefix = "common-6.0.0-beta200416181138",
        urls = ["https://github.com/confluentinc/common/archive/v6.0.0-beta200416181138.tar.gz"],
    )
    http_archive(
        name = "confluent_schema_registry",
        build_file = "//:rules_confluent/schema_registry.bzl",
        sha256 = "04360533c664fa9e7cd85c5735a5058040a70a86c8af17ec4c4fe7ef0fa19fb0",
        strip_prefix = "schema-registry-6.0.0-beta200416181138",
        urls = ["https://github.com/confluentinc/schema-registry/archive/v6.0.0-beta200416181138.tar.gz"],
    )
