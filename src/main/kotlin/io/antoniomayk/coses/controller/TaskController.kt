package io.antoniomayk.coses.controller

import io.antoniomayk.coses.dto.TaskInfoDto
import io.antoniomayk.coses.dto.TaskStatusDto
import io.antoniomayk.coses.entity.Task
import io.antoniomayk.coses.error.SpringErrorPayload
import io.antoniomayk.coses.service.TaskService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/tasks")
class TaskController(
    val taskService: TaskService
) {
    private val logger = KotlinLogging.logger {}

    @Operation(summary = "Get a specific task by its ID", description = "Returns a task as per the id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Successfully retrieved",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = Task::class)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Task was not found",
                content = [Content(
                    schema = Schema()
                )]
            )
        ]
    )
    @GetMapping("/{id}")
    fun getTask(@PathVariable id: UUID): ResponseEntity<Task> {
        return taskService.findById(id)
            .map {
                logger.info("Task found - id: $id")

                ResponseEntity.ok(it)
            }.orElseGet {
                logger.info("Task not found - id: $id")

                ResponseEntity.notFound().build()
            }
    }

    @Operation(summary = "Retrieve all tasks", description = "Returns all tasks")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Successfully retrieved", content = [Content(
                    mediaType = "application/json",
                    array = ArraySchema(schema = Schema(implementation = Task::class), uniqueItems = true),
                )]
            ),
        ]
    )
    @GetMapping
    fun getAllTasks(@RequestParam completed: Boolean?): ResponseEntity<List<Task>> {
        if (completed != null) {
            return ResponseEntity.ok(taskService.findAllByCompleted(completed).toList())
        }

        return ResponseEntity.ok(taskService.findAll().toList())
    }

    @Operation(summary = "Create a new task", description = "Returns a newly created task")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Successfully created", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = Task::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Request body is invalid",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = SpringErrorPayload::class)
                )]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal server error", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = SpringErrorPayload::class)
                )]
            ),
        ]
    )
    @PostMapping
    fun createTask(@RequestBody taskInfoDto: TaskInfoDto): ResponseEntity<Task> {
        return ResponseEntity.ok(taskService.save(taskInfoDto))
    }

    @Operation(summary = "Update task information", description = "Returns the updated task")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successfully updated"),
            ApiResponse(
                responseCode = "400", description = "Request body is invalid", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = SpringErrorPayload::class)
                )]
            ),
            ApiResponse(
                responseCode = "404", description = "Task was not found",
                content = [Content(
                    schema = Schema()
                )]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal server error", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = SpringErrorPayload::class)
                )]
            ),
        ]
    )
    @PutMapping("/{id}")
    fun updateTask(@RequestBody taskInfoDto: TaskInfoDto, @PathVariable id: UUID): ResponseEntity<Task> {
        return taskService.update(taskInfoDto, id)
            .map {
                logger.info { "Task updated - id: $id" }

                ResponseEntity.ok(it)
            }.orElseGet {
                ResponseEntity.notFound().build()
            }
    }

    @Operation(summary = "Update task status only", description = "Returns the updated task")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successfully updated"),
            ApiResponse(
                responseCode = "400", description = "Request body is invalid", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = SpringErrorPayload::class)
                )]
            ),
            ApiResponse(
                responseCode = "404", description = "Task was not found",
                content = [Content(
                    schema = Schema()
                )]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal server error", content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = SpringErrorPayload::class)
                )]
            ),
        ]
    )
    @PutMapping("/{id}/status")
    fun updateTaskStatus(@RequestBody taskStatusDto: TaskStatusDto, @PathVariable id: UUID): ResponseEntity<Task> {
        return taskService.updateStatus(taskStatusDto, id)
            .map {
                logger.info { "Task status updated - id: $id" }

                ResponseEntity.ok(it)
            }.orElseGet {
                ResponseEntity.notFound().build()
            }
    }

    @Operation(summary = "Delete a specific task", description = "")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204", description = "Successfully deleted",
                content = [Content(
                    schema = Schema()
                )]
            ),
        ]
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTask(@PathVariable id: UUID) {
        taskService.delete(id)
    }
}
