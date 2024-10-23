package com.example.todoapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.data.viewmodel.TaskViewModel
import com.example.todoapp.databinding.DialogAddTaskBinding
import com.example.todoapp.databinding.FragmentMainTaskBinding
import com.example.todoapp.ui.screens.tasks.rv.RVTaskAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.example.todoapp.utils.showToast

@AndroidEntryPoint
class MainTaskFragment : Fragment() {

    private val taskViewModel: TaskViewModel by viewModels()
    private var _binding: FragmentMainTaskBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvTaskAdapter: RVTaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvTaskAdapter = RVTaskAdapter(
            onTaskCheckedChange = { task, isChecked ->
                taskViewModel.updateTaskStatus(task.copy(isCompleted = isChecked))
                initUiStateLifecycle()
            },
            onTaskSelected = { task ->
                val bundle = Bundle().apply {
                    putInt("taskId", task.id)
                    putString("taskTitle", task.title)
                    putBoolean("isCompleted", task.isCompleted)
                }
                findNavController().navigate(R.id.action_mainTaskFragment_to_taskDetailsFragment, bundle)
            }
        )

        setupRecyclerView()

        initUiStateLifecycle()

        binding.fabAddTask.setOnClickListener {
            showAddTaskDialog()
        }

        binding.viewCompletedTasksButton.setOnClickListener{
            findNavController().navigate(R.id.action_mainTaskFragment_to_taskCompletedFragment)
        }

    }

    private fun setupRecyclerView() {
        binding.taskRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvTaskAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initUiStateLifecycle() {
        lifecycleScope.launch {
            taskViewModel.uiState.collect { uiState ->
                uiState.tasks?.let { listTasks ->
                    rvTaskAdapter.setTasks(listTasks)
                }
                binding.taskRecyclerView.visibility = if (uiState.isLoading) View.INVISIBLE else View.VISIBLE
                binding.pbTasks.visibility = if (uiState.isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    private fun showAddTaskDialog() {
        val dialogBinding = DialogAddTaskBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setCancelable(true)
            .create()

        dialogBinding.buttonAddTask.setOnClickListener {
            val taskTitle = dialogBinding.editTaskTitle.text.toString().trim()

            if (taskTitle.isNotEmpty()) {
                 taskViewModel.addTask(taskTitle)
                initUiStateLifecycle()
                dialog.dismiss()
            } else {
                requireContext().showToast("Por favor ingresa un titulo")
            }
        }
        dialogBinding.buttonCancelTask.setOnClickListener {
            dialog.dismiss()  // Cierra el diálogo
        }
        dialog.show()
    }
}
