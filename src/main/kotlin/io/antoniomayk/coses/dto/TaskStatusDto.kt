package io.antoniomayk.coses.dto

import io.swagger.v3.oas.annotations.media.Schema

data class TaskStatusDto(
    @Schema(example = "true") val completed: Boolean
)
