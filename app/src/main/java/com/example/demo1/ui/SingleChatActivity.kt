package com.example.demo1.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo1.adapter.ChatPageRecyclerAdapter
import com.example.demo1.callback.MyEMCallback
import com.example.demo1.databinding.ActivitySingleChatBinding
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
        initView()
        resolveIntent()
    }

    private fun initView() {
        chatPageRecyclerAdapter = ChatPageRecyclerAdapter()
        binding.messageList.adapter = chatPageRecyclerAdapter
        binding.messageList.layoutManager = LinearLayoutManager(this)
        intent = getIntent()
        binding.btnSend.setOnClickListener {
            binding.editMessage.text.toString().apply {
                val emMessage = EMMessage.createTextSendMessage(this, conversationId).apply {
                    setMessageStatusCallback(MyEMCallback())
                }
                EMClient.getInstance().chatManager().sendMessage(emMessage)
            }
        }
    }

    private fun resolveIntent() {
        intent?.apply {
            conversationId = getStringExtra("conversationId")
            Log.i(TAG, "[resolveIntent] conversationId: $conversationId ")
        }
    }

    companion object {
        private const val TAG = "SingleChatActivity"
    }
}