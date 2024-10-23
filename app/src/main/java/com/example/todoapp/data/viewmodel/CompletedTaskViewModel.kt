package com.example.todoapp.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.todoapp.data.models.Task
import com.example.todoapp.data.repository.TaskRepository
import com.example.todoapp.ui.screens.completedtasks.uistate.CompletedTaskUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class CompletedTaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CompletedTaskUIState())
    val uiState: StateFlow<CompletedTaskUIState> = _uiState.asStateFlow()


    init {
        loadTasks()
    }

    private fun loadTasks() {
        val completedTasks: List<Task> = repository.getCompletedListTasks()
        Log.d("AndroidRuntime", completedTasks.toString())
        _uiState.value = _uiState.value.copy(completedTasks  = completedTasks,
            isLoading = false)
    }

    fun updateCompletedTaskStatus(task: Task) {
        repository.updateStatusTask(task)
        repository.addNoCompletedTask(task)
        repository.deleteNoCompletedTask(task)
    }
}
