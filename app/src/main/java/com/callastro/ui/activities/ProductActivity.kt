package com.callastro.ui.activities

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.callastro.R
import com.callastro.adapters.SuggestRemedyProductsAdapter
import com.callastro.databinding.ActivityProductBinding
import com.callastro.model.ProductListData
import com.callastro.viewModels.SuggestRemedyProductsViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header.view.*

@AndroidEntryPoint
class ProductActivity : BaseActivity(), SuggestRemedyProductsAdapter.OnClick {

    lateinit var binding : ActivityProductBinding
    private val viewModel : SuggestRemedyProductsViewModel by viewModels()
     var productListData : ArrayList<ProductListData> = ArrayList()
    lateinit var productsAdapter : SuggestRemedyProductsAdapter
    private var listSelectedProduct_id: java.util.ArrayList<String> = java.util.ArrayList()
    var producttypevalue_id: String? = ""
    var str: String? = ""
    lateinit var getUserId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product)


        binding.header.backArrow.setOnClickListener { finish() }
        binding.header.tvHeadName.text = "Product"
        getUserId = intent.getStringExtra("userId").toString()

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.productListResponse.observe(this) {
            if (it?.status == 1) {
                productListData.clear()
                productListData.addAll(it.data)
                productsAdapter = SuggestRemedyProductsAdapter(this, productListData,this)
                binding.rvProduct.adapter =productsAdapter

            } else {
                //toast(it.message)
                snackbar(it?.message!!)
            }
        }
        if (CommonUtils.isInternetAvailable(this)) {
            viewModel.product_listApi("Bearer "+userPref.getToken().toString())
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this,"Please check internet connection.")
        }


        viewModel.addSuggestRemedyResponse.observe(this) {
            if (it.status == 1) {
                toast(this,it.message!!)
                // userName = getlist_userName
                // callCancelledDialog()

                finish()
            } else {
                Log.d("Response", it.toString())
                toast(this,it.message!!)
            }
        }

        binding.btnDone.setOnClickListener(View.OnClickListener {

           /* for (i in 0 until  binding.rvProduct.childCount){
                val cbReason = binding.rvProduct.getChildAt(i).findViewById(R.id.cb_tick) as CheckBox
                if (cbReason.isChecked){
                    val id=productListData[i].id
                    listSelectedProduct_id.add(id.toString())
                    producttypevalue_id =  listSelectedProduct_id.toString()
                    str= android.text.TextUtils.join(",", listSelectedProduct_id)
                    //   datetypevalue_id = android.text.TextUtils.join(",", listDateType_id);
                }
            }*/

            if (str == "" ){
                Toast.makeText(this, "Please select the product.", Toast.LENGTH_SHORT).show()
            }
            else{
                viewModel.add_suggest_remedyApi("Bearer " + userPref.getToken(), str.toString(), getUserId)
                Log.d("CheckBoxInfoId","str_ProductId__"+ str )
            }
        })

    }

    override fun onProductItemAddClicked(productListData: ProductListData) {
       toast(this, productListData.name.toString())
        Log.d("CheckBoxInfoName","str_ProductId__"+ productListData.name.toString() )
        val id=productListData.id
        listSelectedProduct_id.add(id.toString())

        producttypevalue_id =  listSelectedProduct_id.toString()
        str= android.text.TextUtils.join(",", listSelectedProduct_id)
        Log.d(TAG, "onProductItemAddClicked: "+str)

    }

    override fun onProductItemRemoveClicked(productListData: ProductListData) {
        toast(this, productListData.name.toString())
        Log.d("CheckBoxInfoName","str_ProductId__"+ productListData.name.toString() )
        val id=productListData.id
        listSelectedProduct_id.remove(id.toString())

        producttypevalue_id =  listSelectedProduct_id.toString()
        str= android.text.TextUtils.join(",", listSelectedProduct_id)
        Log.d(TAG, "onProductItemRemoveClicked: "+str)

    }

}