package com.example.todoapp.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.todoapp.data.models.Task
import com.example.todoapp.data.repository.TaskRepository
import com.example.todoapp.ui.screens.tasks.uistate.TaskUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskUIState())
    val uiState: StateFlow<TaskUIState> = _uiState.asStateFlow()


    init {
        loadTasks()
    }

    private fun loadTasks() {
        val tasks: List<Task> = repository.getAllTasks()
        Log.d("AndroidRuntime", tasks.toString())
        _uiState.value = _uiState.value.copy(tasks = tasks,
            isLoading = false)
    }

    fun addTask(title: String) {
        repository.addTask(title)
        loadTasks()
    }

    fun updateTaskStatus(task: Task) {
        repository.updateStatusTask(task)
        loadTasks()
        repository.addCompletedTask(task)
        repository.completedTask(task)
    }

}
