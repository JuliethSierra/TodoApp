package com.example.todoapp.data.models

data class Task(
    var title: String,
    var isCompleted: Boolean = false,
    val id: Int = 0
)
