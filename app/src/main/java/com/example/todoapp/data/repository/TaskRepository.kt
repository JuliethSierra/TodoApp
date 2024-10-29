package com.example.todoapp.data.repository

import com.example.todoapp.data.local.TaskDao
import com.example.todoapp.data.mapper.toTask
import com.example.todoapp.data.mapper.toTaskEntity
import com.example.todoapp.data.models.Task
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    suspend fun getAllTasks(): List<Task> {
        return taskDao.getAllTasks().map { it.toTask() }
    }

    suspend fun getCompletedTasks(): List<Task> {
        return taskDao.getTasksByCompletionStatus().map { it.toTask() } // Asegúrate de que esta consulta esté implementada en tu DAO
    }

    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task.toTaskEntity())
    }

    suspend fun addTask(title: String) {
        val task = Task(title)
        taskDao.insertTask(task.toTaskEntity())
    }


    suspend fun deleteTask(taskId: Int) {
        taskDao.deleteTask(taskId)
    }

    suspend fun updateStatusTask(taskId: Int, isCompleted: Boolean) {
        taskDao.updateTaskStatus(taskId, isCompleted)
    }

    /*    suspend fun deleteTask(taskId: Int) {
            taskDao.deleteTask(taskId)
        }*/
}

