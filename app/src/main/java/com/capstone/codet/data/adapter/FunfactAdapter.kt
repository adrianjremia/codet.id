package com.capstone.codet.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.codet.R
import com.capstone.codet.data.model.Funfact

class FunfactAdapter(private val listCountry:ArrayList<Funfact>): RecyclerView.Adapter<FunfactAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_funfact, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listCountry.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (name, description,) = listCountry[position]


        holder.tvName.text = name
        holder.tvDescription.text = description

        //holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listCountry[holder.adapterPosition]) }
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {


        val tvName : TextView = itemView.findViewById(R.id.tv_item_name)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)
    }


}