package com.example.demo1.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.demo1.R
import com.example.demo1.viewmodel.UserInfoViewModel

class UserInfoActivity : AppCompatActivity() {
    
    val userInfoViewModel by viewModel<UserInfoViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
    }
}