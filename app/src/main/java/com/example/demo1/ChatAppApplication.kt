package com.example.demo1

import android.app.Application
import android.util.Log
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMOptions

class ChatAppApplication : Application() {
    private lateinit var options: EMOptions

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        options = EMOptions()
        options.appKey = getString(R.string.app_key)
        // 其他 EMOptions 配置。
        EMClient.getInstance().init(this, options)
        if (EMClient.getInstance().isLoggedIn.not()) {
            EMClient.getInstance().login("10001", "123456", object : EMCallBack {
                override fun onSuccess() {
                    Log.i(TAG, "login success")
                }

                override fun onError(code: Int, error: String?) {
                    Log.e(TAG, "[login failed] code is ${code} reason is ${error}")

                }
            })
        }
        Log.i(TAG, "login OK: ")
        Log.i(TAG, "init: OK")
    }

    companion object {
        private const val TAG = "ChatAppApplication"
    }
}