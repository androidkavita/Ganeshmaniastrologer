package com.callastro.ui.activities

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
import com.callastro.databinding.ActivityCallCancelRequestBinding
import com.callastro.model.ChatCallCancelReasonData
import com.callastro.viewModels.ChatRequestDetailsViewModel
import com.maxtra.astrorahiastrologer.viewmodel.CallRequestDetailsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header.view.*


@AndroidEntryPoint
class CallCancelRequestActivity : BaseActivity() {
    lateinit var binding: ActivityCallCancelRequestBinding
    private val viewModel: CallRequestDetailsViewModel by viewModels()
    private val viewModelChat: ChatRequestDetailsViewModel by viewModels()
    private var listData: ArrayList<ChatCallCancelReasonData> = ArrayList()
    private var chatCancelReasonAdapter: ChatCancelReasonAdapter? = null
    lateinit var getCallListId: String
    lateinit var getlist_userName: String


    var str: String? = ""
    private var listReasonType_id: ArrayList<String> = ArrayList()
    var reasontypevalue_id: String? = ""

    lateinit var bottomCallRequestCancelled: BottomSheetDialog
    var userName = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_call_cancel_request)

        binding.header.backArrow.setOnClickListener { finish() }
        binding.header.tvHeadName.text = "Cancel Request"
        getCallListId = intent.getStringExtra("list_id").toString()
        getlist_userName = intent.getStringExtra("list_userName").toString()

        binding.btnCancel.setOnClickListener(View.OnClickListener { finish() })

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModelChat.chatcallCancelReasonResponse.observe(this) {
            if (it.status == 1) {
                toast(this, it.message!!)
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
                toast(this, it.message!!)
            }
        }
        if (CommonUtils.isInternetAvailable(this)) {
            viewModelChat.chatcallReasonCancelListApi("Bearer " + userPref.getToken())
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }

        viewModel.callCancelResponse.observe(this) {
            if (it.status == 1) {
                toast(this, it.message!!)
                userName = getlist_userName
                callCancelledDialog()

                //   finish()
            } else {
                Log.d("Response", it.toString())
                toast(this, it.message!!)
            }
        }
        binding.btnSubmit.setOnClickListener(View.OnClickListener {

            for (i in 0 until binding.rvReasons.childCount) {
                val cbReason =
                    binding.rvReasons.getChildAt(i).findViewById(R.id.cb_reason) as CheckBox
                if (cbReason.isChecked) {
                    val id = listData[i].id
                    listReasonType_id.add(id.toString())
                    reasontypevalue_id = listReasonType_id.toString()
                    str = android.text.TextUtils.join(",", listReasonType_id)
                    //   datetypevalue_id = android.text.TextUtils.join(",", listDateType_id);
                }
            }

            if (str == "" && binding.etReason.text.toString() == "") {
                Toast.makeText(this, "Please enter the reason.", Toast.LENGTH_SHORT).show()
            } else {

                if (CommonUtils.isInternetAvailable(this)) {
                    viewModel.call_request_cancel_api(
                        "Bearer " + userPref.getToken(),
                        getCallListId,
                        str.toString(),
                        binding.etReason.text.toString(),"2"
                    )
                } else {
                    toast(this,"Please check internet connection.")
                }

                Log.d(
                    "CheckBoxInfo",
                    getCallListId + "str___" + str + binding.etReason.text.toString()
                )

            }

        })


    }


    private fun callCancelledDialog() {
        val dialogBinding = LayoutInflater
            .from(this).inflate(R.layout.bottomsheet_request_cancelled, null)
        bottomCallRequestCancelled = BottomSheetDialog(this)
        bottomCallRequestCancelled.setContentView(dialogBinding)

        bottomCallRequestCancelled.setOnShowListener { dia ->
            val bottomSheetDialog = dia as BottomSheetDialog
            val bottomSheetInternal: FrameLayout =
                bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
            bottomSheetInternal.setBackgroundResource(R.drawable.ic_launcher_background)
        }
        bottomCallRequestCancelled.setCancelable(true)

        val tvName: TextView = bottomCallRequestCancelled.findViewById(R.id.tvTextName)!!
        val ivClose: ImageView = bottomCallRequestCancelled.findViewById(R.id.ivCross)!!
        val btnOk: Button = bottomCallRequestCancelled.findViewById(R.id.btnOk)!!


        // tvCRN.setText("Your booking with "+  crnNumber +" has been cancelled successfully.")
        tvName.setText("Call Request of " + userName + " has been Successfully cancelled.")

        // bottomCallRequestCancelled.dismiss()
        // this.finish()
        ivClose.setOnClickListener {
            bottomCallRequestCancelled.dismiss()
        }
        btnOk.setOnClickListener {
            bottomCallRequestCancelled.dismiss()

            var intent = Intent(this@CallCancelRequestActivity, MainActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        bottomCallRequestCancelled.show()

    }
}