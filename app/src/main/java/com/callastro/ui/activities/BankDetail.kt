package com.callastro.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.callastro.R
import com.callastro.databinding.ActivityBankDetailBinding
import com.callastro.viewModels.ProfileViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header.view.backArrow
import kotlinx.android.synthetic.main.header.view.tvHeadName

@AndroidEntryPoint
class BankDetail : BaseActivity() {
    lateinit var binding: ActivityBankDetailBinding
    private val viewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_detail)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bank_detail)

        binding.header.backArrow.setOnClickListener { finish() }
        binding.header.tvHeadName.text = "Bank Details"

        binding.btnSave.setOnClickListener {
            if (binding.etBankName.text.isNullOrEmpty()){
                snackbar("Please enter Bank Name.")
            }else if (binding.etAccountHoldersName.text.isNullOrEmpty()){
                snackbar("Please enter Account Holder Name.")
            }else if (binding.etAccountNumber.text.isNullOrEmpty()){
                snackbar("Please enter Account Number.")
            }else if (binding.etConfirmAccountNumber.text.isNullOrEmpty()){
                snackbar("Please enter Confirm Account Number.")
            }else if (!binding.etAccountNumber.text.toString().equals(binding.etConfirmAccountNumber.text.toString())){
                snackbar("Confirm account number doesn't match.")
            }else if (binding.etBranch.text.isNullOrEmpty()){
                snackbar("Please enter Branch Name.")
            }else if (binding.etIFSCCode.text.isNullOrEmpty()){
                snackbar("Please enter IFSC code.")
            }else{
                if (CommonUtils.isInternetAvailable(this)) {
                    viewModel.UpdateBank(
                        "Bearer "+userPref.getToken().toString(),
                        binding.etBankName.text.toString(),
                        binding.etAccountNumber.text.toString(),
                        binding.etAccountHoldersName.text.toString(),
                        binding.etIFSCCode.text.toString(),
                        binding.etBranch.text.toString()
                    )
                } else {
                    Log.d("TAG", "onCreate: " + "else part")
                    toast(this,"Please check internet connection.")
                }


            }
        }

        if (CommonUtils.isInternetAvailable(this)) {
            viewModel.GeteBankDetails(
                "Bearer "+userPref.getToken().toString(),
            )
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }

        viewModel.getBankDetailResponse.observe(this){
            if (it.status == 1){
                binding.etBankName.setText(it.data?.bankName.toString())
                binding.etAccountHoldersName.setText(it.data?.holderName.toString())
                binding.etAccountNumber.setText(it.data?.accountNo.toString())
                binding.etConfirmAccountNumber.setText(it.data?.accountNo.toString())
                binding.etBranch.setText(it.data?.branch.toString())
                binding.etIFSCCode.setText(it.data?.ifscCode.toString())
            }
        }

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.updateBankResponse.observe(this) {
            if (it?.status == 1) {
                finish()
            }else{
                snackbar(it.message.toString())
            }
        }


    }
}