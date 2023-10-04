package com.example.demo1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demo1.R

object TwoPageRecyclerAdapter : RecyclerView.Adapter<TwoPageRecyclerAdapter.Holder>() {


    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.item_page_two, parent, false)

        return Holder(inflate)
    }

    override fun getItemCount(): Int {
        return 0
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        TODO("Not yet implemented")
    }
}