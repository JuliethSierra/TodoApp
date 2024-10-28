package com.example.todoapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.todoapp.data.local.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table")
    suspend fun getAllTasks(): List<TaskEntity>

    @Insert
    fun insertTask(tasks: TaskEntity)

    @Delete
    suspend fun deleteTask(tasks: TaskEntity)

}