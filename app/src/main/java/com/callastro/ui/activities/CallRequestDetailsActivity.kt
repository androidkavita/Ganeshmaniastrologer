package com.callastro.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.callastro.R
import com.callastro.databinding.ActivityCallRequestDetailsBinding
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.astrorahiastrologer.viewmodel.CallRequestDetailsViewModel
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header.view.*


@AndroidEntryPoint
class CallRequestDetailsActivity : BaseActivity() {

    lateinit var binding: ActivityCallRequestDetailsBinding
    private val viewModel : CallRequestDetailsViewModel by viewModels()
    lateinit var getChatListId: String
    lateinit var getChatListUserId: String
    var getlist_userName=""
    var callUserNumber = ""
    lateinit var getlist_requeststatus: String
    private var channelName = ""
    // Fill the temp token generated on Agora Console.
    private var token = ""
    private var name = ""
    private var notification = ""
    private var appId = ""
    private var type :Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_call_request_details)

        binding.header.backArrow.setOnClickListener { finish() }
        binding.header.tvHeadName.text = "Call Request Details"



        if (intent != null) {
            getChatListId = intent.getStringExtra("list_idSub").toString()
            getChatListUserId = intent.getStringExtra("list_id").toString()
            getlist_userName = intent.getStringExtra("list_userName").toString()
            getlist_requeststatus = intent.getStringExtra("requeststatus").toString()
//            appId = intent.getStringExtra("app_id").toString()
//            token = intent.getStringExtra(/*agora_*/"agora_token").toString()
//            channelName = intent.getStringExtra("channel_name").toString()
//            name = intent.getStringExtra("title").toString()
//            notification = intent.getStringExtra("call_type").toString()
            type = intent.getIntExtra("type",0)
//            getlist_userName = intent.getStringExtra("astro_name").toString()

        }

        if (type == 2){
            binding.calltype.text = "Audio Call"
        }else{
            binding.calltype.text = "Video Call"
        }

        if(getlist_requeststatus.equals("1")) {
            binding.llAccept.visibility = View.VISIBLE
            binding.llCancel.visibility = View.VISIBLE
            binding.llStartCall.visibility = View.GONE

        }
        else if(getlist_requeststatus.equals("2")) {
            binding.llAccept.visibility = View.GONE
            binding.llCancel.visibility = View.GONE
            binding.llStartCall.visibility = View.VISIBLE

        }

        else if(getlist_requeststatus.equals("3"))
        {
            binding.llAccept.visibility = View.GONE
            binding.llCancel.visibility = View.GONE
            binding.llStartCall.visibility = View.GONE
        }


        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.callDetailResponse.observe(this) {

            if (it.status == 1) {

                binding.tvUserName.text = it.data!!.profileDetail!!.username
                binding.tvLanguage.text = it.data!!.profileDetail!!.language
                binding.tvUserPhnNumber.text = it.data!!.profileDetail!!.mobile
                Glide.with(this).load(it.data!!.profileDetail!!.profileImage).into(binding.ivUserImage)


                if (it.data?.profileDetail?.type == 1){

                    binding.intake.visibility = View.VISIBLE

                    if(it.data!!.intak!!.name!!.isEmpty() ||it.data!!.intak!!.name == "")
                    {binding.tvName.text = "..."}
                    else{binding.tvName.text = it.data!!.intak!!.name}

                    if(it.data!!.intak!!.dob!!.isEmpty() ||it.data!!.intak!!.dob == "")
                    {binding.tvDob.text = "..."}
                    else{binding.tvDob.text = it.data!!.intak!!.dob}

                    if(it.data!!.intak!!.timeBirth!!.isEmpty()|| it.data!!.intak!!.timeBirth == "")
                    {binding.tvTimeOfBirth.text = "..."}
                    else{binding.tvTimeOfBirth.text = it.data!!.intak!!.timeBirth}

                    if(it.data!!.intak!!.placeBirth!!.isEmpty()||it.data!!.intak!!.placeBirth == "")
                    {binding.tvPlaceOfBirth.text = "..."}
                    else{binding.tvPlaceOfBirth.text = it.data!!.intak!!.placeBirth}

                    if(it.data!!.intak!!.occupation!!.isEmpty()||it.data!!.intak!!.occupation == "")
                    {binding.tvOccupation.text = "..."}
                    else{binding.tvOccupation.text = it.data!!.intak!!.occupation}

                    if(it.data!!.intak!!.maritialStatus!!.isEmpty()||it.data!!.intak!!.maritialStatus == "")
                    {binding.tvMaritalStatus.text = "..."}
                    else{binding.tvMaritalStatus.text = it.data!!.intak!!.maritialStatus}

                    if(it.data!!.intak!!.topicConsultant!!.isEmpty()||it.data!!.intak!!.topicConsultant == "")
                    {binding.tvTopicOfConsultation.text = "..."}
                    else{binding.tvTopicOfConsultation.text = it.data!!.intak!!.topicConsultant}

                    callUserNumber = it.data!!.profileDetail!!.mobile.toString()
                }else{

                    binding.matchmaking.visibility = View.VISIBLE

                    if(it.data!!.intak!!.boyName!!.isEmpty() ||it.data!!.intak!!.boyName == "")
                    {binding.tvBoyName.text = "..."}
                    else{binding.tvBoyName.text = it.data!!.intak!!.boyName}

                    if(it.data!!.intak!!.dobBoy!!.isEmpty() ||it.data!!.intak!!.dobBoy == "")
                    {binding.tvBoyDob.text = "..."}
                    else{binding.tvBoyDob.text = it.data!!.intak!!.dobBoy}

                    if(it.data!!.intak!!.birthTimeBoy!!.isEmpty()|| it.data!!.intak!!.birthTimeBoy == "")
                    {binding.tvBoyTimeOfBirth.text = "..."}
                    else{binding.tvBoyTimeOfBirth.text = it.data!!.intak!!.birthTimeBoy}

                    if(it.data!!.intak!!.placeBirthBoy!!.isEmpty()||it.data!!.intak!!.placeBirthBoy == "")
                    {binding.tvBoyPlaceOfBirth.text = "..."}
                    else{binding.tvBoyPlaceOfBirth.text = it.data!!.intak!!.placeBirthBoy}

                    if(it.data!!.intak!!.occupationBoy!!.isEmpty()||it.data!!.intak!!.occupationBoy == "")
                    {binding.tvBoyOccupation.text = "..."}
                    else{binding.tvBoyOccupation.text = it.data!!.intak!!.occupationBoy}

                    if(it.data!!.intak!!.girlName!!.isEmpty() ||it.data!!.intak!!.girlName == "")
                    {binding.tvGirlName.text = "..."}
                    else{binding.tvGirlName.text = it.data!!.intak!!.girlName}

                    if(it.data!!.intak!!.dobGirl!!.isEmpty() ||it.data!!.intak!!.dobGirl == "")
                    {binding.tvGirlDob.text = "..."}
                    else{binding.tvGirlDob.text = it.data!!.intak!!.dobGirl}

                    if(it.data!!.intak!!.birthTimeGirl!!.isEmpty()|| it.data!!.intak!!.birthTimeGirl == "")
                    {binding.tvGirlTimeOfBirth.text = "..."}
                    else{binding.tvGirlTimeOfBirth.text = it.data!!.intak!!.birthTimeGirl}

                    if(it.data!!.intak!!.placeBirthGirl!!.isEmpty()||it.data!!.intak!!.placeBirthGirl == "")
                    {binding.tvGirlPlaceOfBirth.text = "..."}
                    else{binding.tvGirlPlaceOfBirth.text = it.data!!.intak!!.placeBirthGirl}

                    if(it.data!!.intak!!.occupationGirl!!.isEmpty()||it.data!!.intak!!.occupationGirl == "")
                    {binding.tvGirlOccupation.text = "..."}
                    else{binding.tvGirlOccupation.text = it.data!!.intak!!.occupationGirl}





                    callUserNumber = it.data!!.profileDetail!!.mobile.toString()

                }



            } else {
                Log.d("Response", it.toString())
                toast(this,it.message!!)
            }
        }

        if (CommonUtils.isInternetAvailable(this)) {
            viewModel.call_request_detail_api("Bearer " + userPref.getToken(), getChatListId)
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }




        viewModel.callAcceptResponse.observe(this) {
            if (it?.status == 1) {
                snackbar("Call Request Accepted!")
                if (type == 2){
                    startActivity(Intent(this, DashboardAudioCallActivity::class.java).also {
                        it.putExtra("list_idSub", getChatListId)
                        it.putExtra("user_id", getChatListUserId)
                        it.putExtra("list_userName", getlist_userName)
                        it.putExtra("requeststatus", getlist_requeststatus)
                    })
                    finish()
                }else if (type == 3){
                    startActivity(Intent(this, DashboardVideoCallActivity::class.java).also {
                        it.putExtra("list_idSub", getChatListId)
                        it.putExtra("userid", getChatListUserId)
                        it.putExtra("list_userName", getlist_userName)
                        it.putExtra("requeststatus", getlist_requeststatus)
                    })
                    finish()
                }
            } else {
                //toast(it.message)
                snackbar(it?.message!!)
            }
        }

        binding.llAccept.setOnClickListener(View.OnClickListener {
            if (CommonUtils.isInternetAvailable(this)) {
                viewModel.call_request_accecpt_api("Bearer "+userPref.getToken().toString(),getChatListId)
            } else {
                Log.d("TAG", "onCreate: " + "else part")
                toast(this,"Please check internet connection.")
            }

        })

        binding.llCancel.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, CallCancelRequestActivity::class.java).also {
                it.putExtra("list_id", getChatListId)
                it.putExtra("list_userName", getlist_userName)
                Log.d("TAG++", "onProceedClicked: " + getChatListId)
            })
        })


        binding.llStartCall.setOnClickListener(View.OnClickListener {
           /* val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$callUserNumber")
            startActivity(intent)*/
            if (type == 2){
                startActivity(Intent(this, DashboardAudioCallActivity::class.java).also {
                    it.putExtra("list_idSub", getChatListId)
                    it.putExtra("user_id", getChatListUserId)
                    it.putExtra("list_userName", getlist_userName)
                    it.putExtra("requeststatus", getlist_requeststatus)
                })
            }else if (type == 3){
                startActivity(Intent(this, DashboardVideoCallActivity::class.java).also {
                    it.putExtra("list_idSub", getChatListId)
                    it.putExtra("userid", getChatListUserId)
                    it.putExtra("list_userName", getlist_userName)
                    it.putExtra("requeststatus", getlist_requeststatus)
                })
            }
        })


    }
}