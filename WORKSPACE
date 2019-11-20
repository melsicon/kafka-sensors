workspace(name = "de_melsicon_kafka_sensors")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

# ---

http_archive(
    name = "rules_pkg",
    sha256 = "4ba8f4ab0ff85f2484287ab06c0d871dcb31cc54d439457d28fd4ae14b18450a",
    url = "https://github.com/bazelbuild/rules_pkg/releases/download/0.2.4/rules_pkg-0.2.4.tar.gz",
)

load("@rules_pkg//:deps.bzl", "rules_pkg_dependencies")

rules_pkg_dependencies()

# ---

http_archive(
    name = "rules_jvm_external",
    sha256 = "5c1b22eab26807d5286ada7392d796cbc8425d3ef9a57d114b79c5f8ef8aca7c",
    strip_prefix = "rules_jvm_external-2.10",
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/2.10.tar.gz",
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

# ---

http_archive(
    name = "com_google_protobuf",
    sha256 = "6adf73fd7f90409e479d6ac86529ade2d45f50494c5c10f539226693cb8fe4f7",
    strip_prefix = "protobuf-3.10.1",
    urls = ["https://github.com/protocolbuffers/protobuf/archive/v3.10.1.tar.gz"],
)

load("@com_google_protobuf//:protobuf_deps.bzl", "protobuf_deps")

protobuf_deps()

# ---

load("//rules_avro:avro_deps.bzl", "AVRO_ARTIFACTS")

# ---

load("//rules_confluent:repositories.bzl", "confluent_repositories")

confluent_repositories()

# ---

maven_install(
    artifacts = [
        "com.fasterxml.jackson.core:jackson-annotations:2.10.1",
        "com.fasterxml.jackson.core:jackson-core:2.10.1",
        "com.fasterxml.jackson.core:jackson-databind:2.10.1",
        "com.fasterxml.jackson.datatype:jackson-datatype-guava::2.10.1",
        "com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.10.1",
        "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.10.1",
        "com.google.auto.service:auto-service-annotations:1.0-rc6",
        "com.google.auto.service:auto-service:1.0-rc6",
        "com.google.auto.value:auto-value-annotations:1.7",
        "com.google.auto.value:auto-value:1.7",
        "com.google.dagger:dagger-compiler:2.25.2",
        "com.google.dagger:dagger:2.25.2",
        "com.google.flogger:flogger-system-backend:0.4",
        "com.google.flogger:flogger:0.4",
        "com.salesforce.kafka.test:kafka-junit4:3.2.0",
        "com.uber.nullaway:nullaway:0.7.9",
        "info.picocli:picocli:4.0.4",
        "io.github.classgraph:classgraph:4.8.47",
        "io.helidon.config:helidon-config-object-mapping:1.3.1",
        "io.helidon.config:helidon-config-yaml:1.3.1",
        "io.helidon.config:helidon-config:1.3.1",
        "io.swagger:swagger-annotations:1.5.24",
        "jakarta.annotation:jakarta.annotation-api:1.3.5",
        "junit:junit:4.13-rc-1",
        "org.apache.kafka:kafka-clients:2.3.1",
        "org.apache.kafka:kafka-streams-test-utils:2.3.1",
        "org.apache.kafka:kafka-streams:2.3.1",
        "org.apache.kafka:kafka_2.12:2.3.1",
        "org.assertj:assertj-core:3.14.0",
        "org.mapstruct:mapstruct-processor:1.3.1.Final",
        "org.mapstruct:mapstruct:1.3.1.Final",
        "org.slf4j:slf4j-jdk14:1.7.29",
    ] + AVRO_ARTIFACTS,
    # fetch_sources = True,
    maven_install_json = "@de_melsicon_kafka_sensors//:maven_install.json",
    override_targets = {
        # Java EE is now Jakarta EE
        "javax.annotation:javax.annotation-api": ":jakarta_annotation_jakarta_annotation_api",
    },
    repositories = [
        "https://jcenter.bintray.com/",
        "https://repo1.maven.org/maven2/",
    ],
)

# ---

load("@maven//:defs.bzl", "pinned_maven_install")

pinned_maven_install()
