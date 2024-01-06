package com.callastro.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.callastro.R
import com.callastro.adapters.CancellationByUserAdapter
import com.callastro.databinding.ActivityCancellationByUserBinding
import com.callastro.model.CancellationByUserData
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.astrorahiastrologer.viewmodel.ChatCallViewModel
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header.view.backArrow
import kotlinx.android.synthetic.main.header.view.tvHeadName


@AndroidEntryPoint
class CancellationByUser : BaseActivity() {
    lateinit var binding: ActivityCancellationByUserBinding
    private val viewModel: ChatCallViewModel by viewModels()


    var listdata : ArrayList<CancellationByUserData> = ArrayList()
    lateinit var adapter : CancellationByUserAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancellation_by_user)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cancellation_by_user)

        binding.header.backArrow.setOnClickListener { finish() }
        binding.header.tvHeadName.text = "Cancellation by User"




        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }


        viewModel.cancellationListResponse.observe(this) {
            if (it?.status == 1) {
                listdata.clear()
                listdata.addAll(it.data)
                adapter = CancellationByUserAdapter(this, listdata)
                binding.rvCancellationByUser.adapter =adapter
            } else {
                //toast(it.message)
                snackbar(it?.message!!)
            }
        }



    }
    override fun onResume() {
        super.onResume()

        if (CommonUtils.isInternetAvailable(this)) {
            viewModel.cancellationByUserApi("Bearer "+userPref.getToken().toString(),)
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }

    }
}