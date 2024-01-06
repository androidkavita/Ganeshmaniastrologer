package com.callastro.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.callastro.adapters.ConfirmationBookingCallAdapter
import com.callastro.baseClass.BaseFragment
import com.callastro.databinding.FragmentConfirmationBookingCallBinding
import com.callastro.model.ConfirmationBookingData
import com.callastro.ui.activities.DashboardAudioCallActivity
import com.callastro.viewModels.ConfirmationBookingsViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmationBookingCallFragment : BaseFragment(),ConfirmationBookingCallAdapter.getUserid {



    lateinit var binding: FragmentConfirmationBookingCallBinding
    private val viewModel: ConfirmationBookingsViewModel by viewModels()

    var callBookingListData : ArrayList<ConfirmationBookingData> = ArrayList()
    lateinit var confirmationBookingCallAdapter : ConfirmationBookingCallAdapter


    lateinit var callList_id: String
    lateinit var callList_userName: String
    lateinit var callList_requeststatus: String
    lateinit var callList_number: String
    lateinit var otherUser: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentConfirmationBookingCallBinding.inflate(inflater, container, false)

        viewModel.progressBarStatus.observe(requireActivity()) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.confirmationBookingResponse.observe(requireActivity()) {
            if (it.status == 1) {
                callBookingListData.clear()
                // listData!!.addAll(it.getFavLocdata)

                if (it.data.isEmpty() ) {
                    binding.idNouser.visibility = View.VISIBLE
                    binding.rvConfirmBookingCall.visibility = View.GONE

                }
                else {
                    binding.idNouser.visibility = View.GONE
                    binding.rvConfirmBookingCall.visibility = View.VISIBLE
                    callBookingListData.addAll(it.data)
                    confirmationBookingCallAdapter = ConfirmationBookingCallAdapter(requireContext(),callBookingListData,this@ConfirmationBookingCallFragment)
                    binding.rvConfirmBookingCall.apply {
                        adapter = confirmationBookingCallAdapter
                        layoutManager = LinearLayoutManager(requireActivity())
                        // it.getFavLocdata?.let { notificationList?.addAll(it) }
                        //    favouriteLocationsAdapter?.notifyDataSetChanged()
                    }
                }
            } else   {
                Log.d("Response", it.toString())
                toast(requireContext(),it.message!!)
                binding.idNouser.visibility = View.VISIBLE
                binding.rvConfirmBookingCall.visibility = View.GONE
            }
        }
        if (CommonUtils.isInternetAvailable(requireContext())) {
            viewModel.confirmation_booking_listApi("Bearer " +  userPref.getToken(), "2")
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(requireContext(),"Please check internet connection.")
        }


        // Inflate the layout for this fragment
        return binding.root
    }

    override fun Userid(id: String,mainid:String,username:String) {
        startActivity(Intent(requireContext(), DashboardAudioCallActivity::class.java).also {
            it.putExtra("user_id", id)
            it.putExtra("list_userName", username)
        })
    }


//    override fun onCallItemClicked(confirmationBookingData: ConfirmationBookingData) {
//
//       callList_id = confirmationBookingData.id.toString()
//        callList_userName = confirmationBookingData.userName.toString()
//        callList_number = confirmationBookingData.mobileNo.toString()
//        otherUser = confirmationBookingData.mobileNo.toString()
////        val intent = Intent(Intent.ACTION_DIAL)
////        intent.data = Uri.parse("tel:$callList_number")
////        startActivity(intent)
//
//        startActivity(Intent(requireContext(), DashboardAudioCallActivity::class.java).also {
//            it.putExtra("user_id", callList_id)
//            it.putExtra("list_userName", callList_userName)
//        })
//
//    }

//    override fun onListItemClicked(confirmationBookingData: ConfirmationBookingData) {
//        callList_id = confirmationBookingData.id.toString()
//        callList_userName = confirmationBookingData.userName.toString()
//        callList_requeststatus = confirmationBookingData.requestStatus.toString()
//
//        startActivity(Intent(requireContext(), ConfirmationBookingDetailsCallActivity::class.java).also {
//            it.putExtra("user_id", callList_id)
//            it.putExtra("list_userName", callList_userName)
//            it.putExtra("requeststatus", callList_requeststatus)
//
//            Log.d("TAG++", "onProceedClicked: " + confirmationBookingData.id.toString())
//        })
//    }


}