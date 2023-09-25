package com.example.demo1.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demo1.model.User
import com.hyphenate.chat.EMClient

object UserInfoViewModel : ViewModel() {
    init {

    }

    private lateinit var userInfo: MutableLiveData<User>

    fun getUserInfo() {
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

    fun updateUserInfo() {

    }


}