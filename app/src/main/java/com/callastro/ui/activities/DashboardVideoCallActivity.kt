package com.callastro.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Vibrator
import android.util.DisplayMetrics
import android.util.Log
import android.view.SurfaceView
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.callastro.R
import com.callastro.databinding.ActivityDashboardVideoCallBinding
import com.callastro.viewModels.VideoCallViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import io.agora.rtc2.*
import io.agora.rtc2.video.VideoCanvas
import java.text.DecimalFormat
import java.util.Timer
import java.util.TimerTask

@AndroidEntryPoint
class DashboardVideoCallActivity : BaseActivity(), View.OnClickListener  {
    private lateinit var binding: ActivityDashboardVideoCallBinding
    private val viewModel: VideoCallViewModel by viewModels()
    private val PERMISSION_REQ_ID = 22
    val REQUESTED_PERMISSIONS = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA,
        Manifest.permission.READ_PHONE_STATE
    )
    private var callTimer: Timer? = null
    private var callTimerText: TextView? = null
    private var counter = 0
    val handlerStatusCheck = Handler(Looper.getMainLooper())
    var runnableStatusCheck: Runnable? = null
    private var profile = ""
    private var unique_id = ""
    // An integer that identifies the local user.
    lateinit var bottomdialog: BottomSheetDialog
    lateinit var name : TextView
    lateinit var image : CircleImageView
    private val uid = 0
    private var isJoined = false
    private var caller_id = ""
    private var token = ""
    private var channelName = ""
    private var agoraEngine: RtcEngine? = null
    val handlerStatusCheck1 = Handler(Looper.getMainLooper())
    var runnableStatusCheck1: Runnable? = null
    //SurfaceView to render local video in a Container.
    private var localSurfaceView: SurfaceView? = null
    //SurfaceView to render Remote video in a Container.
    private var remoteSurfaceView: SurfaceView? = null
    private var mMuted = false
    var user_id: String? = null
    var names: String? = ""
    var booking_id: String? = null
    var mMediaPlayer: MediaPlayer? = null
    var vib: Vibrator? = null

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard_video_call)


        user_id = intent.getStringExtra("userid").toString()
        names = intent.getStringExtra("list_userName").toString()
        profile= intent.getStringExtra("profile").toString()
        unique_id= intent.getStringExtra("unique_id").toString()
        callTimerText = findViewById(R.id.tvTime)

        if (!checkSelfPermission()) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, PERMISSION_REQ_ID)
        }
//
//        if (notification == "2") {
//            setupVideoSDKEngine()
//            setupLocalVideo()
//            // joinChannel(/*it.data?.agoraToken.toString()*/token,channelName/*it.data?.channelName.toString()*/)
//            joinChannel(token, channelName)
//            toast(token)
//            Log.d("joinchannel", joinChannel(token, channelName).toString())
//        } else {
////            viewAudioVideoCallResponse()
//        }


        setupVideoSDKEngine()
        playSound()
        viewAudioVideoCallResponse()
        binding.name.text = names
        binding.videoBtn.setOnClickListener(this)
        binding.buttonSwitchCamera.setOnClickListener {
            agoraEngine?.switchCamera()
        }
        binding.buttonCall.setOnClickListener {
            if (CommonUtils.isInternetAvailable(this@DashboardVideoCallActivity)) {
                viewModel.call_ring_end("Bearer "+userPref.getToken().toString(),channelName.toString())
            } else {
                Log.d("TAG", "onCreate: " + "else part")
                toast(this,"Please check internet connection.")
            }

            if (!isJoined) {
//                showMessage("Join a channel first")
            } else {
//              finish()
                Alertdialog1()
                stopSound()
                agoraEngine?.leaveChannel()
//                showMessage("You left the channel")
                // Stop remote video rendering.
                if (remoteSurfaceView != null) remoteSurfaceView?.visibility = View.GONE
                // Stop local video rendering.
                if (localSurfaceView != null) localSurfaceView?.visibility = View.GONE
                isJoined = false
            }
        }

