package com.example.demo1.util

import android.widget.Toast
import com.example.demo1.ChatAppApplication
import com.example.demo1.model.User
import com.hyphenate.chat.EMUserInfo

fun EMUserInfo.userToEmUser(user: User): EMUserInfo {
    userId = user.userId
    gender = user.gender ?: 0
    avatarUrl = user.avatarUrl
    birth = user.birth
    email = user.email
    ext = user.ext
    nickname = user.nickname
    phoneNumber = user.phoneNumber
    signature = user.signature
    return this
}

fun EMUserInfo.eMUserToUser(): User {
    val user = User()
    user.userId = userId
    user.ext = ext
    user.gender = gender
    user.birth = birth
    user.email = email
    user.nickname = nickname
    user.phoneNumber = phoneNumber
    user.signature = signature
    user.avatarUrl = avatarUrl
    return user
}

fun publishToast(text: String, duration: Int) = Toast(ChatAppApplication.applicationContext).apply {
    setText(text)
    this.duration = duration
}.show()