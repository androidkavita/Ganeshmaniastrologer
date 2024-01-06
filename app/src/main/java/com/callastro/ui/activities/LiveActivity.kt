package com.callastro.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.SurfaceView
import android.view.View
import android.view.WindowManager
import android.widget.BaseAdapter
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.callastro.R
import com.callastro.adapters.GoLiveAdapter
import com.callastro.databinding.ActivityLiveBinding
import com.callastro.model.LiveCommentsModelClassData
import com.callastro.viewModels.VideoCallViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.Constants
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.RtcEngineConfig
import io.agora.rtc2.video.VideoCanvas

@AndroidEntryPoint
class LiveActivity : BaseActivity(),View.OnClickListener {
    lateinit var binding:ActivityLiveBinding

    private val PERMISSION_REQ_ID = 22
    private val REQUESTED_PERMISSIONS = arrayOf<String>(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA
    )
    private var mMuted = false
    private fun checkSelfPermission(): Boolean {
        return !(ContextCompat.checkSelfPermission(
            this,
            REQUESTED_PERMISSIONS[0]
        ) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    this,
                    REQUESTED_PERMISSIONS[1]
                ) != PackageManager.PERMISSION_GRANTED)
    }

    fun showMessage(message: String?) {
        runOnUiThread {
            Toast.makeText(
                this,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    val handlerStatusCheck = Handler(Looper.getMainLooper())
    var runnableStatusCheck: Runnable? = null
    // Fill the App ID of your project generated on Agora Console.
    private val appId = "5d6b6fe592ec4b1db67bb7ad5a6454a0"
//    private lateinit var appId :String

    // Fill the channel name.
//    private val channelName = "MNC"
    private var channelName :String = ""

    // Fill the temp token generated on Agora Console.
//    private val token = "007eJxTYJhyYtrTjBmJdQc2/ZRNyTqiPYtPM27VuSZHLu8JLv2nS0UVGExTzJLM0lJNLY1Sk02SDFOSzMyTkswTU0wTzUxMTRINMgIUUxoCGRkCV5UyMEIhiM/M4OvnzMAAAD03Hjc="
    private lateinit var agoratoken :String
    //    private lateinit var etTopic :String
//    var token:String = ""
    lateinit var adapter: GoLiveAdapter
    var list : ArrayList<LiveCommentsModelClassData> = arrayListOf()
    // An integer that identifies the local user.
    private val uid = 0
    private var isJoined = false

    private var agoraEngine: RtcEngine? = null

    //SurfaceView to render local video in a Container.
    private var localSurfaceView: SurfaceView? = null

    //SurfaceView to render Remote video in a Container.
    private var remoteSurfaceView: SurfaceView? = null
    private val viewModel: VideoCallViewModel by viewModels()
    // A toggle switch to change the User role.
    private var audienceRole: Switch? = null

    lateinit var topic:String
//    lateinit var start:String
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_live)
//    binding.localVideoViewContainer.getLayoutParams().height =460;
//    binding.localVideoViewContainer.getLayoutParams().width = 350;
//    binding.localVideoViewContainer.requestLayout();
        if (!checkSelfPermission()) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, PERMISSION_REQ_ID);
        }
