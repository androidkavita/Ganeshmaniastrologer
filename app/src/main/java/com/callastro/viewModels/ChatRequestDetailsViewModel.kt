package com.callastro.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callastro.data.MainRepository
import com.callastro.model.ChatCallCancelReasonResponse
import com.callastro.model.ChatRequestCancelResponse
import com.callastro.model.ChatRequestDetailResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
    class ChatRequestDetailsViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

        val progressBarStatus = MutableLiveData<Boolean>()
        val chatDetailResponse = MutableLiveData<ChatRequestDetailResponse>()
    val chatAcceptResponse = MutableLiveData<ChatRequestCancelResponse>()
    val chatcallCancelReasonResponse = MutableLiveData<ChatCallCancelReasonResponse>()
    val chatCancelResponse = MutableLiveData<ChatRequestCancelResponse>()

        fun chat_request_detail_api(
            token: String,
            id: String
        ) {
            progressBarStatus.value = true
            viewModelScope.launch {

                val response =
                    mainRepository.chat_request_detail_api(token, id)
                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    chatDetailResponse.postValue(response.body())
                } else {
                    progressBarStatus.value = false
                    Log.d("TAG", response.body().toString())
                }
            }

        }


    fun chat_request_accecpt_api(
        token: String,id: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.chat_request_accecpt_api(token, id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                chatAcceptResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }


    fun chatcallReasonCancelListApi(
        token: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.chatcallReasonCancelListApi(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                chatcallCancelReasonResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }


    fun chat_request_cancel_api(
        token: String,id: String,
        reason: String,comment: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.chat_request_cancel_api(token, id, reason, comment)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                chatCancelResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }


}