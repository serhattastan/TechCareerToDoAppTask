package com.cloffygames.techcareertodoapptask.di

import android.content.Context
import androidx.room.Room
import com.cloffygames.techcareertodoapptask.data.datasource.TaskDataSource
import com.cloffygames.techcareertodoapptask.data.repository.TaskRepository
import com.cloffygames.techcareertodoapptask.room.TaskDao
import com.cloffygames.techcareertodoapptask.room.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTaskDataSource(tdao: TaskDao): TaskDataSource {
        return TaskDataSource(tdao)
    }

    @Provides
    @Singleton
    fun provideTaskRepository(tds: TaskDataSource): TaskRepository {
        return TaskRepository(tds)
    }

    @Provides
    @Singleton
    fun provideKisilerDao(@ApplicationContext appContext: Context) : TaskDao {
        val vt = Room
            .databaseBuilder(
                context = appContext,
                TaskDatabase::class.java,
                "task.sqlite")
            .createFromAsset("task.sqlite")
            .build()
        return vt.taskDao()

    }
}
