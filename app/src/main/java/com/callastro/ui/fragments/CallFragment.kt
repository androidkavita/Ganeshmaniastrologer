package com.callastro.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.callastro.adapters.CallFragmentAdapter
import com.callastro.baseClass.BaseFragment
import com.callastro.databinding.FragmentCallBinding
import com.callastro.model.CallUserListData
import com.callastro.ui.activities.DashboardAudioCallActivity
import com.callastro.ui.activities.DashboardVideoCallActivity
import com.callastro.viewModels.CallFragmentViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CallFragment : BaseFragment(),CallFragmentAdapter.getId {

    lateinit var binding : FragmentCallBinding
    private val viewModel: CallFragmentViewModel by viewModels()
    var callUserListData : ArrayList<CallUserListData> = ArrayList()
    lateinit var callFragmentAdapter : CallFragmentAdapter



    // lateinit var callUserId: String
    lateinit var callUserName: String
    lateinit var callList_id: String
    lateinit var callList_userid: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCallBinding.inflate(inflater, container, false)

        viewModel.progressBarStatus.observe(requireActivity()) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.callUserListResponse.observe(requireActivity()) {
            if (it.status == 1) {
                callUserListData.clear()
                // listData!!.addAll(it.getFavLocdata)

                if (it.data.isEmpty() ) {
                    binding.idNouser.visibility = View.VISIBLE
                    binding.rvDashboardCall.visibility = View.GONE

                }
                else {
                    binding.idNouser.visibility = View.GONE
                    binding.rvDashboardCall.visibility = View.VISIBLE
                    callUserListData.addAll(it.data)
                    callFragmentAdapter = CallFragmentAdapter(requireContext(),callUserListData,this@CallFragment)
                    binding.rvDashboardCall.apply {
                        adapter = callFragmentAdapter
                        layoutManager = LinearLayoutManager(requireActivity())
                        // it.getFavLocdata?.let { notificationList?.addAll(it) }
                        //    favouriteLocationsAdapter?.notifyDataSetChanged()
                    }
                }
            } else   {
                Log.d("Response", it.toString())
                //  toast(requireContext(),it.message!!)
                binding.idNouser.visibility = View.VISIBLE
                binding.rvDashboardCall.visibility = View.GONE
            }
        }
        if (CommonUtils.isInternetAvailable(requireContext())) {
            viewModel.call_user_listApi("Bearer " +  userPref.getToken())
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(requireContext(),"Please check internet connection.")
        }


        return binding.root
    }

    override fun userId(id:String,username:String,type:String) {
        if (type == "2"){
            startActivity(Intent(requireContext(), DashboardVideoCallActivity::class.java).also {
                it.putExtra("userid", id)
                it.putExtra("list_userName", username)
//                it.putExtra("userid", id)

            })
        }else if (type == "3"){
            startActivity(Intent(requireContext(), DashboardAudioCallActivity::class.java).also {
                it.putExtra("user_id", id)
                it.putExtra("list_userName", username)

            })
        }

    }

//    override fun onCallItemClicked(callUserListData: CallUserListData) {
//
//        Toast.makeText(requireContext(), callUserListData.name, Toast.LENGTH_SHORT).show()
//
//    }
//
//    override fun onVideoItemClicked(callUserListData: CallUserListData) {
//        Toast.makeText(requireContext(), callUserListData.name, Toast.LENGTH_SHORT).show()
//        callList_id = callUserListData.id.toString()
//        callList_userid = callUserListData.userId.toString()
//        callUserName = callUserListData.name.toString()
//        startActivity(Intent(requireContext(), DashboardVideoCallActivity::class.java).also {
//            it.putExtra("list_idSub", callList_id)
//            it.putExtra("list_id", callList_userid)
//            it.putExtra("list_userName", callUserName)
//            Log.d("TAG++", "onProceedClicked_callUserId: " + callList_id)
//        })
//    }
//
//    override fun onAudioItemClicked(callUserListData: CallUserListData) {
//        Toast.makeText(requireContext(), callUserListData.name, Toast.LENGTH_SHORT).show()
//        callList_id = callUserListData.id.toString()
//        callList_userid = callUserListData.userId.toString()
//        callUserName = callUserListData.name.toString()
//        startActivity(Intent(requireContext(), DashboardAudioCallActivity::class.java).also {
//            it.putExtra("list_idSub", callList_id)
//           it.putExtra("list_id", callList_userid)
//            it.putExtra("list_userName", callUserName)
//            Log.d("TAG++", "onProceedClicked_callUserId: " + callList_id)
//        })
//    }

}