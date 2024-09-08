package com.cloffygames.techcareertodoapptask.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "tasks")
data class Task(@PrimaryKey(autoGenerate = true)
                   @ColumnInfo(name = "id") @NotNull var kisi_id:Int,
                   @ColumnInfo(name = "title") @NotNull var kisi_ad:String,
                   @ColumnInfo(name = "description") @NotNull var description:String,
                   @ColumnInfo(name = "priority") @NotNull var priority:Int,
                   @ColumnInfo(name = "isCompleted") @NotNull var isCompleted:Int)