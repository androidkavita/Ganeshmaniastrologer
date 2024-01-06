package com.callastro.ui.activities

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.callastro.R
import com.callastro.databinding.ActivityEmailWithUsBinding
import com.callastro.viewModels.CustomerViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header.view.*

@AndroidEntryPoint
class EmailWithUs : BaseActivity() {
    lateinit var binding : ActivityEmailWithUsBinding
    private val viewModel: CustomerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_with_us)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_email_with_us)

        binding.header.backArrow.setOnClickListener {
            finish()
        }
        binding.forCopy.setOnClickListener {
            copyTextToClipboard()
        }
        binding.email.setOnClickListener {
            val emailIntent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", binding.email.text.toString().trim(), null
                )
            )
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Body")
            startActivity(Intent.createChooser(emailIntent, "Send email..."))
        }
        if (CommonUtils.isInternetAvailable(this@EmailWithUs)) {
            viewModel.EmailUs(
                userPref.getToken().toString()
            )
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this@EmailWithUs,"Please check internet connection.")
        }

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.emailusResponse.observe(this) {
            if (it?.status == 1) {
                binding.email.text = it.data?.email.toString()
            } else {
                snackbar(it?.message!!)
            }

        }

    }
    fun copyTextToClipboard() {
        val textToCopy = binding.email.text
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", textToCopy)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_LONG).show()
    }
}