//        joinChannel(token,channelName)
//        joinChannel("007eJxTYOhan7ztRn1kvZc5843u6VYvXWeu+fvn7to7Uy+7Zby0PCqvwGBobppoYmFiZGpgnmRibmRkaWFuaWJqlpaUmpJikmZp2c8gkdIQyMgQ6afJwAiFID4zQ0VlFQMDAKMaH3c=","xyz")

        viewModel.agoraGenerateTokenResponse.observe(this) {
            if (it.status == 1) {
                token = it.data?.agoraToken.toString()
                channelName = it.data?.channelName.toString()
                caller_id = it.data?.caller_id.toString()
                joinChannel(token,channelName)
                handlerStatusCheck.postDelayed(Runnable { //do something
                    handlerStatusCheck.postDelayed(runnableStatusCheck!!, 10000)
                    CallEndStatus()

                }.also { runnableStatusCheck = it }, 0)
                handlerStatusCheck1.postDelayed(Runnable { //do something
                    handlerStatusCheck1.postDelayed(runnableStatusCheck1!!, 10000)
                    if (CommonUtils.isInternetAvailable(this@DashboardVideoCallActivity)) {
                        viewModel.call_ring_status_save("Bearer "+userPref.getToken().toString(),it.data?.channelName.toString())
                        viewModel.call_ring("Bearer "+userPref.getToken().toString(),userPref.getid().toString(),user_id.toString(),unique_id.toString())
                    } else {
                        Log.d("TAG", "onCreate: " + "else part")
                        toast(this,"Please check internet connection.")
                    }
                }.also { runnableStatusCheck1 = it }, 0)
                it.message?.let { it1 -> toast(this,it1) }
            } else {
                it.message?.let { it1 -> toast(this,it1) }
            }
        }

        viewModel.call_ring_status_save_Response.observe(this){
            if (it.status == 1 ){
                if (it.data?.status == 0){
                    finish()
                }
            }
        }
