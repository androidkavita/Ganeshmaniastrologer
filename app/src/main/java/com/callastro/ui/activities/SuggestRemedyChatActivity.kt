package com.callastro.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.callastro.R
import com.callastro.adapters.SuggestRemedyChosenProductsAdapter
import com.callastro.adapters.SuggestRemedyTextAdapter
import com.callastro.databinding.ActivitySuggestRemedyChatBinding
import com.callastro.model.SuggestRemedyListProducts
import com.callastro.model.SuggestRemedyListSuggestedMsgt
import com.callastro.viewModels.SuggestRemedyProductsViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SuggestRemedyChatActivity : BaseActivity() {

    lateinit var binding : ActivitySuggestRemedyChatBinding
    private val viewModel : SuggestRemedyProductsViewModel by viewModels()
    lateinit var getChatListId: String
    lateinit var getlist_userName: String
    lateinit var getlist_requeststatus: String
    lateinit var userid: String
    var selectedListData : ArrayList<SuggestRemedyListProducts> = ArrayList()
    var selectedListTextData : ArrayList<SuggestRemedyListSuggestedMsgt> = ArrayList()
    lateinit var chosenProductsAdapter : SuggestRemedyChosenProductsAdapter
    lateinit var chosenTextAdapter : SuggestRemedyTextAdapter
    var isclick:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggest_remedy_chat)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_suggest_remedy_chat)
        binding.ivBack.setOnClickListener { finish() }
        getChatListId = intent.getStringExtra("list_id").toString()
        getlist_userName = intent.getStringExtra("list_userName").toString()
        userid = intent.getStringExtra("userid").toString()
        getlist_requeststatus = intent.getStringExtra("requeststatus").toString()
        binding.tvName.text = getlist_userName
        binding.tvProducts.setOnClickListener {
            startActivity(Intent(this, ProductActivity::class.java).also {
                it.putExtra("userId", userid)
            })
        }
        if (CommonUtils.isInternetAvailable(this)) {
            viewModel.suggest_remedy_listApi("Bearer " + userPref.getToken(),userid )
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }


        viewModel.suggestRemedyListResponse.observe(this) {
            if (it?.status == 1) {
                selectedListData.clear()
                selectedListData.addAll(it.data!!.products)
                chosenProductsAdapter = SuggestRemedyChosenProductsAdapter(this, selectedListData)
                binding.rvChosenProduct.adapter =chosenProductsAdapter
                selectedListTextData.clear()
                selectedListTextData.addAll(it.data!!.suggestedMsgt)
                chosenTextAdapter = SuggestRemedyTextAdapter(this, selectedListTextData)
                binding.rvChosenText.adapter =chosenTextAdapter

            } else {
                snackbar(it?.message!!)
            }
        }

        viewModel.suggestRemedyMsgResponse.observe(this) {
                   if (it.status == 1) {
                       isclick = false
                       toast(this,it.message!!)
                       binding.etRemedy.setText("")
                       viewModel.suggest_remedy_listApi("Bearer " + userPref.getToken(),userid )
                   } else {
                       Log.d("Response", it.toString())
                       toast(this,it.message!!)
                   }
               }
         binding.ivSend.setOnClickListener {
             if (CommonUtils.isInternetAvailable(this)) {
                 if(binding.etRemedy.text.toString().isNullOrEmpty()){
                     toast(this@SuggestRemedyChatActivity,"Please enter text.")
                 }else{
                     if (isclick == false) {
                         viewModel.add_suggest_remedy_messageApi(
                             "Bearer " + userPref.getToken(),
                             userid,
                             binding.etRemedy.text.toString()
                         )
                         isclick = true
                     }
                 }
             } else {
                 Log.d("TAG", "onCreate: " + "else part")
                 toast(this,"Please check internet connection.")
             }

         }
    }
    override fun onResume() {
        super.onResume()
        if (CommonUtils.isInternetAvailable(this)) {
            viewModel.suggest_remedy_listApi("Bearer " + userPref.getToken(),userid )
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }

    }
}