"""Base images."""

load("@io_bazel_rules_docker//container:container.bzl", "container_pull")

def base_images():
    """define base images."""

    # https://gcr.io/distroless/java-debian10:11-nonroot
    container_pull(
        name = "java_base",
        architecture = "amd64",
        digest = "sha256:8445a961fa1203ea20016cc73172cdba296ca8df49f79eed00060b85bf00263a",
        os = "linux",
        registry = "gcr.io",
        repository = "distroless/java-debian10",
        tag = "11-nonroot",
    )

    # https://gcr.io/distroless/java-debian10:11-debug-nonroot
    container_pull(
        name = "java_debug",
        architecture = "amd64",
        digest = "sha256:53f06d212a37194988bf5aa5fa92ef4a9da5c53a11fe50fa20c5ea9954154a72",
        os = "linux",
        registry = "gcr.io",
        repository = "distroless/java-debian10",
        tag = "11-debug-nonroot",
    )
