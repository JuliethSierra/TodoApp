package com.example.todoapp.data.repository

import com.example.todoapp.data.models.Task
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor() {
    private val taskList = mutableListOf<Task>()
    private val completedTasks = mutableListOf<Task>()
    private var taskIdCounter = 0

    init {
        repeat(4) { index ->
            taskList.add(
                Task(
                    id = taskIdCounter++,
                    title = "Tarea $index",
                    isCompleted = false
                )
            )
        }
    }

    fun getAllTasks(): List<Task> = taskList

    fun addTask(title: String) {
        val task = Task(id = taskIdCounter++, title)
        taskList.add(task)
    }

    fun updateTask(task: Task) {
        taskList.replaceAll { if (it.id == task.id) task else it }
    }

    fun updateStatusTask(updatedTask: Task) {
        taskList.find { it.id == updatedTask.id }?.let {
            it.isCompleted = updatedTask.isCompleted // Actualizar el estado
        }
    }

    fun completedTask(completedTask: Task){
        taskList.remove(completedTask)
    }

     fun addCompletedTask(task: Task) {
        completedTasks.add(task)
    }

    fun addNoCompletedTask(task: Task) {
        val task = Task(id = task.id, title = task.title, isCompleted = false)
        taskList.add(task)
    }

    fun deleteNoCompletedTask(noCompletedTask: Task){
        completedTasks.remove(noCompletedTask)
    }

    fun getCompletedListTasks(): List<Task> {
        return completedTasks
    }
}
