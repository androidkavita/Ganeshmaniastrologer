package com.callastro.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.callastro.R
import com.callastro.adapters.ViewPagerMyBookingsAdapter
import com.callastro.databinding.ActivityMyBookingsBinding
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header.view.*


@AndroidEntryPoint
class MyBookingsActivity : BaseActivity() {
    private lateinit var binding : ActivityMyBookingsBinding
    private var ecommerceProductsAdapter: ViewPagerMyBookingsAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_bookings)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.header.backArrow.setOnClickListener { finish() }
        binding.header.tvHeadName.text = "My Bookings"


        setTab()

    }
    private fun setTab() {
        ecommerceProductsAdapter = ViewPagerMyBookingsAdapter(supportFragmentManager)
        binding.viewPager.adapter = ecommerceProductsAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tabLayout.getChildAt(0)
    }
}