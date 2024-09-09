package com.cloffygames.techcareertodoapptask.data.datasource

import com.cloffygames.techcareertodoapptask.data.entity.Task
import com.cloffygames.techcareertodoapptask.room.TaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * TaskDataSource sınıfı, TaskDao kullanarak görevlerle ilgili veri işlemlerini yönetir.
 * Bu sınıf, veritabanı işlemlerini yapmak için arka planda asenkron olarak çalışır.
 *
 * @param tds TaskDao nesnesi, veritabanı işlemleri için kullanılır.
 */
class TaskDataSource(val tds: TaskDao) {

    /**
     * Yeni bir görev eklemek için kullanılan fonksiyon.
     * Bu işlem, veritabanına asenkron olarak yapılır.
     *
     * @param task Eklenmek istenen görev.
     */
    suspend fun insertTask(task: Task) {
        tds.insertTask(task)
    }

    /**
     * Mevcut bir görevi güncellemek için kullanılan fonksiyon.
     * Bu işlem, veritabanına asenkron olarak yapılır.
     *
     * @param task Güncellenmek istenen görev.
     */
    suspend fun updateTask(task: Task) {
        tds.updateTask(task)
    }

    /**
     * Bir görevi silmek için kullanılan fonksiyon.
     * Bu işlem, veritabanına asenkron olarak yapılır.
     *
     * @param task Silinmek istenen görev.
     */
    suspend fun deleteTask(task: Task) {
        tds.deleteTask(task)
    }

    /**
     * Tüm görevleri veritabanından getiren fonksiyon.
     * Bu işlem arka planda (Dispatchers.IO) asenkron olarak yapılır.
     *
     * @return Görev listesini döndürür.
     */
    suspend fun getAllTasks(): List<Task> = withContext(Dispatchers.IO) {
        return@withContext tds.getAllTasks()
    }

    /**
     * Verilen ID'ye sahip görevi getiren fonksiyon.
     * Bu işlem, veritabanına asenkron olarak yapılır.
     *
     * @param taskId Alınmak istenen görevin ID'si.
     * @return Verilen ID'ye sahip görev.
     */
    suspend fun getTaskById(taskId: Int): Task {
        return tds.getTaskById(taskId)
    }

    /**
     * Arama sorgusuna uyan görevleri getiren fonksiyon.
     * Başlık ya da açıklama üzerinden arama yapar.
     * Bu işlem arka planda (Dispatchers.IO) asenkron olarak yapılır.
     *
     * @param query Aranacak kelime.
     * @return Arama sonuçlarına uyan görevlerin listesi.
     */
    suspend fun searchTasks(query: String): List<Task> = withContext(Dispatchers.IO) {
        return@withContext tds.searchTasks(query)
    }

    /**
     * Görevin tamamlanma durumunu güncelleyen fonksiyon.
     * Bu işlem, veritabanına asenkron olarak yapılır.
     *
     * @param taskId Tamamlanma durumu güncellenecek görevin ID'si.
     * @param isCompleted Görevin yeni tamamlanma durumu (0 = tamamlanmadı, 1 = tamamlandı).
     */
    suspend fun updateTaskCompletion(taskId: Int, isCompleted: Int) {
        tds.updateTaskCompletion(taskId, isCompleted)
    }
}
