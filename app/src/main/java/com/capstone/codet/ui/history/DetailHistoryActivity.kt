package com.capstone.codet.ui.history

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.capstone.codet.data.model.History
import com.capstone.codet.databinding.ActivityHistoryDetailBinding
import com.capstone.codet.R

class DetailHistoryActivity:AppCompatActivity() {

    private lateinit var binding:ActivityHistoryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDetailData()

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setDetailData() {
        val history = intent.getParcelableExtra<History>(EXTRA_HISTORY)

        binding.apply {
            history?.let {


                Glide.with(this@DetailHistoryActivity)
                    .load(history?.img)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(imgResultHistory)

                tvTitleHistory.text = history?.title
                tvDateHistory.text = history?.date
                tvDescHistory.text = history?.desc



            }


        }
    }

    companion object {
        const val EXTRA_HISTORY = "extra_history"
    }


}