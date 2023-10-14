package com.example.demo1.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.demo1.ui.adapter.OnePageRecyclerAdapter
import com.example.demo1.ui.adapter.TwoPageRecyclerAdapter
import kotlinx.coroutines.GlobalScope

class PagesViewModel : ViewModel() {
    val onePageRecyclerAdapter = OnePageRecyclerAdapter
    val twoPageRecyclerAdapter = TwoPageRecyclerAdapter
    val coroutineScope = GlobalScope
}