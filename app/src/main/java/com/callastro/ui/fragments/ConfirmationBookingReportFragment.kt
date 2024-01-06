package com.callastro.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.callastro.adapters.ConfirmationBookingReportAdapter
import com.callastro.baseClass.BaseFragment
import com.callastro.databinding.FragmentConfirmationBookingReportBinding
import com.callastro.model.ConfirmationBookingData
import com.callastro.ui.activities.ConfirmationBookingDetailsReportActivity
import com.callastro.viewModels.ConfirmationBookingsViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmationBookingReportFragment : BaseFragment(), ConfirmationBookingReportAdapter.OnClick {

    lateinit var binding: FragmentConfirmationBookingReportBinding
    private val viewModel: ConfirmationBookingsViewModel by viewModels()

    var reportBookingListData : ArrayList<ConfirmationBookingData> = ArrayList()
    lateinit var confirmationBookingReportAdapter : ConfirmationBookingReportAdapter


    lateinit var reportList_id: String
    lateinit var user_id: String
    lateinit var reportList_userName: String
    lateinit var reportList_requeststatus: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfirmationBookingReportBinding.inflate(inflater, container, false)


        viewModel.progressBarStatus.observe(requireActivity()) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.confirmationBookingResponse.observe(requireActivity()) {
            if (it.status == 1) {
                reportBookingListData.clear()
                // listData!!.addAll(it.getFavLocdata)

                if (it.data.isEmpty() ) {
                    binding.idNouser.visibility = View.VISIBLE
                    binding.rvConfirmBookingReport.visibility = View.GONE

                }
                else {
                    binding.idNouser.visibility = View.GONE
                    binding.rvConfirmBookingReport.visibility = View.VISIBLE
                    reportBookingListData.addAll(it.data)
                    confirmationBookingReportAdapter = ConfirmationBookingReportAdapter(requireContext(),reportBookingListData,this@ConfirmationBookingReportFragment)
                    binding.rvConfirmBookingReport.apply {
                        adapter = confirmationBookingReportAdapter
                        layoutManager = LinearLayoutManager(requireActivity())
                        // it.getFavLocdata?.let { notificationList?.addAll(it) }
                        //    favouriteLocationsAdapter?.notifyDataSetChanged()
                    }
                }
            } else   {
                toast(requireContext(),it.message!!)
                binding.idNouser.visibility = View.VISIBLE
                binding.rvConfirmBookingReport.visibility = View.GONE
            }
        }

        if (CommonUtils.isInternetAvailable(requireContext())) {
            viewModel.confirmation_booking_listApi("Bearer " +  userPref.getToken(), "4")
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(requireContext(),"Please check internet connection.")
        }
        return binding.root
    }

    override fun onListItemClicked(confirmationBookingData: ConfirmationBookingData) {
        reportList_id = confirmationBookingData.id.toString()
        user_id = confirmationBookingData.userId.toString()
        reportList_userName = confirmationBookingData.userName.toString()
        reportList_requeststatus = confirmationBookingData.requestStatus.toString()


        if (isAdded()){
            startActivity(Intent(requireContext(), ConfirmationBookingDetailsReportActivity::class.java).also {
                it.putExtra("list_id", reportList_id)
                it.putExtra("user_id", user_id)
                it.putExtra("list_userName", reportList_userName)
//            it.putExtra("requeststatus", reportList_requeststatus)
                it.putExtra("profile", confirmationBookingData.profile)


                Log.d("TAG++", "onProceedClicked: " + confirmationBookingData.id.toString())
            })
        } else {
            toast(requireContext(),"Nothing")
        }

    }




}