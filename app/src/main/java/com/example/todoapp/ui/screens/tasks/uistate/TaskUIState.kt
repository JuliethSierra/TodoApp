package com.example.todoapp.ui.screens.tasks.uistate

import com.example.todoapp.data.models.Task

data class TaskUIState(
    val tasks: List<Task>? = emptyList(),
    val isLoading: Boolean = true

)
