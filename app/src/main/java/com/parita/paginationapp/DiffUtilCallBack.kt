package com.parita.paginationapp

import androidx.recyclerview.widget.DiffUtil
import com.parita.paginationapp.network.ResultsItem

class DiffUtilCallBack : DiffUtil.ItemCallback<ResultsItem> (){
    override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
        return oldItem.name == newItem.name && oldItem.species == newItem.species
    }
}