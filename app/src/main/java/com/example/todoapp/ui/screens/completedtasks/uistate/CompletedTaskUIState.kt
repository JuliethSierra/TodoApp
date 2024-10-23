package com.example.todoapp.ui.screens.completedtasks.uistate

import com.example.todoapp.data.models.Task

data class CompletedTaskUIState(
    val completedTasks: List<Task>? = emptyList(),
    val isLoading: Boolean = true

)
