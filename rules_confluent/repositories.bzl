load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")
load("@bazel_tools//tools/build_defs/repo:jvm.bzl", "jvm_maven_import_external")

CONFLUENT_ARTIFACTS = [
    "com.damnhandy:handy-uri-templates:2.1.8",
    "com.google.re2j:re2j:1.4",
    "com.kjetland:mbknor-jackson-jsonschema_2.12:1.0.39",
    "com.squareup.wire:wire-schema:3.2.2",
    "commons-validator:commons-validator:1.6",
    "io.swagger:swagger-annotations:1.6.1",
    "org.json:json:20200518",
]

def confluent_repositories_common():
    jvm_maven_import_external(
        name = "com_github_everit_org_json_schema_org_everit_json_schema",
        artifact = "com.github.everit-org.json-schema:org.everit.json.schema:1.12.1",
        artifact_sha256 = "2444eadc87f6ad45b7434435f936bcfa9847fa52158f744b1f038d13d9aaec8e",
        srcjar_sha256 = "5add4371ecfb0125d9b7ae88804ac39e10c43dd5bc431182b52f368ccc2c5e8b",
        fetch_sources = True,
        server_urls = ["https://jitpack.io/"],
        deps = [
            "@maven//:com_damnhandy_handy_uri_templates",
            "@maven//:com_google_re2j_re2j",
            "@maven//:commons_validator_commons_validator",
            "@maven//:org_json_json",
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
        sha256 = "52cfe3e0cc242e2fc88af5170d069ccbb627d7c0e65369561a1bd9ec28423ddf",
        strip_prefix = "common-6.0.0-6",
        urls = ["https://github.com/confluentinc/common/archive/v6.0.0-6.tar.gz"],
    )
    http_archive(
        name = "confluent_schema_registry",
        build_file = "//:rules_confluent/schema_registry.bzl",
        sha256 = "4004ed2c478f660d750eb5c5153f791e431ef204d33bad43381873610206223f",
        strip_prefix = "schema-registry-6.0.0-6",
        urls = ["https://github.com/confluentinc/schema-registry/archive/v6.0.0-6.tar.gz"],
    )
