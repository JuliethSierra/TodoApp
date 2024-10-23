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
    private val completedTaskViewModel: CompletedTaskViewModel by viewModels()

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

        // Obtener los argumentos pasados
        val taskTitle = args.taskTitle
        val isCompleted = args.isCompleted

        // Configurar la vista
        binding.taskDetailsTitle.text = taskTitle
        binding.taskDetailsCheckbox.isChecked = isCompleted
        binding.taskDetailsStatus.text = if (isCompleted) "Completada" else "No Completada"

        // Configurar el listener del CheckBox
        binding.taskDetailsCheckbox.setOnCheckedChangeListener { _, isChecked ->
            // Crear una nueva tarea con el estado actualizado
            val updatedTask = Task(
                id = args.taskId,
                title = taskTitle,
                isCompleted = isCompleted
            )

            if (isChecked) {
                // Si se marca como completada, actualiza el estado en el ViewModel
                taskViewModel.updateTaskStatus(updatedTask)
            }

            if (!isChecked) {
                // Si se marca como completada, actualiza el estado en el ViewModel
                completedTaskViewModel.updateCompletedTaskStatus(updatedTask.copy(isCompleted = !isChecked))
            }

            // Actualiza el estado en la vista
            binding.taskDetailsStatus.text = if (isChecked) "Completada" else "No Completada"
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
