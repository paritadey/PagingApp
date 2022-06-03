package com.parita.paginationapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.parita.paginationapp.network.ResultsItem

class RecyclerViewAdapter(private val cl: CharacterClickListener): PagingDataAdapter<ResultsItem, RecyclerViewAdapter.MyViewHolder>(DiffUtilCallBack()) {

    override fun onBindViewHolder(holder: RecyclerViewAdapter.MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, cl.clickListener) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(inflater)
    }
    class MyViewHolder(view:View):RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.name)
        val tvSpecies: TextView = view.findViewById(R.id.species)
        val tvImage: ImageView = view.findViewById(R.id.image)
        val itemData: ConstraintLayout = view.findViewById(R.id.item)

        fun bind(data: ResultsItem, clickListener: (ResultsItem) -> Unit) {
            tvName.text = data.name
            tvSpecies.text = data.species
            Glide.with(tvImage).load(data.image).circleCrop().into(tvImage)
            itemData.setOnClickListener{
                Log.d("TAG", "Rest of the data: ${data.id}, ${data.created}, ${data.episode}")
                clickListener(data)
            }
        }
    }
}