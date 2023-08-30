package com.example.demo1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.demo1.R
import com.example.demo1.databinding.ActivitySingleChatBinding
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage

class SingleChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingleChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView(){
        binding.sendBtn.setOnClickListener {
            binding.editMessage.text.toString().apply {
                var emMessage = EMMessage.createTextSendMessage(this,"10001")
                EMClient.getInstance().chatManager().sendMessage(emMessage)
            }

        }
    }
}