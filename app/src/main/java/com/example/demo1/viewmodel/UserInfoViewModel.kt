package com.example.demo1.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demo1.model.User
import com.example.demo1.util.userTOEMUser
import com.hyphenate.EMValueCallBack
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMUserInfo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object UserInfoViewModel : ViewModel() {
    var userInfo = MutableLiveData<User>()

    init {
        getUserInfoT()
    }

    fun getUserInfoT(): User {
        val user = User().apply {
            userId = EMClient.getInstance().currentUser
            nickname = "easemob"
            avatarUrl = "https://www.easemob.com"
            birth = "2000.10.10"
            signature = "hello world"
            phoneNumber = "13333333333"
            email = "123456@qq.com"
            gender = 1
        }
        val emUserInfo = EMUserInfo().apply {
            userTOEMUser(user)
        }
        EMClient.getInstance().userInfoManager()
            .updateOwnInfo(emUserInfo, object : EMValueCallBack<String> {
                override fun onSuccess(value: String?) {
                    Log.i(TAG, "updateOwnInfo success: ")
                }

                override fun onError(error: Int, errorMsg: String?) {
                    Log.e(TAG, "updateOwnInfo onError: ${errorMsg}")
                }

            })
        GlobalScope.launch {
            delay(3000L)
            EMClient.getInstance().userInfoManager()
                .fetchUserInfoByUserId(arrayOf<String>(EMClient.getInstance().currentUser),
                    object : EMValueCallBack<Map<String, EMUserInfo>> {
                        override fun onSuccess(value: Map<String, EMUserInfo>?) {
                            Log.i(TAG, "onSuccess: ${value?.get("10001").toString()}")
                        }

                        override fun onError(error: Int, errorMsg: String?) {
                            Log.e(TAG, "onError: reason is ${errorMsg}")
                        }
                    })
        }

        userInfo.postValue(user)
        return user
    }

    fun updateUserInfo() {
        userInfo.postValue(User().apply {
            userId = EMClient.getInstance().currentUser
            nickname = "easemob"
            avatarUrl = "https://www.easemob.com"
            birth = "2000.10.10"
            signature = "hello world"
            phoneNumber = "13333333333"
            email = "123456@qq.com"
            gender = 1
        })
    }

    private const val TAG = "UserInfoViewModel"

}