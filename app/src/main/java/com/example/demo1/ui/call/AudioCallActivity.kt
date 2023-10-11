package com.example.demo1.ui.call

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.demo1.databinding.ActivityVideoCallBinding
import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.Constants
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.RtcEngineConfig


class AudioCallActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoCallBinding

    // 填写项目的 App ID，可在声网控制台中生成
    private val appId = "038147ad0b414ae4b4051c6104ffa7b4"

    // 填写频道名
    private val channelName = "test01"

    // 填写声网控制台中生成的临时 Token
    private val token = "007eJxTYJiw8n1D7OTbPb+UJueGLysX9uL86/Nl9pk7fZP/RK4Tt2lVYDAwtjA0MU9MMUgyMTRJTDVJMjEwNUw2MzQwSUtLNE8yUZygltoQyMjQcUaEmZEBAkF8NoaS1OISA0MGBgACiSCd"

    private var mRtcEngine: RtcEngine? = null

    private val mRtcEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {
        // 监听频道内的远端主播，获取主播的 uid 信息
        override fun onUserJoined(uid: Int, elapsed: Int) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoCallBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 如果已经授权，则初始化 RtcEngine 并加入频道
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO, PERMISSION_REQ_ID)) {
            initializeAndJoinChannel();
        }
    }

    private fun initializeAndJoinChannel() {
        // 创建 RtcEngineConfig 对象，并进行配置
        val config = RtcEngineConfig()
        config.mContext = baseContext
        config.mAppId = appId
        config.mEventHandler = mRtcEventHandler
        // 创建并初始化 RtcEngine
        mRtcEngine = RtcEngine.create(config)
        createChannelMediaOptions()
    }

    private fun createChannelMediaOptions() {
        // 创建 ChannelMediaOptions 对象，并进行配置
        val options = ChannelMediaOptions()
        // 设置频道场景为 BROADCASTING (直播场景)
        options.channelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING
        // 设置用户角色设置为 BROADCASTER (主播) 或 AUDIENCE (观众)
        options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER
        // 使用临时 Token 加入频道，自行指定用户 ID 并确保其在频道内的唯一性
        mRtcEngine!!.joinChannel(token, channelName, 0, options)
    }

    override fun onDestroy() {
        super.onDestroy()
        mRtcEngine?.leaveChannel()
    }


    private fun checkSelfPermission(permission: String, requestCode: Int): Boolean {
        if (ContextCompat.checkSelfPermission(this, permission) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, requestCode)
            return false
        }
        return true
    }

    companion object {
        private const val PERMISSION_REQ_ID = 22
        private val REQUESTED_PERMISSIONS = arrayOf<String>(
            Manifest.permission.RECORD_AUDIO
        )
    }
}