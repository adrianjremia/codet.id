package com.capstone.codet.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.codet.R
import com.capstone.codet.data.adapter.FunfactAdapter
import com.capstone.codet.data.model.Funfact
import com.capstone.codet.databinding.FragmentHomeBinding
import com.capstone.codet.ui.result.ResultActivity

class HomeFragment:Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var tipsAdapter: FunfactAdapter
    private val list = ArrayList<Funfact>()

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


        if (!::tipsAdapter.isInitialized) {
            tipsAdapter = FunfactAdapter(list)
            binding.rvFunfact.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tipsAdapter
            }
        }

        binding.btnScanSekarang.setOnClickListener {
            val intent = Intent(requireContext(), ResultActivity::class.java)
            startActivity(intent)
        }


        list.clear()
        list.addAll(getListFlowers())
        tipsAdapter.notifyDataSetChanged()

    }


    @SuppressLint("Recycle")
    private fun getListFlowers():ArrayList<Funfact> {
        val dataName = resources.getStringArray(R.array.data_title)
        val dataDescription = resources.getStringArray(R.array.data_description)

        val listTips = ArrayList<Funfact>()
        for (i in dataName.indices) {
            val hero = Funfact(dataName[i], dataDescription[i] )
            listTips.add(hero)
        }
        return listTips
    }

}