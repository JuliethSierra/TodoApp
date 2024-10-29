package com.example.todoapp.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.models.Task
import com.example.todoapp.data.repository.TaskRepository
import com.example.todoapp.ui.screens.tasks.uistate.TaskUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskUIState())
    val uiState: StateFlow<TaskUIState> = _uiState.asStateFlow()


    fun loadTasks() {
        viewModelScope.launch {
            val tasks: List<Task> = repository.getAllTasks()
            Log.d("AndroidRuntime", "Tareas: $tasks")
            val pendingTasks = tasks.filter { !it.isCompleted }
            Log.d("AndroidRuntime", "Tareas pendientes: $pendingTasks")
            _uiState.value = _uiState.value.copy(
                tasks = pendingTasks,
                isLoading = false
            )
        }
    }

    fun loadCompletedTasks() {
        viewModelScope.launch {
            val tasks: List<Task> = repository.getCompletedTasks()
            Log.d("AndroidRuntime", "Tareas Completadas: $tasks")
            _uiState.value = _uiState.value.copy(
                tasks = tasks,
                isLoading = false
            )
        }
    }

    fun addTask(title: String) {
        viewModelScope.launch {
            repository.addTask(title)
            loadTasks()
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task.id)
            loadTasks()
        }
    }

    fun deleteTaskCompleted(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task.id)
            loadCompletedTasks()
        }
    }

    fun updateTaskStatus(task: Task) {
        viewModelScope.launch {
            repository.updateStatusTask(task.id, task.isCompleted) // Cambiar el estado de la tarea
            loadTasks() // Recargar tareas pendientes después de actualizar
        }
    }

    fun updateTaskStatusPending(task: Task) {
        viewModelScope.launch {
            repository.updateStatusTask(task.id, task.isCompleted) // Cambiar el estado de la tarea
            loadCompletedTasks() // Recargar tareas pendientes después de actualizar
        }
    }

}
