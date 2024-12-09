package com.capstone.codet.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.codet.R
import com.capstone.codet.data.adapter.HistoryAdapter
import com.capstone.codet.data.model.History
import com.capstone.codet.databinding.FragmentHistoryBinding

class HistoryFragment:Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val historyAdapter by lazy { HistoryAdapter() }

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

        // Load data from resources
        val titles = resources.getStringArray(R.array.data_title)
        val descriptions = resources.getStringArray(R.array.data_description)
        val dates = resources.getStringArray(R.array.data_date)
        val photos = arrayOf(
            R.drawable.ic_placeholder,
            R.drawable.ic_placeholder,
            R.drawable.ic_placeholder,
            R.drawable.ic_placeholder,
            R.drawable.ic_placeholder,
            R.drawable.ic_placeholder
        )

        // Create a list of History objects
        val historyList = titles.mapIndexed { index, title ->
            History(
                title = title,
                desc = descriptions[index],
                date = dates[index],
                img = photos[index]
            )
        }

        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        // Set up RecyclerView and adapter
        binding.rvHistory.adapter = historyAdapter
        historyAdapter.submitList(historyList)

    }

}