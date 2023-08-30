package com.example.demo1

import android.app.Application
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMOptions

class ChatAppApplication : Application() {
    private lateinit var options: EMOptions

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        options = EMOptions();
        options.setAppKey(getString(R.string.app_key));
        // 其他 EMOptions 配置。
        EMClient.getInstance().init(this, options);
    }
}