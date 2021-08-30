""" Confluent dependencies. """

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")
load("@bazel_tools//tools/build_defs/repo:jvm.bzl", "jvm_maven_import_external")

CONFLUENT_ARTIFACTS = [
    "com.damnhandy:handy-uri-templates:2.1.8",
    "com.google.api.grpc:proto-google-common-protos:2.4.1",
    "com.google.re2j:re2j:1.6",
    "com.kjetland:mbknor-jackson-jsonschema_2.13:1.0.39",
    "com.squareup.wire:wire-schema:3.7.0",
    "commons-validator:commons-validator:1.7",
    "io.swagger:swagger-annotations:1.6.2",
    "org.jetbrains.kotlin:kotlin-stdlib:1.5.20",
    "org.json:json:20210307",
    "com.squareup.wire:wire-runtime:3.7.0",
]

def confluent_repositories_common():
    jvm_maven_import_external(
        name = "com_github_everit_org_json_schema_org_everit_json_schema",
        artifact = "com.github.everit-org.json-schema:org.everit.json.schema:1.13.0",
        artifact_sha256 = "0846364c58924b3c6ace88a98c5ce40faa6a4a066a66a7b6bb55b604b153b531",
        fetch_sources = True,
        licenses = ["notice"],  # Apache 2.0
        server_urls = ["https://jitpack.io/"],
        srcjar_sha256 = "47dbf9c479aa6ccf1553b913522ce323e7c33a5e132ea8715765c3a086a08dbc",
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
        build_file = "//third_party/confluent:BUILD.common.bazel",
        sha256 = "07777053a3d788e723d52f3614795c5a3a56b984fb2c964f9746bf6c6c586ac4",
        strip_prefix = "common-6.2.0",
        urls = ["https://github.com/confluentinc/common/archive/v6.2.0.tar.gz"],
    )
    http_archive(
        name = "confluent_schema_registry",
        build_file = "//third_party/confluent:BUILD.schema_registry.bazel",
        sha256 = "0fccbee1da56722ec196b8cde38b9b92e194c1147996a4f158a635b8e07f5f95",
        strip_prefix = "schema-registry-6.2.0",
        urls = ["https://github.com/confluentinc/schema-registry/archive/v6.2.0.tar.gz"],
    )
