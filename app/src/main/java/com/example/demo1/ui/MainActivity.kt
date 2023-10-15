package com.example.demo1.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.demo1.R
import com.example.demo1.ui.adapter.MyViewPage2Adapter
import com.example.demo1.databinding.ActivityMainBinding
import com.example.demo1.ui.viewmodel.PagesViewHandler
import com.example.demo1.ui.viewmodel.PagesViewModel
import com.hyphenate.chat.EMClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    private val pagesViewModel: PagesViewModel by viewModels<PagesViewModel>()
    private val pagesViewHandler: PagesViewHandler by lazy {
        PagesViewHandler(pagesViewModel, viewBinding)
    }
    private val pageChangeCallback = object : OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            cleanIcon()
            when (position) {
                0 -> viewBinding.imageHome.setImageResource(R.drawable.home48_miss)

                1 -> viewBinding.imageContacter.setImageResource(R.drawable.contacter48_miss)

                2 -> viewBinding.imageSetting.setImageResource(R.drawable.setting48_miss)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initView()
        initListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding.viewPages.unregisterOnPageChangeCallback(pageChangeCallback)
    }

    private fun initView() {
        viewBinding.viewPages.adapter = MyViewPage2Adapter(pagesViewModel, pagesViewHandler)
        viewBinding.viewPages.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewBinding.viewPages.registerOnPageChangeCallback(pageChangeCallback)
        if (!intent.getBooleanExtra("fromLogin", false)) {
            pagesViewHandler.checkLogin()
        }
        viewBinding.viewPages.currentItem = 0
        pagesViewHandler.initConversation()
        pagesViewHandler.initContact()
    }

    private fun initListener() {
        viewBinding.imageHome.setOnClickListener {
            viewBinding.viewPages.setCurrentItem(HOME_PAGE, false)
            cleanIcon()
            viewBinding.imageHome.setImageResource(R.drawable.home48_miss)
        }
        viewBinding.imageContacter.setOnClickListener {
            viewBinding.viewPages.setCurrentItem(CONTACTER_PAGE, false)
            cleanIcon()
            viewBinding.imageContacter.setImageResource(R.drawable.contacter48_miss)
        }
        viewBinding.imageSetting.setOnClickListener {
            viewBinding.viewPages.setCurrentItem(SETTING_PAGE, false)
            cleanIcon()
            viewBinding.imageSetting.setImageResource(R.drawable.setting48_miss)
        }
    }

    private fun cleanIcon() {
        viewBinding.imageHome.setImageResource(R.drawable.home48_not_miss)
        viewBinding.imageContacter.setImageResource(R.drawable.contacter48_not_miss)
        viewBinding.imageSetting.setImageResource(R.drawable.setting48_not_miss)
    }

    companion object {
        private const val HOME_PAGE = 0
        private const val CONTACTER_PAGE = 1
        private const val SETTING_PAGE = 2
    }
}