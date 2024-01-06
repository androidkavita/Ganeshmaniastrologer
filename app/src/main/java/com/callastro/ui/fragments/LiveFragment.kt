package com.callastro.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Switch
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.callastro.adapters.GoLiveAdapter
import com.callastro.baseClass.BaseFragment
import com.callastro.databinding.FragmentLiveBinding
import com.callastro.model.LiveCommentsModelClassData
import com.callastro.viewModels.VideoCallViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import dagger.hilt.android.AndroidEntryPoint
import io.agora.rtc2.*
import io.agora.rtc2.video.VideoCanvas

@AndroidEntryPoint
class LiveFragment : BaseFragment() {
    lateinit var binding : FragmentLiveBinding
    private val PERMISSION_REQ_ID = 22
    private val REQUESTED_PERMISSIONS = arrayOf<String>(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA
    )

    private fun checkSelfPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(
                requireContext(),
                REQUESTED_PERMISSIONS[0]
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                requireContext(),
                REQUESTED_PERMISSIONS[1]
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            false
        } else true
    }

    fun showMessage(message: String?) {
        activity?.runOnUiThread {
            Toast.makeText(
                requireContext(),
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

    lateinit var start:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLiveBinding.inflate(inflater, container, false)
//        etTopic = requireArguments().getString("topic").toString()
        if (!checkSelfPermission()) {
            ActivityCompat.requestPermissions(requireActivity(), REQUESTED_PERMISSIONS, PERMISSION_REQ_ID);
        }
        start = ""
        if (start == ""){
            start = "0"
            if (CommonUtils.isInternetAvailable(requireContext())) {
                viewModel.live_agora_generate_token("Bearer "+userPref.getToken(),"")
            } else {
                Log.d("TAG", "onCreate: " + "else part")
                toast(requireContext(),"Please check internet connection.")
            }

            viewModel.goLiveResponse.observe(viewLifecycleOwner) {
                if (it.status == 1) {
                    agoratoken = it.data?.agoraToken.toString()
                    channelName = it.data?.channelName.toString()
                    setupVideoSDKEngine();
                    joinChannel()
                    setupLocalVideo()
//                userPref.setAgoraToken(it.data?.agoraToken.toString())
//                userPref.setagoraChannelName(it.data?.channelName.toString())
                } else {
                    it.message?.let { it1 -> toast(requireContext(),it1) }
                }
            }
            start = "0"
        }else{
            fragmentManager?.popBackStack()
        }

        binding.leave.setOnClickListener {
            leaveChannel()
//            remoteSurfaceView!!.visibility = View.GONE
            localSurfaceView!!.visibility = View.GONE
        }

        viewModel.commonResponse.observe(viewLifecycleOwner){
            if (it.status==1){
                toast(requireContext(),it.message.toString())
                fragmentManager?.popBackStack()
            }
        }

        handlerStatusCheck.postDelayed(Runnable {
            handlerStatusCheck.postDelayed(runnableStatusCheck!!, 10000)
            if (CommonUtils.isInternetAvailable(requireContext())) {
                viewModel.get_live_comments(
                    "Bearer "+userPref.getToken().toString(),channelName
                )
            } else {
                Log.d("TAG", "onCreate: " + "else part")
                toast(requireContext(),"Please check internet connection.")
            }

        }.also { runnableStatusCheck = it }, 0)

        viewModel.liveCommentsModelClass.observe(viewLifecycleOwner){
            if (it?.status == 1){
                list.clear()
                list.addAll(it.data)
                adapter = GoLiveAdapter(requireContext(),list)
                binding.comment.adapter = adapter
            }
        }
//        binding.imageCommentSend.setOnClickListener {
//            viewModel.add_comment(userPref.getUserId().toString(),astro_id,binding.etSend.text.toString(),"1")
//        }
//        viewModel.progressBarStatus.observe(viewLifecycleOwner) {
//            if (it) {
//                showProgressDialog()
//            } else {
//                hideProgressDialog()
//            }
//        }
//        viewModel.commonResponse.observe(viewLifecycleOwner){
//            if (it.status == 1){
//                toast(requireContext(),it.message.toString())
//                binding.etSend.setText("")
//            }
//        }
        return binding.root
    }
    private fun setupVideoSDKEngine() {
        try {
            val config = RtcEngineConfig()
            config.mContext = requireContext()
            config.mAppId = appId
            config.mEventHandler = mRtcEventHandler
            agoraEngine = RtcEngine.create(config)
            // By default, the video module is disabled, call enableVideo to enable it.
            agoraEngine!!.enableVideo()
        } catch (e: Exception) {
            showMessage(e.toString())
        }
    }

    private val mRtcEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {
        // Listen for the remote host joining the channel to get the uid of the host.
        override fun onUserJoined(uid: Int, elapsed: Int) {
            showMessage("Remote user joined $uid")
            /*if (!audienceRole!!.isChecked)*/ return
            // Set the remote video view
            activity?.runOnUiThread { setupLocalVideo() }
        }

        override fun onJoinChannelSuccess(channel: String, uid: Int, elapsed: Int) {
            isJoined = true
            showMessage("Joined Channel $channel")
        }

        override fun onUserOffline(uid: Int, reason: Int) {
            showMessage("Remote user offline $uid $reason")
            activity?.runOnUiThread { remoteSurfaceView!!.visibility = View.GONE }
        }
    }

//    private fun setupRemoteVideo(uid: Int) {
////        val container = view?.findViewById<FrameLayout>(R.id.remote_video_view_container)
//        var container = activity?.findViewById<FrameLayout>(R.id.frame_layout_local_video)
//        remoteSurfaceView = SurfaceView(requireContext())
//        remoteSurfaceView!!.setZOrderMediaOverlay(true)
//        container?.addView(remoteSurfaceView)
//        agoraEngine!!.setupRemoteVideo(
//            VideoCanvas(
//                remoteSurfaceView,
//                VideoCanvas.RENDER_MODE_FIT,
//                uid
//            )
//        )
//        // Display RemoteSurfaceView.
//        remoteSurfaceView!!.setVisibility(View.VISIBLE)
//    }

    private fun setupLocalVideo() {
//        val container = view.findViewById<FrameLayout>(R.id.local_video_view_container)
        // Create a SurfaceView object and add it as a child to the FrameLayout.
        localSurfaceView = SurfaceView(requireContext())
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
            agoraEngine!!.joinChannel(agoratoken, channelName, uid, options)
        } else {
            Toast.makeText(requireContext(), "Permissions was not granted", Toast.LENGTH_SHORT).show()
        }
    }

    fun leaveChannel() {
//        if (!isJoined) {
//            showMessage("Join a channel first")
//        } else {
//            agoraEngine!!.leaveChannel()
            showMessage("You left the channel")
            // Stop remote video rendering.
            if (remoteSurfaceView != null) remoteSurfaceView!!.visibility = View.GONE
            // Stop local video rendering.
            if (localSurfaceView != null) localSurfaceView!!.visibility = View.GONE
            isJoined = false
        start = "0"
        if (CommonUtils.isInternetAvailable(requireContext())) {
            viewModel.delete_live_astro("Bearer "+userPref.getToken())
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(requireContext(),"Please check internet connection.")
        }

        fragmentManager?.popBackStack()
//        }
//        audienceRole!!.isEnabled = true // Enable the switch
    }

    override fun onDestroy()
     {
        super.onDestroy()
         showMessage("You left the channel")
         // Stop remote video rendering.
         if (remoteSurfaceView != null) remoteSurfaceView!!.visibility = View.GONE
         // Stop local video rendering.
         if (localSurfaceView != null) localSurfaceView!!.visibility = View.GONE
         isJoined = false
         start = "0"
         if (CommonUtils.isInternetAvailable(requireContext())) {
             viewModel.delete_live_astro("Bearer "+userPref.getToken())
         } else {
             Log.d("TAG", "onCreate: " + "else part")
             toast(requireContext(),"Please check internet connection.")
         }
         fragmentManager?.popBackStack()
    }
}