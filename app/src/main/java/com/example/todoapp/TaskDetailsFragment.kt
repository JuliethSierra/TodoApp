package com.example.todoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todoapp.databinding.FragmentTaskDetailsBinding

class TaskDetailsFragment : Fragment() {

    private var _binding: FragmentTaskDetailsBinding? = null
    private val binding get() = _binding!!

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

        // Configurar el listener del botón
        binding.viewCompletedTasksButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
