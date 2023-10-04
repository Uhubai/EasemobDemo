package com.example.demo1.repository

import android.util.Log
import android.widget.Toast
import com.example.demo1.model.User
import com.example.demo1.util.eMUserToUser
import com.example.demo1.util.publishToast
import com.example.demo1.util.userToEmUser
import com.example.demo1.viewmodel.UserInfoViewModel
import com.hyphenate.EMValueCallBack
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMUserInfo

object UserInfoRepository {
    fun query(userId: String) {
        EMClient.getInstance().userInfoManager().fetchUserInfoByUserId(
            arrayOf<String>(userId),
            object : EMValueCallBack<Map<String, EMUserInfo>> {
                override fun onSuccess(value: Map<String, EMUserInfo>?) {
                    UserInfoViewModel.isCurrent = userId == EMClient.getInstance().currentUser
                    Log.i(TAG, "onSuccess: ${value?.get(userId).toString()}")
                    UserInfoViewModel.userInfo.postValue(value?.get(userId)?.eMUserToUser())
                }

                override fun onError(error: Int, errorMsg: String?) {
                    Log.e(TAG, "onError: reason is ${errorMsg}")
                }
            })
    }

    fun update(user: User) {
        EMClient.getInstance().userInfoManager()
            .updateOwnInfo(EMUserInfo().userToEmUser(user), object : EMValueCallBack<String> {
                override fun onSuccess(value: String?) {
                    Log.i(TAG, "updateOwnInfo success: $value")
                    publishToast("更新成功", Toast.LENGTH_SHORT)
                }

                override fun onError(error: Int, errorMsg: String?) {
                    Log.e(TAG, "updateOwnInfo onError: ${errorMsg}")
                }

            })

    }

    private const val TAG = "UserInfoRepository"
}