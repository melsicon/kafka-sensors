load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")
load("@bazel_tools//tools/build_defs/repo:jvm.bzl", "jvm_maven_import_external")

CONFLUENT_ARTIFACTS = [
    "com.damnhandy:handy-uri-templates:2.1.8",
    "com.google.re2j:re2j:1.6",
    "com.kjetland:mbknor-jackson-jsonschema_2.13:1.0.39",
    "com.squareup.wire:wire-schema:3.7.0",
    "commons-validator:commons-validator:1.7",
    "io.swagger:swagger-annotations:1.6.2",
    "org.json:json:20210307",
]

def confluent_repositories_common():
    jvm_maven_import_external(
        name = "com_github_everit_org_json_schema_org_everit_json_schema",
        licenses = ["notice"],  # Apache 2.0
        artifact = "com.github.everit-org.json-schema:org.everit.json.schema:1.12.2",
        artifact_sha256 = "b8df44b274c3230bdc004f138f4a820cb86ea09a42d1ca9f5074dd432aeea198",
        srcjar_sha256 = "b740f5df19b7c5ba99c368962821558591be869b5ee39b69f77d0f6c7a8d7b84",
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
        sha256 = "9bead7e105dee3af107279514d1fbec34adc212be1e6336e4a8191f65ff95fb6",
        strip_prefix = "common-6.1.1",
        urls = ["https://github.com/confluentinc/common/archive/v6.1.1.tar.gz"],
    )
    http_archive(
        name = "confluent_schema_registry",
        build_file = "//:rules_confluent/schema_registry.bzl",
        sha256 = "cd7f63395e99eb45e8d74626564a7af4d60c3f981ea72d9f271798af062238c8",
        strip_prefix = "schema-registry-6.1.1",
        urls = ["https://github.com/confluentinc/schema-registry/archive/v6.1.1.tar.gz"],
    )
