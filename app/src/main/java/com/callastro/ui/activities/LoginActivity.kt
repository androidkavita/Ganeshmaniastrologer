package com.callastro.ui.activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.callastro.R
import com.callastro.databinding.ActivityLoginBinding
import com.callastro.viewModels.LoginViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    lateinit var id :String
    lateinit var otp :String
    lateinit var is_new :String
    var countryCode = ""
    var token = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }

            // Get new FCM registration token
            token = task.result
        })
        countryCode = binding.ccp.selectedCountryCode
        binding.ccp.setOnCountryChangeListener(com.hbb20.CountryCodePicker.OnCountryChangeListener {
            countryCode = binding.ccp.selectedCountryCode

        })
        binding.btnSendOtp.setOnClickListener {
            if (binding.etPhone.text.isNullOrEmpty()){
                snackbar("Please enter mobile number.")
            }else if (binding.etPhone.text.length < 10){
                snackbar("Please enter valid number.")
            }else {
                if (CommonUtils.isInternetAvailable(this)) {
                    viewModel.Login(
                        countryCode,binding.etPhone.text.toString().trim(),"android","android","android",token
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
        viewModel.loginResponse.observe(this) {
            if (it?.status == 1) {
                id = it.data?.id.toString()
                otp = it.data?.otp.toString()
                is_new = it.data?.is_new.toString()
                if (it.approval == 1){
                    val intent = Intent(this@LoginActivity, OTP_Screen::class.java)
                    intent.putExtra("id",id)
                    intent.putExtra("otp",otp)
                    intent.putExtra("is_new",is_new)
                    intent.putExtra("mobile",binding.etPhone.text.toString())
                    startActivity(intent)
                }else if(it.approval == 0){
                    Alertdialog("Please wait for admin approval, once approved, you will get notified.")
                }

//                if (it.data?.is_new == "1"){
//                    var intent = Intent(this, ::class.java)
//                    intent.putExtra("id",id)
//                    intent.putExtra("otp",otp)
//                    startActivity(intent)
//                }else{
//                    var intent = Intent(this, OtpScreenActivity::class.java)
//                    intent.putExtra("id",id)
//                    intent.putExtra("otp",otp)
//                    startActivity(intent)
//                }
//                userPref.user = it.data!!
//                userPref.isLogin = true
//                userPref.setid(it.data?.id.toString())
//                userPref.setToken(it.data!!.apiToken)
//                userPref.setRole(it.data!!.role.toString())
//                userPref.setName(it.data?.name!!)
//                userPref.setEmail(it.data!!.email)
//                userPref.setMobile(it.data!!.mobileNumber.toString())
//                userPref.setUserId(it.data!!.id!!.toString())
//                it.data!!.profileImage?.let { it1 -> userPref.setImage(it1) }
//                userPref.setUserId(it!!.data!!.Id.toString())
            } else {
                //toast(it.message)
                snackbar(it?.message!!)
            }
        }
    }

    private fun Alertdialog(message:String){
        val buinder = AlertDialog.Builder(this)

        buinder.setMessage(message)
        buinder.setIcon(R.drawable.alert)
        buinder.setTitle("Alert")

        buinder.setPositiveButton("OK") { dialogInterface, which ->
        }
        val alertDialog: AlertDialog = buinder.create()
        alertDialog.setCancelable(true)
        alertDialog.show()
    }

}