package com.example.todoapp.ui.screens.completedtasks.rv

import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.data.models.Task
import com.example.todoapp.databinding.TaskViewBinding


class CompletedTaskViewHolder (
    private val binding: TaskViewBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(task: Task, onTaskStatusChanged: (Task, Boolean) -> Unit, onTaskSelected: (Task) -> Unit) {
        binding.taskId.text = task.id.toString()
        binding.taskTitle.text = task.title

        //binding.taskDescription.text = task.description
        binding.taskCheckbox.isChecked = task.isCompleted
        applyStrikeThrough(task.isCompleted)
        // Configurar el listener del CheckBox
        binding.taskCheckbox.setOnCheckedChangeListener { _, isChecked ->
            onTaskStatusChanged(task.copy(isCompleted = isChecked), isChecked) // Notificar el cambio
        }
        binding.root.setOnClickListener {
            onTaskSelected(task)
        }
    }

    private fun applyStrikeThrough(isCompleted: Boolean) {
        val flag = if (isCompleted) {
            Paint.STRIKE_THRU_TEXT_FLAG  // Agrega la línea de tachado
        } else {
            0  // Elimina cualquier flag anterior
        }
        binding.taskId.paintFlags = flag
        binding.taskTitle.paintFlags = flag
    }
}
