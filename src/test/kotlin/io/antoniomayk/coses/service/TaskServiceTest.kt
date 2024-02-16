package io.antoniomayk.coses.service

import io.antoniomayk.coses.dto.TaskInfoDto
import io.antoniomayk.coses.dto.TaskStatusDto
import io.antoniomayk.coses.entity.Task
import io.antoniomayk.coses.repository.TaskRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*

class TaskServiceTest {
    private val taskRepository: TaskRepository = mockk()
    private val taskService: TaskService = TaskService(taskRepository)

    @Test
    fun `When findById then return Task`() {
        val id = UUID.randomUUID()
        val task =
            Task(
                id = id,
                title = "title",
                content = "content",
                completed = false,
                completedAt = null,
                createdAt = null,
                modifiedAt = null
            )


        every { taskRepository.findById(id) } returns Optional.of(task)

        assertEquals(taskService.findById(id), Optional.of(task))

        verify(exactly = 1, verifyBlock = { taskRepository.findById(id) })
    }

    @Test
    fun `When findAll then return all Tasks`() {
        val mutableList = mutableListOf(
            Task(
                id = null,
                title = "title",
                content = "content",
                completed = false,
                completedAt = null,
                createdAt = null,
                modifiedAt = null
            )
        )

        every { taskRepository.findAll() } returns mutableList

        assertEquals(taskService.findAll(), mutableList)

        verify(exactly = 1, verifyBlock = { taskRepository.findAll() })
    }

    @Test
    fun `When findAllByCompleted then return Tasks either completed or uncompleted`() {
        val mutableListFalse = mutableListOf(
            Task(
                id = null,
                title = "title",
                content = "content",
                completed = false,
                completedAt = null,
                createdAt = null,
                modifiedAt = null
            )
        )
        val mutableListTrue = mutableListOf(
            Task(
                id = null,
                title = "title",
                content = "content",
                completed = false,
                completedAt = null,
                createdAt = null,
                modifiedAt = null
            )
        )

        every { taskRepository.findAllByCompleted(false) } returns mutableListFalse
        every { taskRepository.findAllByCompleted(true) } returns mutableListTrue

        assertEquals(taskService.findAllByCompleted(false), mutableListFalse)
        assertEquals(taskService.findAllByCompleted(true), mutableListTrue)

        verify(exactly = 1, verifyBlock = { taskRepository.findAllByCompleted(true) })
        verify(exactly = 1, verifyBlock = { taskRepository.findAllByCompleted(false) })
    }

    @Test
    fun `When save then return newly created Task`() {
        abstract class AbstractTaskRepository : TaskRepository {
            override fun <S : Task?> save(entity: S & Any): S & Any {
                entity.completed = false

                return entity
            }
        }

        val abstractTaskRepository = spyk<AbstractTaskRepository>()
        val taskService = TaskService(abstractTaskRepository)

        val taskInfoDto =
            TaskInfoDto(
                title = "title",
                content = "content",
            )
        val task =
            Task(
                id = null,
                title = taskInfoDto.title,
                content = taskInfoDto.content,
                completed = false,
                completedAt = null,
                createdAt = null,
                modifiedAt = null
            )

        assertEquals(taskService.save(taskInfoDto), task)

        verify(exactly = 1, verifyBlock = { abstractTaskRepository.save(task) })
    }

    @Test
    fun `When update then return updated Task`() {
        abstract class AbstractTaskRepository : TaskRepository {
            override fun <S : Task?> save(entity: S & Any): S & Any {
                return entity
            }
        }

        val abstractTaskRepository = spyk<AbstractTaskRepository>()
        val taskService = TaskService(abstractTaskRepository)

        val id = UUID.randomUUID()

        val taskInfoDto =
            TaskInfoDto(
                title = "title",
                content = "content",
            )

        val oldTask =
            Task(
                id = id,
                title = "old title",
                content = "old content",
                completed = false,
                completedAt = null,
                createdAt = null,
                modifiedAt = null
            )
        val newTask = Task(
            id = id,
            title = taskInfoDto.title,
            content = taskInfoDto.content,
            completed = false,
            completedAt = null,
            createdAt = null,
            modifiedAt = null
        )

        every { abstractTaskRepository.findById(id) } returns Optional.of(oldTask)
        every { abstractTaskRepository.save(newTask) } answers { callOriginal() }

        assertEquals(taskService.update(taskInfoDto, id), Optional.of(newTask))

        verify(exactly = 1, verifyBlock = { abstractTaskRepository.findById(id) })
        verify(exactly = 1, verifyBlock = { abstractTaskRepository.save(newTask) })
    }

    @Test
    fun `When updateStatus then return updated Task`() {
        abstract class AbstractTaskRepository : TaskRepository {
            override fun <S : Task?> save(entity: S & Any): S & Any {
                return entity
            }
        }

        val abstractTaskRepository = spyk<AbstractTaskRepository>()
        val taskService = TaskService(abstractTaskRepository)

        val id = UUID.randomUUID()

        val taskStatusDto =
            TaskStatusDto(
                completed = true
            )
        val task =
            Task(
                id = id,
                title = "old title",
                content = "old content",
                completed = false,
                completedAt = null,
                createdAt = null,
                modifiedAt = null
            )

        every { abstractTaskRepository.findById(id) } returns Optional.of(task)
        every { abstractTaskRepository.save(task) } answers { callOriginal() }

        assertTrue(taskService.updateStatus(taskStatusDto, id).get().completed)

        verify(exactly = 1, verifyBlock = { abstractTaskRepository.findById(id) })
        verify(exactly = 1, verifyBlock = { abstractTaskRepository.save(task) })
    }

    @Test
    fun `When delete then return nothing`() {
        val id = UUID.randomUUID()

        every { taskRepository.deleteById(id) } returns Unit

        taskService.delete(id)

        verify(exactly = 1, verifyBlock = { taskRepository.deleteById(id) })
    }
}
