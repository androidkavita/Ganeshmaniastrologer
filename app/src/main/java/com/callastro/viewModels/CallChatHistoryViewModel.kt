package com.callastro.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callastro.data.MainRepository
import com.callastro.model.CallHistoryListResponse
import com.callastro.model.ChatHistoryListResponse
import com.callastro.model.CommonResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CallChatHistoryViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {
    val progressBarStatus = MutableLiveData<Boolean>()
    var callHistoryListResponse = MutableLiveData<CallHistoryListResponse>()
    var chatHistoryListResponse = MutableLiveData<ChatHistoryListResponse>()
    val commonResponse = MutableLiveData<CommonResponse>()


    fun callHistoryListApi(
        token: String,
//        dummy: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.callHistoryListApi(token, /*dummy*/)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                callHistoryListResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }
    fun request_money(
        token: String,
        money: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.request_money(token, money)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                commonResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

    fun refund_money(
        token: String,
        order_id: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.refund_money(token, order_id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                commonResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }



    fun chatHistoryListApi(
        token: String, /*dummy: String*/
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.chatHistoryListApi(token, /*dummy*/)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                chatHistoryListResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }







}