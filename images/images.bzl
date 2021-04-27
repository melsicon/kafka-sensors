"""Base images."""

load("@io_bazel_rules_docker//container:container.bzl", "container_pull")

def base_images():
    """define base images."""

    # https://gcr.io/distroless/java-debian10:11-nonroot
    container_pull(
        name = "java_base",
        architecture = "amd64",
        digest = "sha256:07d017944edb06f3c5351fdc82e55025c315fe312625c843bc25a0715e0c0a39",
        os = "linux",
        registry = "gcr.io",
        repository = "distroless/java-debian10",
        tag = "11-nonroot",
    )

    # https://gcr.io/distroless/java-debian10:11-debug-nonroot
    container_pull(
        name = "java_debug",
        architecture = "amd64",
        digest = "sha256:92b7b58e3ec748019d4c914135319e82b0e174ff97e7ee47e77857fb86538f05",
        os = "linux",
        registry = "gcr.io",
        repository = "distroless/java-debian10",
        tag = "11-debug-nonroot",
    )
