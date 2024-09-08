package com.cloffygames.techcareertodoapptask.data.datasource

import com.cloffygames.techcareertodoapptask.data.entity.Task
import com.cloffygames.techcareertodoapptask.room.TaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskDataSource (val tds: TaskDao){
    suspend fun insertTask(task: Task){
        tds.insertTask(task)
    }
    suspend fun updateTask(task: Task){
        tds.updateTask(task)
    }
    suspend fun deleteTask(task: Task){
        tds.deleteTask(task)
    }
    suspend fun getAllTasks(): List<Task> = withContext(Dispatchers.IO){
        return@withContext tds.getAllTasks()
    }
    suspend fun getTaskById(taskId: Int): Task{
        return tds.getTaskById(taskId)
    }
    suspend fun searchTasks(query: String): List<Task> = withContext(Dispatchers.IO){
        return@withContext tds.searchTasks(query)
    }
    suspend fun updateTaskCompletion(taskId: Int, isCompleted: Boolean){
        tds.updateTaskCompletion(taskId, isCompleted)
    }
}