load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")
load("@bazel_tools//tools/build_defs/repo:jvm.bzl", "jvm_maven_import_external")

CONFLUENT_ARTIFACTS = [
    "com.damnhandy:handy-uri-templates:2.1.8",
    "com.google.re2j:re2j:1.3",
    "com.kjetland:mbknor-jackson-jsonschema_2.13:1.0.39",
    "com.squareup.wire:wire-schema:3.2.1",
    "commons-validator:commons-validator:1.6",
    "io.swagger:swagger-annotations:1.6.1",
    "org.json:json:20190722",
]

def confluent_repositories_common():
    jvm_maven_import_external(
        name = "org_everit_json_schema",
        artifact = "com.github.everit-org.json-schema:org.everit.json.schema:1.12.1",
        artifact_sha256 = "2444eadc87f6ad45b7434435f936bcfa9847fa52158f744b1f038d13d9aaec8e",
        srcjar_sha256 = "5add4371ecfb0125d9b7ae88804ac39e10c43dd5bc431182b52f368ccc2c5e8b",
        fetch_sources = True,
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
        sha256 = "71b6daec4128ed1a9ddf12640d05874bcecb1ddc7fdb596875537cbef71efd11",
        strip_prefix = "common-6.0.0-beta200503173959",
        urls = ["https://github.com/confluentinc/common/archive/v6.0.0-beta200503173959.tar.gz"],
    )
    http_archive(
        name = "confluent_schema_registry",
        build_file = "//:rules_confluent/schema_registry.bzl",
        sha256 = "1519ebb37619b7338652b251e28d55fa10d378525e3f3c7f5d6cc0700d48c4e5",
        strip_prefix = "schema-registry-6.0.0-beta200503173959",
        urls = ["https://github.com/confluentinc/schema-registry/archive/v6.0.0-beta200503173959.tar.gz"],
    )
