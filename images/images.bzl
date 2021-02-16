"""Base images."""

load("@io_bazel_rules_docker//container:container.bzl", "container_pull")

def base_images():
    """define base images."""

    # https://gcr.io/distroless/java-debian10:11-nonroot
    container_pull(
        name = "java_base",
        architecture = "amd64",
        digest = "sha256:5d2fde2e0dd012fbdae16c2ee0f475463dcb4068514b8921841ea683d2ba68e7",
        os = "linux",
        registry = "gcr.io",
        repository = "distroless/java-debian10",
        tag = "11-nonroot",
    )

    # https://gcr.io/distroless/java-debian10:11-debug-nonroot
    container_pull(
        name = "java_debug",
        architecture = "amd64",
        digest = "sha256:b8fbad69fdcdf4dc9f4aaf52e4789ffaa42f86640c20e09d4e72408988b27430",
        os = "linux",
        registry = "gcr.io",
        repository = "distroless/java-debian10",
        tag = "11-debug-nonroot",
    )
