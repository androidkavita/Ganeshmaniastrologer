package com.callastro.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.callastro.R
import com.callastro.databinding.ActivityAboutusBinding
import com.callastro.viewModels.CustomerViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header.view.*

@AndroidEntryPoint
class AboutusActivity : BaseActivity() {
    lateinit var binding: ActivityAboutusBinding
    private val viewModel: CustomerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aboutus)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_aboutus)

        binding.header.backArrow.setOnClickListener {
            finish()
        }
        binding.header.tvHeadName.text = "About Us"

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.AboutUsResponse.observe(this) {
            if (it?.status == 1) {
                binding.simpletext.text = it.data?.details.toString()
            } else {
                snackbar(it?.message!!)
            }
        }

        if (CommonUtils.isInternetAvailable(this)) {
            viewModel.AboutUs(
                "Bearer "+userPref.getToken().toString()
            )
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }

    }
}