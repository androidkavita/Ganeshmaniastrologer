package com.callastro.ui.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.callastro.R
import com.callastro.adapters.ViewPagerConfirmationOnBooking
import com.callastro.databinding.ActivityConfirmationOnBookingsBinding
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header.view.*

@AndroidEntryPoint
class ConfirmationOnBookings : BaseActivity() {
    private var historyPagerAdapter: ViewPagerConfirmationOnBooking?= null
    lateinit var binding: ActivityConfirmationOnBookingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation_on_bookings)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirmation_on_bookings)
        binding.header.backArrow.setOnClickListener { finish() }
        binding.header.tvHeadName.text = "Confirmation Of Bookings"
        setTab()
    }
    private fun setTab() {
        historyPagerAdapter = ViewPagerConfirmationOnBooking(supportFragmentManager)
        binding.viewPager.adapter = historyPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tabLayout.getChildAt(0)
        binding.viewPager.adapter = historyPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }
}