package com.callastro.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.callastro.R
import com.callastro.adapters.CallHomeAdapter
import com.callastro.databinding.ActivityCallRequestBinding
import com.callastro.model.Chat_Call_ResponseData
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.astrorahiastrologer.viewmodel.CallRequestDetailsViewModel
import com.maxtra.astrorahiastrologer.viewmodel.ChatCallViewModel
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header.view.*

@AndroidEntryPoint
class CallRequest : BaseActivity(), CallHomeAdapter.OnClick {
    lateinit var binding: ActivityCallRequestBinding
    private val viewModel: ChatCallViewModel by viewModels()
    private val viewModeldetail: CallRequestDetailsViewModel by viewModels()

    var Listdata : ArrayList<Chat_Call_ResponseData> = ArrayList()
    lateinit var adapter : CallHomeAdapter

    lateinit var callList_id: String
    lateinit var callList_userid: String
    lateinit var callList_userName: String
    lateinit var callList_requeststatus: String

    private var channelName = ""
    // Fill the temp token generated on Agora Console.
    private var token = ""
    private var name = ""
    private var notificationType = ""
    private var appId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_call_request)
        binding.header.backArrow.setOnClickListener { finish() }
        binding.header.tvHeadName.text = "Call Request"

//        getChatListId = intent.getStringExtra("list_idSub").toString()
//        getUser_id = intent.getStringExtra("list_id").toString()
//        getUser_name = intent.getStringExtra("list_userName").toString()

        if (intent != null) {
            appId = intent.getStringExtra("app_id").toString()
            token = intent.getStringExtra(/*agora_*/"agora_token").toString()
            channelName = intent.getStringExtra("channel_name").toString()
            name = intent.getStringExtra("title").toString()
            notificationType = intent.getStringExtra("call_type").toString()
            callList_userName = intent.getStringExtra("astro_name").toString()
        }

        if (CommonUtils.isInternetAvailable(this)) {
            viewModel.CallList(
                "Bearer "+userPref.getToken().toString(),
            )
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }

        viewModeldetail.callAcceptResponse.observe(this) {
            if (it?.status == 1) {
                snackbar("Call Request Accepted!")
                viewModel.CallList(
                    "Bearer "+userPref.getToken().toString(),
                )
            } else {
                //toast(it.message)
                snackbar(it?.message!!)
            }
        }

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.ChatCallResponse.observe(this) {
            if (it?.status == 1) {
                binding.idNouser.visibility = View.GONE
                binding.rvChatRequest.visibility = View.VISIBLE
                Listdata.clear()
                Listdata.addAll(it.data)
                adapter = CallHomeAdapter(this, Listdata,this)
                binding.rvChatRequest.adapter =adapter
                adapter.notifyDataSetChanged()
            } else {
                binding.nodatatext.text = "No request found."
                binding.idNouser.visibility = View.VISIBLE
                binding.rvChatRequest.visibility = View.GONE
            }
        }
    }

    override fun onCallItemClicked(chat_Call_ResponseData: Chat_Call_ResponseData) {
        callList_id = chat_Call_ResponseData.id.toString()
        callList_userid = chat_Call_ResponseData.userId.toString()
        callList_userName = chat_Call_ResponseData.userName.toString()
        callList_requeststatus = chat_Call_ResponseData.requestStatus.toString()

        startActivity(Intent(this, CallRequestDetailsActivity::class.java).also {
            it.putExtra("list_idSub", callList_id)
            it.putExtra("list_id", callList_userid)
            it.putExtra("list_userName", callList_userName)
            it.putExtra("requeststatus", callList_requeststatus)
            it.putExtra("unique_id", chat_Call_ResponseData.unique_id)
            it  .putExtra("type", chat_Call_ResponseData.type)
//            it.putExtra("title", name)

            //  it   .putExtra("astro_name"/*"id"*/, callList_userName)
//            it    .putExtra("channel_name", channelName)
//            it   .putExtra("agora_token", token)
//            it  .putExtra("call_type", notificationType)



            Log.d("TAG++", "onProceedClicked: " + chat_Call_ResponseData.userId.toString())
        })
    }


    override fun onCallAcceptClicked(chat_Call_ResponseData: Chat_Call_ResponseData) {
        callList_id = chat_Call_ResponseData.id.toString()
        callList_userid = chat_Call_ResponseData.userId.toString()
        callList_userName = chat_Call_ResponseData.userName.toString()
        callList_requeststatus = chat_Call_ResponseData.requestStatus.toString()
        viewModeldetail.call_request_accecpt_api("Bearer "+userPref.getToken().toString(),callList_id)
        if (chat_Call_ResponseData.type == 2){
            startActivity(Intent(this, DashboardAudioCallActivity::class.java).also {
                it.putExtra("list_idSub", callList_id)
                it.putExtra("user_id", callList_userid)
                it.putExtra("list_userName", callList_userName)
                it.putExtra("requeststatus", callList_requeststatus)
                it.putExtra("unique_id", chat_Call_ResponseData.unique_id)
                Log.d("TAG++", "onProceedClicked---: " + callList_userid)
            })
        }else if (chat_Call_ResponseData.type == 3){
            startActivity(Intent(this, DashboardVideoCallActivity::class.java).also {
                it.putExtra("list_idSub", callList_id)
                it.putExtra("userid", callList_userid)
                it.putExtra("list_userName", callList_userName)
                it.putExtra("requeststatus", callList_requeststatus)
                it.putExtra("unique_id", chat_Call_ResponseData.unique_id)
                Log.d("TAG++", "onProceedClicked---: " + callList_userid)
            })
        }
    }

    override fun onCallCancelClicked(chat_Call_ResponseData: Chat_Call_ResponseData) {
        callList_id = chat_Call_ResponseData.id.toString()
        callList_userName = chat_Call_ResponseData.userName.toString()
        startActivity(Intent(this, CallCancelRequestActivity::class.java).also {
            it.putExtra("list_id", callList_id)
            it.putExtra("list_userName", callList_userName)
            Log.d("TAG++", "onProceedClicked: " + chat_Call_ResponseData.id.toString())
        })
    }


    override fun onStartCallButtonClicked(chat_Call_ResponseData: Chat_Call_ResponseData) {
        callList_id = chat_Call_ResponseData.id.toString()
        callList_userid = chat_Call_ResponseData.userId.toString()
        callList_userName = chat_Call_ResponseData.userName.toString()
        callList_requeststatus = chat_Call_ResponseData.requestStatus.toString()

        if (chat_Call_ResponseData.type == 2){
            startActivity(Intent(this, DashboardAudioCallActivity::class.java).also {
                it.putExtra("list_idSub", callList_id)
                it.putExtra("user_id", callList_userid)
                it.putExtra("list_userName", callList_userName)
                it.putExtra("requeststatus", callList_requeststatus)
                it.putExtra("unique_id", chat_Call_ResponseData.unique_id)
                it.putExtra("profile", chat_Call_ResponseData.profile)
                Log.d("TAG++", "onProceedClicked---: " + callList_userid)
            })
        }else if (chat_Call_ResponseData.type == 3){
            startActivity(Intent(this, DashboardVideoCallActivity::class.java).also {
                it.putExtra("list_idSub", callList_id)
                it.putExtra("userid", callList_userid)
                it.putExtra("list_userName", callList_userName)
                it.putExtra("requeststatus", callList_requeststatus)
                it.putExtra("profile", chat_Call_ResponseData.profile)
                it.putExtra("unique_id", chat_Call_ResponseData.unique_id)
                Log.d("TAG++", "onProceedClicked---: " + callList_userid)
            })
        }

    }

    override fun onResume() {
        super.onResume()
        if (CommonUtils.isInternetAvailable(this)) {
            viewModel.CallList(
                "Bearer "+userPref.getToken().toString(),
            )
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }

    }
}


