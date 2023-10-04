package com.example.demo1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.demo1.adapter.OnePageRecyclerAdapter
import com.example.demo1.adapter.TwoPageRecyclerAdapter

class PagesViewModel : ViewModel() {
    val onePageRecyclerAdapter = OnePageRecyclerAdapter
    val twoPageRecyclerAdapter = TwoPageRecyclerAdapter
}