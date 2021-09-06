""" Base images. """

load("@io_bazel_rules_docker//container:container.bzl", "container_pull")

def base_images():
    """define base images."""

    # https://hub.docker.com/r/azul/zulu-openjdk-alpine/tags?name=16-jre
    container_pull(
        name = "java16_jre",
        architecture = "amd64",
        digest = "sha256:d323c348f27b9458ec88fd69947560314df0fe059ff7acb3055f96b28f466a4d",
        os = "linux",
        registry = "registry-1.docker.io",
        repository = "azul/zulu-openjdk-alpine",
        tag = "16-jre",
    )

    # https://hub.docker.com/r/azul/zulu-openjdk-debian/tags?name=16
    container_pull(
        name = "java16_jdk",
        architecture = "amd64",
        digest = "sha256:b52f50a0f16869513906c3931298a05078144e050c84646b1cf4745f8c7042ee",
        os = "linux",
        registry = "registry-1.docker.io",
        repository = "azul/zulu-openjdk-debian",
        tag = "16",
    )
