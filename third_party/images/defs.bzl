""" Base images. """

load("@io_bazel_rules_docker//container:container.bzl", "container_pull")

def base_images():
    """define base images."""

    # https://hub.docker.com/r/azul/zulu-openjdk-alpine/tags?name=16-jre
    container_pull(
        name = "java16_jre",
        architecture = "amd64",
        digest = "sha256:1a23722961def965e840779b7c1c1e07e83ec080089c82befe9cb23ab0fd8655",
        os = "linux",
        registry = "registry-1.docker.io",
        repository = "azul/zulu-openjdk-alpine",
        tag = "16-jre",
    )

    # https://hub.docker.com/r/azul/zulu-openjdk-debian/tags?name=16
    container_pull(
        name = "java16_jdk",
        architecture = "amd64",
        digest = "sha256:ff591e9d377ee2052a66c104ee1bec6be360c84e31982799864b5a82fe7d6446",
        os = "linux",
        registry = "registry-1.docker.io",
        repository = "azul/zulu-openjdk-debian",
        tag = "16",
    )
