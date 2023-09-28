package com.example.demo1.model

import com.hyphenate.EMValueCallBack
import com.hyphenate.chat.EMUserInfo

class User {
    var userId: String? = null
    var avatarUrl: String? = null

    var email: String? = null

    var phoneNumber: String? = null

    /**
     * 0 表示男性，1 表示女性
     */
    var gender: Int? = null

    var signature: String? = null

    var birth: String? = null

    var nickname: String? = null

    var ext: String? = null

    fun getGenderStr(): String {
        return when (gender) {
            1 -> "男"
            2 -> "女"
            else -> "none"
        }
    }

    inner class MySelfInfoCall : EMValueCallBack<Map<String, EMUserInfo>> {
        override fun onSuccess(value: Map<String, EMUserInfo>?) {
            TODO("Not yet implemented")
        }

        override fun onError(error: Int, errorMsg: String?) {
            TODO("Not yet implemented")
        }

    }
}
