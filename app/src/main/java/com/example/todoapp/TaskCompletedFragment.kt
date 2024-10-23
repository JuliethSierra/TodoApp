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
import com.example.todoapp.data.viewmodel.CompletedTaskViewModel
import com.example.todoapp.data.viewmodel.TaskViewModel
import com.example.todoapp.databinding.DialogAddTaskBinding
import com.example.todoapp.databinding.FragmentMainTaskBinding
import com.example.todoapp.databinding.FragmentTaskCompletedBinding
import com.example.todoapp.ui.screens.completedtasks.rv.RVCompletedTaskAdapter
import com.example.todoapp.ui.screens.tasks.rv.RVTaskAdapter
import com.example.todoapp.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaskCompletedFragment : Fragment() {

    private val completedTaskViewModel: CompletedTaskViewModel by viewModels()
    private var _binding: FragmentTaskCompletedBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvCompletedTaskAdapter: RVCompletedTaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskCompletedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvCompletedTaskAdapter = RVCompletedTaskAdapter(
            onTaskCheckedChange = { task, isChecked ->
                if (!isChecked) {
                    completedTaskViewModel.updateCompletedTaskStatus(task.copy(isCompleted = !isChecked))
                }
            },
            onTaskSelected = { task ->
                val bundle = Bundle().apply {
                    putString("taskTitle", task.title)
                    putBoolean("isCompleted", task.isCompleted)
                }
                findNavController().navigate(R.id.action_taskCompletedFragment_to_taskDetailsFragment, bundle)
            }
        )
        // Configurar el RecyclerView
        setupRecyclerView()

        initUiStateLifecycle()

        binding.viewTasksButton.setOnClickListener{
            findNavController().navigate(R.id.action_taskCompletedFragment_to_mainTaskFragment)
        }
    }

    private fun setupRecyclerView() {
        binding.taskRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvCompletedTaskAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initUiStateLifecycle() {
        lifecycleScope.launch {
            completedTaskViewModel.uiState.collect { uiState ->
                uiState.completedTasks?.let { listCompletedTasks ->
                    rvCompletedTaskAdapter.setCompletedTasks(listCompletedTasks) // Usar el método setTasks
                }
                binding.taskRecyclerView.visibility = if (uiState.isLoading) View.INVISIBLE else View.VISIBLE
                binding.pbTasks.visibility = if (uiState.isLoading) View.VISIBLE else View.GONE
            }
        }
    }

}
