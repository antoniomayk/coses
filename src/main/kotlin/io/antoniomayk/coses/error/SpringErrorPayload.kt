package io.antoniomayk.coses.error

import io.swagger.v3.oas.annotations.media.Schema

data class SpringErrorPayload(
    @Schema(example = "0000-00-00T00:00:00.000+00:00") val timestamp: String,
    @Schema(example = "500") val status: Int,
    @Schema(example = "Internal Server Error") val error: String,
    @Schema(example = "java.lang.RuntimeException") val exception: String,
    @Schema(example = "Exception") val message: String,
    @Schema(example = "/api/v1/endpoint") val path: String,
)
