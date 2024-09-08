package com.cloffygames.techcareertodoapptask.uix.viewmodel

import androidx.lifecycle.ViewModel
import com.cloffygames.techcareertodoapptask.data.entity.Task
import com.cloffygames.techcareertodoapptask.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(var trepo: TaskRepository) : ViewModel() {
    var taskList = ArrayList<Task>()
    init {
        loadTasks()
    }
    fun loadTasks(){
        CoroutineScope(Dispatchers.Main).launch {
            taskList = trepo.getAllTasks() as ArrayList<Task>
        }
    }
    fun searchTasks(query: String){
        CoroutineScope(Dispatchers.Main).launch {
            taskList = trepo.searchTasks(query) as ArrayList<Task>
        }
    }
    fun updateTaskCompletion(taskId: Int, isCompleted: Boolean){
        CoroutineScope(Dispatchers.Main).launch {
            trepo.updateTaskCompletion(taskId, isCompleted)
        }
    }
    fun deleteTask(task: Task){
        CoroutineScope(Dispatchers.Main).launch {
            trepo.deleteTask(task)
        }
    }
}