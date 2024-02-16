package io.antoniomayk.coses.repository

import io.antoniomayk.coses.entity.Task
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers


@SpringBootTest
@Testcontainers
class TaskRepositoryTest {
    companion object {
        @Container
        private val postgres = PostgreSQLContainer("postgres:16-alpine")

        @JvmStatic
        @DynamicPropertySource
        fun configureProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.datasource.password", postgres::getPassword)
        }
    }

    @Autowired
    private lateinit var taskRepository: TaskRepository

    @BeforeEach
    fun setUp() {
        taskRepository.deleteAll()

        taskRepository.saveAll(
            listOf(
                Task(
                    id = null,
                    title = "Title 00",
                    content = "Content 00",
                    completed = false,
                    completedAt = null,
                    createdAt = null,
                    modifiedAt = null
                ),
                Task(
                    id = null,
                    title = "Title 01",
                    content = "Content 01",
                    completed = true,
                    completedAt = null,
                    createdAt = null,
                    modifiedAt = null
                ),
                Task(
                    id = null,
                    title = "Title 02",
                    content = "Content 02",
                    completed = true,
                    completedAt = null,
                    createdAt = null,
                    modifiedAt = null
                ),
                Task(
                    id = null,
                    title = "Title 03",
                    content = "Content 03",
                    completed = true,
                    completedAt = null,
                    createdAt = null,
                    modifiedAt = null
                )
            )
        )
    }

    @Test
    fun `Should return only completed tasks`() {
        val tasks = taskRepository.findAllByCompleted(true).toList()

        assertEquals(3, tasks.size)
    }

    @Test
    fun `Should return only uncompleted tasks`() {
        val tasks = taskRepository.findAllByCompleted(false).toList()

        assertEquals(1, tasks.size)
    }
}
