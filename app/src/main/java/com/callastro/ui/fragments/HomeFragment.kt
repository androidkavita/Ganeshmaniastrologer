package com.callastro.ui.fragments

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Vibrator
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.callastro.R
import com.callastro.adapters.BannerAdapter
import com.callastro.baseClass.BaseFragment
import com.callastro.databinding.ActivityOtpScreenBinding
import com.callastro.databinding.AddmoneyBinding
import com.callastro.databinding.FragmentHomeBinding
import com.callastro.model.BannerResponseData
import com.callastro.ui.activities.*
import com.callastro.util.SoundUtil
import com.callastro.viewModels.VideoCallViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
lateinit var binding: FragmentHomeBinding
    private val viewModel: VideoCallViewModel by viewModels()
    private var vib: Vibrator? = null
    var mMediaPlayer: MediaPlayer? = null
    val handlerStatusCheck = Handler(Looper.getMainLooper())
    var runnableStatusCheck: Runnable? = null
    var Listdata4: ArrayList<BannerResponseData> = ArrayList()
    lateinit var adapter2 : BannerAdapter
    val handler = Handler()
    val interval = 60000
    var callCheck = 0

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

        binding = FragmentHomeBinding.inflate(inflater,container,false)
        handler.post(runnableCode)

        binding.calender.setOnClickListener {
            SoundUtil.stopSound(requireContext())
            var intent = Intent(requireContext(), Calender_Schedule::class.java)
            startActivity(intent)
        }

        binding.chatrequestofcustomer.setOnClickListener {
          SoundUtil.stopSound(requireContext())
            var intent = Intent(requireContext(), ChatRequest::class.java)
            startActivity(intent)
        }

        binding.callrequestofcustomer.setOnClickListener {
          SoundUtil.stopSound(requireContext())
            var intent = Intent(requireContext(), CallRequest::class.java)
            startActivity(intent)
        }

        binding.updateprofile.setOnClickListener {
          SoundUtil.stopSound(requireContext())
            var intent = Intent(requireContext(), EditProfile::class.java)
            startActivity(intent)
        }

        binding.confirmationonbooking.setOnClickListener {
          SoundUtil.stopSound(requireContext())
            var intent = Intent(requireContext(), ConfirmationOnBookings::class.java)
            startActivity(intent)
        }

        binding.cancellationbyuser.setOnClickListener {
          SoundUtil.stopSound(requireContext())
            var intent = Intent(requireContext(), CancellationByUser::class.java)
            startActivity(intent)
        }

        binding.totalearnings.setOnClickListener {
          SoundUtil.stopSound(requireContext())
            var intent = Intent(requireContext(), TotalEarnings::class.java)
            startActivity(intent)
        }

        binding.cvFixedsession.setOnClickListener {
          SoundUtil.stopSound(requireContext())
            var intent = Intent(requireContext(), FixedSession::class.java)
            intent.putExtra("callCheck", callCheck)
            startActivity(intent)
        }

        binding.requestmoney.setOnClickListener {
          SoundUtil.stopSound(requireContext())
            var intent = Intent(requireContext(), EarningsHome::class.java)
            startActivity(intent)
//            REQUESTMONEY()
        }

        if (CommonUtils.isInternetAvailable(requireContext())) {
            viewModel.Banner(
                "Bearer "+userPref.getToken().toString()
            )
        } else {
            Log.d("TAG", "onCreate: " + "else part")
//            toast(requireContext(),"Please check internet connection.")
            snackBar(binding.root,"Please check internet connection.")
        }






        handlerStatusCheck.postDelayed(Runnable { //do something
            handlerStatusCheck.postDelayed(runnableStatusCheck!!, 10000)
            if (CommonUtils.isInternetAvailable(requireContext())) {
                viewModel.astro_home("Bearer "+userPref.getToken())
            } else {
                Log.d("TAG", "onCreate: " + "else part")
//                toast(requireContext(),"Please check internet connection.")
                snackBar(binding.root,"Please check internet connection.")
            }


        }.also { runnableStatusCheck = it }, 0)

        viewModel.progressBarStatus.observe(requireActivity()) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.callchatstatusResponse.observe(viewLifecycleOwner){
            if (it.status == 1){
                if (it.data?.chatReq == 1){
                    callCheck = 0
                    binding.chatrequestofcustomer.setBackgroundResource(R.drawable.dashboardstatusbackground)
                    //playSound()
                }else{
                    callCheck = 1
                    binding.chatrequestofcustomer.setBackgroundResource(R.drawable.homedrawable)
//                    binding.cvFixedsession.setBackgroundResource(R.drawable.homedrawable)
                }
                if (it.data?.callReq == 1){
                    callCheck = 0
                    binding.callrequestofcustomer.setBackgroundResource(R.drawable.dashboardstatusbackground)
              //      playSound()
                }else{
                    callCheck = 1
                    binding.callrequestofcustomer.setBackgroundResource(R.drawable.homedrawable)
//                    binding.cvFixedsession.setBackgroundResource(R.drawable.homedrawable)
                }
                if (it.data?.fix_req == 1){
                    callCheck = 0
                    binding.cvFixedsession.setBackgroundResource(R.drawable.dashboardstatusbackground)
                }else{
                    callCheck = 1
                    binding.cvFixedsession.setBackgroundResource(R.drawable.homedrawable)
//                    binding.callrequestofcustomer.setBackgroundResource(R.drawable.homedrawable)
//                    binding.chatrequestofcustomer.setBackgroundResource(R.drawable.homedrawable)
                }
            }
        }


        viewModel.bannerResponse.observe(viewLifecycleOwner) {
            if (it?.status == 1) {
                Listdata4.clear()
                for (i in 0 until it.data.size){
                    Listdata4.add(it.data[i])
                }
                adapter2 = BannerAdapter(Listdata4)
                binding.imageSlider.setSliderAdapter(adapter2)
            } else {
                toast(requireContext(),it.message.toString())
            }
        }
        viewModel.commonResponse.observe(viewLifecycleOwner){
            if (it.status == 1){
                toast(requireContext(),"Money added successfully")
            }
        }

        return binding.root
    }


    private val runnableCode = object : Runnable {
        override fun run() {
            viewModel.checkOnlineStatusModel("Bearer "+userPref.getToken())
            handler.postDelayed(this, interval.toLong())
        }
    }

    override fun onPause() {
        super.onPause()
        handlerStatusCheck.removeCallbacks(runnableStatusCheck!!)
    }



    private fun REQUESTMONEY() {
        val cDialog = Dialog(requireContext())
        val bindingDialog: AddmoneyBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.addmoney,
            null,
            false
        )
        cDialog.setContentView(bindingDialog.root)
        cDialog.setCancelable(true)
        cDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        cDialog.show()
        bindingDialog.btnCancel.setOnClickListener {
            cDialog.dismiss()
        }

        bindingDialog.btnLogout.setOnClickListener {
            if (CommonUtils.isInternetAvailable(requireContext())) {
                viewModel.request_money("Bearer "+userPref.getToken().toString(),bindingDialog.fullnameet.text.toString())
            } else {
                toast(requireContext(),"Please check internet connection.")
            }


//            amt = bindingDialog.fullnameet.text.toString()
//
//            startActivity(
//                Intent(this@EarningsHome,PaymentInformation::class.java)
//                .putExtra("amount",amt)
//            )
//            startPayment(amt)
            cDialog.dismiss()
        }
    }
    fun playSound() {
//            if (mMediaPlayer == null) {
        mMediaPlayer = MediaPlayer.create(requireContext(), R.raw.rington)
        vib = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vib!!.vibrate(1000)
        mMediaPlayer!!.isLooping = false
        mMediaPlayer!!.start()
//            } else mMediaPlayer!!.start()

    }

    fun stopSound() {
        mMediaPlayer?.apply {
            if (isPlaying) {
                stop() // Stop the MediaPlayer
            }
            release() // Release the MediaPlayer's resources
        }
        mMediaPlayer = null // Set it to null, so you can create it again if needed
    }

    override fun onResume() {
        super.onResume()
        if (CommonUtils.isInternetAvailable(requireContext())) {
            viewModel.astro_home("Bearer "+userPref.getToken())
            viewModel.Banner(
                "Bearer "+userPref.getToken().toString()
            )
        } else {
//            toast(requireContext(),"Please check internet connection.")
            snackBar(binding.root,"Please check internet connection.")
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        // Remove the callback to stop the execution
        handler.removeCallbacks(runnableCode)
    }

}