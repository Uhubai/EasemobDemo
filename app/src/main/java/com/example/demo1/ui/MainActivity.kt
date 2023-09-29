package com.example.demo1.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.demo1.adapter.MyViewPage2Adapter
import com.example.demo1.databinding.ActivityMainBinding
import com.example.demo1.viewmodel.PagesViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    private val pagesViewModel: PagesViewModel by viewModels<PagesViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        viewBinding.viewPages.adapter = MyViewPage2Adapter(pagesViewModel)
        viewBinding.viewPages.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        setContentView(viewBinding.root)
    }
}