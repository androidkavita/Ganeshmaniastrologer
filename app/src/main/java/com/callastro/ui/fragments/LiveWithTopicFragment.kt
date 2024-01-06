package com.callastro.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.callastro.R
import com.callastro.baseClass.BaseFragment
import com.callastro.databinding.FragmentLiveWithTopicBinding
import com.callastro.ui.activities.LiveActivity
import com.callastro.viewModels.VideoCallViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LiveWithTopicFragment : BaseFragment() {
    lateinit var binding: FragmentLiveWithTopicBinding
    private val viewModel: VideoCallViewModel by viewModels()
    lateinit var agoratoken:String
    lateinit var channelName:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLiveWithTopicBinding.inflate(inflater, container, false)

        binding.btnGoLive.setOnClickListener {
            if (CommonUtils.isInternetAvailable(requireContext())) {
                if (binding.etTopic.text.isNullOrEmpty()) {
                    toast(requireContext(),"Please enter topic first.")
                } else {
                    viewModel.live_agora_topic("Bearer "+userPref.getToken(),binding.etTopic.text.toString())
                }
            } else {
                toast(requireContext(),"Please check internet connection.")
            }
        }



        viewModel.goLiveResponse.observe(viewLifecycleOwner) {
            if (it.status == 1) {
//                agoratoken = it.data?.agoraToken.toString()
//                channelName = it.data?.channelName.toString()

//                userPref.setAgoraToken(it.data?.agoraToken.toString())
//                userPref.setagoraChannelName(it.data?.channelName.toString())

//                val bundle = Bundle()
////                bundle.putString("agoratoken",agoratoken)
////                bundle.putString("channelName",channelName)
//                val fragobj = LiveFragment()
//                fragobj.setArguments(bundle)
//                fragmentManager?.beginTransaction()?.replace(
//                    R.id.flContent,
//                    fragobj, "Payment"
//                )?.addToBackStack(null)?.commit()
//
//
//                fragmentManager?.beginTransaction()?.replace(R.id.flContent, LiveFragment())
//                    ?.commit()

                startActivity(Intent(requireContext(), LiveActivity::class.java).putExtra("topic",binding.etTopic.text.toString()))
            } else {
                it.message?.let { it1 -> toast(requireContext(),it1) }
            }
        }


        return binding.root
    }


}