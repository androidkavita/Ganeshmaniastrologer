package com.callastro.ui.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.callastro.R
import com.callastro.adapters.ChatHistoryListAdapter
import com.callastro.databinding.ActivityChatHistoryBinding
import com.callastro.databinding.AddmoneyBinding
import com.callastro.databinding.DialogRefundRequestBinding
import com.callastro.model.ChatHistoryListData
import com.callastro.viewModels.CallChatHistoryViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header.view.*
import java.util.ArrayList


@AndroidEntryPoint
class ChatHistoryActivity : BaseActivity(), ChatHistoryListAdapter.OnClick {

    lateinit var binding : ActivityChatHistoryBinding
    private val viewModel : CallChatHistoryViewModel by viewModels()
    private var chatHistoryAdapter : ChatHistoryListAdapter?= null
    private var listChatHistory: ArrayList<ChatHistoryListData> = ArrayList()
    lateinit var chatList_id: String
    lateinit var chatList_userName: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_history)


        binding.header.backArrow.setOnClickListener { finish() }
        binding.header.tvHeadName.text = "Chat History"
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.chatHistoryListResponse.observe(this) {
            if (it.status == 1) {
                listChatHistory.clear()
                // listData!!.addAll(it.getFavLocdata)

                if (it.data.isEmpty() ) {
                    binding.idNouser.visibility = View.VISIBLE
                    binding.rvChatHistory.visibility = View.GONE

                }
                else {
                    binding.idNouser.visibility = View.GONE
                    binding.rvChatHistory.visibility = View.VISIBLE
                    listChatHistory.addAll(it.data)
                    chatHistoryAdapter = ChatHistoryListAdapter(listChatHistory,this)
                    binding.rvChatHistory.apply {
                        adapter = chatHistoryAdapter
                        layoutManager = LinearLayoutManager(this@ChatHistoryActivity)
                        // it.getFavLocdata?.let { notificationList?.addAll(it) }
                    }
                }
            }else
            {
                Log.d("Response", it.toString())
                toast(this,it.message!!)
                binding.idNouser.visibility = View.VISIBLE
                binding.rvChatHistory.visibility = View.GONE
            }
        }

        if (CommonUtils.isInternetAvailable(this)) {
            viewModel.chatHistoryListApi("Bearer " +  userPref.getToken(), /*"dummy"*/)
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }


        viewModel.commonResponse.observe(this){
            if (it.status == 1){
                toast(this,"Request sent successfully")
                if (CommonUtils.isInternetAvailable(this)) {
                    viewModel.chatHistoryListApi("Bearer " +  userPref.getToken(), /*"dummy"*/)
                } else {
                    Log.d("TAG", "onCreate: " + "else part")
                    toast(this,"Please check internet connection.")
                }
                refundSuccessDialog()
//                if (CommonUtils.isInternetAvailable(this)) {
//                    viewModel.astro_earningApi("Bearer "+userPref.getToken().toString(),"1")
//                } else {
//                    Log.d("TAG", "onCreate: " + "else part")
//                    toast(this,"Please check internet connection.")
//                }
            }else{
                toast(this@ChatHistoryActivity,it.message.toString())
            }
        }



    }






    private fun refundSuccessDialog() {
        val cDialog = Dialog(this, R.style.Theme_Tasker_Dialog)
        val bindingDialog: DialogRefundRequestBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_refund_request,
            null,
            false
        )
        cDialog.setContentView(bindingDialog.root)
        cDialog.setCancelable(false)
        cDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        cDialog.show()
        bindingDialog.btnOk.setOnClickListener {
            cDialog.dismiss()
        }
    }


    override fun onSuggestRemedyClicked(chatHistoryListData: ChatHistoryListData) {
        chatList_id = chatHistoryListData.id.toString()
        chatList_userName = chatHistoryListData.userName.toString()
        startActivity(Intent(this, SuggestRemedyChatActivity::class.java).also {
            it.putExtra("list_id", chatList_id)
            it.putExtra("userid", chatHistoryListData.userid)
            it.putExtra("list_userName", chatList_userName)
            Log.d("TAG++", "onProceedClicked: " + chatHistoryListData.id.toString())
        })

    }

    override fun onRefundAmountClicked(chatHistoryListData: ChatHistoryListData) {
//        refundSuccessDialog()
//        REQUESTMONEY()

        val buinder = AlertDialog.Builder(this)
        buinder.setMessage("Are you sure, you want to refund amount?")
        buinder.setIcon(R.drawable.alert)
        buinder.setTitle("Refund Amount")
        buinder.setPositiveButton("Yes") { dialogInterface, which ->
            if (CommonUtils.isInternetAvailable(this@ChatHistoryActivity)) {

                viewModel.refund_money("Bearer "+userPref.getToken().toString(),chatHistoryListData.orderId.toString())
            } else {
                Log.d("TAG", "onCreate: " + "else part")
                toast(this@ChatHistoryActivity,"Please check internet connection.")
            }
        }
        buinder.setNegativeButton("No") { dialogInterface, which ->

        }
        val alertDialog: AlertDialog = buinder.create()
        alertDialog.setCancelable(true)

        try {
            alertDialog.show()
        }
        catch (e: WindowManager.BadTokenException) {
            e.printStackTrace()
        }


    }
    private fun REQUESTMONEY() {
        val cDialog = Dialog(this)
        val bindingDialog: AddmoneyBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.addmoney,
            null,
            false
        )
        cDialog.setContentView(bindingDialog.root)
        cDialog.setCancelable(true)
        cDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        cDialog.show()
        bindingDialog.btnCancel.setOnClickListener {
            cDialog.dismiss()
        }

        bindingDialog.btnLogout.setOnClickListener {
            cDialog.dismiss()
        }
    }


}