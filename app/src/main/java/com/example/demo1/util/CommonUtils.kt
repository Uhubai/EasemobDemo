package com.example.demo1.util

import com.example.demo1.model.User
import com.hyphenate.chat.EMUserInfo

fun EMUserInfo.userTOEMUser(user: User) {
    gender = user.gender ?: 0
    avatarUrl = user.avatarUrl
    birth = user.birth
    email = user.email
    ext = user.ext
    nickname = user.nickname
    phoneNumber = user.phoneNumber
    signature = user.signature
}