"""Base images."""

load("@io_bazel_rules_docker//container:container.bzl", "container_pull")

def base_images():
    """define base images."""

    # https://gcr.io/distroless/java-debian10:11
    container_pull(
        name = "java_base",
        architecture = "amd64",
        digest = "sha256:7e57b3476ae69407b26f39e2d1d78b54efef4db177104e1e3de7e9b5198827a9",
        os = "linux",
        registry = "gcr.io",
        repository = "distroless/java-debian10",
        tag = "11",
    )

    # https://gcr.io/distroless/java-debian10:11-debug
    container_pull(
        name = "java_debug",
        architecture = "amd64",
        digest = "sha256:04290be4a52109bbabf0054c55d99cf5dea9e8cfe1098c50cb17a3955a9ee520",
        os = "linux",
        registry = "gcr.io",
        repository = "distroless/java-debian10",
        tag = "11-debug",
    )
