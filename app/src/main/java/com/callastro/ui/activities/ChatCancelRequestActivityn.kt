package com.callastro.ui.activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.callastro.MainActivity
import com.callastro.R
import com.callastro.adapters.ChatCancelReasonAdapter
import com.callastro.databinding.ActivityChatCancelRequestActivitynBinding
import com.callastro.model.ChatCallCancelReasonData
import com.callastro.viewModels.ChatRequestDetailsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header.view.*


@AndroidEntryPoint
class ChatCancelRequestActivityn : BaseActivity() {

    lateinit var binding: ActivityChatCancelRequestActivitynBinding
    private val viewModel : ChatRequestDetailsViewModel by viewModels()
    private var listData: ArrayList<ChatCallCancelReasonData> = ArrayList()
    private var chatCancelReasonAdapter: ChatCancelReasonAdapter? = null
    lateinit var getCallListId: String
    lateinit var getlist_userName: String


    //private lateinit var str: String
    private var listReasonType_id:ArrayList<String> = ArrayList()
    var reasontypevalue_id: String? = ""
    var str: String? = ""

    lateinit var bottomchatRequestCancelled: BottomSheetDialog
    var userName = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_cancel_request_activityn)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_cancel_request_activityn)
        binding.header.backArrow.setOnClickListener { finish() }
        binding.header.tvHeadName.text = "Cancel Request"


        getCallListId = intent.getStringExtra("list_id").toString()
        getlist_userName = intent.getStringExtra("list_userName").toString()


        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        binding.btnCancel.setOnClickListener(View.OnClickListener { finish() })


        viewModel.chatcallCancelReasonResponse.observe(this) {
            if (it.status == 1) {
                toast(this,it.message!!)
                listData.clear()
                listData.addAll(it.data)
                chatCancelReasonAdapter =
                    ChatCancelReasonAdapter(this, listData)
                binding.rvReasons.apply {
                    adapter = chatCancelReasonAdapter
                    layoutManager = LinearLayoutManager(context)
                }
            } else {
                Log.d("Response", it.toString())
                toast(this,it.message!!)
            }
        }

        if (CommonUtils.isInternetAvailable(this)) {
            viewModel.chatcallReasonCancelListApi("Bearer " +  userPref.getToken())
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }






        viewModel.chatCancelResponse.observe(this) {
            if (it.status == 1) {
                toast(this,it.message!!)
                userName = getlist_userName
                chatCancelledDialog()

                //   finish()
            } else {
                Log.d("Response", it.toString())
                toast(this,it.message!!)
            }
        }


        binding.btnSubmit.setOnClickListener(View.OnClickListener {


            for (i in 0 until  binding.rvReasons.childCount){
                val cbReason = binding.rvReasons.getChildAt(i).findViewById(R.id.cb_reason) as CheckBox
                if (cbReason.isChecked){
                    val id=listData[i].id
                    listReasonType_id.add(id.toString())

                    reasontypevalue_id =  listReasonType_id.toString()
                    str= android.text.TextUtils.join(",", listReasonType_id)
                    Log.d(ContentValues.TAG, "onCreatestr__: "+str)
                    //   datetypevalue_id = android.text.TextUtils.join(",", listDateType_id);
                }
            }

            if (str == "" && binding.etReason.text.toString() == ""){
                Toast.makeText(this, "Please enter the reason.", Toast.LENGTH_SHORT).show()
            }

            else{
                viewModel.chat_request_cancel_api("Bearer " + userPref.getToken(), getCallListId, str.toString(),binding.etReason.text.toString())
            }

        })

    }


    private fun chatCancelledDialog() {
        val dialogBinding = LayoutInflater
            .from(this).inflate(R.layout.bottomsheet_request_cancelled, null)
        bottomchatRequestCancelled = BottomSheetDialog(this)
        bottomchatRequestCancelled.setContentView(dialogBinding)

        bottomchatRequestCancelled.setOnShowListener { dia ->
            val bottomSheetDialog = dia as BottomSheetDialog
            val bottomSheetInternal: FrameLayout =
                bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
            bottomSheetInternal.setBackgroundResource(R.drawable.ic_launcher_background)
        }
        bottomchatRequestCancelled.setCancelable(true)

        val tvName: TextView = bottomchatRequestCancelled.findViewById(R.id.tvTextName)!!
        val ivClose: ImageView = bottomchatRequestCancelled.findViewById(R.id.ivCross)!!
        val btnOk: Button = bottomchatRequestCancelled.findViewById(R.id.btnOk)!!



        // tvCRN.setText("Your booking with "+  crnNumber +" has been cancelled successfully.")
        tvName.setText("The chat Request of "+  userName +" has been Successfully cancelled.")

        // bottomchatRequestCancelled.dismiss()
        // this.finish()
        ivClose.setOnClickListener {
            bottomchatRequestCancelled.dismiss()
        }
        btnOk.setOnClickListener {
            bottomchatRequestCancelled.dismiss()
            var intent = Intent(this@ChatCancelRequestActivityn, MainActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

        }
        bottomchatRequestCancelled.show()
    }
}