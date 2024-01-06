package com.callastro.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.callastro.R
import com.callastro.adapters.MyBookingsUpcomingAdapter
import com.callastro.baseClass.BaseFragment
import com.callastro.databinding.FragmentMyBookingsUpcomingBinding
import com.callastro.model.MyBookingsUpcomingCompletedData
import com.callastro.ui.activities.ChatwithUsActivity
import com.callastro.viewModels.MyBookingsViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class MyBookingsUpcomingFragment : BaseFragment(), MyBookingsUpcomingAdapter.OnClick {
    lateinit var binding : FragmentMyBookingsUpcomingBinding
    private val viewModel : MyBookingsViewModel by viewModels()
    private var bookingsUpcomingAdapter : MyBookingsUpcomingAdapter?= null
    private var listupcomingBooking: ArrayList<MyBookingsUpcomingCompletedData> = ArrayList()


    lateinit var chatList_id: String
    lateinit var chatList_userName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_bookings_upcoming, container, false)



        viewModel.progressBarStatus.observe(requireActivity()) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.upcomingCompletedBookingsResponse.observe(requireActivity()) {
            if (it.status == 1) {
                listupcomingBooking.clear()
                // listData!!.addAll(it.getFavLocdata)

                if (it.data.isEmpty() ) {
                    binding.idNouser.visibility = View.VISIBLE
                    binding.rvUpcomingBookings.visibility = View.GONE

                }
                else {
                    binding.idNouser.visibility = View.GONE
                    binding.rvUpcomingBookings.visibility = View.VISIBLE
                    listupcomingBooking.addAll(it.data)

//                    bookingsUpcomingAdapter = MyBookingsUpcomingAdapter()
//                    binding.rvUpcomingBookings.adapter = bookingsUpcomingAdapter


                    bookingsUpcomingAdapter = MyBookingsUpcomingAdapter(listupcomingBooking,this)
                    binding.rvUpcomingBookings.apply {
                        adapter = bookingsUpcomingAdapter
                        layoutManager = LinearLayoutManager(requireActivity())
                        // it.getFavLocdata?.let { notificationList?.addAll(it) }
                        //    favouriteLocationsAdapter?.notifyDataSetChanged()
                    }
                }
            } else   {
                Log.d("Response", it.toString())
                toast(requireContext(),it.message!!)
                binding.idNouser.visibility = View.VISIBLE
                binding.rvUpcomingBookings.visibility = View.GONE
            }
        }
        if (CommonUtils.isInternetAvailable(requireContext())) {
            viewModel.upcomingCompletedBookingsApi("Bearer " + userPref.getToken(), "1")
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(requireContext(),"Please check internet connection.")
        }
        return binding.root

    }

    override fun onChatlItemClicked(myBookingsUpcomingCompletedData: MyBookingsUpcomingCompletedData) {
        chatList_id = myBookingsUpcomingCompletedData.id.toString()
        chatList_userName = myBookingsUpcomingCompletedData.userName.toString()
        startActivity(Intent(requireContext(), ChatwithUsActivity::class.java).also {
            it.putExtra("list_id", chatList_id)
            it.putExtra("list_userName", chatList_userName)
            Log.d("TAG++", "onProceedClicked: " + myBookingsUpcomingCompletedData.userName.toString())
        })
    }

}