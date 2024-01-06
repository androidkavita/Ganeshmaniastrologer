package com.callastro.ui.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.callastro.R
import com.callastro.adapters.EarningsAdapter
import com.callastro.databinding.ActivityEarningsHomeBinding
import com.callastro.databinding.AddmoneyBinding
import com.callastro.model.AstroEarningTransactionHistory
import com.callastro.viewModels.EarningsViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header.view.backArrow
import kotlinx.android.synthetic.main.header.view.tvHeadName

@AndroidEntryPoint
class EarningsHome : BaseActivity() , EarningsAdapter.OnClick{
    lateinit var binding: ActivityEarningsHomeBinding
    private val viewModel: EarningsViewModel by viewModels()
    var selectedOption = ""
    var earningsListData : ArrayList<AstroEarningTransactionHistory> = ArrayList()
    lateinit var earningAdapter : EarningsAdapter
    var filter = "1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_earnings_home)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_earnings_home)

        binding.header.backArrow.setOnClickListener { finish() }
        binding.header.tvHeadName.text = "Money Requests"

        binding.requestmoney.setOnClickListener {
            REQUESTMONEY()
        }




        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.astroEarningResponse.observe(this) {
            if (it?.status == 1) {
                earningsListData.clear()
                earningsListData.addAll(it.data!!.transactionHistory)
                earningAdapter = EarningsAdapter(this, earningsListData,this)
                binding.rvPayments.adapter =earningAdapter
                binding.tvTotalEarning.setText( "₹"+ it!!.data!!.wallet)
                binding.tvwithdrawal.setText( "₹"+ it!!.data!!.withdrawal)
                binding.remaining.setText( "₹"+ it!!.data!!.remainig)




            } else {
                //toast(it.message)
                snackbar(it?.message!!)
            }
        }


        viewModel.commonResponse.observe(this){
            if (it.status == 1){
                toast(this@EarningsHome,"Request Sent")
                if (CommonUtils.isInternetAvailable(this)) {
                    viewModel.astro_earningApi("Bearer "+userPref.getToken().toString(),"1")
                } else {
                    Log.d("TAG", "onCreate: " + "else part")
                    toast(this,"Please check internet connection.")
                }

            }else{
                toast(this@EarningsHome,it.message.toString())
            }
        }


        binding.spinnerLoaderHistory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedOption = parent.getItemAtPosition(position).toString()
                    Log.d("PositionSelected", selectedOption)
                    /*if (selectedOption == "Select payment Mode") {
                    toast(this@EarningsHome,"Please select payment mode.")
                }*/
                    if (selectedOption == "All") {
                        filter = "1"
                        toast(this@EarningsHome, "All")
                        if (CommonUtils.isInternetAvailable(this@EarningsHome)) {
                            viewModel.astro_earningApi("Bearer "+userPref.getToken().toString(),filter)
                        } else {
                            Log.d("TAG", "onCreate: " + "else part")
                            toast(this@EarningsHome,"Please check internet connection.")
                        }

                    }
                    else if (selectedOption == "Daily") {
                        filter = "2"
                        toast(this@EarningsHome, "Daily")
                        if (CommonUtils.isInternetAvailable(this@EarningsHome)) {
                            viewModel.astro_earningApi("Bearer "+userPref.getToken().toString(),filter)
                        } else {
                            Log.d("TAG", "onCreate: " + "else part")
                            toast(this@EarningsHome,"Please check internet connection.")
                        }


                    }
                    else if (selectedOption == "Monthly") {
                        filter = "3"
                        toast(this@EarningsHome, "Monthly")
                        if (CommonUtils.isInternetAvailable(this@EarningsHome)) {
                            viewModel.astro_earningApi("Bearer "+userPref.getToken().toString(),filter)
                        } else {
                            Log.d("TAG", "onCreate: " + "else part")
                            toast(this@EarningsHome,"Please check internet connection.")
                        }


                    }
                    else if (selectedOption == "Yearly") {
                        filter = "4"
                        toast(this@EarningsHome, "Yearly")
                        if (CommonUtils.isInternetAvailable(this@EarningsHome)) {
                            viewModel.astro_earningApi("Bearer "+userPref.getToken().toString(),filter)
                        } else {
                            Log.d("TAG", "onCreate: " + "else part")
                            toast(this@EarningsHome,"Please check internet connection.")
                        }


                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun REQUESTMONEY() {
        val cDialog = Dialog(this)
        val bindingDialog: AddmoneyBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.addmoney,
            null,
            false
        )
        cDialog.setContentView(bindingDialog.root)
        cDialog.setCancelable(true)
        cDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        cDialog.show()
        bindingDialog.btnCancel.setOnClickListener {
            cDialog.dismiss()
        }

        bindingDialog.btnLogout.setOnClickListener {
            if (CommonUtils.isInternetAvailable(this@EarningsHome)) {
                viewModel.request_money("Bearer "+userPref.getToken().toString(),bindingDialog.fullnameet.text.toString())
            } else {
                toast(this@EarningsHome,"Please check internet connection.")
            }

            cDialog.dismiss()
        }
    }
    override fun onCallItemClicked(astroEarningTransactionHistory: AstroEarningTransactionHistory) {

    }
}