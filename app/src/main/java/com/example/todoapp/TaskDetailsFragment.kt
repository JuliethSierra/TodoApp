package com.example.todoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.data.models.Task
import com.example.todoapp.data.viewmodel.CompletedTaskViewModel
import com.example.todoapp.data.viewmodel.TaskViewModel
import com.example.todoapp.databinding.FragmentTaskDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskDetailsFragment : Fragment() {

    private var _binding: FragmentTaskDetailsBinding? = null
    private val binding get() = _binding!!

    private val taskViewModel: TaskViewModel by viewModels()

    private val args: TaskDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taskId = args.taskId
        val taskTitle = args.taskTitle
        val isCompleted = args.isCompleted

        binding.taskId.text = "ID: ${taskId}"
        binding.taskDetailsTitle.text = taskTitle
        binding.taskDetailsCheckbox.isChecked = isCompleted
        binding.taskDetailsStatus.text = if (isCompleted) "Completada" else "No Completada"

        binding.taskDetailsCheckbox.setOnCheckedChangeListener { _, isChecked ->
            val updatedTask = Task(
                id = taskId,
                title = taskTitle,
                isCompleted = isCompleted
            )

            if (isChecked) {
                taskViewModel.updateTaskStatus(updatedTask.copy(isCompleted = true))
            }

            if (!isChecked) {
                taskViewModel.updateTaskStatusPending(updatedTask.copy(isCompleted = false))
            }

            binding.taskDetailsStatus.text = if (isChecked) "Completada" else "No Completada"
        }

        // Acción para eliminar tarea según estado del checkbox
        binding.deleteButton.setOnClickListener {
            val taskToDelete = Task(id = taskId, title = taskTitle, isCompleted = isCompleted)

            if (binding.taskDetailsCheckbox.isChecked) {
                taskViewModel.deleteTaskCompleted(taskToDelete)
            } else {
                taskViewModel.deleteTask(taskToDelete)
            }

            findNavController().popBackStack()
        }


        binding.viewCompletedTasksButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
