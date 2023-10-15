package com.example.demo1.ui.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.view.ContentInfoCompat.Flags
import com.example.demo1.ChatAppApplication
import com.example.demo1.databinding.ActivityMainBinding
import com.example.demo1.model.ChatListItem
import com.example.demo1.ui.LoginActivity
import com.example.demo1.ui.adapter.OnePageRecyclerAdapter
import com.example.demo1.ui.adapter.TwoPageRecyclerAdapter
import com.example.demo1.util.ToastUtil
import com.hyphenate.EMValueCallBack
import com.hyphenate.chat.EMClient
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.log

class PagesViewHandler(
    private val pagesViewModel: PagesViewModel,
    private val activity: Activity
) {

    @OptIn(DelicateCoroutinesApi::class)
    fun checkLogin() {
        pagesViewModel.coroutineScope.launch {
            delay(500L)
            if (EMClient.getInstance().isLoggedIn.not()) {
                ToastUtil.showShootToast(activity, "登录超时")
                toLogin()
            }
        }
    }

    private fun toLogin() {
        val intent = Intent()
        intent.setClass(activity, LoginActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }

    fun logout() {
        Log.i(TAG, "logout--")
        EMClient.getInstance().logout(true)
        OnePageRecyclerAdapter.clear()
        TwoPageRecyclerAdapter.clear()
        toLogin()
    }

    fun initConversation() {
        EMClient.getInstance().chatManager().allConversations.forEach {
            OnePageRecyclerAdapter.addChatItem(ChatListItem(it.value.lastMessage))
        }
    }

    fun initContact() {
        EMClient.getInstance().contactManager()
            .asyncGetAllContactsFromServer(object : EMValueCallBack<List<String>> {
                override fun onSuccess(value: List<String>?) {
                    Log.i(TAG, "[asyncGetAllContactsFromServer] Success: ")
                    TwoPageRecyclerAdapter.addContacts(value)
                }

                override fun onError(error: Int, errorMsg: String?) {
                    Log.e(
                        TAG,
                        "[asyncGetAllContactsFromServer] Error: code is $error, msg is $errorMsg.",
                    )
                }

            })
    }

    companion object {
        private const val TAG = "PagesViewHandler"
    }
}