package io.antoniomayk.coses.dto

import io.swagger.v3.oas.annotations.media.Schema

data class TaskInfoDto(
    @Schema(example = "Renew gym membership") val title: String,
    @Schema(example = "Don't miss out! Renew gym membership before it expires") val content: String,
)
