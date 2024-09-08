package com.cloffygames.techcareertodoapptask.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cloffygames.techcareertodoapptask.data.entity.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}