workspace(name = "de_melsicon_kafka_sensors")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

# ---

http_archive(
    name = "io_bazel_rules_go",
    sha256 = "52d0a57ea12139d727883c2fef03597970b89f2cc2a05722c42d1d7d41ec065b",
    urls = [
        "https://github.com/bazelbuild/rules_go/releases/download/v0.24.13/rules_go-v0.24.13.tar.gz",
        "https://mirror.bazel.build/github.com/bazelbuild/rules_go/releases/download/v0.24.13/rules_go-v0.24.13.tar.gz",
    ],
)

http_archive(
    name = "io_bazel_rules_docker",
    sha256 = "c15ef66698f5d2122a3e875c327d9ecd34a231a9dc4753b9500e70518464cc21",
    strip_prefix = "rules_docker-7da0de3d094aae5601c45ae0855b64fb2771cd72",
    urls = ["https://github.com/bazelbuild/rules_docker/archive/7da0de3d094aae5601c45ae0855b64fb2771cd72.tar.gz"],
)

http_archive(
    name = "rules_jvm_external",
    sha256 = "31d226a6b3f5362b59d261abf9601116094ea4ae2aa9f28789b6c105e4cada68",
    strip_prefix = "rules_jvm_external-4.0",
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/4.0.tar.gz",
)

http_archive(
    name = "com_google_protobuf",
    sha256 = "b10bf4e2d1a7586f54e64a5d9e7837e5188fc75ae69e36f215eb01def4f9721b",
    strip_prefix = "protobuf-3.15.3",
    urls = ["https://github.com/protocolbuffers/protobuf/archive/v3.15.3.tar.gz"],
)

http_archive(
    name = "com_google_dagger",
    sha256 = "bdcec99530c0b5f154f17798302757c5bd41cd0ebb06b1167b202d450b4c7df5",
    strip_prefix = "dagger-dagger-2.33",
    urls = ["https://github.com/google/dagger/archive/dagger-2.33.tar.gz"],
)

# ---

load("@io_bazel_rules_go//go:deps.bzl", "go_register_toolchains", "go_rules_dependencies")

go_register_toolchains(go_version = "1.16")

go_rules_dependencies()

# ---

load("@io_bazel_rules_docker//repositories:repositories.bzl", container_repositories = "repositories")

container_repositories()

load("@io_bazel_rules_docker//repositories:deps.bzl", container_deps = "deps")

container_deps()

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

load("@com_google_dagger//:workspace_defs.bzl", "DAGGER_ARTIFACTS")

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
        "com.fasterxml.jackson.core:jackson-annotations:2.12.1",
        "com.fasterxml.jackson.core:jackson-core:2.12.1",
        "com.fasterxml.jackson.core:jackson-databind:2.12.1",
        "com.fasterxml.jackson.datatype:jackson-datatype-guava:2.12.1",
        "com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.12.1",
        "com.fasterxml.jackson.datatype:jackson-datatype-joda:2.12.1",
        "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.12.1",
        "com.fasterxml.jackson.module:jackson-module-parameter-names:2.12.1",
        "com.google.auto.service:auto-service-annotations:1.0-rc7",
        "com.google.auto.service:auto-service:1.0-rc7",
        "com.google.auto.value:auto-value-annotations:1.7.4",
        "com.google.auto.value:auto-value:1.7.4",
        "com.google.code.gson:gson:2.8.6",
        "com.google.errorprone:error_prone_annotations:2.5.1",
        "com.google.flogger:flogger-system-backend:0.5.1",
        "com.google.flogger:flogger:0.5.1",
        "com.google.guava:guava:30.1-jre",
        "com.uber.nullaway:nullaway:0.8.0",
        "info.picocli:picocli:4.6.1",
        "io.github.classgraph:classgraph:4.8.102",
        "io.helidon.config:helidon-config-object-mapping:2.2.1",
        "io.helidon.config:helidon-config-yaml:2.2.1",
        "io.helidon.config:helidon-config:2.2.1",
        "jakarta.annotation:jakarta.annotation-api:1.3.5",
        "jakarta.servlet:jakarta.servlet-api:4.0.4",
        "jakarta.validation:jakarta.validation-api:2.0.2",
        "jakarta.ws.rs:jakarta.ws.rs-api:2.1.6",
        "org.apache.kafka:kafka-clients:2.7.0",
        "org.apache.kafka:kafka-streams:2.7.0",
        "org.apache.kafka:kafka_2.13:2.7.0",
        "org.checkerframework:checker-qual:3.11.0",
        "org.checkerframework:checker:3.11.0",
        "org.mapstruct:mapstruct-processor:1.4.2.Final",
        "org.mapstruct:mapstruct:1.4.2.Final",
        "org.openjdk.jmh:jmh-core:1.28",
        "org.openjdk.jmh:jmh-generator-annprocess:1.28",
        "org.slf4j:slf4j-api:1.8.0-beta4",
        "org.slf4j:slf4j-jdk14:1.8.0-beta4",
        maven.artifact(
            "com.google.truth",
            "truth",
            "1.1.2",
            testonly = True,
        ),
        maven.artifact(
            "com.google.truth.extensions",
            "truth-java8-extension",
            "1.1.2",
            testonly = True,
        ),
        maven.artifact(
            "com.google.truth.extensions",
            "truth-liteproto-extension",
            "1.1.2",
            testonly = True,
        ),
        maven.artifact(
            "com.google.truth.extensions",
            "truth-proto-extension",
            "1.1.2",
            testonly = True,
        ),
        maven.artifact(
            "com.salesforce.kafka.test",
            "kafka-junit4",
            "3.2.2",
            testonly = True,
        ),
        maven.artifact(
            "junit",
            "junit",
            "4.13.2",
            testonly = True,
        ),
        maven.artifact(
            "org.apache.kafka",
            "kafka-streams-test-utils",
            "2.7.0",
            testonly = True,
        ),
        maven.artifact(
            "org.ow2.asm",
            "asm",
            "9.1",
            testonly = True,
        ),
    ] + DAGGER_ARTIFACTS + AVRO_ARTIFACTS + CONFLUENT_ARTIFACTS,
    fetch_sources = True,
    maven_install_json = "@de_melsicon_kafka_sensors//:maven_install.json",
    override_targets = {
        # Java EE is now Jakarta EE
        "javax.annotation:javax.annotation-api": ":jakarta_annotation_jakarta_annotation_api",
        "javax.servlet:javax.servlet-api": ":jakarta_servlet_jakarta_servlet_api",
        "javax.validation:validation-api": ":jakarta_validation_jakarta_validation_api",
        "javax.ws.rs:javax.ws.rs-api": ":jakarta_ws_rs_jakarta_ws_rs_api",
    },
    repositories = [
        "https://maven-central-eu.storage-download.googleapis.com/maven2/",
        "https://repo1.maven.org/maven2",
    ],
)

# ---

load("@maven//:defs.bzl", "pinned_maven_install")

pinned_maven_install()
