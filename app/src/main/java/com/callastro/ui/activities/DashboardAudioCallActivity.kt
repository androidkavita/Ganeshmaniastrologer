package com.callastro.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Vibrator
import android.util.DisplayMetrics
import android.util.Log
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
import com.callastro.databinding.ActivityDashboardAudioCallBinding
import com.callastro.viewModels.VideoCallViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.astrorahiastrologer.util.toast
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import io.agora.rtc2.*
import java.text.DecimalFormat
import java.util.*

@AndroidEntryPoint
class DashboardAudioCallActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDashboardAudioCallBinding
    private val viewModel: VideoCallViewModel by viewModels()
    var user_id: String? = null
    var booking_id: String? = null
    var patient_image: String? = null
    var callend = false
    var patient_number: String? = null
    private val PERMISSION_REQ_ID = 22
    val handlerStatusCheck = Handler(Looper.getMainLooper())
    var runnableStatusCheck: Runnable? = null
    val handlerStatusCheck1 = Handler(Looper.getMainLooper())
    var runnableStatusCheck1: Runnable? = null
    private val REQUESTED_PERMISSIONS = arrayOf(
        Manifest.permission.RECORD_AUDIO
    )
    private var token = ""
    private var caller_id = ""
    private var channelName = ""
    private var profile = ""
    private var names = ""
    private var unique_id = ""
    lateinit var name :TextView
    lateinit var image : CircleImageView
    // An integer that identifies the local user.
    private val uid = 0
    // Track the status of your connection
    private var isJoined = false
    private var mMuted = false
    private var callTimer: Timer? = null
    private var callTimerText: TextView? = null
    private var counter = 0
    // Agora engine instance
    private var agoraEngine: RtcEngine? = null
    var shahbaz = ""
    lateinit var bottomdialog: BottomSheetDialog
    private fun checkSelfPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            REQUESTED_PERMISSIONS[0]
        ) == PackageManager.PERMISSION_GRANTED
    }
    var mMediaPlayer: MediaPlayer? = null
    var vib: Vibrator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard_audio_call)
        shahbaz = "1"

        if (intent != null){
            user_id = intent.getStringExtra("user_id").toString()
            profile = intent.getStringExtra("profile").toString()
            names = intent.getStringExtra("list_userName").toString()
            unique_id= intent.getStringExtra("unique_id").toString()
        }
        binding.tvName.text = names
        binding.tvTime.text = "Ringing"

        if (!checkSelfPermission()) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, PERMISSION_REQ_ID)
        }

        playSound()
        viewAudioVideoCallResponse()
        setupVoiceSDKEngine()
        callTimerText = findViewById(R.id.tvTime)
        binding.ivCallEnd.setOnClickListener(this)

        viewModel.agoraGenerateTokenResponse.observe(this) {
            if (it.status == 1) {
                binding.tvName.text = it.data?.to_name.toString()
                Glide.with(this@DashboardAudioCallActivity).load(it.data?.to_profile.toString()).into(binding.ivProfileImage)
                token = it.data?.agoraToken.toString()
                caller_id = it.data?.caller_id.toString()
                channelName = it.data?.channelName.toString()
                joinChannel(it.data?.agoraToken.toString(), channelName)
                handlerStatusCheck.postDelayed(Runnable { //do something
                    handlerStatusCheck.postDelayed(runnableStatusCheck!!, 10000)
                    CallEndStatus()
                }.also { runnableStatusCheck = it }, 0)
                handlerStatusCheck.postDelayed(Runnable { //do something
                    handlerStatusCheck.postDelayed(runnableStatusCheck!!, 5000)
                    if (CommonUtils.isInternetAvailable(this@DashboardAudioCallActivity)) {

                        viewModel.call_end_by_status("Bearer "+
                            userPref.getToken().toString(), it.data?.caller_id.toString()
                        )
                        viewModel.call_ring_status_save("Bearer "+userPref.getToken().toString(),channelName.toString())
                        viewModel.call_ring("Bearer "+userPref.getToken().toString(),userPref.getid().toString(),user_id.toString(),unique_id.toString())
                    } else {
                        toast(this,"Please check internet connection.")
                    }
                }.also { runnableStatusCheck = it }, 0)

//                handlerStatusCheck1.postDelayed(Runnable { //do something
//                    handlerStatusCheck1.postDelayed(runnableStatusCheck1!!, 5000)
//                    if (CommonUtils.isInternetAvailable(this@DashboardAudioCallActivity)) {
//                        viewModel.call_end_by_status(userPref.getToken().toString(), it.data?.caller_id.toString())
////                        viewModel.call_ring_status_save("Bearer "+userPref.getToken().toString(),it.data?.channelName.toString())
//                    } else {
//                        Log.d("TAG", "onCreate: " + "else part")
//                        toast(this,"Please check internet connection.")
//                    }
//                }.also { runnableStatusCheck1 = it }, 0)
//                it.message?.let { it1 -> toast(this,it1) }
            } else {
                it.message?.let { it1 -> toast(this,it1) }
            }
        }

        viewModel.call_ring_status_save_Response.observe(this){
            if (it.status == 1 ){
                if (it.data?.status == 0){
                    toast("callringstatus")
                    finish()
                }
            }
        }

        viewModel.callRingResponse.observe(this){
            if (it.status == 1){
                if (it.data?.ringStatus == 1){
                    ReviewAndRating()
                }
            }
        }

        viewModel.givereviewResponse.observe(this){
            if (it.status == 1){
                toast("review")
                finish()
            }else{
                snackbar(it.message.toString())
            }
        }
