package com.callastro.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.callastro.R
import com.callastro.adapters.EarningsAdapter
import com.callastro.adapters.TotalEarningsAdapter
import com.callastro.databinding.ActivityEarningsHomeBinding
import com.callastro.databinding.ActivityTotalEarningsBinding
import com.callastro.model.AstroEarningListResponseData
import com.callastro.model.AstroEarningTransactionHistory
import com.callastro.viewModels.EarningsViewModel
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header.view.backArrow
import kotlinx.android.synthetic.main.header.view.tvHeadName

@AndroidEntryPoint
class TotalEarnings : BaseActivity() {
    lateinit var binding: ActivityTotalEarningsBinding
    private val viewModel: EarningsViewModel by viewModels()
    var selectedOption = ""
    var earningsListData : ArrayList<AstroEarningListResponseData> = ArrayList()
    lateinit var earningAdapter : TotalEarningsAdapter
    var filter = "1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total_earnings)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_total_earnings)


        binding.header.backArrow.setOnClickListener { finish() }
        binding.header.tvHeadName.text = "Earnings"

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }


        viewModel.astro_history_wallets("Bearer "+userPref.getToken().toString())
        viewModel.astro_earningApi("Bearer "+userPref.getToken().toString(),"1")

        viewModel.astroEarningListResponse.observe(this){
            if (it.status == 1){
                earningsListData.clear()
                earningsListData.addAll(it.data)
                earningAdapter = TotalEarningsAdapter(this, earningsListData)
                binding.rvPayments.adapter =earningAdapter
//                binding.tvTotalEarning.setText( "₹"+ it!!.data!!.wallet)
//                binding.tvwithdrawal.setText( "₹"+ it!!.data!!.withdrawal)
//                binding.remaining.setText( "₹"+ it!!.data!!.remainig)
            }
        }

        viewModel.astroEarningResponse.observe(this) {
            if (it?.status == 1) {
                binding.tvTotalEarning.setText( "₹"+ it!!.data!!.wallet)
//                binding.tvwithdrawal.setText( "₹"+ it!!.data!!.withdrawal)
                binding.remaining.setText( "₹"+ it!!.data!!.remainig)
            } else {
                //toast(it.message)
                snackbar(it?.message!!)
            }
        }
    }
}