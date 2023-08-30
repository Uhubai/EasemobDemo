package com.example.demo1.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.demo1.R
import com.example.demo1.databinding.ActivityLoginBinding
import com.example.demo1.util.checkUserName
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val sharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
        binding.editLoginUser.setText(sharedPreferences.getString("username", ""));
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.registerBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        binding.loginBtn.setOnClickListener {
            var username = binding.editLoginUser.text.toString()
            var pass = binding.editLoginPass.text.toString()

            if (checkUserName(username)) {
                Log.i(TAG, "username: ${username},pass: ${pass} ")
                EMClient.getInstance().login(username, pass, LoginCallBack())
            }
        }

    }


    override fun onDestroy() {
        val sharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
        sharedPreferences.edit().putString("username", binding.editLoginUser.text.toString()).apply()
        super.onDestroy()
    }

    inner class LoginCallBack : EMCallBack {
        override fun onSuccess() {
            startActivity(Intent(baseContext,SingleChatActivity::class.java))
        }

        override fun onError(code: Int, error: String?) {
            Log.i(TAG, "onError: ")
        }

        override fun onProgress(progress: Int, status: String?) {
            Log.i(TAG, "onProgress: ")
        }
    }

    companion object {
        private const val TAG = "LoginActivity"
    }


}