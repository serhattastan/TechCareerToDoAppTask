package com.cloffygames.techcareertodoapptask.data.repository

import com.cloffygames.techcareertodoapptask.data.datasource.TaskDataSource
import com.cloffygames.techcareertodoapptask.data.entity.Task
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(var tds: TaskDataSource) {
    suspend fun insertTask(task: Task) = tds.insertTask(task)
    suspend fun updateTask(task: Task) = tds.updateTask(task)
    suspend fun deleteTask(task: Task) = tds.deleteTask(task)
    suspend fun getAllTasks(): List<Task> = tds.getAllTasks()
    suspend fun getTaskById(taskId: Int): Task = tds.getTaskById(taskId)
    suspend fun searchTasks(query: String): List<Task> = tds.searchTasks(query)
    suspend fun updateTaskCompletion(taskId: Int, isCompleted: Boolean) = tds.updateTaskCompletion(taskId, isCompleted)
}
