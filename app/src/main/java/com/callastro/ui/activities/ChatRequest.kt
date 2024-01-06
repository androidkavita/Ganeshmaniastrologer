package com.callastro.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.callastro.R
import com.callastro.adapters.ChatHomeAdapter
import com.callastro.databinding.ActivityChatRequestBinding
import com.callastro.model.Chat_Call_ResponseData
import com.callastro.viewModels.ChatRequestDetailsViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.astrorahiastrologer.viewmodel.ChatCallViewModel
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header.view.*

@AndroidEntryPoint
class ChatRequest : BaseActivity() , ChatHomeAdapter.OnClick{
    lateinit var binding: ActivityChatRequestBinding
    private val viewModel: ChatCallViewModel by viewModels()
    private val viewModeldetail: ChatRequestDetailsViewModel by viewModels()
    var Listdata : ArrayList<Chat_Call_ResponseData> = ArrayList()
    lateinit var adapter : ChatHomeAdapter

    lateinit var chatList_id: String
    lateinit var User_id: String
     var chatList_userName=""
    lateinit var caller_id: String
    lateinit var chatList_requeststatus: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_request)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_request)
        binding.header.backArrow.setOnClickListener { finish() }
        binding.header.tvHeadName.text = "Chat Request"



        viewModeldetail.chatAcceptResponse.observe(this) {
            if (it?.status == 1) {
                snackbar("Chat Request Accepted!")
                caller_id = it.data.unique_id.toString()
                viewModel.ChatList(
                    "Bearer "+userPref.getToken().toString(),
                )
                startActivity(Intent(this, ChatwithUsActivity::class.java).also {
                    it.putExtra("Userid", User_id)
                    it.putExtra("list_id", User_id)
                    it.putExtra("list_userName", chatList_userName)
                    it.putExtra("caller_id", caller_id)
                })
            } else {
                //toast(it.message)
                snackbar(it?.message!!)
            }
        }

        if (CommonUtils.isInternetAvailable(this)) {
            viewModel.ChatList(
                "Bearer "+userPref.getToken().toString(),
            )
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
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
                adapter = ChatHomeAdapter(this, Listdata, this)
                binding.rvChatRequest.adapter =adapter
            } else {
                binding.nodatatext.text = "No Request found."
                binding.idNouser.visibility = View.VISIBLE
                binding.rvChatRequest.visibility = View.GONE

                //toast(it.message)
                snackbar(it?.message!!)
            }
        }
    }
    override fun onChatItemClicked(chat_Call_ResponseData: Chat_Call_ResponseData) {
        chatList_id = chat_Call_ResponseData.id.toString()
        User_id = chat_Call_ResponseData.userId.toString()
        chatList_userName = chat_Call_ResponseData.userName.toString()
        chatList_requeststatus = chat_Call_ResponseData.requestStatus.toString()
        startActivity(Intent(this, ChatRequestDetailsActivity::class.java).also {
            it.putExtra("list_id", chatList_id)
            it.putExtra("userid", User_id)
            it.putExtra("list_userName", chatList_userName)
            it.putExtra("requeststatus", chatList_requeststatus)
        })
    }
    override fun onChatAcceptClicked(chat_Call_ResponseData: Chat_Call_ResponseData) {
        chatList_id = chat_Call_ResponseData.id.toString()
        User_id = chat_Call_ResponseData.userId.toString()
        chatList_userName = chat_Call_ResponseData.userName.toString()
        if (CommonUtils.isInternetAvailable(this)) {
            viewModeldetail.chat_request_accecpt_api("Bearer "+userPref.getToken().toString(),chatList_id)
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }
    }
    override fun onChatCancelClicked(chat_Call_ResponseData: Chat_Call_ResponseData) {
        chatList_id = chat_Call_ResponseData.id.toString()
        chatList_userName = chat_Call_ResponseData.userName.toString()
        User_id = chat_Call_ResponseData.userId.toString()
        startActivity(Intent(this, ChatCancelRequestActivityn::class.java).also {
            it.putExtra("list_id", chatList_id)
            it.putExtra("list_userName", chatList_userName)
            Log.d("TAG++", "onProceedClicked: " + chat_Call_ResponseData.id.toString())
        })
    }
    override fun onStartChatButtonClicked(chat_Call_ResponseData: Chat_Call_ResponseData) {
        chatList_id = chat_Call_ResponseData.id.toString()
        chatList_userName = chat_Call_ResponseData.userName.toString()
        User_id = chat_Call_ResponseData.userId.toString()
        startActivity(Intent(this, ChatwithUsActivity::class.java).also {
            it.putExtra("Userid", User_id)
            it.putExtra("list_id", User_id)
            it.putExtra("list_userName", chatList_userName)
            Log.d("TAG++", "onProceedClicked: " + chat_Call_ResponseData.userName.toString())
        })
    }
    override fun onResume() {
        super.onResume()
        if (CommonUtils.isInternetAvailable(this)) {
            viewModel.ChatList(
                "Bearer "+userPref.getToken().toString(),
            )
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }
    }
}