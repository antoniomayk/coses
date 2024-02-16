package io.antoniomayk.coses.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.antoniomayk.coses.dto.TaskInfoDto
import io.antoniomayk.coses.dto.TaskStatusDto
import io.antoniomayk.coses.entity.Task
import io.antoniomayk.coses.service.TaskService
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Instant
import java.util.*

@WebMvcTest(controllers = [TaskController::class])
class TaskControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockkBean
    private lateinit var taskService: TaskService

    @Test
    fun `List all tasks`() {
        val mutableList =
            mutableListOf(
                Task(
                    id = UUID.randomUUID(),
                    title = "Title",
                    content = "Content",
                    completed = true,
                    completedAt = Instant.now(),
                    createdAt = Instant.now(),
                    modifiedAt = Instant.now(),
                )
            )

        every { taskService.findAll() } returns mutableList

        mockMvc.perform(get("/api/v1/tasks"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(mutableList)))

        verify(exactly = 1, verifyBlock = { taskService.findAll() })
    }

    @Test
    fun `Get task by UUID`() {
        val task =
            Task(
                id = UUID.randomUUID(),
                title = "Title",
                content = "Content",
                completed = true,
                completedAt = Instant.now(),
                createdAt = Instant.now(),
                modifiedAt = Instant.now(),
            )

        every { taskService.findById(task.id!!) } returns Optional.of(task)

        mockMvc.perform(get("/api/v1/tasks/${task.id}"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(task)))

        verify(exactly = 1, verifyBlock = { taskService.findById(task.id!!) })
    }

    @Test
    fun `List completed tasks`() {
        val completed = true
        val mutableList = mutableListOf(
            Task(
                id = UUID.randomUUID(),
                title = "Title 0",
                content = "Content",
                completed = completed,
                completedAt = Instant.now(),
                createdAt = Instant.now(),
                modifiedAt = Instant.now(),
            ),
            Task(
                id = UUID.randomUUID(),
                title = "Title 1",
                content = "Content",
                completed = completed,
                completedAt = Instant.now(),
                createdAt = Instant.now(),
                modifiedAt = Instant.now(),
            )
        )

        every { taskService.findAllByCompleted(completed) } returns mutableList

        mockMvc.perform(get("/api/v1/tasks?completed=$completed"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(mutableList)))

        verify(exactly = 1, verifyBlock = { taskService.findAllByCompleted(completed) })
    }

    @Test
    fun `List uncompleted tasks`() {
        val completed = false
        val mutableList = mutableListOf(
            Task(
                id = UUID.randomUUID(),
                title = "Title 0",
                content = "Content",
                completed = completed,
                completedAt = Instant.now(),
                createdAt = Instant.now(),
                modifiedAt = Instant.now(),
            ),
            Task(
                id = UUID.randomUUID(),
                title = "Title 1",
                content = "Content",
                completed = completed,
                completedAt = Instant.now(),
                createdAt = Instant.now(),
                modifiedAt = Instant.now(),
            )
        )

        every { taskService.findAllByCompleted(completed) } returns mutableList

        mockMvc.perform(get("/api/v1/tasks?completed=$completed"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(mutableList)))

        verify(exactly = 1, verifyBlock = { taskService.findAllByCompleted(completed) })
    }

    @Test
    fun `Should save and return newly created Task`() {
        val taskInfoDto =
            TaskInfoDto(
                title = "Title",
                content = "Content",
            )
        val task =
            Task(
                id = UUID.randomUUID(),
                title = taskInfoDto.title,
                content = taskInfoDto.content,
                completed = false,
                completedAt = null,
                createdAt = Instant.now(),
                modifiedAt = Instant.now(),
            )

        every { taskService.save(taskInfoDto) } returns task

        mockMvc.perform(
            post("/api/v1/tasks")
                .content(objectMapper.writeValueAsString(taskInfoDto))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(task)))

        verify(exactly = 1, verifyBlock = { taskService.save(taskInfoDto) })
    }

    @Test
    fun `Should update and return newly updated Task`() {
        val id = UUID.randomUUID()

        val taskInfoDto =
            TaskInfoDto(
                title = "Title",
                content = "Content",
            )
        val task =
            Task(
                id = id,
                title = taskInfoDto.title,
                content = taskInfoDto.content,
                completed = false,
                completedAt = null,
                createdAt = Instant.now(),
                modifiedAt = Instant.now(),
            )

        every { taskService.update(taskInfoDto, id) } returns Optional.of(task)

        mockMvc.perform(
            put("/api/v1/tasks/${id}")
                .content(objectMapper.writeValueAsString(taskInfoDto))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(task)))

        verify(exactly = 1, verifyBlock = { taskService.update(taskInfoDto, id) })
    }

    @Test
    fun `Should not update when id is non-existent`() {
        val id = UUID.randomUUID()

        val taskInfoDto =
            TaskInfoDto(
                title = "Title",
                content = "Content",
            )

        every { taskService.update(taskInfoDto, id) } returns Optional.empty()

        mockMvc.perform(
            put("/api/v1/tasks/${id}")
                .content(objectMapper.writeValueAsString(taskInfoDto))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isNotFound())

        verify(exactly = 1, verifyBlock = { taskService.update(taskInfoDto, id) })
    }

    @Test
    fun `Should update status and return newly updated Task`() {
        val id = UUID.randomUUID()

        val taskStatusDto =
            TaskStatusDto(
                completed = true
            )
        val task =
            Task(
                id = id,
                title = "Title",
                content = "Content",
                completed = true,
                completedAt = Instant.now(),
                createdAt = Instant.now(),
                modifiedAt = Instant.now(),
            )

        every { taskService.updateStatus(taskStatusDto, id) } returns Optional.of(task)

        mockMvc.perform(
            put("/api/v1/tasks/${id}/status")
                .content(objectMapper.writeValueAsString(taskStatusDto))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(task)))

        verify(exactly = 1, verifyBlock = { taskService.updateStatus(taskStatusDto, id) })
    }

    @Test
    fun `Should not update status when id is non-existent`() {
        val id = UUID.randomUUID()

        val taskStatusDto =
            TaskStatusDto(
                completed = true
            )

        every { taskService.updateStatus(taskStatusDto, id) } returns Optional.empty()

        mockMvc.perform(
            put("/api/v1/tasks/${id}/status")
                .content(objectMapper.writeValueAsString(taskStatusDto))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isNotFound())

        verify(exactly = 1, verifyBlock = { taskService.updateStatus(taskStatusDto, id) })
    }

    @Test
    fun `Should delete when task id is found`() {
        val id = UUID.randomUUID()

        every { taskService.delete(id) } returns Unit

        mockMvc.perform(delete("/api/v1/tasks/${id}"))
            .andDo(print())
            .andExpect(status().isNoContent())

        verify(exactly = 1, verifyBlock = { taskService.delete(id) })
    }
}
