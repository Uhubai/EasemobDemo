package com.example.demo1

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.demo1.ui.adapter.OnePageRecyclerAdapter
import com.example.demo1.ui.adapter.TwoPageRecyclerAdapter
import com.example.demo1.model.ChatListItem
import com.hyphenate.EMCallBack
import com.hyphenate.EMValueCallBack
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMOptions

class ChatAppApplication : Application() {
    private lateinit var options: EMOptions

    // 消息监听对象
    private var msgListener = OnePageRecyclerAdapter.MyEMMessageListener()

    override fun onCreate() {
        super.onCreate()
        ChatAppApplication.applicationContext = applicationContext
        init()
    }

    override fun onTerminate() {
        super.onTerminate()
        // 解注册消息监听
        EMClient.getInstance().chatManager().removeMessageListener(msgListener)
    }

    private fun init() {
        options = EMOptions()
        options.appKey = getString(R.string.app_key)
        // 其他 EMOptions 配置。
        EMClient.getInstance().init(this, options)
        // 注册消息监听
        EMClient.getInstance().chatManager().addMessageListener(msgListener)

        EMClient.getInstance().chatManager().allConversations.forEach {
            OnePageRecyclerAdapter.addChatItem(ChatListItem(it.value.lastMessage))
        }
        Log.i(TAG, "init: OK")
    }

    companion object {
        private const val TAG = "ChatAppApplication"
        var applicationContext: Context? = null
    }
}