//
//        handlerStatusCheck.postDelayed(Runnable { //do something
//            viewModel.call_ring("Bearer "+userPref.getToken().toString(),astroid.toString(),userPref.getUserId().toString(),caller_id.toString())
//            handlerStatusCheck.postDelayed(runnableStatusCheck!!, 500)
//        }.also { runnableStatusCheck = it }, 0)
//        viewModel.callRingResponse.observe(this){
//            if (it.status == 1){
//                if (it.data?.ringStatus == 1){
//                    finish()
//                }
//            }
//        }

        binding.buttonMute.setOnClickListener {
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


        viewModel.callerendResponse.observe(this){
            if (it.status == 1){
                if (it.data?.callEndStatus == 1){
                    CallEnd()
                }
            }
        }

        viewModel.givereviewResponse.observe(this){
            if (it.status == 1){
                finish()
            }else{
                snackbar(it.message.toString())
            }
        }
        viewModel.commonResponse.observe(this){
            if (it.status == 1){
//                finish()
                ReviewAndRating()
            }
        }
    }
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        openOptionsMenu()
    }
    private fun viewAudioVideoCallResponse() {
        viewModel.agora_generate_tokenApi("Bearer " + userPref.getToken(), user_id.toString(), "2")
    }

    private fun onRemoteUserVideoMuted(uid: Int, muted: Boolean) {
        val surfaceView = binding.remoteVideoView.getChildAt(0) as SurfaceView?
        val tag = surfaceView?.tag
        if (tag != null && tag as Int == uid) {
            surfaceView.visibility = if (muted) View.GONE else View.VISIBLE
        }
    }

    fun showMessage(message: String?) {
        runOnUiThread {
            Toast.makeText(
                applicationContext,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    fun playSound() {
        val am = getSystemService(AUDIO_SERVICE) as AudioManager

        when (am.ringerMode) {
            AudioManager.RINGER_MODE_SILENT -> Log.i("MyApp", "Silent mode")
            AudioManager.RINGER_MODE_VIBRATE -> {
                vib = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vib?.vibrate(2000)
                Log.i("MyApp", "Vibrate mode")
            }
            AudioManager.RINGER_MODE_NORMAL -> {
                /*val manager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
                manager.setStreamVolume(AudioManager.STREAM_MUSIC, 10, 0)
                val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
               mMediaPlayer = MediaPlayer.create(applicationContext, notification)
                if(manager.ringerMode != AudioManager.RINGER_MODE_SILENT)
                    mMediaPlayer?.isLooping = true
                    mMediaPlayer?.start()*/
                Log.i("MyApp", "Normal mode")
            }
        }
        /* if (mMediaPlayer == null) {
             mMediaPlayer = MediaPlayer.create(this, R.raw.call_ringtune)
             vib = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
             vib?.vibrate(500)
             mMediaPlayer?.isLooping = true
             mMediaPlayer?.start()
         } else mMediaPlayer?.start()*/

    }


    private fun setupVideoSDKEngine() {
        try {
            val config = RtcEngineConfig()
            config.mContext = baseContext
            config.mAppId = getString(R.string.agora_app_id)
            config.mEventHandler = mRtcEventHandler
            agoraEngine = RtcEngine.create(config)
            // By default, the video module is disabled, call enableVideo to enable it.
            agoraEngine?.enableVideo()

        } catch (e: Exception) {
            showMessage(e.toString())
        }
    }

    fun stopSound() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.stop()
            vib!!.cancel()
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    private var mRtcEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {
        // Listen for the remote host joining the channel to get the uid of the host.
        override fun onUserJoined(uid: Int, elapsed: Int) {
//            showMessage("Remote user joined $uid")
            // Set the remote video view
            runOnUiThread {
                setupRemoteVideo(uid)
            }
            callTimer = Timer()
            startCallTimer()
            stopSound()
        }

        override fun onJoinChannelSuccess(channel: String, uid: Int, elapsed: Int) {
            isJoined = true
//            showMessage("Joined Channel $channel")
        }

        override fun onUserOffline(uid: Int, reason: Int) {
//            showMessage("Remote user offline $uid $reason")
            runOnUiThread { remoteSurfaceView!!.visibility = View.GONE
                Alertdialog1()
            }
//            finish()

            agoraEngine?.leaveChannel()
            stopSound()
            Log.d(TAG, "___finishVideo____: "+"onUserOffline")
        }

        // remote user has toggled their video
        override fun onRemoteVideoStateChanged(uid: Int, state: Int, reason: Int, elapsed: Int) {
            super.onRemoteVideoStateChanged(uid, state, reason, elapsed)
            runOnUiThread {onRemoteUserVideoToggle(uid, state)}
        }

        /*override fun onUserMuteVideo(uid: Int, muted: Boolean) {
            runOnUiThread { onRemoteUserVideoMuted(uid, muted) }
        }*/

    }
    fun Alertdialog1(){
        val buinder = AlertDialog.Builder(this)
        buinder.setMessage("Call has been ended.")
        buinder.setIcon(R.drawable.alert)
        buinder.setTitle("Call Ended!!")
        buinder.setPositiveButton("OK") { dialogInterface, which ->
//            finish()
            ReviewAndRating()
        }
        val alertDialog: AlertDialog = buinder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()

    }
    fun ReviewAndRating() {
        // on below line we are creating a new bottom sheet dialog.
        bottomdialog = BottomSheetDialog(this)
        // on below line we are inflating a layout file which we have created.
        val view = layoutInflater.inflate(R.layout.astro_review_and_rating, null)
        val metrics = DisplayMetrics()
        windowManager?.defaultDisplay?.getMetrics(metrics)
        bottomdialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomdialog.behavior.peekHeight = metrics.heightPixels
        name = view.findViewById(R.id.name)
        image = view.findViewById(R.id.iv_image)
        var btnSubmit = view.findViewById<AppCompatButton>(R.id.btnSubmitratingreview)
        var rating_complete = view.findViewById<RatingBar>(R.id.rating_complete)
        var etReason = view.findViewById<EditText>(R.id.etReason)
        var cancel = view.findViewById<ImageView>(R.id.cancel)
        name.text = names.toString()
        Glide.with(this@DashboardVideoCallActivity).load(profile).into(image)

//        if (CommonUtils.isInternetAvailable(this@DashboardAudioCallActivity)) {
//            viewModel.strologer_details(
//                "Bearer " + userPref.getToken().toString(),
//                astroid.toString()
//            )
//        } else {
//            Log.d("TAG", "onCreate: " + "else part")
//            toast(this,"Please check internet connection.")
//        }


        cancel.setOnClickListener{
            bottomdialog.dismiss()
            finish()
        }

        btnSubmit.setOnClickListener{
            if (rating_complete.rating.toInt().equals(0)){
                toast(this@DashboardVideoCallActivity,"Please rate to our astrologer.")
            }else {
                if (CommonUtils.isInternetAvailable(this@DashboardVideoCallActivity)) {
                    viewModel.astro_give_review(
                        "Bearer " + userPref.getToken().toString(),
                        user_id.toString(),
                        rating_complete.rating.toString(),
                        etReason.text.toString(),
                    )
                } else {
                    Log.d("TAG", "onCreate: " + "else part")
                    toast(this, "Please check internet connection.")
                }
            }
        }
        bottomdialog.setCancelable(false)
        // on below line we are setting
        // content view to our view.
        bottomdialog.setContentView(view)
        // on below line we are calling
        // a show method to display a dialog.
        bottomdialog.show()

    }
    private fun setupRemoteVideo(uid: Int) {
        remoteSurfaceView = SurfaceView(baseContext)
        remoteSurfaceView?.setZOrderMediaOverlay(true)
        binding.remoteVideoView.addView(remoteSurfaceView)
        agoraEngine?.setupRemoteVideo(
            VideoCanvas(
                remoteSurfaceView,
                VideoCanvas.RENDER_MODE_FIT,
                uid
            )
        )
        // Display RemoteSurfaceView.
        remoteSurfaceView?.visibility = View.VISIBLE
    }


    private fun onRemoteUserVideoToggle(uid: Int, state: Int) {
//        val videoContainer = findViewById<FrameLayout>(com.maxtra.swasthrakshak.R.id.bg_video_container)
        val videoSurface = binding.remoteVideoView.getChildAt(0) as SurfaceView
        videoSurface.visibility = if (state == 0) View.GONE else View.VISIBLE

        // add an icon to let the other user know remote video has been disabled
        if (state == 0) {
            val noCamera = ImageView(this)
            noCamera.setImageResource(R.drawable.ic_video_cross_y)
            binding.remoteVideoView.addView(noCamera)
        } else {
            try{
                val noCamera = binding.remoteVideoView.getChildAt(1) as ImageView?
                 if (noCamera != null) {
                    binding.remoteVideoView.removeView(noCamera)
                }
            }catch(e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun joinChannel(token1: String, channelName1: String) {
        if (checkSelfPermission()) {
            val options = ChannelMediaOptions()

            // For a Video call, set the channel profile as COMMUNICATION.
            options.channelProfile = Constants.CHANNEL_PROFILE_COMMUNICATION
            // Set the client role as BROADCASTER or AUDIENCE according to the scenario.
            options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER
            // Display LocalSurfaceView.
            setupLocalVideo()
            localSurfaceView?.visibility = View.VISIBLE
            // Start local preview.
            agoraEngine?.startPreview()
            // Join the channel with a temp token.
            // You need to specify the user ID yourself, and ensure that it is unique in the channel.
            agoraEngine?.joinChannel(token1, channelName1, uid, options)
        } else {
            Toast.makeText(applicationContext, "Permissions was not granted", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setupLocalVideo() {

        // Create a SurfaceView object and add it as a child to the FrameLayout.
        localSurfaceView = SurfaceView(baseContext)
        localSurfaceView?.setZOrderMediaOverlay(true)
        binding.localVideoView.addView(localSurfaceView)
        // Pass the SurfaceView object to Agora so that it renders the local video.
        agoraEngine?.setupLocalVideo(
            VideoCanvas(
                localSurfaceView,
                VideoCanvas.RENDER_MODE_HIDDEN,
                0
            )
        )
    }


    override fun onDestroy() {
        super.onDestroy()
        agoraEngine?.stopPreview()
        agoraEngine?.leaveChannel()
        stopSound()
        callTimer?.cancel()
        // Destroy the engine in a sub-thread to avoid congestion
        Thread {
            RtcEngine.destroy()
            agoraEngine = null
        }.start()
    }
    private fun startCallTimer() {
        callTimer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (++counter == 1600) counter =
                    0 // for the taluxi app a call must not be longer than 1h.
                val formatter = DecimalFormat("00")
                val timerText =
                    formatter.format((counter / 60).toLong()) + ":" + formatter.format((counter % 60).toLong())
                runOnUiThread { callTimerText?.text = timerText }
            }
        }, 0, 1000)
    }
    @SuppressLint("ResourceAsColor")
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
                    binding.localVideoView.setBackgroundColor(R.color.yellow)
                    binding.localVideoView.background =
                        ContextCompat.getDrawable(this, R.drawable.ic_video_cross_y)
                    btn.setImageResource(R.drawable.ic_video_cross_y)
                } else {
                    binding.localVideoView.visibility = View.VISIBLE
                    btn.setImageResource(R.drawable.ic_video_new)
                }

                val videoSurface = binding.localVideoView.getChildAt(0) as SurfaceView?
                videoSurface?.setZOrderMediaOverlay(!btn.isSelected)
//                videoSurface?.visibility = if (btn.isSelected) View.GONE else View.VISIBLE
                if (btn.isSelected){
                    videoSurface?.visibility = View.GONE
                }else{
                    videoSurface?.visibility = View.VISIBLE
                }

            }
        }
    }

    fun CallEndStatus(){
        if (CommonUtils.isInternetAvailable(this@DashboardVideoCallActivity)) {
            viewModel.call_end_by_status(
                "Bearer "+userPref.getToken().toString(), caller_id
            )
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }
    }

    private fun CallEnd() {
        if (CommonUtils.isInternetAvailable(this@DashboardVideoCallActivity)) {
            viewModel.call_end(
                "Bearer "+userPref.getToken().toString(),
                "0",userPref.getid().toString(),user_id.toString(),caller_id.toString(),"3",unique_id

                /* userPref.getChannelName().toString()*/
            )
        } else {
            toast(this,"Please check internet connection.")
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        if (CommonUtils.isInternetAvailable(this@DashboardVideoCallActivity)) {
            viewModel.call_ring_end("Bearer "+userPref.getToken().toString(),channelName.toString())
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }

        if (!isJoined) {
//                showMessage("Join a channel first")
        } else {
//              finish()
            Alertdialog1()
            Log.d(TAG, "___finishVideo____: "+"onCallEnd")

            stopSound()
            agoraEngine?.leaveChannel()
//                showMessage("You left the channel")
            // Stop remote video rendering.
            if (remoteSurfaceView != null) remoteSurfaceView?.visibility = View.GONE
            // Stop local video rendering.
            if (localSurfaceView != null) localSurfaceView?.visibility = View.GONE
            isJoined = false
        }
    }


}