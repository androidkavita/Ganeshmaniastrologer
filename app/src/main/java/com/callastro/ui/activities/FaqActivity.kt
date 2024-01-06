package com.callastro.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.callastro.R
import com.callastro.adapters.FAQAdapter
import com.callastro.databinding.ActivityFaqBinding
import com.callastro.model.FAQResponseData
import com.callastro.viewModels.CustomerViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header.view.*

@AndroidEntryPoint
class FaqActivity : BaseActivity() {
    lateinit var binding: ActivityFaqBinding
    private val viewModel: CustomerViewModel by viewModels()
    var Listdata : ArrayList<FAQResponseData> = ArrayList()
    lateinit var adapter : FAQAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_faq)


        binding.header.backArrow.setOnClickListener { finish() }
        binding.header.tvHeadName.text = "FAQ"

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.FAQResponse.observe(this) {
            if (it?.status == 1) {
                Listdata.clear()
                Listdata.addAll(it.data)
                adapter = FAQAdapter(this, Listdata)
                binding.rvFaq.adapter =adapter
            } else {
                snackbar(it?.message!!)
            }
        }
        if (CommonUtils.isInternetAvailable(this@FaqActivity)) {
            viewModel.FAQ(
                "Bearer "+userPref.getToken().toString()
            )
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this@FaqActivity,"Please check internet connection.")
        }

    }
}