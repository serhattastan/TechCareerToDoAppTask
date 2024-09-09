package com.cloffygames.techcareertodoapptask.uix.viewmodel

import androidx.lifecycle.ViewModel
import com.cloffygames.techcareertodoapptask.data.entity.Task
import com.cloffygames.techcareertodoapptask.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * AddTaskViewModel, yeni görev ekleme işlemlerini yöneten ViewModel sınıfıdır.
 * Görev verilerini TaskRepository üzerinden yönetir.
 *
 * @param trepo TaskRepository, görev verilerini yöneten repository'dir.
 */
@HiltViewModel
class AddTaskViewModel @Inject constructor(var trepo: TaskRepository) : ViewModel() {

    /**
     * Yeni bir görev eklemek için kullanılan fonksiyon.
     * Bu işlem, CoroutineScope kullanılarak ana thread'de asenkron olarak yapılır.
     *
     * @param task Eklenmek istenen görev nesnesi.
     */
    fun insertTask(task: Task) {
        CoroutineScope(Dispatchers.Main).launch {
            trepo.insertTask(task) // Görev repository üzerinden veritabanına eklenir.
        }
    }
}
