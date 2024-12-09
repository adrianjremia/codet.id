package com.capstone.codet.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.codet.data.model.History
import com.capstone.codet.databinding.ItemHistoryBinding
import com.capstone.codet.ui.history.DetailHistoryActivity

class HistoryAdapter: ListAdapter<History, HistoryAdapter.HistoryViewHolder>(DIFF_CALLBACK) {

    class HistoryViewHolder(private val binding: ItemHistoryBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: History){
            binding.apply {
                Glide.with(itemView.context)
                    .load(data.img)
                    .centerCrop()
                    .into(imgHistory)

                tvHistoryTitle.text = data.title
                tvDate.text = data.date

                itemView.setOnClickListener {
                    val intentDetail = Intent(itemView.context, DetailHistoryActivity::class.java)
                    intentDetail.putExtra(DetailHistoryActivity.EXTRA_HISTORY, data)
                    itemView.context.startActivity(intentDetail)
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  HistoryViewHolder(binding)
    }


    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<History> =
            object : DiffUtil.ItemCallback<History>() {


                override fun areItemsTheSame(oldItem: History, storyItem:History): Boolean {
                    return oldItem.title == storyItem.title
                }


                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: History, storyItem: History): Boolean {
                    return oldItem == storyItem
                }
            }
    }

}