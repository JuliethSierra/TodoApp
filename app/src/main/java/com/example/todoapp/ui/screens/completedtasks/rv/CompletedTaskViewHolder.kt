package com.example.todoapp.ui.screens.completedtasks.rv

import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.data.models.Task
import com.example.todoapp.databinding.TaskViewBinding


class CompletedTaskViewHolder (
    private val binding: TaskViewBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(task: Task, onTaskStatusChanged: (Task, Boolean) -> Unit, onTaskSelected: (Task) -> Unit) {
        binding.taskId.text = "ID: ${task.id}"
        binding.taskTitle.text = task.title

        binding.taskCheckbox.isChecked = task.isCompleted
        applyStrikeThrough(task.isCompleted)

        binding.taskCheckbox.setOnCheckedChangeListener { _, isChecked ->
            onTaskStatusChanged(task.copy(isCompleted = isChecked), isChecked)
        }
        binding.root.setOnClickListener {
            onTaskSelected(task)
        }
    }

    private fun applyStrikeThrough(isCompleted: Boolean) {
        val flag = if (isCompleted) {
            Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            0
        }
        binding.taskId.paintFlags = flag
        binding.taskTitle.paintFlags = flag
    }
}
