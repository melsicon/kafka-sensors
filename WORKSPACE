workspace(name = "de_melsicon_kafka_sensors")

register_toolchains("//toolchain:toolchain_java16_definition")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

# ---

http_archive(
    name = "io_bazel_rules_go",
    sha256 = "8e968b5fcea1d2d64071872b12737bbb5514524ee5f0a4f54f5920266c261acb",
    urls = [
        "https://github.com/bazelbuild/rules_go/releases/download/v0.28.0/rules_go-v0.28.0.zip",
        "https://mirror.bazel.build/github.com/bazelbuild/rules_go/releases/download/v0.28.0/rules_go-v0.28.0.zip",
    ],
)

http_archive(
    name = "bazel_gazelle",
    sha256 = "62ca106be173579c0a167deb23358fdfe71ffa1e4cfdddf5582af26520f1c66f",
    urls = [
        "https://github.com/bazelbuild/bazel-gazelle/releases/download/v0.23.0/bazel-gazelle-v0.23.0.tar.gz",
        "https://mirror.bazel.build/github.com/bazelbuild/bazel-gazelle/releases/download/v0.23.0/bazel-gazelle-v0.23.0.tar.gz",
    ],
)

http_archive(
    name = "com_google_protobuf",
    sha256 = "7040ca8311c49847affb00040c104e118c7600f523fa859228847fc8fb618d6d",
    strip_prefix = "protobuf-3.18.0-rc2",
    urls = ["https://github.com/protocolbuffers/protobuf/archive/refs/tags/v3.18.0-rc2.tar.gz"],
)

http_archive(
    name = "com_github_bazelbuild_buildtools",
    sha256 = "e57212a61211e86ab31da77235931fc708705745b34754d0a15047a5d4ab648c",
    strip_prefix = "buildtools-5f69634d34899f1e410b88bc3468e1832e7227c4",
    url = "https://github.com/bazelbuild/buildtools/archive/5f69634d34899f1e410b88bc3468e1832e7227c4.tar.gz",
)

http_archive(
    name = "io_bazel_rules_docker",
    sha256 = "1f4e59843b61981a96835dc4ac377ad4da9f8c334ebe5e0bb3f58f80c09735f4",
    strip_prefix = "rules_docker-0.19.0",
    urls = ["https://github.com/bazelbuild/rules_docker/releases/download/v0.19.0/rules_docker-v0.19.0.tar.gz"],
)

http_archive(
    name = "rules_jvm_external",
    sha256 = "995ea6b5f41e14e1a17088b727dcff342b2c6534104e73d6f06f1ae0422c2308",
    strip_prefix = "rules_jvm_external-4.1",
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/4.1.tar.gz",
)

http_archive(
    name = "com_google_dagger",
    sha256 = "f763a42e418bcea094c4709e36ab06683b1a0b6edc8521b8f2e908d0c0b0706d",
    strip_prefix = "dagger-dagger-2.38.1",
    urls = ["https://github.com/google/dagger/archive/dagger-2.38.1.tar.gz"],
)

# ---

load("@io_bazel_rules_go//go:deps.bzl", "go_register_toolchains", "go_rules_dependencies")

go_register_toolchains(go_version = "1.17")

go_rules_dependencies()

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

load("@io_bazel_rules_docker//repositories:repositories.bzl", container_repositories = "repositories")

container_repositories()

load("@io_bazel_rules_docker//repositories:deps.bzl", container_deps = "deps")

container_deps()

load("@io_bazel_rules_docker//go:image.bzl", go_repositories = "repositories")

go_repositories()

# ---

load("@rules_jvm_external//:repositories.bzl", "rules_jvm_external_deps")

rules_jvm_external_deps()

load("@rules_jvm_external//:setup.bzl", "rules_jvm_external_setup")

rules_jvm_external_setup()

# ---

load("@com_google_dagger//:workspace_defs.bzl", "DAGGER_ARTIFACTS")

# ---

load("//third_party/avro:defs.bzl", "AVRO_ARTIFACTS")

