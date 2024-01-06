package com.callastro.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.callastro.R
import com.callastro.adapters.NotificationAdapter
import com.callastro.baseClass.BaseFragment
import com.callastro.databinding.FragmentCallBinding
import com.callastro.databinding.FragmentNotificationBinding
import com.callastro.model.NotificationData
import com.callastro.viewModels.CustomerViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : BaseFragment() {
    lateinit var binding: FragmentNotificationBinding
    lateinit var adapter :NotificationAdapter
    private val viewModel: CustomerViewModel by viewModels()
    var Listdata : ArrayList<NotificationData> = ArrayList()
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
        binding = FragmentNotificationBinding.inflate(inflater,container,false)


        if (CommonUtils.isInternetAvailable(requireContext())) {
            viewModel.Notification(
                "Bearer "+userPref.getToken().toString()
            )
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(requireContext(),"Please check internet connection.")
        }

        viewModel.NotificationResponse.observe(viewLifecycleOwner) {
            if (it?.status == 1) {
                Listdata.clear()
                Listdata.addAll(it.data)
                adapter = NotificationAdapter(requireContext(), Listdata)
                binding.rvNotification.adapter =adapter
            } else {
//                snackbar(it?.message!!)
            }
        }

//        adapter = NotificationAdapter(requireContext())
//        binding.rvNotification.adapter = adapter



        return binding.root
    }


}