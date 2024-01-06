package com.callastro.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.callastro.R
import com.callastro.adapters.ChatAdapter2
import com.callastro.databinding.ActivityChatwithUsBinding
import com.callastro.model.ChatListMessageData
import com.callastro.viewModels.ChatWithUsViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.astrorahiastrologer.util.toast
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ChatwithUsActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding : ActivityChatwithUsBinding
    private val  viewModel: ChatWithUsViewModel by viewModels()
    var getUser_id: String? = null
    var getUser_name: String? = null
    var getChatListId: String? = null
    var unique_id: String? = null
    var chatAdapter: ChatAdapter2? = null
    private var callTimer: Timer? = null
    private var callTimerText: TextView? = null
    private var counter = 0
    private val SPLASH_TIMEOUT: Long = 1000
    val handlerStatusCheck = Handler(Looper.getMainLooper())
    var runnableStatusCheck: Runnable? = null
    val handlerCallEndStatusCheck = Handler(Looper.getMainLooper())
    var runnableCallEndStatusCheck: Runnable? = null
    private val messageList: ArrayList<ChatListMessageData> = ArrayList<ChatListMessageData>()
    var finalstate:String = "1"
    var isscrollable = false
    var token = ""
    var appid = ""
    var channel_name = ""
    lateinit var bottomdialog: BottomSheetDialog
    var homepage = ""
    var caller_id = ""
    private var profile = ""
    var flag = ""
    var userId = ""
    lateinit var name :TextView
    lateinit var image : CircleImageView
    var type = ""
    var isclick:Boolean = false
    var count :Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chatwith_us)
        binding.backArrow.setOnClickListener {
//            CallEnd()
            AlertdialogExit()
        }
        callTimer = Timer()

        getChatListId = intent.getStringExtra("list_idSub").toString()

        getUser_id = intent.getStringExtra("list_id").toString()
        getUser_name = intent.getStringExtra("list_userName").toString()
        caller_id = intent.getStringExtra("caller_id").toString()
        profile = intent.getStringExtra("profile").toString()
        binding.tvHeadName.text = getUser_name

        if (intent != null) {
            userId = intent.getStringExtra("Userid").toString()
            appid = intent.getStringExtra("app_id").toString()
            token = intent.getStringExtra("agora_token").toString()
            channel_name = intent.getStringExtra("channel_name").toString()
            homepage = intent.getStringExtra("homepage").toString()

        }
        binding.rvUserChat.isVerticalScrollBarEnabled = isscrollable != false
        isscrollable = true




        startCallTimer()
        moveForward()
        messageList()
        viewModel.agoraChatListMessageResponse.observe(this) {
            if (it.status == 1) {
                isscrollable = false
                messageList.clear()
                messageList.addAll(it.data)
                chatAdapter = ChatAdapter2(this, messageList)
                if (count == 0){
                    binding.rvUserChat.adapter = chatAdapter
                    binding.rvUserChat.scrollToPosition(messageList.size - 1);
                    binding.rvUserChat.smoothScrollToPosition(binding.rvUserChat.adapter!!.itemCount)
                    binding.rvUserChat.stopScroll()
                    chatAdapter!!.notifyDataSetChanged()
                    count = messageList.size
                }else if (count < messageList.size){
                    binding.rvUserChat.adapter = chatAdapter
                    binding.rvUserChat.scrollToPosition(messageList.size - 1);
                    binding.rvUserChat.smoothScrollToPosition(binding.rvUserChat.adapter!!.itemCount)
                    chatAdapter?.notifyDataSetChanged()
                    count = messageList.size
                }
                Log.e("chat", it.message.toString())
            } else {
                snackbar(it?.message!!)
            }
        }
        binding.ivChatSend.setOnClickListener(this)
        binding.endchat.setOnClickListener(this)


        viewModel.checkChatEndResponse.observe(this){
            if (it.status == 1){
                if (it.data.is_chat_end == 1){
                    if (finalstate == "1"){
                        CallEnd()
                        binding.tvTime.visibility = View.GONE
                        if (it.data.user_type == 2){
                            Alertdialog1()
                        }
                        finalstate = "2"
                    }
                }
            }
        }


        handlerCallEndStatusCheck.postDelayed(Runnable { //do // something
            if (homepage == "homepage"){
                handlerCallEndStatusCheck.removeCallbacks(runnableCallEndStatusCheck!!)
                binding.tvTime.visibility = View.GONE
                binding.endchat.visibility = View.GONE
                binding.messgnikrna.visibility = View.GONE
            }else{
                if (CommonUtils.isInternetAvailable(this)) {
                    viewModel.check_chat_end(
                        "Bearer "+userPref.getToken().toString(), caller_id
                    )
                } else {
                    Log.d("TAG", "onCreate: " + "else part")
                    toast(this,"Please check internet connection.")
                }

            }

            handlerCallEndStatusCheck.postDelayed(runnableCallEndStatusCheck!!, 5000)
        }.also { runnableCallEndStatusCheck = it }, 0)

        viewModel.commonResponse.observe(this){
            if (it.status == 1){
                toast(it.message.toString())
//                finish()
                Alertdialog1()
            }
        }

        viewModel.givereviewResponse.observe(this){
            if (it.status == 1){
                finish()
            }
        }
    }

    fun AlertdialogExit() {
        val buinder = AlertDialog.Builder(this)

        buinder.setMessage("Are you sure, You want to leave chat.")
        buinder.setIcon(R.drawable.alert)
        buinder.setTitle("Chat End")

        buinder.setPositiveButton("Yes") { dialogInterface, which ->
            CallEnd()
        }
        buinder.setNegativeButton("No") { dialogInterface, which ->

        }
        val alertDialog: AlertDialog = buinder.create()
        alertDialog.setCancelable(true)
        alertDialog.show()
    }

    fun Alertdialog1(){
        val buinder = AlertDialog.Builder(this)
        buinder.setMessage("Chat has been closed")
        buinder.setIcon(R.drawable.alert)
        buinder.setTitle("Chat Closed!!")

        buinder.setPositiveButton("OK") { dialogInterface, which ->
//                        val intent = Intent(this, CreateOrder::class.java)
//                        intent.putExtra("POPUP_FLAG", "yes")
//                        startActivity(intent)
            ReviewAndRating()
        }
        val alertDialog: AlertDialog = buinder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
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

        name.text = getUser_name.toString()
        Glide.with(this@ChatwithUsActivity).load(profile).into(image)

        cancel.setOnClickListener{
            bottomdialog.dismiss()
            finish()
        }

        btnSubmit.setOnClickListener{
            if (rating_complete.rating.toInt().equals(0)){
                toast(this@ChatwithUsActivity,"Please rate to our astrologer.")
            }else {
                if (CommonUtils.isInternetAvailable(this@ChatwithUsActivity)) {
                    viewModel.astro_give_review(
                        "Bearer " + userPref.getToken().toString(),
                        userId.toString(),
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
//        if (counter< Calculatetime){
        callTimer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (++counter == 3600000) counter =
                    0 // for the taluxi app a call must not be longer than 1h.
                val formatter = DecimalFormat("00")
                val timerText =
                    formatter.format((counter / 60).toLong()) + ":" + formatter.format((counter % 60).toLong())
                runOnUiThread {
                    if (counter == 0){
                        if (CommonUtils.isInternetAvailable(this@ChatwithUsActivity)) {
                            CallEnd()
                        } else {
                            Log.d("TAG", "onCreate: " + "else part")
                            toast(this@ChatwithUsActivity,"Please check internet connection.")
                        }

//                        finish()
//                        if (isJoined) {
////                    viewCallStatusApi()
//                            showMessage("You left the channel")
//                            agoraEngine?.leaveChannel()
//                            finish()
//                            isJoined = false
//                            sendBroadcast(Intent(Constant.ACTION_HANG_UP_ANSWERED_CALL))
//
//                        } else {
////                    showMessage("Join a channel first")
//
//                        }
                    }else{
//                        apitimer = timerText
                        binding.tvTime.text = timerText
                    }
                }
                Thread.sleep(1000);
            }
        }, 0, 1000)
//        }else{
//            snackbar("you don't have enough balence.")
//        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        CallEnd()
    }
    override fun onDestroy() {
        super.onDestroy()
        CallEnd()
    }
    fun messagedata() {
        viewModel.chatAgoraSendResponse.observe(this) {
            if (it.status == 1) {
                isclick = false
                binding.etChatMsg.setText("")
//                caller_id = it.data?.caller_id.toString()
                messageList()
            } else {
                binding.etChatMsg.setText("")
            }
        }
    }

    private fun viewChatAgoraTestResponse() {
        if (CommonUtils.isInternetAvailable(this)) {
            viewModel.chatagoraApi("Bearer "+userPref.getToken().toString(),
                getUser_id.toString(),
//            "62",
                binding.etChatMsg.text.toString(),"1")
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }

    }

    private fun moveForward() {
        handlerStatusCheck.postDelayed(Runnable { //do // something
            messageList()
            handlerStatusCheck.postDelayed(runnableStatusCheck!!, 10 * 500)
        }.also { runnableStatusCheck = it }, 500)
    }

    fun messageList() {
        if (CommonUtils.isInternetAvailable(this)) {
            viewModel.chat_list_MessageApi("Bearer "+userPref.getToken().toString(), getUser_id.toString())
        } else {
            toast(this,"Please check internet connection.")
        }

    }

    private fun showLog(content: String, showToast: Boolean) {
        if (TextUtils.isEmpty(content)) {
            return
        }
        runOnUiThread {
            if (showToast) {
                Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
            }
            val builder = java.lang.StringBuilder()
            builder.append(
                SimpleDateFormat(
                    "yyyy-MM-dd  hh:mm a : ",
                    Locale.getDefault()
                ).format(Date())
            )
        }
    }
    private fun CallEnd() {

        if (CommonUtils.isInternetAvailable(this)) {
            viewModel.call_end(
                "Bearer "+userPref.getToken().toString(),
                "00:00",userPref.getid().toString(),getUser_id.toString(), caller_id,"1",caller_id
                /* userPref.getChannelName().toString()*/
            )
        } else {
            toast(this,"Please check internet connection.")
        }

    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivChatSend -> {
                if (binding.etChatMsg.text.toString() == "") {
                    toast("Pls enter text")
                } else {
                    if (isclick == false) {
                        viewChatAgoraTestResponse()
                        isclick = true
                    }
                    messagedata()
                }
            }
            R.id.endchat ->{
                binding.tvTime.visibility = View.GONE
                AlertdialogExit()
//                CallEnd()
//                finish()
            }
        }
    }
}