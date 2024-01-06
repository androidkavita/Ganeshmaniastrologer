package com.callastro.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.callastro.R
import com.callastro.adapters.ChatAdapter2
import com.callastro.adapters.CustomerSupportChat
import com.callastro.adapters.FAQAdapter
import com.callastro.databinding.ActivityCustomerAdminChatBinding
import com.callastro.databinding.ActivityLiveBinding
import com.callastro.model.FAQResponseData
import com.callastro.model.GetCustomerSupportChatData
import com.callastro.viewModels.CustomerViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header.view.*

@AndroidEntryPoint
class CustomerAdminChat : BaseActivity() {
    lateinit var binding: ActivityCustomerAdminChatBinding
    private val viewModel: CustomerViewModel by viewModels()
    var Listdata : ArrayList<GetCustomerSupportChatData> = ArrayList()
    lateinit var adapter : CustomerSupportChat
    val handlerStatusCheck = Handler(Looper.getMainLooper())
    var runnableStatusCheck: Runnable? = null
    lateinit var getUser_name :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_admin_chat)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_customer_admin_chat)

        getUser_name = intent.getStringExtra("list_userName").toString()
        binding.tvHeadName.text = getUser_name


        binding.backArrow.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        moveForward()
        messageList()


    binding.ivChatSend.setOnClickListener {
        if (CommonUtils.isInternetAvailable(this)) {
            viewModel.send_chat_with_us("Bearer "+userPref.getToken().toString(),binding.etChatMsg.text.toString())
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }

    }


        viewModel.customerSupportChat.observe(this) {
            if (it.status == 1) {
//                isscrollable = false
                Listdata.clear()
                Listdata.addAll(it.data)
                adapter = CustomerSupportChat(this, Listdata)
                binding.rvUserChat.adapter = adapter
                binding.rvUserChat.scrollToPosition(Listdata.size - 1);
                binding.rvUserChat.smoothScrollToPosition(binding.rvUserChat.adapter!!.itemCount)
                adapter!!.notifyDataSetChanged()
                Log.e("chat", it.message.toString())
            } else {
                snackbar(it?.message!!)
            }
        }

    viewModel.CommonResponse.observe(this){
        if (it.status == 1){
            binding.etChatMsg.setText("")
        }
    }



    }
    private fun moveForward() {
        handlerStatusCheck.postDelayed(Runnable { //do // something
            messageList()
            handlerStatusCheck.postDelayed(runnableStatusCheck!!, 10 * 500)
        }.also { runnableStatusCheck = it }, 500)
    }

    fun messageList() {
        if (CommonUtils.isInternetAvailable(this)) {
            viewModel.get_chat_with_us("Bearer "+userPref.getToken().toString())
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }

    }


}