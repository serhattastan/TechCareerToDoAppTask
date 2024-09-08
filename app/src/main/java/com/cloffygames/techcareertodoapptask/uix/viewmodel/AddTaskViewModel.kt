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
class AddTaskViewModel @Inject constructor(var trepo: TaskRepository) : ViewModel() {
    fun insertTask(task: Task){
        CoroutineScope(Dispatchers.Main).launch {
            trepo.insertTask(task)
        }
    }
}