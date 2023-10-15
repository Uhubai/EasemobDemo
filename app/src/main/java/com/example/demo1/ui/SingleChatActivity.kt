package com.example.demo1.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Telephony.Sms.Conversations
import android.util.Log
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo1.ui.adapter.ChatPageRecyclerAdapter
import com.example.demo1.databinding.ActivitySingleChatBinding
import com.example.demo1.model.ChatListItem
import com.example.demo1.ui.adapter.OnePageRecyclerAdapter
import com.hyphenate.EMCallBack
import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMConversation
import com.hyphenate.chat.EMMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class SingleChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingleChatBinding
    private lateinit var chatPageRecyclerAdapter: ChatPageRecyclerAdapter
    private var conversationId: String? = null
    private var conversation: EMConversation? = null
    private var latestMessage: EMMessage? = null
    private var msgListener: ChatPageEMMessageListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        resolveIntent()
        initView()
    }

    override fun onStart() {
        super.onStart()
        msgListener = ChatPageEMMessageListener(conversationId!!, chatPageRecyclerAdapter)
        EMClient.getInstance().chatManager().addMessageListener(msgListener)
        initMoreMessageHistory()
    }

    override fun onStop() {
        super.onStop()
        EMClient.getInstance().chatManager().removeMessageListener(msgListener)
        Log.i(TAG, "removeMessageListener: msgListener")
    }

    private fun initView() {
        conversation = EMClient.getInstance().chatManager().getConversation(conversationId)
        chatPageRecyclerAdapter = ChatPageRecyclerAdapter()
        val allMsgCount =
            EMClient.getInstance().chatManager().getConversation(conversationId).allMsgCount
        Log.i(TAG, "allMsgCount: $allMsgCount")
        if (allMsgCount != 0) {
            latestMessage = conversation!!.allMessages[0]
            chatPageRecyclerAdapter.addMessagesBack(conversation!!.allMessages)
        }
        binding.messageList.adapter = chatPageRecyclerAdapter
        binding.messageList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        binding.messageList.scrollToPosition(chatPageRecyclerAdapter.itemCount - 1)
        binding.messageList.addOnScrollListener(scrollListener)
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
                            chatPageRecyclerAdapter.addMessageBack(msg)
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
        conversationId = intent.getStringExtra("conversationId")
        Log.i(TAG, "[resolveIntent] conversationId: $conversationId")
        conversationId ?: finish()
    }

    private fun initMoreMessageHistory() {
        chatPageRecyclerAdapter.addMessagesHEAD(
            conversation!!.loadMoreMsgFromDB(
                latestMessage!!.msgId, 10
            )
        )
        latestMessage = chatPageRecyclerAdapter.emMessages.first()
    }

    private class ChatPageEMMessageListener(
        val conversationId: String,
        val adapter: ChatPageRecyclerAdapter
    ) : EMMessageListener {
        override fun onMessageReceived(messages: MutableList<EMMessage>?) {
            // 收到消息，遍历消息队列，解析和显示。
            EMClient.getInstance().chatManager().importMessages(messages)
            Log.i(TAG, "onMessageReceived: ${messages?.size}")
            runBlocking(Dispatchers.Default) {
                messages?.forEach {
                    if (conversationId == it.conversationId()) {
                        withContext(Dispatchers.Main){
                            adapter.addMessageBack(it)
                        }
                    }
                }
            }
        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        private var isScrollingUp = false;
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                // 判断是否滚动到顶部，可以根据具体需求进行调整
                if (!recyclerView.canScrollVertically(-1)) {
                    // 触发上拉事件
                    initMoreMessageHistory()
                    Log.i(TAG, "onScrollStateChanged: 触顶")
                }
                // 判断是否滚动到底部
                if (!recyclerView.canScrollVertically(1)) {
                    // 触发上拉事件
                    Log.i(TAG, "onScrollStateChanged: 触底")
                }
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            isScrollingUp = dy < 0;
        }
    }

    companion object {
        private const val TAG = "SingleChatActivity"
    }
}