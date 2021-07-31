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
        digest = "sha256:9abd9cf15ec4229fcf0b612d4463f4de1d18db61996371c74fa7afc4a94342a3",
        os = "linux",
        registry = "gcr.io",
        repository = "distroless/java-debian10",
        tag = "11-debug-nonroot",
    )
