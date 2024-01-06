package com.callastro.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.callastro.R
import com.callastro.databinding.ActivityConfirmationBookingDetailsFsactivityBinding
import com.callastro.viewModels.ConfirmationBookingsViewModel
import com.maxtra.callastro.baseClass.BaseActivity
import kotlinx.android.synthetic.main.header.view.*

class ConfirmationBookingDetailsFSActivity : BaseActivity() {

    lateinit var binding: ActivityConfirmationBookingDetailsFsactivityBinding
    lateinit var getFsListId: String
    lateinit var getlist_userName: String
    lateinit var getlist_requeststatus: String
    private val viewModel: ConfirmationBookingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirmation_booking_details_fsactivity)

        binding.header.backArrow.setOnClickListener { finish() }
        binding.header.tvHeadName.text = "Fixed Session Booking Details"

        getFsListId = intent.getStringExtra("list_id").toString()
        getlist_userName = intent.getStringExtra("list_userName").toString()
        getlist_requeststatus = intent.getStringExtra("requeststatus").toString()


        viewModel.fix_session_detail(
            "Bearer " +  userPref.getToken(), getFsListId
        )

        viewModel.fixsessionDetailResponse.observe(this){
            if (it.status == 1){
                toast(this@ConfirmationBookingDetailsFSActivity,it.data.toString())
                binding.tvUserName.text = it.data.name.toString()
                binding.tvLanguage.text = it.data.language.toString()
                binding.tvUserPhnNumber.text = it.data.mobile_no.toString()
                binding.tvDob.text = it.data.dob.toString()
                binding.tvTimeOfBirth.text = it.data.birthTime.toString()
                binding.tvPlaceOfBirth.text = it.data.placeBirth.toString()
                binding.tvOccupation.text = it.data.occupation.toString()
                binding.tvMaritalStatus.text = it.data.maritialStatus.toString()
                binding.tvTopicOfConsultation.text = it.data.topicConsultation.toString()




            }
        }

        binding.llCall.setOnClickListener {

        }

        binding.llCancel.setOnClickListener {


        }



    }
}