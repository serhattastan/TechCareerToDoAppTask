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
 * DetailViewModel, görev detaylarının yönetildiği ViewModel sınıfıdır.
 * Bu sınıf, bir görevi güncelleme işlemini yönetir ve TaskRepository ile veri erişimini sağlar.
 *
 * @param trepo TaskRepository, görev verilerini yöneten repository'dir.
 */
@HiltViewModel
class DetailViewModel @Inject constructor(var trepo: TaskRepository) : ViewModel() {

    /**
     * Verilen görevi güncellemek için kullanılan fonksiyon.
     * Bu işlem, CoroutineScope kullanılarak ana thread'de asenkron olarak yapılır.
     *
     * @param task Güncellenmek istenen görev nesnesi.
     */
    fun updateTask(task: Task) {
        CoroutineScope(Dispatchers.Main).launch {
            trepo.updateTask(task) // Görev repository üzerinden güncellenir.
        }
    }
}
