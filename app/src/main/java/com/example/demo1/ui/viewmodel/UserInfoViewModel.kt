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

    private const val TAG = "UserInfoViewModel"

}