//        viewModel.commonResponse.observe(this){
//            if (it.status == 1 ){
//
//            }
//        }

        binding.btnUnmute.setOnClickListener {
            mMuted = !mMuted
            agoraEngine?.muteLocalAudioStream(mMuted)
            val res: Int = if (mMuted) {
                R.drawable.ic_unmute
            } else {
                R.drawable.ic_mute
            }
            binding.btnUnmute.setImageResource(res)
            onRemoteUserVoiceMuted(uid, mMuted)
        }

        viewModel.callerendResponse.observe(this){
            if (it.status == 1){
                if (it.data?.callEndStatus == 1){
                    CallEnd()

                }
            }
        }

        viewModel.commonResponse.observe(this){
            if (it.status == 1){
                if (callend==true){
                }else{
                    Alertdialog1()
                }
            }
        }

//        joinChannel(token,channelName)
//        joinChannel("007eJxTYOhan7ztRn1kvZc5843u6VYvXWeu+fvn7to7Uy+7Zby0PCqvwGBobppoYmFiZGpgnmRibmRkaWFuaWJqlpaUmpJikmZp2c8gkdIQyMgQ6afJwAiFID4zQ0VlFQMDAKMaH3c=","xyz")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        openOptionsMenu()
    }

    @SuppressLint("MissingInflatedId")
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
        Glide.with(this@DashboardAudioCallActivity).load(profile).into(image)

        cancel.setOnClickListener{
            bottomdialog.dismiss()
            finish()
        }

        btnSubmit.setOnClickListener{
            if (rating_complete.rating.toInt().equals(0)){
                toast(this@DashboardAudioCallActivity,"Please rate to our astrologer.")
            }else {
                if (CommonUtils.isInternetAvailable(this@DashboardAudioCallActivity)) {
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

    fun playSound() {
        val am = getSystemService(AUDIO_SERVICE) as AudioManager

        when (am.ringerMode) {
            AudioManager.RINGER_MODE_SILENT -> Log.i("MyApp", "Silent mode")
            AudioManager.RINGER_MODE_VIBRATE -> {
//                vib = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//                vib?.vibrate(2000)
                Log.i("MyApp", "Vibrate mode")
            }
            AudioManager.RINGER_MODE_NORMAL -> {
                /*    val manager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
                    manager.setStreamVolume(AudioManager.STREAM_MUSIC, 10, 0)
                    val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
                    mMediaPlayer = MediaPlayer.create(applicationContext, notification)
                    if (manager.ringerMode != AudioManager.RINGER_MODE_SILENT)
                        mMediaPlayer?.isLooping = true
                    mMediaPlayer?.start()*/
                Log.i("MyApp", "Normal mode")
            }
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

    fun stopSound() {
        if (mMediaPlayer != null) {
            mMediaPlayer?.stop()
            vib?.cancel()
            mMediaPlayer?.release()
            mMediaPlayer = null
        }
    }

    fun onSwitchSpeakerphoneClicked(view: View) {
        val iv: ImageView = view as ImageView
        if (iv.isSelected) {
            iv.isSelected = false
            iv.clearColorFilter()
        } else {
            iv.isSelected = true
            iv.setColorFilter(resources.getColor(R.color.theme_purple), PorterDuff.Mode.MULTIPLY)
        }

        // Enables/Disables the audio playback route to the speakerphone.
        // This method sets whether the audio is routed to the speakerphone or earpiece. After calling this method, the SDK returns the onAudioRouteChanged callback to indicate the changes.
        agoraEngine?.setEnableSpeakerphone(iv.isSelected)
    }

    fun Alertdialog1(){
        val buinder = AlertDialog.Builder(this)
        buinder.setMessage("Call has been ended.")
        buinder.setIcon(R.drawable.alert)
        buinder.setTitle("Call Ended!!")
        buinder.setPositiveButton("OK") { dialogInterface, which ->

            ReviewAndRating()
        }
        val alertDialog: AlertDialog = buinder.create()
        alertDialog.setCancelable(false)

        try {
            alertDialog.show()
        }
        catch (e: WindowManager.BadTokenException) {
            e.printStackTrace()
        }
//        alertDialog.show()

    }

    private fun setupVoiceSDKEngine() {
        try {
            val config = RtcEngineConfig()
            config.mContext = baseContext
            config.mAppId = getString(R.string.app_id)
            config.mEventHandler = mRtcEventHandler
            agoraEngine = RtcEngine.create(config)
        } catch (e: Exception) {
            throw RuntimeException("Check the error.")
        }
    }

    private var mRtcEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {
        // Listen for the remote user joining the channel.
        override fun onUserJoined(uid: Int, elapsed: Int) {
            runOnUiThread {
//                binding.infoText.text = "Remote user joined: $uid"
            }
            callTimer = Timer()
            startCallTimer()
            stopSound()
        }

        override fun onJoinChannelSuccess(channel: String, uid: Int, elapsed: Int) {
            // Successfully joined a channel
            isJoined = true
//            showMessage("Joined Channel $channel")
            runOnUiThread {
//                binding.infoText.text = "Waiting for a remote user to join"
            }
        }

        override fun onUserOffline(uid: Int, reason: Int) {
            // Listen for remote users leaving the channel
//            showMessage("Remote user offline $uid $reason")
            if (isJoined) runOnUiThread {
//                binding.infoText.text = "Waiting for a remote user to join"
                callend=true
                Alertdialog1()
            }

            stopSound()
            agoraEngine?.leaveChannel()
//            if (shahbaz == "1"){
////                Alertdialog1()
//                shahbaz = "2"
//            }
        }

//        override fun onLeaveChannel(stats: RtcStats) {
//            try {
//                // Listen for the local user leaving the channel
//                runOnUiThread {
//                    binding.infoText.text = "Press the button to join a channel"
//                    toast("onleavechannel")
//                    Alertdialog1()
//                }
//                isJoined = false
////            finish()
//                stopSound()
//                agoraEngine?.leaveChannel()
//                if (shahbaz == "1") {
////                Alertdialog1()
//                    shahbaz = "2"
//                }
//            }catch (e:Exception){
//                e.printStackTrace()
//            }
//        }

        /**
         * Occurs when a remote user stops/resumes sending the audio stream.
         * The SDK triggers this callback when the remote user stops or resumes sending the audio stream by calling the muteLocalAudioStream method.
         *
         * @param uid ID of the remote user.
         * @param muted Whether the remote user's audio stream is muted/unmuted:
         *
         * true: Muted.
         * false: Unmuted.
         */
        override fun onUserMuteAudio(uid: Int, muted: Boolean) { // Tutorial Step 6
            runOnUiThread { onRemoteUserVoiceMuted(uid, muted) }
        }
    }

    private fun onRemoteUserVoiceMuted(uid: Int, muted: Boolean) {
//        showLongToast(
//            java.lang.String.format(
//                Locale.US, "user %d muted or unmuted %b",
//                uid and 0xFFFFFFFFL.toInt(), muted
//            )
//        )
    }

    fun showLongToast(msg: String?) {
        runOnUiThread { Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show() }
    }

    private fun joinChannel(token: String, channelName: String) {
        val options = ChannelMediaOptions()
        options.autoSubscribeAudio = true
        // Set both clients as the BROADCASTER.
        options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER
        // Set the channel profile as BROADCASTING.
        options.channelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING

        // Join the channel with a temp token.
        // You need to specify the user ID yourself, and ensure that it is unique in the channel.
        agoraEngine?.joinChannel(token, channelName, 0, options)
    }

    private fun viewAudioVideoCallResponse() {
        if (CommonUtils.isInternetAvailable(this@DashboardAudioCallActivity)) {
            viewModel.agora_generate_tokenApi("Bearer " + userPref.getToken(), user_id.toString(), "1")
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_CallEnd -> {
                if (CommonUtils.isInternetAvailable(this@DashboardAudioCallActivity)) {
                    viewModel.call_ring_end("Bearer "+userPref.getToken().toString(),channelName.toString())
                } else {
                    Log.d("TAG", "onCreate: " + "else part")
                    toast(this,"Please check internet connection.")
                }
                if (isJoined) {
                    showMessage("You left the channel")
                    agoraEngine?.leaveChannel()

                    stopSound()
                    isJoined = false
                } else {
                    callend=true
                    Alertdialog1()

                    showMessage("Join a channel first")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        agoraEngine?.leaveChannel()
        stopSound()
        callTimer?.cancel()
        // Destroy the engine in a sub-thread to avoid congestion
        Thread {
            RtcEngine.destroy()
            agoraEngine = null
        }.start()
    }

    fun CallEndStatus(){
        viewModel.call_end_by_status(
            userPref.getToken().toString(), caller_id
        )
    }

    private fun CallEnd() {
        if (CommonUtils.isInternetAvailable(this@DashboardAudioCallActivity)) {
            viewModel.call_end(
                "Bearer "+userPref.getToken().toString(),
                callTimerText?.text.toString(),userPref.getid().toString(),user_id.toString(),caller_id.toString(),"2",unique_id.toString()

                /* userPref.getChannelName().toString()*/
            )
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        if (CommonUtils.isInternetAvailable(this@DashboardAudioCallActivity)) {
            viewModel.call_ring_end("Bearer "+userPref.getToken().toString(),channelName.toString())
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }
        if (isJoined) {
            showMessage("You left the channel")
            agoraEngine?.leaveChannel()
            stopSound()
            isJoined = false
        } else {
            showMessage("Join a channel first")
        }

    }

}