package com.cloffygames.techcareertodoapptask.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cloffygames.techcareertodoapptask.data.entity.Task

/**
 * TaskDao, Room veritabanı ile etkileşim kurmak için kullanılan arayüzdür.
 * Bu arayüz, görevlerle ilgili temel CRUD (Create, Read, Update, Delete) işlemlerini sağlar.
 */
@Dao
interface TaskDao {

    /**
     * Veritabanına yeni bir görev eklemek için kullanılan fonksiyon.
     * Eğer aynı ID'ye sahip bir görev zaten varsa, üzerine yazar (REPLACE stratejisi).
     *
     * @param task Veritabanına eklenecek görev.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    /**
     * Mevcut bir görevi güncellemek için kullanılan fonksiyon.
     *
     * @param task Güncellenmek istenen görev nesnesi.
     */
    @Update
    suspend fun updateTask(task: Task)

    /**
     * Veritabanından bir görevi silmek için kullanılan fonksiyon.
     *
     * @param task Silinecek görev nesnesi.
     */
    @Delete
    suspend fun deleteTask(task: Task)

    /**
     * Tüm görevleri öncelik sırasına göre (yüksekten düşüğe) sıralı şekilde almak için sorgu.
     *
     * @return Tüm görevleri içeren bir liste.
     */
    @Query("SELECT * FROM tasks ORDER BY priority DESC")
    fun getAllTasks(): List<Task>

    /**
     * Verilen ID'ye sahip bir görevi veritabanından almak için sorgu.
     *
     * @param taskId Alınmak istenen görevin ID'si.
     * @return Verilen ID'ye sahip görev.
     */
    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): Task

    /**
     * Görev başlığı veya açıklaması arama kriterlerine uyan görevleri aramak için sorgu.
     *
     * @param query Arama yapılacak kelime veya kelimeler.
     * @return Arama kriterlerine uyan görevlerin listesi.
     */
    @Query("SELECT * FROM tasks WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchTasks(query: String): List<Task>

    /**
     * Bir görevin tamamlanma durumunu güncellemek için kullanılan sorgu.
     *
     * @param taskId Tamamlanma durumu güncellenecek görevin ID'si.
     * @param isCompleted Görevin tamamlanma durumu (0 = tamamlanmadı, 1 = tamamlandı).
     */
    @Query("UPDATE tasks SET isCompleted = :isCompleted WHERE id = :taskId")
    suspend fun updateTaskCompletion(taskId: Int, isCompleted: Int)
}
