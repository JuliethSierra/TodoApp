package com.example.todoapp.ui.screens.completedtasks.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.data.models.Task
import com.example.todoapp.databinding.TaskViewBinding


class RVCompletedTaskAdapter(private val onTaskCheckedChange: (Task, Boolean) -> Unit, private val onTaskSelected: (Task) -> Unit ) : RecyclerView.Adapter<CompletedTaskViewHolder>() {

    private var completedTaskList = emptyList<Task>()

    fun setCompletedTasks(newTasks: List<Task>) {
        this.completedTaskList = newTasks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompletedTaskViewHolder {
        val binding = TaskViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CompletedTaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompletedTaskViewHolder, position: Int) {
        holder.bind(completedTaskList[position], onTaskCheckedChange, onTaskSelected)
    }

    override fun getItemCount() = completedTaskList.size
}
