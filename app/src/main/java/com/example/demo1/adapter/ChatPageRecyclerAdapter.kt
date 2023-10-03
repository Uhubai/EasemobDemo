package com.example.demo1.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo1.R
import com.hyphenate.EMValueCallBack
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMMessage.Direct
import com.hyphenate.chat.EMUserInfo

class ChatPageRecyclerAdapter(val emMessages: MutableList<EMMessage>) :
    RecyclerView.Adapter<ChatPageRecyclerAdapter.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView? = null
        var textMsg: TextView? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = if (viewType == RECEIVE) {
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat_left, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat_right, parent, false)
        }
        return Holder(view).apply {
            imageView = view.findViewById(R.id.imageHead)
            textMsg = view.findViewById(R.id.txtMsg)
        }
    }

    override fun getItemCount(): Int {
        return emMessages.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val emMessage = emMessages[position]
//        holder.imageView?.setImageURI(Uri.parse(getUserById(emMessage.from)?.avatarUrl))
        holder.textMsg?.text = emMessage.body.toString()
    }

    private fun getUserById(str: String): EMUserInfo? {
        var emUserInfo: EMUserInfo? = null
        EMClient.getInstance().userInfoManager()
            .fetchUserInfoByUserId(arrayOf(str), object : EMValueCallBack<Map<String, EMUserInfo>> {
                override fun onSuccess(value: Map<String, EMUserInfo>?) {
                    // TODO 处理用户头像逻辑
                }

                override fun onError(error: Int, errorMsg: String?) {
                    Log.e(TAG, "[EMValueCallBack] Error: code is $error, msg is $errorMsg")
                }

            })
        return emUserInfo
    }

    override fun getItemViewType(position: Int): Int {
        return if (emMessages[position].direct().equals(Direct.RECEIVE)) {
            // 接收方
            RECEIVE
        } else {
            SEND
        }
    }

    fun addMessage(message: EMMessage) {
        emMessages.apply {
            add(message)
            Log.i(TAG, "addMessage: ${message.direct()}")
            notifyItemChanged(size)
        }
    }

    companion object {
        private const val TAG = "ChatPageRecyclerAdapter"
        private const val SEND = 0
        private const val RECEIVE = 1
    }
}