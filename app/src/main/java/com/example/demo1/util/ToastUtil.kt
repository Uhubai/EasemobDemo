package com.example.demo1.util

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ToastUtil {
    companion object {
        @JvmStatic
        suspend fun showShootToast(context: Context, text: String) = withContext(Dispatchers.Main) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }

        @JvmStatic
        suspend fun showLongToast(context: Context, text: String) = withContext(Dispatchers.Main) {
            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
        }
    }
}