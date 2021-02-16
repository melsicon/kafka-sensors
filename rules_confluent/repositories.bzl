load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")
load("@bazel_tools//tools/build_defs/repo:jvm.bzl", "jvm_maven_import_external")

CONFLUENT_ARTIFACTS = [
    "com.damnhandy:handy-uri-templates:2.1.8",
    "com.google.re2j:re2j:1.5",
    "com.kjetland:mbknor-jackson-jsonschema_2.13:1.0.39",
    "com.squareup.wire:wire-schema:3.2.2",  # Do not upgrade - used by kafka-protobuf-provider
    "commons-validator:commons-validator:1.7",
    "io.swagger:swagger-annotations:1.6.2",
    "org.json:json:20201115",
]

def confluent_repositories_common():
    jvm_maven_import_external(
        name = "com_github_everit_org_json_schema_org_everit_json_schema",
        licenses = ["notice"],  # Apache 2.0
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
        sha256 = "89149a5e170135a709d5c78ced83f2bcccdf271b94d62eb9855063df0cee1317",
        strip_prefix = "common-6.1.0",
        urls = ["https://github.com/confluentinc/common/archive/v6.1.0.tar.gz"],
    )
    http_archive(
        name = "confluent_schema_registry",
        build_file = "//:rules_confluent/schema_registry.bzl",
        sha256 = "1bbb8f45be5a57055d7e2b5fdfd638f7e53ec9ef3b1b8d5842352e90e964890e",
        strip_prefix = "schema-registry-6.1.0",
        patches = ["//:rules_confluent/schema_registry.patch"],
        urls = ["https://github.com/confluentinc/schema-registry/archive/v6.1.0.tar.gz"],
    )