//        start = ""
//        if (start == ""){
//            start = "0"

    if (intent != null){
        topic = intent.getStringExtra("topic").toString()
    }
    binding.tvPersonName.text = userPref.getName().toString()
    Glide.with(this@LiveActivity).load(userPref.getProfileImage().toString()).into(binding.profilePhoto)

    if (CommonUtils.isInternetAvailable(this)) {
        viewModel.live_agora_generate_token("Bearer "+userPref.getToken(),topic)
    } else {
        Log.d("TAG", "onCreate: " + "else part")
        toast(this,"Please check internet connection.")
    }

    binding.buttonSwitchCamera.setOnClickListener(this)
    binding.videoBtn.setOnClickListener(this)
    binding.buttonMute.setOnClickListener(this)
            viewModel.goLiveResponse.observe(this) {
                if (it.status == 1) {
                    agoratoken = it.data?.agoraToken.toString()
                    channelName = it.data?.channelName.toString()
                    setupVideoSDKEngine();
                    joinChannel()
                    setupLocalVideo()
                    agoraEngine?.startPreview()
                } else {
                    it.message?.let { it1 -> toast(this,it1) }
                }
            }


        binding.leave.setOnClickListener {
            leaveChannel()
//            remoteSurfaceView!!.visibility = View.GONE
            localSurfaceView!!.visibility = View.GONE
        }

        viewModel.commonResponse.observe(this){
            if (it.status==1){
//                toast(this,it.message.toString())
//                fragmentManager?.popBackStack()
                finish()
            }
        }

        handlerStatusCheck.postDelayed(Runnable {
            handlerStatusCheck.postDelayed(runnableStatusCheck!!, 10000)
            if (CommonUtils.isInternetAvailable(this)) {
                viewModel.get_live_comments(
                    "Bearer "+userPref.getToken().toString(),channelName
                )
            } else {
                Log.d("TAG", "onCreate: " + "else part")
                toast(this,"Please check internet connection.")
            }


        }.also { runnableStatusCheck = it }, 0)
        viewModel.liveCommentsModelClass.observe(this){
            if (it?.status == 1){
                list.clear()
                list.addAll(it.data)
                adapter = GoLiveAdapter(this,list)
                binding.comment.adapter = adapter
            }
        }
    }
    private fun setupVideoSDKEngine() {
        try {
            val config = RtcEngineConfig()
            config.mContext = this
            config.mAppId = appId
            config.mEventHandler = mRtcEventHandler
            agoraEngine = RtcEngine.create(config)
            // By default, the video module is disabled, call enableVideo to enable it.
            agoraEngine!!.enableVideo()
        } catch (e: Exception) {
            showMessage(e.toString())
        }
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.videoBtn -> {

                val btn = v as ImageView
                if (btn.isSelected) {
                    btn.isSelected = false
                    btn.clearColorFilter()
                    btn.setImageResource(R.drawable.ic_video_cross_y)
                } else {
                    btn.isSelected = true
                    btn.setImageResource(R.drawable.ic_video_cross_y)

                }
                agoraEngine?.muteLocalVideoStream(btn.isSelected)
                if (btn.isSelected) {
//                    binding.localVideoView.visibility = View.GONE
                    binding.localVideoViewContainer.setBackgroundColor(R.color.yellow)
                    binding.localVideoViewContainer.background =
                        ContextCompat.getDrawable(this, R.drawable.ic_video_cross_y)
                    btn.setImageResource(R.drawable.ic_video_cross_y)
                } else {
                    binding.localVideoViewContainer.visibility = View.VISIBLE
                    btn.setImageResource(R.drawable.ic_video_new)
                }

                val videoSurface = binding.localVideoViewContainer.getChildAt(0) as SurfaceView?
                videoSurface?.setZOrderMediaOverlay(!btn.isSelected)
//                videoSurface?.visibility = if (btn.isSelected) View.GONE else View.VISIBLE
                if (btn.isSelected){
                    videoSurface?.visibility = View.GONE
                }else{
                    videoSurface?.visibility = View.VISIBLE
                }
            }
//            R.id.buttonCall->{
////                CallEnd()
//                if (!isJoined) {
//                    showMessage("Join a channel first")
//
//                } else {
////                    finish()
//                    stopSound()
////                    viewCallStatusApi()
//                    agoraEngine?.leaveChannel()
//                    showMessage("You left the channel")
//                    // Stop remote video rendering.
//                    if (remoteSurfaceView != null) remoteSurfaceView?.visibility = View.GONE
//                    // Stop local video rendering.
//                    if (localSurfaceView != null) localSurfaceView?.visibility = View.GONE
//                    isJoined = false
//                }
//            }
            R.id.buttonMute -> {
                mMuted = !mMuted
                agoraEngine?.muteLocalAudioStream(mMuted)
                val res: Int = if (mMuted) {
                    R.drawable.ic_mute
                } else {
                    R.drawable.ic_unmute
                }

                binding.buttonMute.setImageResource(res)
                onRemoteUserVideoMuted(uid, mMuted)
            }
            R.id.buttonSwitchCamera-> {
                agoraEngine?.switchCamera()

            }
        }
    }



    private fun onRemoteUserVideoMuted(uid: Int, muted: Boolean) {
        val surfaceView = binding.frameLayoutRemoteVideo.getChildAt(0) as SurfaceView?

        val tag = surfaceView?.tag
        if (tag != null && tag as Int == uid) {
            surfaceView.visibility = if (muted) View.GONE else View.VISIBLE
        }
    }

    private val mRtcEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {
        override fun onUserJoined(uid: Int, elapsed: Int) {
//            showMessage("Remote user joined $uid")
            /*if (!audienceRole!!.isChecked)*/ /*return*/
            // Set the remote video view
            runOnUiThread {
                setupRemoteVideo(uid)

            }
        }

        override fun onJoinChannelSuccess(channel: String, uid: Int, elapsed: Int) {
            isJoined = true
//            showMessage("Joined Channel $channel")
        }

        override fun onUserOffline(uid: Int, reason: Int) {
//            showMessage("Remote user offline $uid $reason")
            runOnUiThread { remoteSurfaceView!!.visibility = View.GONE
                binding.frameLayoutRemoteVideo.visibility = View.GONE}
        }
        override fun onRemoteVideoStateChanged(uid: Int, state: Int, reason: Int, elapsed: Int) {
            super.onRemoteVideoStateChanged(uid, state, reason, elapsed)
            runOnUiThread {
                binding.frameLayoutRemoteVideo.visibility = View.VISIBLE
                onRemoteUserVideoToggle(uid, state)

            }
        }
    }

