package com.callastro.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.callastro.R
import com.callastro.databinding.ActivityConfirmationBookingDetailsChatBinding
import com.callastro.viewModels.ChatRequestDetailsViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header.view.*

@AndroidEntryPoint
class ConfirmationBookingDetailsChatActivity : BaseActivity() {
    lateinit var binding: ActivityConfirmationBookingDetailsChatBinding
    private val viewModel : ChatRequestDetailsViewModel by viewModels()
    lateinit var getChatListId: String
    lateinit var getlist_userName: String
    lateinit var getlist_requeststatus: String
    lateinit var chatList_id: String
    lateinit var chatList_userName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirmation_booking_details_chat)

        binding.header.backArrow.setOnClickListener { finish() }
        binding.header.tvHeadName.text = "Chat Booking Details"

        getChatListId = intent.getStringExtra("list_id").toString()
        getlist_userName = intent.getStringExtra("list_userName").toString()
        getlist_requeststatus = intent.getStringExtra("requeststatus").toString()


        /*if(getlist_requeststatus.equals("1"))
        {
           // binding.llAccept.visibility = View.VISIBLE
            binding.llCancel.visibility = View.VISIBLE
            binding.llStartChat.visibility = View.GONE

        }
        else if(getlist_requeststatus.equals("2"))
        {
           // binding.llAccept.visibility = View.GONE
            binding.llCancel.visibility = View.GONE
            binding.llStartChat.visibility = View.VISIBLE

        }

        else if(getlist_requeststatus.equals("3"))
        {
           // binding.llAccept.visibility = View.GONE
            binding.llCancel.visibility = View.GONE
           binding.llStartChat.visibility = View.GONE
        }*/

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.chatDetailResponse.observe(this) {

            if (it.status == 1) {

                binding.tvUserName.text = it.data!!.profileDetail!!.username
                binding.tvLanguage.text = it.data!!.profileDetail!!.language
                binding.tvUserPhnNumber.text = it.data!!.profileDetail!!.mobile
                Glide.with(this).load(it.data!!.profileDetail!!.profileImage).into(binding.ivUserImage)



                if(it.data!!.reports!!.dob!!.isEmpty() ||it.data!!.reports!!.dob == null)
                {binding.tvDob.text = "..."}
                else{binding.tvDob.text = it.data!!.reports!!.dob}

                if(it.data!!.reports!!.timeBirth!!.isEmpty()|| it.data!!.reports!!.timeBirth == null)
                {binding.tvTimeOfBirth.text = "..."}
                else{binding.tvTimeOfBirth.text = it.data!!.reports!!.timeBirth}

                if(it.data!!.reports!!.placeBirth!!.isEmpty()||it.data!!.reports!!.placeBirth == null)
                {binding.tvPlaceOfBirth.text = "..."}
                else{binding.tvPlaceOfBirth.text = it.data!!.reports!!.placeBirth}

                if(it.data!!.reports!!.occupation!!.isEmpty()||it.data!!.reports!!.occupation == null)
                {binding.tvOccupation.text = "..."}
                else{binding.tvOccupation.text = it.data!!.reports!!.occupation}

                if(it.data!!.reports!!.maritialStatus!!.isEmpty()||it.data!!.reports!!.maritialStatus == null)
                {binding.tvMaritalStatus.text = "..."}
                else{binding.tvMaritalStatus.text = it.data!!.reports!!.maritialStatus}

                if(it.data!!.reports!!.topicConsultant!!.isEmpty()||it.data!!.reports!!.topicConsultant == null)
                {binding.tvTopicOfConsultation.text = "..."}
                else{binding.tvTopicOfConsultation.text = it.data!!.reports!!.topicConsultant}


            } else {
                Log.d("Response", it.toString())
                toast(this,it.message!!)
            }
        }

        if (CommonUtils.isInternetAvailable(this)) {
            viewModel.chat_request_detail_api("Bearer " + userPref.getToken(), getChatListId)
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }

        viewModel.chatAcceptResponse.observe(this) {
            if (it?.status == 1) {
                snackbar("Chat Request Accepted!")
            } else {
                //toast(it.message)
//                snackbar(it?.message!!)
                startActivity(Intent(this, ChatwithUsActivity::class.java).also {
                    it.putExtra("list_id", getChatListId)
                    it.putExtra("list_userName", getlist_userName)
                    Log.d("TAG++", "onProceedClicked: " + getlist_userName)
                })
            }
        }

        /*binding.llAccept.setOnClickListener(View.OnClickListener {
            viewModel.chat_request_accecpt_api("Bearer "+userPref.getToken().toString(),getChatListId)
        })*/

        binding.llCancel.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, ChatCancelRequestActivityn::class.java).also {
                it.putExtra("list_id", getChatListId)
                it.putExtra("list_userName", getlist_userName)
                Log.d("TAG++", "onProceedClicked: " + getChatListId)
            })
        })


        binding.llStartChat.setOnClickListener(View.OnClickListener {

            startActivity(Intent(this, ChatwithUsActivity::class.java).also {
                it.putExtra("list_id", getChatListId)
                it.putExtra("list_userName", getlist_userName)
                Log.d("TAG++", "onProceedClicked: " + getlist_userName)
            })


        })


    }
}