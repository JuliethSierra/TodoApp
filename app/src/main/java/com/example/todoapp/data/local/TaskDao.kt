package com.example.todoapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todoapp.data.local.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table")
    suspend fun getAllTasks(): List<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(tasks: TaskEntity)

   /* @Delete
    suspend fun deleteTask(tasks: TaskEntity)
*/
    @Query("DELETE FROM task_table WHERE id = :taskId")
    suspend fun deleteTask(taskId: Int)

    @Query("UPDATE task_table SET isCompleted = :isCompleted WHERE id = :taskId")
    suspend fun updateTaskStatus(taskId: Int, isCompleted: Boolean)

    @Query("SELECT * FROM task_table WHERE isCompleted = 1")
    suspend fun getTasksByCompletionStatus(): List<TaskEntity>
}