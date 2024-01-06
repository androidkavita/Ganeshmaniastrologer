package com.callastro.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.callastro.R
import com.callastro.databinding.ActivityConfirmationBookingDetailsReportBinding
import com.callastro.viewModels.ConfirmationBookingsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header.view.*

@AndroidEntryPoint
class ConfirmationBookingDetailsReportActivity : BaseActivity() {
    lateinit var binding: ActivityConfirmationBookingDetailsReportBinding
    lateinit var getReportListId: String
    lateinit var user_id: String
    lateinit var getlist_userName: String
    lateinit var getlist_requeststatus: String
    lateinit var profile: String
    private val viewModel: ConfirmationBookingsViewModel by viewModels()
    lateinit var bottomdialog: BottomSheetDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirmation_booking_details_report)

        binding.header.backArrow.setOnClickListener { finish() }
        binding.header.tvHeadName.text = "Report Request"

        getReportListId = intent.getStringExtra("list_id").toString()
        user_id = intent.getStringExtra("user_id").toString()
        getlist_userName = intent.getStringExtra("list_userName").toString()
//        getlist_requeststatus = intent.getStringExtra("requeststatus").toString()
        profile = intent.getStringExtra("profile").toString()



//        if (isAdded()){
//            //your code goes here
//        } else {
//            //handle the case
//        }
        viewModel.confirmation_booking_report_detail("Bearer " +  userPref.getToken(),getReportListId)

        viewModel.confirmationReportHistoryResponse.observe(this){
            if (it.status == 1){
                binding.tvUserName.text = getlist_userName
                binding.tvLanguage.text = it.data!!.languageId
                Glide.with(this).load(profile).into(binding.profile)



                if(it.data!!.dob!!.isEmpty() ||it.data!!.dob == null)
                {binding.tvDob.text = "..."}
                else{binding.tvDob.text = it.data!!.dob}

                if(it.data!!.birthTime!!.isEmpty()|| it.data!!.birthTime == null)
                {binding.tvTimeOfBirth.text = "..."}
                else{binding.tvTimeOfBirth.text = it.data!!.birthTime}

                if(it.data!!.placeBirth!!.isEmpty()||it.data!!.placeBirth == null)
                {binding.tvPlaceOfBirth.text = "..."}
                else{binding.tvPlaceOfBirth.text = it.data!!.placeBirth}

                if(it.data!!.occupation!!.isEmpty()||it.data!!.occupation == null)
                {binding.tvOccupation.text = "..."}
                else{binding.tvOccupation.text = it.data!!.occupation}

                if(it.data!!.maritialStatus!!.isEmpty()||it.data!!.maritialStatus == null)
                {binding.tvMaritalStatus.text = "..."}
                else{binding.tvMaritalStatus.text = it.data!!.maritialStatus}

                if(it.data!!.topicConsultation!!.isEmpty()||it.data!!.topicConsultation == null)
                {binding.tvTopicOfConsultation.text = "..."}
                else{binding.tvTopicOfConsultation.text = it.data!!.topicConsultation}



            }
        }









        binding.llSendReport.setOnClickListener {
            startActivity(Intent(this@ConfirmationBookingDetailsReportActivity,UploadDocument::class.java)
                .putExtra("id",getReportListId)
                .putExtra("user_id",user_id)
            )
        }
    }
}