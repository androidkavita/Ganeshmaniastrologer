package com.callastro.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.callastro.adapters.ConfirmationBookingFSAdapter
import com.callastro.baseClass.BaseFragment
import com.callastro.databinding.FragmentConfirmationBookingFixedSessionBinding
import com.callastro.model.ConfirmationBookingData
import com.callastro.ui.activities.ChatwithUsActivity
import com.callastro.ui.activities.ConfirmationBookingDetailsFSActivity
import com.callastro.ui.activities.DashboardAudioCallActivity
import com.callastro.ui.activities.DashboardVideoCallActivity
import com.callastro.viewModels.ConfirmationBookingsViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.row_confirmationbooking_fixedsession.*

@AndroidEntryPoint
class ConfirmationBookingFixedSessionFragment : BaseFragment(),
    ConfirmationBookingFSAdapter.OnClick {

    lateinit var binding: FragmentConfirmationBookingFixedSessionBinding
    private val viewModel: ConfirmationBookingsViewModel by viewModels()

    var fsBookingListData: ArrayList<ConfirmationBookingData> = ArrayList()
    lateinit var confirmationBookingFSAdapter: ConfirmationBookingFSAdapter

    lateinit var callList_id: String
    lateinit var callList_userid: String
    lateinit var callList_userName: String
    lateinit var callList_requeststatus: String

    lateinit var fsList_id: String
    lateinit var fsList_userName: String
    lateinit var fsList_requeststatus: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfirmationBookingFixedSessionBinding.inflate(inflater, container, false)

        viewModel.progressBarStatus.observe(requireActivity()) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.confirmationBookingResponse.observe(requireActivity()) {
            if (it.status == 1) {
                fsBookingListData.clear()
                // listData!!.addAll(intent.getFavLocdata)
                if (it.data.isEmpty()) {
                    binding.idNouser.visibility = View.VISIBLE
                    binding.rvConfirmBookingFixedSession.visibility = View.GONE
                } else {
                    binding.idNouser.visibility = View.GONE
                    binding.rvConfirmBookingFixedSession.visibility = View.VISIBLE
                    fsBookingListData.addAll(it.data)
                    confirmationBookingFSAdapter = ConfirmationBookingFSAdapter(
                        requireContext(),
                        fsBookingListData,
                        this@ConfirmationBookingFixedSessionFragment
                    )
                    binding.rvConfirmBookingFixedSession.apply {
                        adapter = confirmationBookingFSAdapter
                        layoutManager = LinearLayoutManager(requireActivity())
                        // intent.getFavLocdata?.let { notificationList?.addAll(it) }
                        //    favouriteLocationsAdapter?.notifyDataSetChanged()
                    }
                }
            } else {
                Log.d("Response", it.toString())
                toast(requireContext(), it.message!!)
                binding.idNouser.visibility = View.VISIBLE
                binding.rvConfirmBookingFixedSession.visibility = View.GONE
            }
        }
        if (CommonUtils.isInternetAvailable(requireContext())) {
            viewModel.confirmation_booking_listApi("Bearer " + userPref.getToken(), "3")
        } else {
            toast(requireContext(), "Please check internet connection.")
        }




        return binding.root
    }


    override fun onListItemClicked(confirmationBookingData: ConfirmationBookingData) {

        callList_id = confirmationBookingData.id.toString()
        callList_userid = confirmationBookingData.userId.toString()
        callList_userName = confirmationBookingData.userName.toString()
        callList_requeststatus = confirmationBookingData.requestStatus.toString()

        if (confirmationBookingData.type == 1) {
            val intent = Intent(context, ChatwithUsActivity::class.java)
            intent.putExtra("list_idSub", callList_id)
            intent.putExtra("Userid", callList_userid)
            intent.putExtra("id", callList_id)
            intent.putExtra("caller_id", confirmationBookingData.uniqueId)
            intent.putExtra("list_userName", callList_userName)
            intent.putExtra("requeststatus", callList_requeststatus)
            startActivity(intent)
        }

        if (confirmationBookingData.type == 2) {
            val intent2 = Intent(context, DashboardAudioCallActivity::class.java)
                intent2.putExtra("list_idSub", callList_id)
                intent2.putExtra("user_id", callList_userid)
                intent2.putExtra("id", callList_id)
                intent2.putExtra("list_userName", callList_userName)
                intent2.putExtra("requeststatus", callList_requeststatus)
                startActivity(intent2)
        }

        if (confirmationBookingData.type == 3) {
            val intent3 = Intent(context, DashboardVideoCallActivity::class.java)
                intent3.putExtra("list_idSub", callList_id)
                intent3.putExtra("userid", callList_userid)
                intent3.putExtra("id", callList_id)
                intent3.putExtra("list_userName", callList_userName)
                intent3.putExtra("requeststatus", callList_requeststatus)
                startActivity(intent3)
        }
    }
}