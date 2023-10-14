package com.example.demo1.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.demo1.databinding.ActivityRegisterBinding
import com.example.demo1.util.checkPassAgain
import com.example.demo1.util.checkUserName
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import kotlin.concurrent.thread

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.registerBtn.setOnClickListener {
            val username = binding.editUser.text.toString()
            val pass = binding.editPass.text.toString()
            val passAgain = binding.editPassAgain.text.toString()
            if (checkUserName(username) && checkPassAgain(pass, passAgain)) {
                thread {
                    //TODO 同步方法
                    try {
                        EMClient.getInstance().createAccount(username, pass);
                        Log.i(TAG, "register OK !!!")
                    } catch (e: HyphenateException) {
                        e.printStackTrace()
                    }
                }
            } else {
                //TODO
            }
        }
        binding.cancelBtn.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }

    companion object {
        private const val TAG = "RegisterActivity"
    }
}