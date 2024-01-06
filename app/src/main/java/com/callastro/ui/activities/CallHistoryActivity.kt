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
import com.callastro.adapters.CallHistoryListAdapter
import com.callastro.databinding.ActivityCallHistoryBinding
import com.callastro.databinding.DialogRefundRequestBinding
import com.callastro.model.CallHistoryListData
import com.callastro.model.ChatHistoryListData
import com.callastro.viewModels.CallChatHistoryViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header.view.*
import java.util.ArrayList

@AndroidEntryPoint
class CallHistoryActivity : BaseActivity(), CallHistoryListAdapter.OnClick  {

    lateinit var binding : ActivityCallHistoryBinding
    private val viewModel : CallChatHistoryViewModel by viewModels()
    private var callHistoryAdapter : CallHistoryListAdapter?= null
    private var listCallHistory: ArrayList<CallHistoryListData> = ArrayList()



    lateinit var callList_id: String
    lateinit var callList_userName: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_call_history)


        binding.header.backArrow.setOnClickListener { finish() }
        binding.header.tvHeadName.text = "Call History"

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.commonResponse.observe(this){
            if (it.status == 1){
                toast(this,"Request sent successfully")
                if (CommonUtils.isInternetAvailable(this)) {
                    viewModel.callHistoryListApi("Bearer " +  userPref.getToken(), /*"dummy"*/)
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
                toast(this@CallHistoryActivity,it.message.toString())
            }
        }
        viewModel.callHistoryListResponse.observe(this) {
            if (it.status == 1) {
                listCallHistory.clear()
                // listData!!.addAll(it.getFavLocdata)

                if (it.data.isEmpty() ) {
                    binding.idNouser.visibility = View.VISIBLE
                    binding.rvCallHistory.visibility = View.GONE

                }
                else {
                    binding.idNouser.visibility = View.GONE
                    binding.rvCallHistory.visibility = View.VISIBLE
                    listCallHistory.addAll(it.data)
                    callHistoryAdapter = CallHistoryListAdapter(listCallHistory,this)
                    binding.rvCallHistory.apply {
                        adapter = callHistoryAdapter
                        layoutManager = LinearLayoutManager(this@CallHistoryActivity)
                        // it.getFavLocdata?.let { notificationList?.addAll(it) }
                        //    favouriteLocationsAdapter?.notifyDataSetChanged()
                    }
                }
            } else   {
                Log.d("Response", it.toString())
                toast(this,it.message!!)
                binding.idNouser.visibility = View.VISIBLE
                binding.rvCallHistory.visibility = View.GONE
            }
        }
        if (CommonUtils.isInternetAvailable(this)) {
            viewModel.callHistoryListApi(
                "Bearer " +  userPref.getToken()/*, "dummy"*/
            )
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
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
    override fun onSuggestRemedyClicked(callHistoryListData: CallHistoryListData) {
        callList_id = callHistoryListData.id.toString()
        callList_userName = callHistoryListData.userName.toString()
        startActivity(Intent(this, SuggestRemedyChatActivity::class.java).also {
            it.putExtra("list_id", callList_id)
            it.putExtra("userid", callHistoryListData.userid)
            it.putExtra("list_userName", callList_userName)
            Log.d("TAG++", "onProceedClicked: " + callHistoryListData.id.toString())
        })
    }

    override fun onRefundAmountClicked(callHistoryListData: CallHistoryListData) {
//        refundSuccessDialog()
//        REQUESTMONEY()

        val buinder = AlertDialog.Builder(this)
        buinder.setMessage("Are you sure, you want to refund amount?")
        buinder.setIcon(R.drawable.alert)
        buinder.setTitle("Refund Amount")
        buinder.setPositiveButton("Yes") { dialogInterface, which ->
            if (CommonUtils.isInternetAvailable(this@CallHistoryActivity)) {
                viewModel.refund_money("Bearer "+userPref.getToken().toString(),callHistoryListData.orderId.toString())
            } else {
                Log.d("TAG", "onCreate: " + "else part")
                toast(this@CallHistoryActivity,"Please check internet connection.")
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

}