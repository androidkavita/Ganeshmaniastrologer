package com.callastro.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callastro.data.MainRepository
import com.callastro.model.AddSuggestRemedyResponse
import com.callastro.model.CommonResponse
import com.callastro.model.ProductListResponse
import com.callastro.model.SuggestRemedyListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
    class SuggestRemedyProductsViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

        val progressBarStatus = MutableLiveData<Boolean>()
        val productListResponse = MutableLiveData<ProductListResponse>()
        val addSuggestRemedyResponse = MutableLiveData<AddSuggestRemedyResponse>()
        val suggestRemedyListResponse = MutableLiveData<SuggestRemedyListResponse>()
        val suggestRemedyMsgResponse = MutableLiveData<CommonResponse>()



    fun product_listApi(
            token: String
        ) {
            progressBarStatus.value = true
            viewModelScope.launch {

                val response =
                    mainRepository.product_listApi(token)
                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    productListResponse.postValue(response.body())
                } else {
                    progressBarStatus.value = false
                    Log.d("TAG", response.body().toString())
                }
            }

        }



    fun add_suggest_remedyApi(
        token:String, product_ids:String, user_id:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.add_suggest_remedyApi(token, product_ids, user_id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                addSuggestRemedyResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }


    fun suggest_remedy_listApi(
        token:String, user_id:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.suggest_remedy_listApi(token, user_id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                suggestRemedyListResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

    fun add_suggest_remedy_messageApi(
        token:String, user_id:String,  message: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.add_suggest_remedy_messageApi(token, user_id, message)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                suggestRemedyMsgResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }


}