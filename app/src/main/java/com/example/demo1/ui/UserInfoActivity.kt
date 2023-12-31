package com.example.demo1.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.demo1.R
import com.example.demo1.databinding.ActivityUserInfoBinding
import com.example.demo1.model.User
import com.example.demo1.repository.UserInfoRepository
import com.example.demo1.ui.call.VideoCallActivity
import com.example.demo1.ui.viewmodel.UserInfoViewModel
import com.hyphenate.chat.EMClient

class UserInfoActivity : AppCompatActivity(), Observer<User> {
    private val userInfoViewModel: UserInfoViewModel by viewModels<UserInfoViewModel>()
    private lateinit var viewBinding: ActivityUserInfoBinding
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_info)
        userId = intent.getStringExtra("userId").toString()
        viewBinding.vm = userInfoViewModel
        userInfoViewModel.userInfo.observe(this, this)
        UserInfoRepository.query(userId)
        initListener()
    }

    @SuppressLint("SetTextI18n")
    override fun onChanged(newData: User) {
        viewBinding.imageView.setImageURI(Uri.parse(newData.avatarUrl))
        viewBinding.txtUserId.text = "ID: ${newData.nickname}(${newData.userId})"
        viewBinding.txtEmail.text = "Email: ${newData.email}"
        viewBinding.txtGender.text = "性别: ${newData.getGenderStr()}"
        viewBinding.txtPhone.text = "电话: ${newData.phoneNumber}"
        viewBinding.txtSignature.text = "签名: ${newData.signature}"
    }

    private fun initListener() {
        viewBinding.btnSendMsg.setOnClickListener {
            val intent = Intent(this, SingleChatActivity::class.java).apply {
                putExtra("conversationId", userId)
            }
            startActivity(intent)
        }
        viewBinding.btnCallOptions.setOnClickListener {
            // TODO 发起视频或者语音通话
            val intent = Intent(this, VideoCallActivity::class.java).apply {
                putExtra("channelName", EMClient.getInstance().currentUser + userId)
            }
            startActivity(intent)
        }
    }
}