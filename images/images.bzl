"""Base images."""

load("@io_bazel_rules_docker//container:container.bzl", "container_pull")

def base_images():
    """define base images."""

    # https://gcr.io/distroless/java-debian10:11-nonroot
    container_pull(
        name = "java_base",
        architecture = "amd64",
        digest = "sha256:df0b28dc0f224f2c8f3606bfbb2a03de387670d1649fb549a98eaa0ea58b7dcb",
        os = "linux",
        registry = "gcr.io",
        repository = "distroless/java-debian10",
        tag = "11-nonroot",
    )

    # https://gcr.io/distroless/java-debian10:11-debug-nonroot
    container_pull(
        name = "java_debug",
        architecture = "amd64",
        digest = "sha256:9abd9cf15ec4229fcf0b612d4463f4de1d18db61996371c74fa7afc4a94342a3",
        os = "linux",
        registry = "gcr.io",
        repository = "distroless/java-debian10",
        tag = "11-debug-nonroot",
    )
