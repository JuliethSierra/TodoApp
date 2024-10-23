package com.example.todoapp.data.models

data class Task(
    val id: Int,
    var title: String,
    var isCompleted: Boolean = false
)
