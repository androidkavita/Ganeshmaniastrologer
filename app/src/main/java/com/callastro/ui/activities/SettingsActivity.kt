package com.callastro.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.callastro.R
import com.callastro.databinding.ActivitySettingsBinding
import com.callastro.viewModels.SettingsViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header.view.*


@AndroidEntryPoint
class SettingsActivity : BaseActivity() {

    lateinit var binding: ActivitySettingsBinding
    val viewModel: SettingsViewModel by viewModels()


    lateinit var getChatStatus: String
    lateinit var getAudioStatus: String
    lateinit var getVideoStatus: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)
        binding.header.backArrow.setOnClickListener { finish() }
        binding.header.tvHeadName.text = "Settings"


        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }


        viewModel.getSettingsResponse.observe(this) {
            if (it?.status == 1) {

                getChatStatus = it.data!!.isChat.toString()
                getAudioStatus = it.data!!.isAudioCall.toString()
                getVideoStatus = it.data!!.isVideoCall.toString()

                if (getChatStatus == "1") {
                    binding.switchChat.isChecked = true
                } else if (getChatStatus == "0")  {
                    binding.switchChat.isChecked = false
                }

                if (getAudioStatus == "1") {
                    binding.switchAudioCall.isChecked = true
                } else if (getAudioStatus == "0")  {
                    binding.switchAudioCall.isChecked = false
                }

                if (getVideoStatus == "1") {
                    binding.switchVideoCall.isChecked = true
                } else if (getVideoStatus == "0")  {
                    binding.switchVideoCall.isChecked = false
                }

            } else {
                snackbar(it?.message!!)
            }
        }
        if (CommonUtils.isInternetAvailable(this)) {
            viewModel.setting_detailsApi("Bearer " + userPref.getToken())
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }



        viewModel.updateSettingsResponse.observe(this) {
            if (it?.status == 1) {

              //  toast(this,it.message!!)

            } else {
                snackbar(it?.message!!)
            }
        }












        binding.switchChat.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                getChatStatus = "1"
                if (CommonUtils.isInternetAvailable(this)) {
                    viewModel.setting_updateApi("Bearer " + userPref.getToken(),getChatStatus,getAudioStatus,getVideoStatus)
                } else {
                    Log.d("TAG", "onCreate: " + "else part")
                    toast(this,"Please check internet connection.")
                }

            } else {
                getChatStatus = "0"
                if (CommonUtils.isInternetAvailable(this)) {
                    viewModel.setting_updateApi("Bearer " + userPref.getToken(),getChatStatus,getAudioStatus,getVideoStatus)
                } else {
                    Log.d("TAG", "onCreate: " + "else part")
                    toast(this,"Please check internet connection.")
                }
            }
        }


        binding.switchAudioCall.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                getAudioStatus = "1"
                if (CommonUtils.isInternetAvailable(this)) {
                    viewModel.setting_updateApi("Bearer " + userPref.getToken(),getChatStatus,getAudioStatus,getVideoStatus)
                } else {
                    Log.d("TAG", "onCreate: " + "else part")
                    toast(this,"Please check internet connection.")
                }
            } else {
                getAudioStatus = "0"
                if (CommonUtils.isInternetAvailable(this)) {
                    viewModel.setting_updateApi("Bearer " + userPref.getToken(),getChatStatus,getAudioStatus,getVideoStatus)
                } else {
                    Log.d("TAG", "onCreate: " + "else part")
                    toast(this,"Please check internet connection.")
                }
            }
        }


        binding.switchVideoCall.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                getVideoStatus = "1"
                if (CommonUtils.isInternetAvailable(this)) {
                    viewModel.setting_updateApi("Bearer " + userPref.getToken(),getChatStatus,getAudioStatus,getVideoStatus)
                } else {
                    Log.d("TAG", "onCreate: " + "else part")
                    toast(this,"Please check internet connection.")
                }
            } else {
                getVideoStatus = "0"
                if (CommonUtils.isInternetAvailable(this)) {
                    viewModel.setting_updateApi("Bearer " + userPref.getToken(),getChatStatus,getAudioStatus,getVideoStatus)
                } else {
                    Log.d("TAG", "onCreate: " + "else part")
                    toast(this,"Please check internet connection.")
                }
            }
        }















    }
}