package com.example.todoapp.ui.screens.tasks.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.data.models.Task
import com.example.todoapp.databinding.TaskViewBinding

class RVTaskAdapter(private val onTaskCheckedChange: (Task, Boolean) -> Unit, private val onTaskSelected: (Task) -> Unit ) : RecyclerView.Adapter<TaskViewHolder>() {

    private var taskList = emptyList<Task>()

    fun setTasks(newTasks: List<Task>) {
        this.taskList = newTasks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position], onTaskCheckedChange, onTaskSelected)
    }

    override fun getItemCount() = taskList.size
}