# ---

load("//third_party/confluent:defs.bzl", "CONFLUENT_ARTIFACTS", "confluent_repositories")

confluent_repositories()

# ---

load("//third_party/jmh:defs.bzl", "JMH_ARTIFACTS", "jmh_repositories")

jmh_repositories()

# ---

load("//third_party/async_profiler:defs.bzl", "async_profiler_repositories")

async_profiler_repositories()

# ---

load("//third_party/images:defs.bzl", "base_images")

base_images()

# ---

load("@rules_jvm_external//:defs.bzl", "maven_install")
load("@rules_jvm_external//:specs.bzl", "maven")

maven_install(
    artifacts = [
        "com.amazon.ion:ion-java:1.8.3",
        "com.fasterxml.jackson.core:jackson-annotations:2.13.0-rc2",
        "com.fasterxml.jackson.core:jackson-core:2.13.0-rc2",
        "com.fasterxml.jackson.core:jackson-databind:2.13.0-rc2",
        "com.fasterxml.jackson.datatype:jackson-datatype-guava:2.13.0-rc2",
        "com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.13.0-rc2",
        "com.fasterxml.jackson.datatype:jackson-datatype-joda:2.13.0-rc2",
        "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.0-rc2",
        "com.fasterxml.jackson.module:jackson-module-parameter-names:2.13.0-rc2",
        "com.google.auto.service:auto-service-annotations:1.0",
        "com.google.auto.service:auto-service:1.0",
        "com.google.code.findbugs:jsr305:3.0.2",
        "com.google.code.gson:gson:2.8.8",
        "com.google.devtools.ksp:symbol-processing-api:1.5.30-1.0.0-beta09",
        "com.google.errorprone:error_prone_annotations:2.9.0",
        "com.google.flogger:flogger-system-backend:0.6",
        "com.google.flogger:flogger:0.6",
        "com.google.guava:guava:30.1.1-jre",
        "info.picocli:picocli:4.6.1",
        "io.github.classgraph:classgraph:4.8.115",
        "io.helidon.config:helidon-config-object-mapping:2.3.2",
        "io.helidon.config:helidon-config-yaml:2.3.2",
        "io.helidon.config:helidon-config:2.3.2",
        "jakarta.annotation:jakarta.annotation-api:1.3.5",
        "jakarta.servlet:jakarta.servlet-api:4.0.4",
        "jakarta.validation:jakarta.validation-api:2.0.2",
        "jakarta.ws.rs:jakarta.ws.rs-api:2.1.6",
        "javax.inject:javax.inject:1",
        "org.apache.kafka:kafka-clients:2.8.0",
        "org.apache.kafka:kafka-streams:2.8.0",
        "org.apache.kafka:kafka_2.13:2.8.0",
        "org.checkerframework:checker-qual:3.18.0",
        "org.checkerframework:checker:3.18.0",
        "org.immutables:gson:2.9.0-beta3",
        "org.immutables:value-annotations:2.9.0-beta3",
        "org.immutables:value-processor:2.9.0-beta3",
        "org.mapstruct:mapstruct-processor:1.5.0.Beta1",
        "org.mapstruct:mapstruct:1.5.0.Beta1",
        "org.scala-lang:scala-library:2.13.5",
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
            "junit",
            "junit",
            "4.13.2",
            testonly = True,
        ),
        maven.artifact(
            "net.mguenther.kafka",
            "kafka-junit",
            "2.8.0",
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
            "9.2",
            testonly = True,
        ),
    ] + DAGGER_ARTIFACTS + AVRO_ARTIFACTS + CONFLUENT_ARTIFACTS + JMH_ARTIFACTS,
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
        "https://repo1.maven.org/maven2",
        "https://repo.maven.apache.org/maven2",
        "https://maven-central-eu.storage-download.googleapis.com/maven2",
    ],
    strict_visibility = True,
)

# ---

load("@maven//:defs.bzl", "pinned_maven_install")

pinned_maven_install()
