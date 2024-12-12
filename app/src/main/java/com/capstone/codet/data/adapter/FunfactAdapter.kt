package com.capstone.codet.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.codet.R
import com.capstone.codet.data.model.Funfact
import com.capstone.codet.databinding.ItemFunfactBinding

class FunfactAdapter(private var listFunFacts: List<Funfact>) :
    RecyclerView.Adapter<FunfactAdapter.FunfactViewHolder>() {

    class FunfactViewHolder(private val binding: ItemFunfactBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Funfact) {
            binding.apply {
                tvItemName.text = data.title
                tvItemDescription.text = data.desc
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunfactViewHolder {
        val binding = ItemFunfactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FunfactViewHolder(binding)
    }

    override fun getItemCount(): Int = listFunFacts.size

    override fun onBindViewHolder(holder: FunfactViewHolder, position: Int) {
        holder.bind(listFunFacts[position])
    }

    fun updateData(newList: List<Funfact>) {
        listFunFacts = newList
        notifyDataSetChanged()
    }
}
