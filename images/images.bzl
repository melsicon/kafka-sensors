"""Base images."""

load("@io_bazel_rules_docker//container:container.bzl", "container_pull")

def base_images():
    """define base images."""

    # https://gcr.io/distroless/java-debian10:11-nonroot
    container_pull(
        name = "java_base",
        architecture = "amd64",
        digest = "sha256:51d2d3ceb469c7963a415a4cd37888ff6dec6f048e95b882a2755b91192cd392",
        os = "linux",
        registry = "gcr.io",
        repository = "distroless/java-debian10",
        tag = "11-nonroot",
    )

    # https://gcr.io/distroless/java-debian10:11-debug-nonroot
    container_pull(
        name = "java_debug",
        architecture = "amd64",
        digest = "sha256:3d7584e8cc32f4e00a8c196d3f1972c2d266ab04605051d3307f7d62ab591225",
        os = "linux",
        registry = "gcr.io",
        repository = "distroless/java-debian10",
        tag = "11-debug-nonroot",
    )

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

    # https://hub.docker.com/r/azul/zulu-openjdk-alpine/tags?name=16
    container_pull(
        name = "java16_jdk",
        architecture = "amd64",
        digest = "sha256:ca49bb1c7391d945c3c21dc18082a9dee70afcc71139e7a486574f5197ca26a6",
        os = "linux",
        registry = "registry-1.docker.io",
        repository = "azul/zulu-openjdk-alpine",
        tag = "16",
    )
