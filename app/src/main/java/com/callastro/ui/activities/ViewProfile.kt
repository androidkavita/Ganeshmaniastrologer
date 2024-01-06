package com.callastro.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.callastro.R
import com.callastro.databinding.ActivityViewProfileBinding
import com.callastro.viewModels.ProfileViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewProfile : BaseActivity() {
    lateinit var binding: ActivityViewProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_profile)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_profile)
        binding.backArrow.setOnClickListener {
            finish()
        }

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        binding.llEditProfile.setOnClickListener {
            var intent = Intent(this,EditProfile::class.java)
            startActivity(intent)
        }
//        binding.llManageBookings.setOnClickListener {
//            var intent = Intent(this,MyBookingsActivity::class.java)
//            startActivity(intent)
//        }
        binding.llBankDetails.setOnClickListener {
            var intent = Intent(this,BankDetail::class.java)
            startActivity(intent)
        }
//        binding.llLocationDetails.setOnClickListener {
//            var intent = Intent(this,LocationDetailsActivity::class.java)
//            startActivity(intent)
//        }
        if (CommonUtils.isInternetAvailable(this)) {
            viewModel.ViewProfile("Bearer "+userPref.getToken().toString())
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }
        viewModel.viewProfileResponse.observe(this) {
            if (it?.status == 1) {
                binding.tvFullName.text = it.data?.name.toString()
                binding.tvGender.text = it.data?.gender.toString()
                binding.tvPhone.text = it.data?.mobileNo.toString()
                binding.tvEmailid.text = it.data?.email.toString()

                Glide.with(this).load(it.data!!.profile)
                    .apply(RequestOptions.placeholderOf(R.drawable.user_image_place_holder))
                    .apply(RequestOptions.errorOf(R.drawable.user_image_place_holder))
                    .into(binding.ivPict)

              userPref.setToken(it.data!!.apiToken)
                userPref.setName(it.data!!.name.toString())
                userPref.setMobile(it.data!!.mobileNo.toString())
                userPref.setEmail(it.data!!.email.toString())
                userPref.setGender(it.data!!.gender.toString())
                if (it.data!!.profile != null) {
                    userPref.setProfileImage(it.data!!.profile)
                    Log.e("@@image2", userPref.getProfileImage().toString())
                }else{
                    userPref.setProfileImage("")
                }
                if (it.data?.gender.toString() == "1"){
                    binding.llGender.visibility = View.VISIBLE
                    binding.tvGender.text = "Male"

                }else if (it.data?.gender.toString() == "2"){
                    binding.llGender.visibility = View.VISIBLE
                    binding.tvGender.text = "Female"
                }
                else if (it.data?.gender.toString() == "0"){
                    binding.llGender.visibility = View.GONE
                }
            }else{
                snackbar(it.message.toString())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (CommonUtils.isInternetAvailable(this)) {
            viewModel.ViewProfile("Bearer "+userPref.getToken().toString())
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }


    }
}