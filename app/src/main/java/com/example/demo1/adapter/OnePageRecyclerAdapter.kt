package com.example.demo1.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo1.databinding.ItemPageOneBinding
import com.example.demo1.model.ChatListItem
import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

object OnePageRecyclerAdapter : RecyclerView.Adapter<OnePageRecyclerAdapter.Holder>() {
    private val chatList = ArrayDeque<ChatListItem>()
    private val conversationIdSet = HashSet<String>()

    fun addChatItem(chatListItem: ChatListItem) {
        if (conversationIdSet.contains(chatListItem.conversationId)) {
            // TODO 消息刷新
        } else {
            chatList.add(chatListItem)
            conversationIdSet.add(chatListItem.conversationId)
            notifyItemChanged(chatList.size - 1)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemPageOneBinding = ItemPageOneBinding.inflate(LayoutInflater.from(parent.context))
        Log.i(TAG, "onCreateViewHolder: ")
        return Holder(itemPageOneBinding.root).apply {
            itemImage = itemPageOneBinding.itemImage
            itemTxt = itemPageOneBinding.itemTxt
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemTxt?.text = chatList[position].chatName
        holder.itemImage?.setImageURI(Uri.parse(chatList[position].imageSrc))
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView? = null
        var itemTxt: TextView? = null
    }

    class MyEMMessageListener : EMMessageListener {
        override fun onMessageReceived(messages: MutableList<EMMessage>?) {
            // 收到消息，遍历消息队列，解析和显示。
            EMClient.getInstance().chatManager().importMessages(messages)

            messages?.forEach {
                runBlocking(Dispatchers.Main) {
                    addChatItem(ChatListItem(it))
                }
            }
        }
    }

    private const val TAG = "OnePageRecyclerAdapter"
}