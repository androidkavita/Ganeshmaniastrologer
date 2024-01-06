package com.callastro.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.callastro.ui.fragments.ConfirmationBookingCallFragment
import com.callastro.ui.fragments.ConfirmationBookingChatFragment
import com.callastro.ui.fragments.ConfirmationBookingFixedSessionFragment
import com.callastro.ui.fragments.ConfirmationBookingReportFragment


class ViewPagerConfirmationOnBooking(
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val titles =
        arrayOf("Chat","Call","Fixed Session","Report")

    override fun getItem(position: Int): Fragment {

        val bundle = Bundle()
        val fragment : Fragment
        return when (position) {
            0 -> {
                fragment = ConfirmationBookingChatFragment()
                bundle.putInt("type",0)
                fragment.arguments = bundle
                fragment
            }
            1 -> {
                fragment = ConfirmationBookingCallFragment()
                bundle.putInt("type",1)
                fragment.arguments = bundle
                fragment
            }
            2 -> {
                fragment = ConfirmationBookingFixedSessionFragment()
                bundle.putInt("type",2)
                fragment.arguments = bundle
                fragment
            }
            else -> {
                fragment = ConfirmationBookingReportFragment()
                bundle.putInt("type",3)
                fragment.arguments = bundle
                fragment
            }
        }
    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position].toString()
    }
}