package com.capstone.codet.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.codet.R
import com.capstone.codet.data.ViewModelFactory
import com.capstone.codet.data.adapter.HistoryAdapter
import com.capstone.codet.data.model.History
import com.capstone.codet.databinding.FragmentHistoryBinding
import com.capstone.codet.data.utils.Result

class HistoryFragment:Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val historyAdapter by lazy { HistoryAdapter() }
    private val viewModel: HistoryViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvInit()
        observeViewModel()

    }

    private fun rvInit() {
        binding.apply {
            rvHistory.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvHistory.adapter = historyAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.history().observe(requireActivity()) { result->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val event = result.data
                        historyAdapter.submitList(event)
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE

                    }

                }
            }

        }
    }



}