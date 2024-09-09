package com.cloffygames.techcareertodoapptask.data.repository

import com.cloffygames.techcareertodoapptask.data.datasource.TaskDataSource
import com.cloffygames.techcareertodoapptask.data.entity.Task
import javax.inject.Inject
import javax.inject.Singleton

/**
 * TaskRepository, veritabanı işlemlerini yönetir ve veri erişim katmanını sağlar.
 * TaskDataSource ile etkileşimde bulunarak görev verilerini sağlar.
 * @Inject anotasyonu ile DI (Dependency Injection) kullanılarak bağımlılık yönetimi yapılır.
 *
 * @param tds TaskDataSource, veritabanı işlemlerini gerçekleştiren veri kaynağıdır.
 */
@Singleton
class TaskRepository @Inject constructor(var tds: TaskDataSource) {

    /**
     * Yeni bir görev eklemek için kullanılan fonksiyon.
     * @param task Eklenmek istenen görev.
     */
    suspend fun insertTask(task: Task) = tds.insertTask(task)

    /**
     * Mevcut bir görevi güncellemek için kullanılan fonksiyon.
     * @param task Güncellenmek istenen görev.
     */
    suspend fun updateTask(task: Task) = tds.updateTask(task)

    /**
     * Veritabanından bir görevi silmek için kullanılan fonksiyon.
     * @param task Silinecek görev.
     */
    suspend fun deleteTask(task: Task) = tds.deleteTask(task)

    /**
     * Tüm görevleri getiren fonksiyon.
     * @return Görevlerin listesi.
     */
    suspend fun getAllTasks(): List<Task> = tds.getAllTasks()

    /**
     * Verilen ID'ye sahip görevi getiren fonksiyon.
     * @param taskId Alınmak istenen görevin ID'si.
     * @return Verilen ID'ye sahip görev.
     */
    suspend fun getTaskById(taskId: Int): Task = tds.getTaskById(taskId)

    /**
     * Görev başlığı veya açıklaması üzerinden arama yapan fonksiyon.
     * @param query Arama yapılacak kelime.
     * @return Arama sonuçlarına uyan görevlerin listesi.
     */
    suspend fun searchTasks(query: String): List<Task> = tds.searchTasks(query)

    /**
     * Görevin tamamlanma durumunu güncelleyen fonksiyon.
     * @param taskId Tamamlanma durumu güncellenecek görevin ID'si.
     * @param isCompleted Görevin yeni tamamlanma durumu (0 = tamamlanmadı, 1 = tamamlandı).
     */
    suspend fun updateTaskCompletion(taskId: Int, isCompleted: Int) = tds.updateTaskCompletion(taskId, isCompleted)
}
