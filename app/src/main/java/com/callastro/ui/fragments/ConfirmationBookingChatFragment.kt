package com.callastro.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.callastro.adapters.ConfirmationBookingChatAdapter
import com.callastro.baseClass.BaseFragment
import com.callastro.databinding.FragmentConfirmationBookingChatBinding
import com.callastro.model.ConfirmationBookingData
import com.callastro.ui.activities.ChatwithUsActivity
import com.callastro.ui.activities.ConfirmationBookingDetailsChatActivity
import com.callastro.viewModels.ConfirmationBookingsViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmationBookingChatFragment : BaseFragment(), ConfirmationBookingChatAdapter.OnClick {

    lateinit var binding: FragmentConfirmationBookingChatBinding
    private val viewModel: ConfirmationBookingsViewModel by viewModels()
    var chatBookingListData : ArrayList<ConfirmationBookingData> = ArrayList()
    lateinit var confirmationBookingChatAdapter : ConfirmationBookingChatAdapter
    lateinit var chatList_id: String
    lateinit var chatList_userName: String
    lateinit var chatList_requeststatus: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfirmationBookingChatBinding.inflate(inflater, container, false)

       /* // Inflate the layout for this fragment
        binding = FragmentConfirmationBookingChatBinding.inflate(inflater, container, false)
        confirmationBookingChatAdapter = ConfirmationBookingChatAdapter(requireContext(),)
        binding.rvConfirmBookingChat.adapter =confirmationBookingChatAdapter
*/
        viewModel.progressBarStatus.observe(requireActivity()) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.confirmationBookingResponse.observe(requireActivity()) {
            if (it.status == 1) {
                chatBookingListData.clear()
                // listData!!.addAll(it.getFavLocdata)

                if (it.data.isEmpty() ) {
                    binding.idNouser.visibility = View.VISIBLE
                    binding.rvConfirmBookingChat.visibility = View.GONE
                }
                else {
                    binding.idNouser.visibility = View.GONE
                    binding.rvConfirmBookingChat.visibility = View.VISIBLE
                    chatBookingListData.addAll(it.data)
                    confirmationBookingChatAdapter = ConfirmationBookingChatAdapter(requireContext(),chatBookingListData,this@ConfirmationBookingChatFragment)
                    binding.rvConfirmBookingChat.apply {
                        adapter = confirmationBookingChatAdapter
                        layoutManager = LinearLayoutManager(requireActivity())
                        // it.getFavLocdata?.let { notificationList?.addAll(it) }
                        //    favouriteLocationsAdapter?.notifyDataSetChanged()
                    }
                }
            } else   {
                Log.d("Response", it.toString())
                toast(requireContext(),it.message!!)
                binding.idNouser.visibility = View.VISIBLE
                binding.rvConfirmBookingChat.visibility = View.GONE
            }
        }
        if (CommonUtils.isInternetAvailable(requireContext())) {
            viewModel.confirmation_booking_listApi("Bearer " +  userPref.getToken(), "1")
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(requireContext(),"Please check internet connection.")
        }

        return binding.root
    }


    override fun onChatItemClicked(confirmationBookingData: ConfirmationBookingData) {
        chatList_id = confirmationBookingData.userId.toString()
        chatList_userName = confirmationBookingData.userName.toString()
        startActivity(Intent(requireContext(), ChatwithUsActivity::class.java).also {
            it.putExtra("list_id", chatList_id)
            it.putExtra("list_userName", chatList_userName)
            Log.d("TAG++", "onProceedClicked: " + confirmationBookingData.userName.toString())
        })
    }

    override fun onListItemClicked(confirmationBookingData: ConfirmationBookingData) {
        chatList_id = confirmationBookingData.userId.toString()
        chatList_userName = confirmationBookingData.userName.toString()
        chatList_requeststatus = confirmationBookingData.requestStatus.toString()
        startActivity(Intent(requireContext(), ConfirmationBookingDetailsChatActivity::class.java).also {
            it.putExtra("list_id", chatList_id)
            it.putExtra("list_userName", chatList_userName)
            it.putExtra("requeststatus", chatList_requeststatus)
            Log.d("TAG++", "onProceedClicked: " + confirmationBookingData.id.toString())
        })
    }


}