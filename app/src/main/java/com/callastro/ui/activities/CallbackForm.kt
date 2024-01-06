package com.callastro.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.callastro.R
import com.callastro.databinding.ActivityCallbackFormBinding
import com.callastro.viewModels.CustomerViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.astrorahiastrologer.util.toast
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header.view.*

@AndroidEntryPoint
class CallbackForm : BaseActivity() {
    lateinit var binding: ActivityCallbackFormBinding
    private val viewModel: CustomerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_callback_form)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_callback_form)

        binding.header.backArrow.setOnClickListener {  onBackPressedDispatcher.onBackPressed() }
        binding.header.tvHeadName.text = "Callback Form"

        binding.submitbtn.setOnClickListener {
            if (binding.etMobile.text.isNullOrEmpty()){
                snackbar("Please enter mobile number.")
            }
            else if (binding.etMobile.text.length < 10){
                snackbar("Please enter valid number.")
                binding.etMobile.requestFocus()
            }
            else if (binding.dicription.text.isNullOrEmpty()){
                snackbar("Please enter your discription.")
            }else{
                if (CommonUtils.isInternetAvailable(this)) {
                    viewModel.Callback(
                        "Bearer "+userPref.getToken().toString(),
                        binding.etMobile.text.toString(),
                        binding.dicription.text.toString()
                    )
                } else {
                    Log.d("TAG", "onCreate: " + "else part")
                    toast(this,"Please check internet connection.")
                }

            }
        }
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.CommonResponse.observe(this) {
            if (it?.status == 1) {
                finish()
                toast(it.message.toString())
            } else {
                snackbar(it?.message!!)
            }
        }
    }
}