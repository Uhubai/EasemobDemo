package com.example.demo1.ui

import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.demo1.R
import com.example.demo1.databinding.ActivityUserInfoBinding
import com.example.demo1.model.User
import com.example.demo1.viewmodel.UserInfoViewModel

class UserInfoActivity : AppCompatActivity(), Observer<User> {
    private val userInfoViewModel: UserInfoViewModel by viewModels<UserInfoViewModel>()
    private lateinit var viewBinding: ActivityUserInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_info)

        viewBinding.vm = userInfoViewModel
        userInfoViewModel.userInfo.observe(this, this)
    }

    override fun onChanged(newData: User) {
        viewBinding.imageView.setImageURI(Uri.parse(newData.avatarUrl))
        viewBinding.txtUserId.text = newData.nickname + "(" + newData.userId + ")"
        viewBinding.txtEmail.text = newData.email
        viewBinding.txtGender.text = newData.getGenderStr()
        viewBinding.txtPhone.text = newData.phoneNumber
        viewBinding.txtSignature.text = newData.signature
    }
}