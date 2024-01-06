package com.callastro.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.callastro.R
import com.callastro.databinding.ActivityCustomerSupportBinding
import com.maxtra.callastro.baseClass.BaseActivity
import kotlinx.android.synthetic.main.header.view.*

class Customer_Support : BaseActivity() {
    lateinit var binding: ActivityCustomerSupportBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_support)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer_support)


        binding.header.backArrow.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.header.tvHeadName.text = "Customer Support"

        binding.emailusLL.setOnClickListener {
            var intent = Intent(this, EmailWithUs::class.java)
            startActivity(intent)
        }

        binding.callbackLL.setOnClickListener {
            var intent = Intent(this, CallbackForm::class.java)
            startActivity(intent)
        }
        binding.llChatWithUs.setOnClickListener {
            var intent = Intent(this, CustomerAdminChat::class.java)
                .putExtra("list_id","1")
                .putExtra("list_userName","Customer Support")
            startActivity(intent)
        }


    }
}