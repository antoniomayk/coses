package io.antoniomayk.coses.service

import io.antoniomayk.coses.dto.TaskInfoDto
import io.antoniomayk.coses.dto.TaskStatusDto
import io.antoniomayk.coses.entity.Task
import io.antoniomayk.coses.repository.TaskRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class TaskService(
    val taskRepository: TaskRepository
) {
    private val logger = KotlinLogging.logger {}

    fun findById(id: UUID): Optional<Task> {
        logger.info { "Get task by id - id: $id" }

        return taskRepository.findById(id)
    }

    fun findAll(): MutableIterable<Task> {
        logger.info { "Get all tasks" }

        return taskRepository.findAll()
    }

    fun findAllByCompleted(completed: Boolean): MutableIterable<Task> {
        logger.info { "Get all tasks - completed: $completed" }

        return taskRepository.findAllByCompleted(completed)
    }

    fun save(taskInfoDto: TaskInfoDto): Task {
        logger.info { "Create task - task: $taskInfoDto" }

        return taskRepository.save(
            Task(
                id = null,
                title = taskInfoDto.title,
                content = taskInfoDto.content,
                completed = false,
                completedAt = null,
                createdAt = null,
                modifiedAt = null,
            )
        )
    }

    fun update(taskInfoDto: TaskInfoDto, id: UUID): Optional<Task> {
        logger.info { "Update task - id: $id" }

        val optionalTask = taskRepository.findById(id)

        if (optionalTask.isPresent)
            logger.info { "Task found - task: $optionalTask" }
        else
            logger.info { "Task not found - id: $id" }

        return optionalTask.map {
            it.title = taskInfoDto.title
            it.content = taskInfoDto.content

            return@map taskRepository.save(it)
        }
    }

    fun updateStatus(taskStatusDto: TaskStatusDto, id: UUID): Optional<Task> {
        logger.info { "Update task status - id: $id" }

        val optionalTask = taskRepository.findById(id)

        if (optionalTask.isPresent)
            logger.info { "Task found - task: $optionalTask" }
        else
            logger.info { "Task not found for id - id: $id" }

        return optionalTask.map {
            it.completed = taskStatusDto.completed
            it.completedAt = if (it.completed) Instant.now() else null

            return@map taskRepository.save(it)
        }
    }

    fun delete(id: UUID) {
        logger.info { "Delete task - id: $id" }

        taskRepository.deleteById(id)
    }
}
