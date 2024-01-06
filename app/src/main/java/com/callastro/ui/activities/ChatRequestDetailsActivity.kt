package com.callastro.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.callastro.R
import com.callastro.databinding.ActivityChatRequestDetailsBinding
import com.callastro.viewModels.ChatRequestDetailsViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header.view.*

@AndroidEntryPoint
class ChatRequestDetailsActivity : BaseActivity() {

    lateinit var binding: ActivityChatRequestDetailsBinding
    private val viewModel : ChatRequestDetailsViewModel by viewModels()
    lateinit var getChatListId: String
    lateinit var userid: String
    lateinit var getlist_userName: String
    lateinit var getlist_requeststatus: String
    lateinit var chatList_id: String
    var chatList_userName= ""
    var callUserNumber = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_request_details)

        binding.header.backArrow.setOnClickListener { finish() }
        binding.header.tvHeadName.text = "Chat Request Details"

        getChatListId = intent.getStringExtra("list_id").toString()
        userid = intent.getStringExtra("userid").toString()
        getlist_userName = intent.getStringExtra("list_userName").toString()
        getlist_requeststatus = intent.getStringExtra("requeststatus").toString()
        chatList_userName = intent.getStringExtra("list_userName").toString()


        if(getlist_requeststatus.equals("1")) {
            binding.llAccept.visibility = View.VISIBLE
            binding.llCancel.visibility = View.VISIBLE
            binding.llStartChat.visibility = View.GONE
        }
        else if(getlist_requeststatus.equals("2"))
        {
            binding.llAccept.visibility = View.GONE
            binding.llCancel.visibility = View.GONE
            binding.llStartChat.visibility = View.VISIBLE

        }

        else if(getlist_requeststatus.equals("3"))
        {
            binding.llAccept.visibility = View.GONE
            binding.llCancel.visibility = View.GONE
            binding.llStartChat.visibility = View.GONE
        }

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

                if (it.data?.profileDetail?.type == 1){

                    binding.intake.visibility = View.VISIBLE

                    if(it.data!!.reports!!.name!!.isEmpty() ||it.data!!.reports!!.name == "")
                    {binding.tvName.text = "..."}
                    else{binding.tvName.text = it.data!!.reports!!.name}

                    if(it.data!!.reports!!.dob!!.isEmpty() ||it.data!!.reports!!.dob == "")
                    {binding.tvDob.text = "..."}
                    else{binding.tvDob.text = it.data!!.reports!!.dob}

                    if(it.data!!.reports!!.timeBirth!!.isEmpty()|| it.data!!.reports!!.timeBirth == "")
                    {binding.tvTimeOfBirth.text = "..."}
                    else{binding.tvTimeOfBirth.text = it.data!!.reports!!.timeBirth}

                    if(it.data!!.reports!!.placeBirth!!.isEmpty()||it.data!!.reports!!.placeBirth == "")
                    {binding.tvPlaceOfBirth.text = "..."}
                    else{binding.tvPlaceOfBirth.text = it.data!!.reports!!.placeBirth}

                    if(it.data!!.reports!!.occupation!!.isEmpty()||it.data!!.reports!!.occupation == "")
                    {binding.tvOccupation.text = "..."}
                    else{binding.tvOccupation.text = it.data!!.reports!!.occupation}

                    if(it.data!!.reports!!.maritialStatus!!.isEmpty()||it.data!!.reports!!.maritialStatus == "")
                    {binding.tvMaritalStatus.text = "..."}
                    else{binding.tvMaritalStatus.text = it.data!!.reports!!.maritialStatus}

                    if(it.data!!.reports!!.topicConsultant!!.isEmpty()||it.data!!.reports!!.topicConsultant == "")
                    {binding.tvTopicOfConsultation.text = "..."}
                    else{binding.tvTopicOfConsultation.text = it.data!!.reports!!.topicConsultant}

                    callUserNumber = it.data!!.profileDetail!!.mobile.toString()
                }else{

                    binding.matchmaking.visibility = View.VISIBLE

                    if(it.data!!.reports!!.boyName!!.isEmpty() ||it.data!!.reports!!.boyName == "")
                    {binding.tvBoyName.text = "..."}
                    else{binding.tvBoyName.text = it.data!!.reports!!.boyName}

                    if(it.data!!.reports!!.dobBoy!!.isEmpty() ||it.data!!.reports!!.dobBoy == "")
                    {binding.tvBoyDob.text = "..."}
                    else{binding.tvBoyDob.text = it.data!!.reports!!.dobBoy}

                    if(it.data!!.reports!!.birthTimeBoy!!.isEmpty()|| it.data!!.reports!!.birthTimeBoy == "")
                    {binding.tvBoyTimeOfBirth.text = "..."}
                    else{binding.tvBoyTimeOfBirth.text = it.data!!.reports!!.birthTimeBoy}

                    if(it.data!!.reports!!.placeBirthBoy!!.isEmpty()||it.data!!.reports!!.placeBirthBoy == "")
                    {binding.tvBoyPlaceOfBirth.text = "..."}
                    else{binding.tvBoyPlaceOfBirth.text = it.data!!.reports!!.placeBirthBoy}

                    if(it.data!!.reports!!.occupationBoy!!.isEmpty()||it.data!!.reports!!.occupationBoy == "")
                    {binding.tvBoyOccupation.text = "..."}
                    else{binding.tvBoyOccupation.text = it.data!!.reports!!.occupationBoy}

                    if(it.data!!.reports!!.girlName!!.isEmpty() ||it.data!!.reports!!.girlName == "")
                    {binding.tvGirlName.text = "..."}
                    else{binding.tvGirlName.text = it.data!!.reports!!.girlName}

                    if(it.data!!.reports!!.dobGirl!!.isEmpty() ||it.data!!.reports!!.dobGirl == "")
                    {binding.tvGirlDob.text = "..."}
                    else{binding.tvGirlDob.text = it.data!!.reports!!.dobGirl}

                    if(it.data!!.reports!!.birthTimeGirl!!.isEmpty()|| it.data!!.reports!!.birthTimeGirl == "")
                    {binding.tvGirlTimeOfBirth.text = "..."}
                    else{binding.tvGirlTimeOfBirth.text = it.data!!.reports!!.birthTimeGirl}

                    if(it.data!!.reports!!.placeBirthGirl!!.isEmpty()||it.data!!.reports!!.placeBirthGirl == "")
                    {binding.tvGirlPlaceOfBirth.text = "..."}
                    else{binding.tvGirlPlaceOfBirth.text = it.data!!.reports!!.placeBirthGirl}

                    if(it.data!!.reports!!.occupationGirl!!.isEmpty()||it.data!!.reports!!.occupationGirl == "")
                    {binding.tvGirlOccupation.text = "..."}
                    else{binding.tvGirlOccupation.text = it.data!!.reports!!.occupationGirl}





                    callUserNumber = it.data!!.profileDetail!!.mobile.toString()

                }


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
                var callerid = it.data.unique_id.toString()
                startActivity(Intent(this, ChatwithUsActivity::class.java).also {
                    it.putExtra("Userid", userid)
                    it.putExtra("list_id", userid)
                    it.putExtra("list_userName", chatList_userName)
                    it.putExtra("caller_id", callerid)
                })
                finish()

            } else {
                //toast(it.message)
                snackbar(it?.message!!)
            }
        }

        binding.llAccept.setOnClickListener(View.OnClickListener {
            if (CommonUtils.isInternetAvailable(this)) {
                viewModel.chat_request_accecpt_api("Bearer "+userPref.getToken().toString(),getChatListId)
            } else {
                toast(this,"Please check internet connection.")
            }

        })

        binding.llCancel.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, ChatCancelRequestActivityn::class.java).also {
                it.putExtra("list_id", getChatListId)
                it.putExtra("list_userName", getlist_userName)
                Log.d("TAG++", "onProceedClicked: " + getChatListId)
            })
        })


        binding.llStartChat.setOnClickListener(View.OnClickListener {

            startActivity(Intent(this, ChatwithUsActivity::class.java).also {
                it.putExtra("list_id", userid)
                it.putExtra("list_userName", getlist_userName)
                Log.d("TAG++", "onProceedClicked: " + getlist_userName)
            })


        })


    }
}