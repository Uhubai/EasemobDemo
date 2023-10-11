package com.example.demo1.ui.call

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.AttributeSet
import android.view.Gravity
import android.view.SurfaceView
import android.widget.FrameLayout
import android.widget.FrameLayout.LayoutParams
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import com.example.demo1.databinding.ActivityVideoCallBinding
import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.Constants
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.RtcEngineConfig
import io.agora.rtc2.video.VideoCanvas


class VideoCallActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoCallBinding

    // 填写项目的 App ID，可在声网控制台中生成
    private val appId = "038147ad0b414ae4b4051c6104ffa7b4"

    // 填写频道名
    private val channelName = "test01"

    // 填写声网控制台中生成的临时 Token
    private val token =
        "007eJxTYBAz1o7e18x+skBU22B/Xnjnp3CnQ9sPRFxJvr47WJObeYkCg4GxhaGJeWKKQZKJoUliqkmSiYGpYbKZoYFJWlqieZIJxwS11IZARoYGo39MjAwQCOKzMZSkFpcYGDIwAADB0h06"

    private var mRtcEngine: RtcEngine? = null

    private val imageView by lazy { ImageView(baseContext).apply {
        background = Color.BLACK.toDrawable()
    } }
    private val imageLayoutParams: LayoutParams by lazy {
        LayoutParams(100, 100).apply {
            gravity = Gravity.BOTTOM+Gravity.CENTER_HORIZONTAL
            bottomMargin = 100
        }
    }

    private val mRtcEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {
        // 监听频道内的远端用户，获取用户的 uid 信息
        override fun onUserJoined(uid: Int, elapsed: Int) {
            runOnUiThread { // 获取 uid 后，设置远端视频视图
                setupRemoteVideo(uid)
            }
        }
    }

    private fun initializeAndJoinChannel() {
        try {
            // 创建 RtcEngineConfig 对象，并进行配置
            val config = RtcEngineConfig()
            config.mContext = baseContext
            config.mAppId = appId
            config.mEventHandler = mRtcEventHandler
            // 创建并初始化 RtcEngine
            mRtcEngine = RtcEngine.create(config)
        } catch (e: Exception) {
            throw RuntimeException("Check the error.")
        }
        // 启用视频模块
        mRtcEngine!!.enableVideo()
        // 开启本地预览
        mRtcEngine!!.startPreview()

        // 创建一个 SurfaceView 对象，并将其作为 FrameLayout 的子对象
        val container = binding.localVideoViewContainer
        val surfaceView = SurfaceView(baseContext)
        container.addView(surfaceView)
        container.addView(imageView,imageLayoutParams)
        // 将 SurfaceView 对象传入声网实时互动 SDK，设置本地视图
        mRtcEngine!!.setupLocalVideo(VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, 0))

        // 创建 ChannelMediaOptions 对象，并进行配置
        val options = ChannelMediaOptions()
        // 根据场景将用户角色设置为 BROADCASTER (主播) 或 AUDIENCE (观众)
        options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER
        // 直播场景下，设置频道场景为 BROADCASTING (直播场景)
        options.channelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING

        // 使用临时 Token 加入频道，自行指定用户 ID 并确保其在频道内的唯一性
        mRtcEngine!!.joinChannel(token, channelName, 0, options)
    }

    private fun setupRemoteVideo(uid: Int) {
        val container =
            findViewById<FrameLayout>(com.example.demo1.R.id.remote_video_view_container)
        val surfaceView = SurfaceView(baseContext)
        surfaceView.setZOrderMediaOverlay(true)
        container.addView(surfaceView)
        // 将 SurfaceView 对象传入声网实时互动 SDK，设置远端视图
        mRtcEngine!!.setupRemoteVideo(VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, uid))
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoCallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 如果已经授权，则初始化 RtcEngine 并加入频道
        if (checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID) &&
            checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID)
        ) {
            initializeAndJoinChannel()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 停止本地视频预览
        mRtcEngine?.stopPreview()
        // 离开频道
        mRtcEngine?.leaveChannel()
    }

    companion object {
        private const val PERMISSION_REQ_ID = 22
        private val REQUESTED_PERMISSIONS = arrayOf<String>(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
        )
    }

}