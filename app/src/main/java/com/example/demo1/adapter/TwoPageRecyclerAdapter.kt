package com.example.demo1.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo1.ChatAppApplication
import com.example.demo1.R
import com.example.demo1.ui.UserInfoActivity
import com.hyphenate.EMValueCallBack
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMUserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

object TwoPageRecyclerAdapter : RecyclerView.Adapter<TwoPageRecyclerAdapter.Holder>() {

    private val contacts = ArrayList<EMUserInfo>()
    fun addContacts(value: List<String>?) {
        EMClient.getInstance().userInfoManager().fetchUserInfoByUserId(value?.toTypedArray(),
            object : EMValueCallBack<Map<String, EMUserInfo>> {
                override fun onSuccess(values: Map<String, EMUserInfo>?) {
                    Log.i(TAG, "[fetchUserInfoByUserId] Success")
                    runBlocking(Dispatchers.Main) {
                        values?.values?.forEach {
                            addContact(it)
                        }
                    }
                }

                override fun onError(error: Int, errorMsg: String?) {
                    Log.e(
                        TAG, "[fetchUserInfoByUserId] Error: code is $error, reason is $errorMsg"
                    )
                }
            })
    }

    fun addContact(userInfo: EMUserInfo) {
        contacts.add(userInfo)
        notifyItemChanged(contacts.size)
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView? = null
        var textView: TextView? = null
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.item_page_two, parent, false)
        return Holder(inflate).apply {
            imageView = inflate.findViewById(R.id.imageContactItem)
            textView = inflate.findViewById(R.id.txtContactName)
        }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val userInfo = contacts[position]

        holder.imageView?.setImageURI(Uri.parse(userInfo.avatarUrl))
        holder.textView?.text = userInfo.userId

        holder.itemView.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                v.setBackgroundColor(Color.LTGRAY)
            } else {
                v.setBackgroundColor(Color.WHITE)
            }
            false
        }
        holder.itemView.setOnClickListener {
            // TODO JUMP 用户信息页
            val intent = Intent()
            val context = ChatAppApplication.applicationContext!!
            intent.setClass(context, UserInfoActivity::class.java)
            intent.putExtra("userId", contacts[position].userId)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    private const val TAG = "TwoPageRecyclerAdapter"
}