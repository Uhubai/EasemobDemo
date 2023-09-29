package com.example.demo1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.demo1.R
import com.example.demo1.databinding.ActivityPageOneBinding
import com.example.demo1.databinding.ActivityPageThreeBinding
import com.example.demo1.databinding.ActivityPageTwoBinding
import com.example.demo1.viewmodel.PagesViewModel

class MyViewPage2Adapter(private val pagesViewModel: PagesViewModel) :
    RecyclerView.Adapter<MyViewPage2Adapter.PageHolder>() {

    inner class PageHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageHolder {
        return when (viewType) {
            R.layout.activity_page_one -> createDefaultHolder(parent)
            R.layout.activity_page_two -> createTwoHolder(parent)
            R.layout.activity_page_three -> createThreeHolder(parent)
            else -> createDefaultHolder(parent)
        }
    }

    private fun createDefaultHolder(parent: ViewGroup): PageHolder {
        val pageOneBinding: ActivityPageOneBinding =
            DataBindingUtil.inflate<ActivityPageOneBinding?>(
                LayoutInflater.from(parent.context), R.layout.activity_page_one, parent, false
            ).apply { pageVM = pagesViewModel }
        return PageHolder(pageOneBinding.root)
    }

    private fun createTwoHolder(parent: ViewGroup): PageHolder {
        val pageTwoBinding: ActivityPageTwoBinding =
            DataBindingUtil.inflate<ActivityPageTwoBinding>(
                LayoutInflater.from(parent.context), R.layout.activity_page_two, parent, false
            ).apply { pageVM = pagesViewModel }
        return PageHolder(pageTwoBinding.root)
    }

    private fun createThreeHolder(parent: ViewGroup): PageHolder {
        val pageThreeBinding: ActivityPageThreeBinding =
            DataBindingUtil.inflate<ActivityPageThreeBinding>(
                LayoutInflater.from(parent.context), R.layout.activity_page_three, parent, false
            ).apply { pageVM = pagesViewModel }
        return PageHolder(pageThreeBinding.root)
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: PageHolder, position: Int) {

    }

    private fun bindDefaultHolder() {

    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> R.layout.activity_page_one
            1 -> R.layout.activity_page_two
            2 -> R.layout.activity_page_three
            else -> R.layout.activity_page_one
        }
    }
}