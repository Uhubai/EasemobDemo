package com.example.demo1.callback

import android.util.Log
import com.hyphenate.EMCallBack

class MyEMCallback : EMCallBack {
    override fun onSuccess() {
        Log.i(TAG, "EMCallBack Success !!!")
    }

    override fun onError(code: Int, error: String?) {
        Log.e(TAG, "EMCallBack Error: code is $code, msg is $error")
    }

    companion object {
        private const val TAG = "MyEMCallback"
    }
}