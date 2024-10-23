package com.example.todoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.data.models.Task
import com.example.todoapp.data.viewmodel.TaskViewModel
import com.example.todoapp.databinding.FragmentTaskDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskDetailsFragment : Fragment() {

    private var _binding: FragmentTaskDetailsBinding? = null
    private val binding get() = _binding!!

    private val taskViewModel: TaskViewModel by viewModels() // Asegúrate de tener tu ViewModel

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
        val taskTitle = arguments?.getString("taskTitle") ?: "Título no disponible"
        val isCompleted = arguments?.getBoolean("isCompleted") ?: false

        // Configurar la vista
        binding.taskDetailsTitle.text = taskTitle
        binding.taskDetailsCheckbox.isChecked = isCompleted
        binding.taskDetailsStatus.text = if (isCompleted) "Completada" else "No Completada"

        // Configurar el listener del CheckBox
        binding.taskDetailsCheckbox.setOnCheckedChangeListener { _, isChecked ->
            // Crear una nueva tarea con el estado actualizado
            val updatedTask = Task(
                id = arguments?.getInt("taskId") ?: 0, // Asegúrate de pasar el ID de la tarea
                title = taskTitle,
                isCompleted = isChecked
            )

            if (isChecked) {
                // Si se marca como completada, actualiza el estado en el ViewModel
                taskViewModel.updateTaskStatus(updatedTask)
            }

            // Actualiza el estado en la vista
            binding.taskDetailsStatus.text = if (isChecked) "Completada" else "No Completada"
        }

        // Configurar el listener del botón
        binding.viewCompletedTasksButton.setOnClickListener {
            findNavController().navigateUp() // Vuelve al fragmento anterior
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
