package com.callastro.ui.fragments

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
import com.callastro.adapters.MyBookingsCompletedAdapter
import com.callastro.baseClass.BaseFragment
import com.callastro.databinding.FragmentMyBookingsCompletedBinding
import com.callastro.model.MyBookingsUpcomingCompletedData
import com.callastro.viewModels.MyBookingsViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class MyBookingsCompletedFragment : BaseFragment() {

    lateinit var binding : FragmentMyBookingsCompletedBinding
    private val viewModel : MyBookingsViewModel by viewModels()
    private var bookingsCompletedAdapter : MyBookingsCompletedAdapter?= null
    private var listCompletedBooking: ArrayList<MyBookingsUpcomingCompletedData> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_bookings_completed, container, false)

        viewModel.progressBarStatus.observe(requireActivity()) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.upcomingCompletedBookingsResponse.observe(requireActivity()) {
            if (it.status == 1) {
                listCompletedBooking.clear()
                // listData!!.addAll(it.getFavLocdata)

                if (it.data.isEmpty() ) {
                    binding.idNouser.visibility = View.VISIBLE
                    binding.rvList.visibility = View.GONE

                }
                else {
                    binding.idNouser.visibility = View.GONE
                    binding.rvList.visibility = View.VISIBLE
                    listCompletedBooking.addAll(it.data)
                    bookingsCompletedAdapter = MyBookingsCompletedAdapter(listCompletedBooking)
                    binding.rvList.apply {
                        adapter = bookingsCompletedAdapter
                        layoutManager = LinearLayoutManager(requireActivity())
                        // it.getFavLocdata?.let { notificationList?.addAll(it) }
                        //    favouriteLocationsAdapter?.notifyDataSetChanged()
                    }
                }
            } else   {
                Log.d("Response", it.toString())
                toast(requireContext(),it.message!!)
                binding.idNouser.visibility = View.VISIBLE
                binding.rvList.visibility = View.GONE
            }
        }

        if (CommonUtils.isInternetAvailable(requireContext())) {
            viewModel.upcomingCompletedBookingsApi("Bearer " +  userPref.getToken(), "2")
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(requireContext(),"Please check internet connection.")
        }





        return binding.root

    }


}