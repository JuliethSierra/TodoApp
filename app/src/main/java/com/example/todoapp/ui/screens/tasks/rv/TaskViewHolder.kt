package com.example.todoapp.ui.screens.tasks.rv

import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.data.models.Task
import com.example.todoapp.databinding.TaskViewBinding

class TaskViewHolder (
    private val binding: TaskViewBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(task: Task, onTaskStatusChanged: (Task, Boolean) -> Unit, onTaskSelected: (Task) -> Unit) {
        binding.taskId.text = "ID: ${task.id}"
        binding.taskTitle.text = task.title

        binding.taskCheckbox.isChecked = task.isCompleted

        binding.taskCheckbox.setOnCheckedChangeListener { _, isChecked ->
            onTaskStatusChanged(task.copy(isCompleted = isChecked), isChecked)
        }

        binding.root.setOnClickListener {
            onTaskSelected(task)
        }

    }
}
