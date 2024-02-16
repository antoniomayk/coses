package io.antoniomayk.coses.repository

import io.antoniomayk.coses.entity.Task
import org.springframework.data.repository.CrudRepository
import java.util.*

interface TaskRepository : CrudRepository<Task, UUID> {
    fun findAllByCompleted(completed: Boolean): MutableIterable<Task>
}
