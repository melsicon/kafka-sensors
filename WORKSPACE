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
    sha256 = "19d402ef15f58750352a1a38b694191209ebc7f0252104b81196124fdd43ffa0",
    strip_prefix = "rules_jvm_external-3.2",
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/3.2.tar.gz",
)

load("@rules_jvm_external//:defs.bzl", "maven_install")
load("@rules_jvm_external//:specs.bzl", "maven")

# ---

http_archive(
    name = "com_google_protobuf",
    sha256 = "a79d19dcdf9139fa4b81206e318e33d245c4c9da1ffed21c87288ed4380426f9",
    strip_prefix = "protobuf-3.11.4",
    urls = ["https://github.com/protocolbuffers/protobuf/archive/v3.11.4.tar.gz"],
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
        "com.fasterxml.jackson.core:jackson-annotations:2.10.3",
        "com.fasterxml.jackson.core:jackson-core:2.10.3",
        "com.fasterxml.jackson.core:jackson-databind:2.10.3",
        "com.fasterxml.jackson.datatype:jackson-datatype-guava:2.10.3",
        "com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.10.3",
        "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.10.3",
        "com.github.spotbugs:spotbugs-annotations:4.0.0",
        "com.google.auto.service:auto-service-annotations:1.0-rc6",
        "com.google.auto.service:auto-service:1.0-rc6",
        "com.google.auto.value:auto-value-annotations:1.7",
        "com.google.auto.value:auto-value:1.7",
        "com.google.dagger:dagger-compiler:2.27",
        "com.google.dagger:dagger:2.27",
        "com.google.errorprone:error_prone_annotations:2.3.4",
        "com.google.flogger:flogger-system-backend:0.4",
        "com.google.flogger:flogger:0.4",
        "com.google.guava:guava:28.2-jre",
        "com.uber.nullaway:nullaway:0.7.9",
        "info.picocli:picocli:4.2.0",
        "io.github.classgraph:classgraph:4.8.65",
        "io.helidon.config:helidon-config-object-mapping:2.0.0-M1",
        "io.helidon.config:helidon-config-yaml:2.0.0-M1",
        "io.helidon.config:helidon-config:2.0.0-M1",
        "io.swagger:swagger-annotations:1.6.0",
        "jakarta.annotation:jakarta.annotation-api:1.3.5",
        "jakarta.servlet:jakarta.servlet-api:4.0.3",
        "jakarta.validation:jakarta.validation-api:2.0.2",
        "jakarta.ws.rs:jakarta.ws.rs-api:2.1.6",
        "org.apache.kafka:kafka-clients:2.4.1",
        "org.apache.kafka:kafka-streams:2.4.1",
        "org.apache.kafka:kafka_2.13:2.4.1",
        "org.mapstruct:mapstruct-processor:1.3.1.Final",
        "org.mapstruct:mapstruct:1.3.1.Final",
        "org.openjdk.jmh:jmh-core:1.23",
        "org.openjdk.jmh:jmh-generator-annprocess:1.23",
        "org.slf4j:slf4j-jdk14:1.7.30",
        maven.artifact(
            "com.salesforce.kafka.test",
            "kafka-junit4",
            "3.2.1",
            testonly = True,
        ),
        maven.artifact(
            "junit",
            "junit",
            "4.13",
            testonly = True,
        ),
        maven.artifact(
            "org.apache.kafka",
            "kafka-streams-test-utils",
            "2.4.1",
            testonly = True,
        ),
        maven.artifact(
            "org.assertj",
            "assertj-core",
            "3.15.0",
            testonly = True,
        ),
        maven.artifact(
            "org.jetbrains",
            "annotations",
            "19.0.0",
            neverlink = True,
        ),
    ] + AVRO_ARTIFACTS,
    fetch_sources = True,
    maven_install_json = "@de_melsicon_kafka_sensors//:maven_install.json",
    override_targets = {
        # Java EE is now Jakarta EE
        "javax.annotation:javax.annotation-api": ":jakarta_annotation_jakarta_annotation_api",
        "javax.servlet:javax.servlet-api": "jakarta_servlet_jakarta_servlet_api",
        "javax.validation:validation-api": ":jakarta_validation_jakarta_validation_api",
        "javax.ws.rs:javax.ws.rs-api": ":jakarta_ws_rs_jakarta_ws_rs_api",
    },
    repositories = [
        "https://jcenter.bintray.com/",
        "https://repo1.maven.org/maven2/",
    ],
)

# ---

load("@maven//:defs.bzl", "pinned_maven_install")

pinned_maven_install()
