package com.capstone.codet.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.capstone.codet.R
import com.capstone.codet.data.model.Funfact
import com.capstone.codet.data.model.History
import com.capstone.codet.databinding.ActivityFunfactDetailBinding
import com.capstone.codet.ui.history.DetailHistoryActivity

class FunfactDetailActivity:AppCompatActivity() {

    private lateinit var binding: ActivityFunfactDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFunfactDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDetailData()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setDetailData() {
        val history = intent.getParcelableExtra<Funfact>(EXTRA_FUNFACT)

        binding.apply {
            history?.let {
                tvTitleFunfact.text = history?.title
                tvDescFunfact.text = history?.desc
            }
        }
    }

    companion object {
        const val EXTRA_FUNFACT = "extra_funfact"
    }
}