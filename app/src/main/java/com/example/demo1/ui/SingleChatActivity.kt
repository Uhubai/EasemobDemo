package com.example.demo1.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo1.adapter.ChatPageRecyclerAdapter
import com.example.demo1.databinding.ActivitySingleChatBinding
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage

class SingleChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingleChatBinding
    private lateinit var chatPageRecyclerAdapter: ChatPageRecyclerAdapter
    private var intent: Intent? = null
    private var conversationId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        resolveIntent()
        initView()
    }

    private fun initView() {
        chatPageRecyclerAdapter = ChatPageRecyclerAdapter(
            EMClient.getInstance().chatManager().getConversation(conversationId).allMessages
        )
        binding.messageList.adapter = chatPageRecyclerAdapter
        binding.messageList.layoutManager = LinearLayoutManager(this)

        setSupportActionBar(binding.toolbarChat)
        supportActionBar?.title = "测试111"
        supportActionBar?.subtitle = "测试"

        binding.btnSend.setOnClickListener {
            binding.editMessage.text.toString().apply {
                val emMessage = EMMessage.createTextSendMessage(this, conversationId).apply {
                    val msg = this
                    setMessageStatusCallback(object : EMCallBack {
                        override fun onSuccess() {
                            Log.i(TAG, "[EMCallBack] Success !!!")
                            chatPageRecyclerAdapter.addMessage(msg)
                        }

                        override fun onError(code: Int, error: String?) {
                            Log.e(TAG, "[EMCallBack] Error: code is $code, msg is $error")
                        }
                    })
                }
                EMClient.getInstance().chatManager().sendMessage(emMessage)
            }
        }
    }

    private fun resolveIntent() {
        intent = getIntent()
        intent?.apply {
            conversationId = getStringExtra("conversationId")
            Log.i(TAG, "[resolveIntent] conversationId: $conversationId ")
        }
    }

    companion object {
        private const val TAG = "SingleChatActivity"
    }
}