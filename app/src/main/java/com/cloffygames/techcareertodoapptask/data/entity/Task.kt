package com.cloffygames.techcareertodoapptask.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

/**
 * Task veri sınıfı, Room veritabanında "tasks" tablosunu temsil eder.
 * Her görev, bir başlık, açıklama, öncelik ve tamamlanma durumu gibi bilgiler içerir.
 */
@Entity(tableName = "tasks")
data class Task(

    // Otomatik olarak artan birincil anahtar (id), her görev için benzersiz kimlik sağlar.
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") @NotNull var id: Int,

    // Görevin başlığını temsil eden sütun.
    @ColumnInfo(name = "title") @NotNull var title: String,

    // Görevin açıklamasını temsil eden sütun.
    @ColumnInfo(name = "description") @NotNull var description: String,

    // Görevin öncelik seviyesini temsil eden sütun. (Düşük: 1, Orta: 2, Yüksek: 3 gibi).
    @ColumnInfo(name = "priority") @NotNull var priority: Int,

    // Görevin tamamlanma durumunu temsil eden sütun. 0 = tamamlanmadı, 1 = tamamlandı.
    @ColumnInfo(name = "isCompleted") @NotNull var isCompleted: Int // 0 = false, 1 = true
)
