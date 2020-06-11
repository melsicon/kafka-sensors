workspace(name = "de_melsicon_kafka_sensors")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

# ---

http_archive(
    name = "io_bazel_rules_go",
    sha256 = "a8d6b1b354d371a646d2f7927319974e0f9e52f73a2452d2b3877118169eb6bb",
    urls = [
        "https://github.com/bazelbuild/rules_go/releases/download/v0.23.3/rules_go-v0.23.3.tar.gz",
        "https://mirror.bazel.build/github.com/bazelbuild/rules_go/releases/download/v0.23.3/rules_go-v0.23.3.tar.gz",
    ],
)

http_archive(
    name = "io_bazel_rules_docker",
    sha256 = "6287241e033d247e9da5ff705dd6ef526bac39ae82f3d17de1b69f8cb313f9cd",
    strip_prefix = "rules_docker-0.14.3",
    urls = ["https://github.com/bazelbuild/rules_docker/releases/download/v0.14.3/rules_docker-v0.14.3.tar.gz"],
)

http_archive(
    name = "rules_jvm_external",
    sha256 = "19d402ef15f58750352a1a38b694191209ebc7f0252104b81196124fdd43ffa0",
    strip_prefix = "rules_jvm_external-3.2",
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/3.2.tar.gz",
)

http_archive(
    name = "com_google_protobuf",
    sha256 = "71030a04aedf9f612d2991c1c552317038c3c5a2b578ac4745267a45e7037c29",
    strip_prefix = "protobuf-3.12.3",
    urls = ["https://github.com/protocolbuffers/protobuf/archive/v3.12.3.tar.gz"],
)

# ---

load("@io_bazel_rules_go//go:deps.bzl", "go_register_toolchains", "go_rules_dependencies")

go_register_toolchains()

go_rules_dependencies()

# ---

load("@io_bazel_rules_docker//repositories:repositories.bzl", container_repositories = "repositories")

container_repositories()

load("@io_bazel_rules_docker//go:image.bzl", go_repositories = "repositories")

go_repositories()

# ---

load("@rules_jvm_external//:defs.bzl", "maven_install")
load("@rules_jvm_external//:specs.bzl", "maven")

# ---

load("@com_google_protobuf//:protobuf_deps.bzl", "protobuf_deps")

protobuf_deps()

bind(
    name = "error_prone_annotations",
    actual = "@maven//:com_google_errorprone_error_prone_annotations",
)

bind(
    name = "gson",
    actual = "@maven//:com_google_code_gson_gson",
)

bind(
    name = "guava",
    actual = "@maven//:com_google_guava_guava",
)

# ---

load("//rules_avro:avro_deps.bzl", "AVRO_ARTIFACTS")

# ---

load("//rules_confluent:repositories.bzl", "CONFLUENT_ARTIFACTS", "confluent_repositories")

confluent_repositories()

# ---

load("//images:images.bzl", "base_images")

base_images()

# ---

maven_install(
    artifacts = [
        "com.fasterxml.jackson.core:jackson-annotations:2.11.0",
        "com.fasterxml.jackson.core:jackson-core:2.11.0",
        "com.fasterxml.jackson.core:jackson-databind:2.11.0",
        "com.fasterxml.jackson.datatype:jackson-datatype-guava:2.11.0",
        "com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.11.0",
        "com.fasterxml.jackson.datatype:jackson-datatype-joda:2.11.0",
        "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.11.0",
        "com.fasterxml.jackson.module:jackson-module-parameter-names:2.11.0",
        "com.google.auto.service:auto-service-annotations:1.0-rc7",
        "com.google.auto.service:auto-service:1.0-rc7",
        "com.google.auto.value:auto-value-annotations:1.7.3",
        "com.google.auto.value:auto-value:1.7.3",
        "com.google.code.gson:gson:2.8.6",
        "com.google.dagger:dagger-compiler:2.28",
        "com.google.dagger:dagger:2.28",
        "com.google.errorprone:error_prone_annotations:2.4.0",
        "com.google.flogger:flogger-system-backend:0.5.1",
        "com.google.flogger:flogger:0.5.1",
        "com.google.guava:guava:29.0-jre",
        "com.uber.nullaway:nullaway:0.7.10",
        "info.picocli:picocli:4.3.2",
        "io.github.classgraph:classgraph:4.8.85",
        "io.helidon.config:helidon-config-object-mapping:2.0.0-RC1",
        "io.helidon.config:helidon-config-yaml:2.0.0-RC1",
        "io.helidon.config:helidon-config:2.0.0-RC1",
        "jakarta.annotation:jakarta.annotation-api:1.3.5",
        "jakarta.servlet:jakarta.servlet-api:4.0.3",
        "jakarta.validation:jakarta.validation-api:2.0.2",
        "jakarta.ws.rs:jakarta.ws.rs-api:2.1.6",
        "org.apache.kafka:kafka-clients:2.5.0",
        "org.apache.kafka:kafka-streams:2.5.0",
        "org.apache.kafka:kafka_2.12:2.5.0",
        "org.checkerframework:checker-qual:3.4.1",
        "org.checkerframework:checker:3.4.1",
        "org.mapstruct:mapstruct-processor:1.3.1.Final",
        "org.mapstruct:mapstruct:1.3.1.Final",
        "org.openjdk.jmh:jmh-core:1.23",
        "org.openjdk.jmh:jmh-generator-annprocess:1.23",
        "org.slf4j:slf4j-api:1.7.30",
        "org.slf4j:slf4j-jdk14:1.7.30",
        maven.artifact(
            "com.google.truth",
            "truth",
            "1.0.1",
            testonly = True,
        ),
        maven.artifact(
            "com.google.truth.extensions",
            "truth-java8-extension",
            "1.0.1",
            testonly = True,
        ),
        maven.artifact(
            "com.google.truth.extensions",
            "truth-liteproto-extension",
            "1.0.1",
            testonly = True,
        ),
        maven.artifact(
            "com.google.truth.extensions",
            "truth-proto-extension",
            "1.0.1",
            testonly = True,
        ),
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
            "2.5.0",
            testonly = True,
        ),
        maven.artifact(
            "org.ow2.asm",
            "asm",
            "8.0.1",
            testonly = True,
        ),
    ] + AVRO_ARTIFACTS + CONFLUENT_ARTIFACTS,
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
