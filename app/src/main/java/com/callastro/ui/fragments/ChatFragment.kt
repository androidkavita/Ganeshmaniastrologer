package com.callastro.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.callastro.R
import com.callastro.adapters.ChatFragmentAdapter
import com.callastro.baseClass.BaseFragment
import com.callastro.databinding.FragmentChatBinding
import com.callastro.model.ChatUserListData
import com.callastro.ui.activities.ChatwithUsActivity
import com.callastro.viewModels.ChatFragmentViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : BaseFragment(), ChatFragmentAdapter.OnClick {

    lateinit var binding :FragmentChatBinding
    private val viewModel: ChatFragmentViewModel by viewModels()
    var chatUserListData : ArrayList<ChatUserListData> = ArrayList()
    lateinit var chatFragmentAdapter : ChatFragmentAdapter

    // lateinit var chatUserId: String
    lateinit var chatUserName: String
    lateinit var chatList_id: String
    lateinit var chatList_userid: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)

        viewModel.progressBarStatus.observe(requireActivity()) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.chatUserListResponse.observe(requireActivity()) {
            if (it.status == 1) {
                chatUserListData.clear()
                // listData!!.addAll(it.getFavLocdata)

                if (it.data.isEmpty() ) {
                    binding.idNouser.visibility = View.VISIBLE
                    binding.rvDashboardChat.visibility = View.GONE

                }
                else {
                    binding.idNouser.visibility = View.GONE
                    binding.rvDashboardChat.visibility = View.VISIBLE
                    chatUserListData.addAll(it.data)
                    chatFragmentAdapter = ChatFragmentAdapter(requireContext(),chatUserListData,this@ChatFragment)
                    binding.rvDashboardChat.apply {
                        adapter = chatFragmentAdapter
                        layoutManager = LinearLayoutManager(requireActivity())
                        // it.getFavLocdata?.let { notificationList?.addAll(it) }
                        //    favouriteLocationsAdapter?.notifyDataSetChanged()
                    }
                }
            } else   {
                binding.idNouser.visibility = View.VISIBLE
                binding.rvDashboardChat.visibility = View.GONE
            }
        }
        if (CommonUtils.isInternetAvailable(requireContext())) {
            viewModel.chat_user_listApi("Bearer " +  userPref.getToken())
        } else {
            toast(requireContext(),"Please check internet connection.")
        }

        viewModel.commonResponse.observe(requireActivity()) {
            if (it.status == 1) {

            } else   {

            }
        }
        return binding.root
    }

    override fun onChatItemClicked(chatUserListData: ChatUserListData) {
        // chatUserId = chatUserListData.id.toString()
        chatList_id = chatUserListData.id.toString()
        chatList_userid = chatUserListData.userId.toString()
        chatUserName = chatUserListData.name.toString()
        viewModel.click_user_chat("Bearer " +  userPref.getToken(),chatList_userid)

        startActivity(Intent(requireContext(), ChatwithUsActivity::class.java).also {
            it.putExtra("list_idSub", chatList_id)
            it.putExtra("list_id", chatList_userid)
            it.putExtra("list_userName", chatUserName)
            it.putExtra("caller_id", chatUserListData.unique_id)
            it.putExtra("homepage","homepage")

        })

    }
}