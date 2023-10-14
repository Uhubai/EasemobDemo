package com.example.demo1.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.demo1.ChatAppApplication
import com.example.demo1.databinding.ActivityMainBinding
import com.example.demo1.ui.LoginActivity
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
    private val activityMainBinding: ActivityMainBinding
) {
    private val context: Context by lazy {
        activityMainBinding.root.context
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun checkLogin() {
        pagesViewModel.coroutineScope.launch {
            delay(500L)
            if (EMClient.getInstance().isLoggedIn.not()) {
                ToastUtil.showShootToast(context, "登录超时")
                toLogin()
            }
        }
    }

    private fun toLogin() {
        val intent = Intent()
        intent.setClass(activityMainBinding.root.context, LoginActivity::class.java)
        context.startActivity(intent)
    }

    fun logout() {
        Log.i(TAG, "logout--")
        EMClient.getInstance().logout(true)
        toLogin()
    }

    fun initContact() {
        EMClient.getInstance().contactManager()
            .asyncGetAllContactsFromServer(object : EMValueCallBack<List<String>> {
                override fun onSuccess(value: List<String>?) {
                    Log.i(TAG, "[asyncGetAllContactsFromServer] Success: ")
                    TwoPageRecyclerAdapter.addContacts(value)
                }

                override fun onError(error: Int, errorMsg: String?) {
                    Log.e(TAG, "[asyncGetAllContactsFromServer] Error: code is $error, msg is $errorMsg.",
                    )
                }

            })
    }

    companion object {
        private const val TAG = "PagesViewHandler"
    }
}