workspace(name = "de_melsicon_kafka_sensors")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

# ---

http_archive(
    name = "io_bazel_rules_go",
    sha256 = "e0015762cdeb5a2a9c48f96fb079c6a98e001d44ec23ad4fa2ca27208c5be4fb",
    urls = [
        "https://github.com/bazelbuild/rules_go/releases/download/v0.24.14/rules_go-v0.24.14.tar.gz",
        "https://mirror.bazel.build/github.com/bazelbuild/rules_go/releases/download/v0.24.14/rules_go-v0.24.14.tar.gz",
    ],
)

http_archive(
    name = "io_bazel_rules_docker",
    sha256 = "59d5b42ac315e7eadffa944e86e90c2990110a1c8075f1cd145f487e999d22b3",
    strip_prefix = "rules_docker-0.17.0",
    urls = ["https://github.com/bazelbuild/rules_docker/releases/download/v0.17.0/rules_docker-v0.17.0.tar.gz"],
)

http_archive(
    name = "rules_jvm_external",
    sha256 = "995ea6b5f41e14e1a17088b727dcff342b2c6534104e73d6f06f1ae0422c2308",
    strip_prefix = "rules_jvm_external-4.1",
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/4.1.tar.gz",
)

http_archive(
    name = "com_google_protobuf",
    sha256 = "036d66d6eec216160dd898cfb162e9d82c1904627642667cc32b104d407bb411",
    strip_prefix = "protobuf-3.17.1",
    urls = ["https://github.com/protocolbuffers/protobuf/archive/v3.17.1.tar.gz"],
)

http_archive(
    name = "com_google_dagger",
    sha256 = "15dd24cf713b5b5110e2f0ca1708ceaa44bab89c6bfca547806980c00c4393e0",
    strip_prefix = "dagger-dagger-2.36",
    urls = ["https://github.com/google/dagger/archive/dagger-2.36.tar.gz"],
)

http_archive(
    name = "io_bazel_rules_scala",
    sha256 = "e749a8ade22828419e734e2fb94d8af747bcae1b35c1b664eff1f2dc35c1ab76",
    strip_prefix = "rules_scala-2b7edf77c153f3fbb882005e0f199f95bd322880",
    url = "https://github.com/bazelbuild/rules_scala/archive/2b7edf77c153f3fbb882005e0f199f95bd322880.tar.gz",
)

# ---

load("@io_bazel_rules_go//go:deps.bzl", "go_register_toolchains", "go_rules_dependencies")

go_register_toolchains(go_version = "1.16.2")

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

load("@io_bazel_rules_scala//:scala_config.bzl", "scala_config")

scala_config(scala_version = "2.13.3")

load("@io_bazel_rules_scala//scala:scala.bzl", "scala_repositories")

scala_repositories()

load("@io_bazel_rules_scala//scala:toolchains.bzl", "scala_register_toolchains")

scala_register_toolchains()

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
        "com.amazon.ion:ion-java:1.8.2",
        "com.fasterxml.jackson.core:jackson-annotations:2.12.3",
        "com.fasterxml.jackson.core:jackson-core:2.12.3",
        "com.fasterxml.jackson.core:jackson-databind:2.12.3",
        "com.fasterxml.jackson.datatype:jackson-datatype-guava:2.12.3",
        "com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.12.3",
        "com.fasterxml.jackson.datatype:jackson-datatype-joda:2.12.3",
        "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.12.3",
        "com.fasterxml.jackson.module:jackson-module-parameter-names:2.12.3",
        "com.google.auto.service:auto-service-annotations:1.0",
        "com.google.auto.service:auto-service:1.0",
        "com.google.auto.value:auto-value-annotations:1.8.1",
        "com.google.auto.value:auto-value:1.8.1",
        "com.google.code.gson:gson:2.8.7",
        "com.google.errorprone:error_prone_annotations:2.7.1",
        "com.google.flogger:flogger-system-backend:0.6",
        "com.google.flogger:flogger:0.6",
        "com.google.guava:guava:30.1.1-jre",
        "info.picocli:picocli:4.6.1",
        "io.github.classgraph:classgraph:4.8.106",
        "io.helidon.config:helidon-config-object-mapping:2.3.0",
        "io.helidon.config:helidon-config-yaml:2.3.0",
        "io.helidon.config:helidon-config:2.3.0",
        "jakarta.annotation:jakarta.annotation-api:1.3.5",
        "jakarta.servlet:jakarta.servlet-api:4.0.4",
        "jakarta.validation:jakarta.validation-api:2.0.2",
        "jakarta.ws.rs:jakarta.ws.rs-api:2.1.6",
        "org.apache.kafka:kafka-clients:2.8.0",
        "org.apache.kafka:kafka-streams:2.8.0",
        "org.apache.kafka:kafka_2.13:2.8.0",
        "org.checkerframework:checker-compat-qual:2.5.5",
        "org.checkerframework:checker-qual:3.13.0",
        "org.checkerframework:checker:3.13.0",
        "org.mapstruct:mapstruct-processor:1.4.2.Final",
        "org.mapstruct:mapstruct:1.4.2.Final",
        "org.openjdk.jmh:jmh-core:1.32",
        "org.openjdk.jmh:jmh-generator-annprocess:1.32",
        "org.slf4j:slf4j-api:1.8.0-beta4",
        "org.slf4j:slf4j-jdk14:1.8.0-beta4",
        maven.artifact(
            "com.google.truth",
            "truth",
            "1.1.3",
            testonly = True,
        ),
        maven.artifact(
            "com.google.truth.extensions",
            "truth-java8-extension",
            "1.1.3",
            testonly = True,
        ),
        maven.artifact(
            "com.google.truth.extensions",
            "truth-liteproto-extension",
            "1.1.3",
            testonly = True,
        ),
        maven.artifact(
            "com.google.truth.extensions",
            "truth-proto-extension",
            "1.1.3",
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
            "2.8.0",
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
        "https://maven-central-eu.storage-download.googleapis.com/maven2",
        "https://repo1.maven.org/maven2",
    ],
)

# ---

load("@maven//:defs.bzl", "pinned_maven_install")

pinned_maven_install()
