package com.example.todoapp

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
import com.example.todoapp.databinding.FragmentTaskCompletedBinding
import com.example.todoapp.ui.screens.completedtasks.rv.RVCompletedTaskAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaskCompletedFragment : Fragment() {

    private val taskViewModel: TaskViewModel by viewModels()
    private var _binding: FragmentTaskCompletedBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvTaskCompletedAdapter: RVCompletedTaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskCompletedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvTaskCompletedAdapter = RVCompletedTaskAdapter(
            onTaskCheckedChange = { task, isChecked ->
                taskViewModel.updateTaskStatusPending(task.copy(isCompleted = false))
            },
            onTaskSelected = { task ->
                val action =
                    TaskCompletedFragmentDirections.actionTaskCompletedFragmentToTaskDetailsFragment(
                        taskId = task.id,
                        taskTitle = task.title,
                        isCompleted = task.isCompleted
                    )
                findNavController().navigate(action)
            },
            onTaskDeleted = { task ->
                taskViewModel.deleteTaskCompleted(task)
            }
        )

        setupRecyclerView()

        loadCompletedTasks()

        initUiStateLifecycle()

        binding.viewTasksButton.setOnClickListener {
            findNavController().navigate(R.id.action_taskCompletedFragment_to_mainTaskFragment)
        }
    }

    private fun setupRecyclerView() {
        binding.taskRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvTaskCompletedAdapter
        }
    }

    private fun loadCompletedTasks() {
        lifecycleScope.launch {
            taskViewModel.loadCompletedTasks()
        }
    }

    private fun initUiStateLifecycle() {
        lifecycleScope.launch {
            taskViewModel.uiState.collect { uiState ->
                uiState.tasks?.let { listTasks ->
                    rvTaskCompletedAdapter.setCompletedTasks(listTasks)
                }
                binding.taskRecyclerView.visibility =
                    if (uiState.isLoading) View.INVISIBLE else View.VISIBLE
                binding.pbTasks.visibility = if (uiState.isLoading) View.VISIBLE else View.GONE
            }
        }
    }

}