package com.cloffygames.techcareertodoapptask.uix.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cloffygames.techcareertodoapptask.data.entity.Task
import com.cloffygames.techcareertodoapptask.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * HomeViewModel, ana ekran verilerini yöneten ViewModel sınıfıdır.
 * Görevlerin listelenmesi, arama işlemleri, tamamlanma durumu güncellemeleri gibi işlemleri yönetir.
 * TaskRepository ile veri katmanına erişim sağlar.
 *
 * @param trepo TaskRepository, görev verilerini yöneten repository'dir.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(var trepo: TaskRepository) : ViewModel() {

    // Görev listesini tutan LiveData nesnesi, UI bileşenleri bu veri değişikliklerini gözlemleyebilir.
    var taskList = MutableLiveData<List<Task>>()

    // ViewModel ilk yüklendiğinde görevleri yükleyen init bloğu.
    init {
        loadTasks() // Görevler ilk açıldığında yüklenir.
    }

    /**
     * Veritabanındaki tüm görevleri yükleyip taskList'e atayan fonksiyon.
     * CoroutineScope kullanarak ana thread'de çalışır.
     */
    fun loadTasks() {
        CoroutineScope(Dispatchers.Main).launch {
            taskList.value = trepo.getAllTasks() // Görevler repository'den alınıp taskList'e atanır.
        }
    }

    /**
     * Veritabanında verilen arama sorgusuna uyan görevleri arayan fonksiyon.
     * Arama sonuçları taskList'e atanır.
     *
     * @param query Aranacak kelime veya kelimeler.
     */
    fun searchTasks(query: String) {
        CoroutineScope(Dispatchers.Main).launch {
            taskList.value = trepo.searchTasks(query) // Arama sonuçları taskList'e atanır.
        }
    }

    /**
     * Verilen görev ID'sine göre tamamlanma durumunu güncelleyen fonksiyon.
     * Görev tamamlandı ya da tamamlanmadı olarak işaretlenir ve liste güncellenir.
     *
     * @param taskId Tamamlanma durumu güncellenecek görevin ID'si.
     * @param isCompleted Görevin tamamlanma durumu (0 = tamamlanmadı, 1 = tamamlandı).
     */
    fun updateTaskCompletion(taskId: Int, isCompleted: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            trepo.updateTaskCompletion(taskId, isCompleted) // Tamamlanma durumu güncellenir.
            loadTasks() // Güncellenen görevler tekrar yüklenir.
        }
    }

    /**
     * Veritabanından bir görevi silen fonksiyon.
     * Görev silindikten sonra görevler listesi güncellenir.
     *
     * @param task Silinecek görev nesnesi.
     */
    fun deleteTask(task: Task) {
        CoroutineScope(Dispatchers.Main).launch {
            trepo.deleteTask(task) // Görev silinir.
            loadTasks() // Silinen görev sonrası liste güncellenir.
        }
    }
}
