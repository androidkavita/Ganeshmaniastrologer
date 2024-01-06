package com.callastro.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.callastro.R
import com.callastro.adapters.FixedSessionAdapter
import com.callastro.databinding.ActivityFixedSessionBinding
import com.callastro.model.FixedsessionResponseListData
import com.callastro.viewModels.FixedSessionViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header.view.backArrow
import kotlinx.android.synthetic.main.header.view.tvHeadName

@AndroidEntryPoint
class FixedSession : BaseActivity(), FixedSessionAdapter.OnClick {
    lateinit var binding: ActivityFixedSessionBinding
    private val viewModel: FixedSessionViewModel by viewModels()
    lateinit var adapter: FixedSessionAdapter
    var list :ArrayList<FixedsessionResponseListData> = arrayListOf()

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
    private var callCheck = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fixed_session)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_fixed_session)


        if (intent != null) {
            appId = intent.getStringExtra("app_id").toString()
            token = intent.getStringExtra(/*agora_*/"agora_token").toString()
            channelName = intent.getStringExtra("channel_name").toString()
            name = intent.getStringExtra("title").toString()
            notificationType = intent.getStringExtra("call_type").toString()
            callList_userName = intent.getStringExtra("astro_name").toString()
            callCheck = intent.getStringExtra("callCheck").toString()
        }

        binding.header.backArrow.setOnClickListener{
            finish()
        }
        binding.header.tvHeadName.text = "Fixed Session"

        if (CommonUtils.isInternetAvailable(this)) {
            viewModel.fixedsessionListAPI(
                "Bearer "+userPref.getToken().toString(),
            )
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }


        viewModel.fixedsessionResponseList.observe(this){
            if (it.status == 1){
                list.clear()
                list.addAll(it.data)
                adapter = FixedSessionAdapter(this@FixedSession, list,this)
                binding.FixedSession.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCallItemClicked(fixedsessionResponseListData: FixedsessionResponseListData) {
        callList_id = fixedsessionResponseListData.id.toString()
        callList_userid = fixedsessionResponseListData.userId.toString()
        callList_userName = fixedsessionResponseListData.userName.toString()
        callList_requeststatus = fixedsessionResponseListData.requestStatus.toString()

        if (fixedsessionResponseListData.type == 1){
            startActivity(Intent(this, ChatRequestDetailsActivity::class.java).also {
                it.putExtra("list_id", callList_id)
                it.putExtra("userid", callList_userid)
                it.putExtra("list_userName", callList_userName)
                it.putExtra("requeststatus", fixedsessionResponseListData.type)

                it.putExtra("title", name)
                it  .putExtra("app_id", appId)
                // it   .putExtra("list_id"/*"id"*/, user_id)
                //  it   .putExtra("astro_name"/*"id"*/, callList_userName)
                it    .putExtra("channel_name", channelName)
                it   .putExtra("agora_token", token)
                it  .putExtra("call_type", notificationType)

            })
        }else  if (fixedsessionResponseListData.type == 2){
            startActivity(Intent(this, CallRequestDetailsActivity::class.java).also {
                it.putExtra("list_idSub", callList_id)
                it.putExtra("list_id", callList_userid)
                it.putExtra("list_userName", callList_userName)
                it.putExtra("requeststatus", fixedsessionResponseListData.type)

                it.putExtra("title", name)
                it  .putExtra("app_id", appId)
                // it   .putExtra("list_id"/*"id"*/, user_id)
                //  it   .putExtra("astro_name"/*"id"*/, callList_userName)
                it    .putExtra("channel_name", channelName)
                it   .putExtra("agora_token", token)
                it  .putExtra("call_type", notificationType)

            })
        }


    }

    override fun onCallAcceptClicked(fixedsessionResponseListData: FixedsessionResponseListData) {
        callList_id = fixedsessionResponseListData.id.toString()
        callList_userid = fixedsessionResponseListData.userId.toString()
        callList_userName = fixedsessionResponseListData.userName.toString()
        callList_requeststatus = fixedsessionResponseListData.requestStatus.toString()

        viewModel.fixed_session_request_accecpt("Bearer "+ userPref.getToken().toString(), callList_id)

        Toast.makeText(this, "Booking Confirm", Toast.LENGTH_SHORT).show()

  /*      if (fixedsessionResponseListData.type == 1){
            startActivity(Intent(this, ChatwithUsActivity::class.java).also {
                it.putExtra("list_idSub", callList_id)
                it.putExtra("Userid", callList_userid)
                it.putExtra("id", callList_id)
                it.putExtra("caller_id", fixedsessionResponseListData.uniqueId)
                it.putExtra("list_userName", callList_userName)
                it.putExtra("requeststatus", callList_requeststatus)
                Log.d("TAG++", "onProceedClicked---: " + callList_userid)
            })
        }

        if (fixedsessionResponseListData.type == 2){
            startActivity(Intent(this, DashboardAudioCallActivity::class.java).also {
                it.putExtra("list_idSub", callList_id)
                it.putExtra("user_id", callList_userid)
                it.putExtra("id", callList_id)
                it.putExtra("list_userName", callList_userName)
                it.putExtra("requeststatus", callList_requeststatus)
                Log.d("TAG++", "onProceedClicked---: " + callList_userid)
            })
        }

        if (fixedsessionResponseListData.type == 3){
            startActivity(Intent(this, DashboardVideoCallActivity::class.java).also {
                it.putExtra("list_idSub", callList_id)
                it.putExtra("userid", callList_userid)
                it.putExtra("id", callList_id)
                it.putExtra("list_userName", callList_userName)
                it.putExtra("requeststatus", callList_requeststatus)
                Log.d("TAG++", "onProceedClicked---: " + callList_userid)
            })
        }*/
    }

    override fun onCallCancelClicked(fixedsessionResponseListData: FixedsessionResponseListData) {
        callList_id = fixedsessionResponseListData.userId.toString()
        callList_userName = fixedsessionResponseListData.userName.toString()
        startActivity(Intent(this, CallCancelRequestActivity::class.java).also {
            it.putExtra("list_id", callList_id)
            it.putExtra("list_userName", callList_userName)
            Log.d("TAG++", "onProceedClicked: " + fixedsessionResponseListData.id.toString())
        })
    }

    override fun onStartCallButtonClicked(fixedsessionResponseListData: FixedsessionResponseListData) {
        callList_id = fixedsessionResponseListData.id.toString()
        callList_userid = fixedsessionResponseListData.userId.toString()
        callList_userName = fixedsessionResponseListData.userName.toString()
        callList_requeststatus = fixedsessionResponseListData.requestStatus.toString()
        if (fixedsessionResponseListData.type == 1){
//            startActivity(Intent(this, DashboardAudioCallActivity::class.java).also {
//                it.putExtra("list_idSub", callList_id)
//                it.putExtra("user_id", callList_userid)
//                it.putExtra("id", callList_id)
//                it.putExtra("list_userName", callList_userName)
//                it.putExtra("requeststatus", callList_requeststatus)
//                Log.d("TAG++", "onProceedClicked---: " + callList_userid)
//            })
            startActivity(Intent(this, ChatwithUsActivity::class.java).also {
                it.putExtra("list_idSub", callList_id)
                it.putExtra("Userid", callList_userid)
                it.putExtra("list_userName", callList_userName)

            })
        }else if (fixedsessionResponseListData.type == 2){
            startActivity(Intent(this, DashboardAudioCallActivity::class.java).also {
                it.putExtra("list_idSub", callList_id)
                it.putExtra("user_id", callList_userid)
                it.putExtra("id", callList_id)
                it.putExtra("list_userName", callList_userName)
                it.putExtra("requeststatus", callList_requeststatus)
                Log.d("TAG++", "onProceedClicked---: " + callList_userid)
            })
        }else if (fixedsessionResponseListData.type == 3){
            startActivity(Intent(this, DashboardVideoCallActivity::class.java).also {
                it.putExtra("list_idSub", callList_id)
                it.putExtra("userid", callList_userid)
                it.putExtra("id", callList_id)
                it.putExtra("list_userName", callList_userName)
                it.putExtra("requeststatus", callList_requeststatus)
                Log.d("TAG++", "onProceedClicked---: " + callList_userid)
            })
        }
    }

}