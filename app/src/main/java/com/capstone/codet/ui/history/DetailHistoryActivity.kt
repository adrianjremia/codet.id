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
import com.capstone.codet.data.response.ListHistory

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
        val history = intent.getParcelableExtra<ListHistory>(EXTRA_HISTORY)

        binding.apply {
            history?.let {


                Glide.with(this@DetailHistoryActivity)
                    .load(history.imageURL)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(imgResultHistory)

                tvNamaPenyakit.text = history.status
                tvPenyakitDesc.text = history.details
                tvIndication.text = history.indication
                tvTreatment.text = history.treatment



            }


        }
    }

    companion object {
        const val EXTRA_HISTORY = "extra_history"
    }


}