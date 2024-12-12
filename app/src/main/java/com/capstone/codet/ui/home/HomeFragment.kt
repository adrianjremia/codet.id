package com.capstone.codet.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.codet.R
import com.capstone.codet.data.ViewModelFactory
import com.capstone.codet.data.adapter.FunfactAdapter
import com.capstone.codet.data.model.Funfact
import com.capstone.codet.databinding.FragmentHomeBinding
import com.capstone.codet.ui.result.ResultActivity

class HomeFragment:Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var funfactAdapter: FunfactAdapter
    private val list = ArrayList<Funfact>()

    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        funfactAdapter = FunfactAdapter(emptyList())
        binding.rvFunfact.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = funfactAdapter
        }

        viewModel.funFacts.observe(viewLifecycleOwner) { funFacts ->
            funfactAdapter.updateData(funFacts)
        }

        if (viewModel.funFacts.value.isNullOrEmpty()) {
            val dataName = resources.getStringArray(R.array.data_title)
            val dataDescription = resources.getStringArray(R.array.data_description)
            viewModel.loadFunFacts(dataName, dataDescription)
        }

        binding.btnScanSekarang.setOnClickListener {
            val intent = Intent(requireContext(), ResultActivity::class.java)
            startActivity(intent)
        }
    }
}