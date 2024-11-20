package com.example.todoapp.ui.screens.tasks.uistate

import com.example.todoapp.data.local.entities.TaskEntity
import com.example.todoapp.data.models.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class TaskUIState(
    val tasks: List<Task>? = emptyList(),
    val isLoading: Boolean = true

)
