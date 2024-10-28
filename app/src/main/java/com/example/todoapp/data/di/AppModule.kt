package com.example.todoapp.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todoapp.data.local.TaskDao
import com.example.todoapp.data.local.TodoDatabase
import com.example.todoapp.data.local.entities.TaskEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    @Provides
    @Singleton

    fun provideDatabase(@ApplicationContext context: Context): TodoDatabase =
        Room.databaseBuilder(
        context,
            TodoDatabase::class.java,
            "task_database"
    ).build()

    @Provides
    @Singleton
    fun provideTaskDao(todoDatabase: TodoDatabase): TaskDao = todoDatabase.taskDao()

/*    fun provideDatabase(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "task_database"
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // Inserta las tareas por defecto
                    CoroutineScope(Dispatchers.IO).launch {
                        val taskDao = provideDatabase(context).taskDao()
                        taskDao.insertTask(TaskEntity(title = "Tarea 1", isCompleted = false))
                        //taskDao.insertTask(TaskEntity(title = "Tarea 2", isCompleted = false))
                        //taskDao.insertTask(TaskEntity(title = "Tarea 3", isCompleted = true))
                    }
                }
            })
            .build()
    }

    @Provides
    fun provideTaskDao(database: TodoDatabase): TaskDao {
        return database.taskDao()
    }*/


}