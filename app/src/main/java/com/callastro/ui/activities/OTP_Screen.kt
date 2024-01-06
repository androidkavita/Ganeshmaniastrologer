package com.callastro.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.callastro.MainActivity
import com.callastro.R
import com.callastro.data.MainRepository
import com.callastro.databinding.ActivityOtpScreenBinding
import com.callastro.viewModels.LoginViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.astrorahiastrologer.util.toast
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OTP_Screen : BaseActivity() {
    lateinit var binding: ActivityOtpScreenBinding
    private val viewModel: LoginViewModel by viewModels()
    var id:String = ""
    var otp:String = ""
    var is_new:String = ""
    var mobile:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_screen)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_otp_screen)

        binding.btnSubmit.setOnClickListener {
            startActivity(Intent(this@OTP_Screen,MainActivity::class.java))
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        if (intent!=null){
            id = intent.getStringExtra("id").toString()
            otp = intent.getStringExtra("otp").toString()
            is_new = intent.getStringExtra("is_new").toString()
            mobile = intent.getStringExtra("mobile").toString()
        }
        binding.otptext.setText("OTP sent to $mobile")
        toast(otp)

        TimeCountDown()

        binding.otpView.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val  getOtp=p0.toString()
                    if (getOtp.length==4){
//                        veriefyOtp!!.isEnabled=true
//                        veriefyOtp.isClickable=true
//                        veriefyOtp.setBackgroundColor(resources.getColor(R.color.black))
                        val imm: InputMethodManager? = getSystemService(Context.INPUT_METHOD_SERVICE)as InputMethodManager;
                        imm?.hideSoftInputFromWindow(binding.otpView.getWindowToken(), 0);
                    }else{
//                        veriefyOtp!!.isEnabled=false
//                        veriefyOtp.isClickable=false
//                        veriefyOtp.setBackgroundColor(resources.getColor(R.color.gray_button))
                    }
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            }
        )

        binding.resend.setOnClickListener {
            viewModel.Recent_otp(
                mobile,"2"
            )
        }
        viewModel.loginResponse.observe(this){
            if (it.status == 1){
                toast(this@OTP_Screen,it.data?.otp.toString())
                TimeCountDown()
            }
        }
        binding.btnSubmit.setOnClickListener {
            if (binding.otpView.text.isNullOrEmpty()){
                snackbar("Please enter OTP")
            }else{
                if (CommonUtils.isInternetAvailable(this)) {
                    viewModel.LoginVerification(
                        id,
                        binding.otpView.text.toString()
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
        viewModel.loginVerificationResponse.observe(this) {
            if (it?.status == 1) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("id",id)
                intent.putExtra("otp",otp)
                intent.putExtra("is_new",is_new)
                startActivity(intent)
                finishAffinity()
//                userPref.user = it.data!!
                userPref.isLogin = true
                userPref.setToken(it.otpdata!!.apiToken.toString())
                userPref.setid(it.otpdata?.id.toString())
//                userPref.setUserId(it.data!!.id!!.toString())
                userPref.setMobile(it.otpdata!!.mobileNo.toString())
                userPref.setGender(it.otpdata!!.gender.toString())
                userPref.set_is_new(is_new)
                if(is_new.equals("0")) {
                    userPref.setName(it.otpdata?.name!!)
                    userPref.setEmail(it.otpdata!!.email)
                }
                userPref.setProfileImage(it.otpdata!!.profile.toString())
//                viewModel.agora_create_userApi("Bearer " + it.otpdata!!.apiToken.toString(), it.otpdata?.id.toString(), "TestName")
            } else {
                //toast(it.message)
                snackbar(it?.message!!)
            }
        }


    }
    fun TimeCountDown(){
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if(millisUntilFinished / 1000<10){
                    binding.timer.setText("00:0" + millisUntilFinished / 1000)
                }else{
                    binding.timer.setText("00:" + millisUntilFinished / 1000)

                }
                binding.resend.isEnabled = false
                binding.resend.setBackgroundDrawable(resources.getDrawable(R.drawable.buttondisable))

            }

            override fun onFinish() {
                binding.timer.setText("00:00")
                binding.resend.isEnabled = true
                binding.resend.setBackgroundDrawable(resources.getDrawable(R.drawable.button))

            }
        }.start()
    }
}