package com.example.demo1.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demo1.model.User
import com.example.demo1.util.userToEmUser
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMUserInfo

object UserInfoViewModel : ViewModel() {

    var isCurrent = false
    var userInfo = MutableLiveData<User>()


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
            userToEmUser(user)
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