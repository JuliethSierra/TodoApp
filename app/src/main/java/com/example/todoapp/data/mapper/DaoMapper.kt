package com.example.todoapp.data.mapper

import com.example.todoapp.data.local.entities.TaskEntity
import com.example.todoapp.data.models.Task

fun Task.toTaskEntity(): TaskEntity {
    return TaskEntity(id = id, title = title, isCompleted = isCompleted)
}

fun TaskEntity.toTask(): Task {
    return Task(id = id, title = title, isCompleted = isCompleted)
}