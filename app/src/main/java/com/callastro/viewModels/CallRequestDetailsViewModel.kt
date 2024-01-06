package com.maxtra.astrorahiastrologer.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callastro.data.MainRepository
import com.callastro.model.CallRequestDetailResponse
import com.callastro.model.ChatRequestCancelResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
    class CallRequestDetailsViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

        val progressBarStatus = MutableLiveData<Boolean>()
        val callDetailResponse = MutableLiveData<CallRequestDetailResponse>()
    val callAcceptResponse = MutableLiveData<ChatRequestCancelResponse>()
    val callCancelResponse = MutableLiveData<ChatRequestCancelResponse>()


    fun call_request_detail_api(
            token: String,
            id: String
        ) {
            progressBarStatus.value = true
            viewModelScope.launch {
                val response =
                    mainRepository.call_request_detail_api(token, id)
                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    callDetailResponse.postValue(response.body())
                } else {
                    progressBarStatus.value = false
                    Log.d("TAG", response.body().toString())
                }
            }
        }




    fun call_request_accecpt_api(
        token: String,id: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.call_request_accecpt_api(token, id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                callAcceptResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }



    fun call_request_cancel_api(
        token: String,id: String,
        reason: String,comment: String,action_by: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.call_request_cancel_api(token, id, reason, comment,action_by)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                callCancelResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }





}