//    private fun onRemoteUserVideoToggle(uid: Int, state: Int) {
////        val videoContainer = findViewById<FrameLayout>(com.maxtra.swasthrakshak.R.id.bg_video_container)
//        val videoSurface = binding.frameLayoutRemoteVideo.getChildAt(0) as SurfaceView
//        videoSurface.visibility = if (state == 0) View.GONE else View.VISIBLE
//
//        // add an icon to let the other user know remote video has been disabled
//        if (state == 0) {
//            val noCamera = ImageView(this)
//            noCamera.setImageResource(R.drawable.ic_video_cross_y)
//            binding.frameLayoutRemoteVideo.addView(noCamera)
//        } else {
//            try{
//                val noCamera = binding.frameLayoutRemoteVideo.getChildAt(1) as ImageView?
//                if (noCamera != null) {
//                    binding.frameLayoutRemoteVideo.removeView(noCamera)
//                }
//            }catch(e:Exception){
//                e.printStackTrace()
//            }
//        }
//    }

    private fun onRemoteUserVideoToggle(uid: Int, state: Int) {
//        val videoContainer = findViewById<FrameLayout>(com.maxtra.swasthrakshak.R.id.bg_video_container)
        val videoSurface = binding.frameLayoutRemoteVideo.getChildAt(0) as SurfaceView
        videoSurface.visibility = if (state == 0) View.GONE else View.VISIBLE

        // add an icon to let the other user know remote video has been disabled
        if (state == 0) {
            val noCamera = ImageView(this)
            noCamera.setImageResource(R.drawable.ic_video_cross_y)
            binding.frameLayoutRemoteVideo.addView(noCamera)
        } else {
            try{
                val noCamera = binding.frameLayoutRemoteVideo.getChildAt(1) as ImageView?
                if (noCamera != null) {
                    binding.frameLayoutRemoteVideo.removeView(noCamera)
                }
            }catch(e:Exception){
                e.printStackTrace()
            }
        }
    }


    private fun setupRemoteVideo(uid: Int) {
//        val container = view?.findViewById<FrameLayout>(R.id.remote_video_view_container)
//        var container = activity?.findViewById<FrameLayout>(R.id.frame_layout_local_video)
        remoteSurfaceView = SurfaceView(this)
        remoteSurfaceView!!.setZOrderMediaOverlay(true)
        binding.frameLayoutRemoteVideo.addView(remoteSurfaceView)
        agoraEngine!!.setupRemoteVideo(
            VideoCanvas(
                remoteSurfaceView,
                VideoCanvas.RENDER_MODE_FIT,
                uid
            )
        )
        // Display RemoteSurfaceView.
//        remoteSurfaceView!!.setVisibility(View.VISIBLE)
        remoteSurfaceView?.visibility = View.VISIBLE
    }

    private fun setupLocalVideo() {
//        val container = view.findViewById<FrameLayout>(R.id.local_video_view_container)
        // Create a SurfaceView object and add it as a child to the FrameLayout.
        localSurfaceView = SurfaceView(this)
        localSurfaceView?.setZOrderMediaOverlay(true)
        binding.localVideoViewContainer.addView(localSurfaceView)
        // Call setupLocalVideo with a VideoCanvas having uid set to 0.
        agoraEngine!!.setupLocalVideo(
            VideoCanvas(
                localSurfaceView,
                VideoCanvas.RENDER_MODE_HIDDEN,
                0
            )
        )
    }


    fun joinChannel() {
        if (checkSelfPermission()) {
            val options = ChannelMediaOptions()
            // For Live Streaming, set the channel profile as LIVE_BROADCASTING.
            options.channelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING
            // Set the client role as BROADCASTER or AUDIENCE according to the scenario.
//            if (audienceRole!!.isChecked) { //Audience
//            options.clientRoleType = Constants.CLIENT_ROLE_AUDIENCE
//            }
//            else { //Host
            options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER
            // Display LocalSurfaceView.
            setupLocalVideo()
            localSurfaceView!!.visibility = View.VISIBLE
            // Start local preview.
            agoraEngine!!.startPreview()
//            }
//            audienceRole!!.isEnabled = false // Disable the switch
            // Join the channel with a temp token.
            // You need to specify the user ID yourself, and ensure that it is unique in the channel.
            agoraEngine!!.joinChannel(agoratoken, channelName, 0, options)
        } else {
            Toast.makeText(this, "Permissions was not granted", Toast.LENGTH_SHORT).show()
        }
    }

    fun leaveChannel() {
//        if (!isJoined) {
//            showMessage("Join a channel first")
//        } else {
            agoraEngine!!.leaveChannel()
        showMessage("You left the channel")
        // Stop remote video rendering.
        if (remoteSurfaceView != null) remoteSurfaceView!!.visibility = View.GONE
        // Stop local video rendering.
        if (localSurfaceView != null) localSurfaceView!!.visibility = View.GONE
        isJoined = false
//        start = "0"
        if (CommonUtils.isInternetAvailable(this)) {
            viewModel.delete_live_astro("Bearer "+userPref.getToken())
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }

//        fragmentManager?.popBackStack()
//        finish()
//        }
//        audienceRole!!.isEnabled = true // Enable the switch
    }

//    override fun onPause() {
//        super.onPause()
//        toast(this,"pause")
//    }

//    override fun onStop() {
//        super.onStop()
//        toast(this,"stop")
//    }

    override fun onBackPressed() {
        super.onBackPressed()
        leaveChannel()
    }
    override fun onDestroy() {
        super.onDestroy()
        if (CommonUtils.isInternetAvailable(this)) {
            viewModel.delete_live_astro("Bearer "+userPref.getToken())
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }

//        showMessage("You left the channel")
        // Stop remote video rendering.
//        agoraEngine!!.leaveChannel()

//        if (remoteSurfaceView != null) remoteSurfaceView!!.visibility = View.GONE
//        // Stop local video rendering.
//        if (localSurfaceView != null) localSurfaceView!!.visibility = View.GONE
//        isJoined = false
        leaveChannel()
//        start = "0"
//        viewModel.delete_live_astro("Bearer "+userPref.getToken())
//        finish()
    }
}