JAVA_BASE = select({
    "//toolchain:debug": "//third_party/images:java16_debug_base",
    "//toolchain:fastbuild": "//third_party/images:java16_base",
    "//toolchain:optimized": "//third_party/images:java16_base",
    "//conditions:default": "//third_party/images:java16_base",
})
