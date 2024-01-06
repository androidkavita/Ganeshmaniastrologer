package com.maxtra.astrorahiastrologer.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callastro.data.MainRepository
import com.callastro.model.CancellationByUserResponse
import com.callastro.model.Chat_Call_Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatCallViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    val progressBarStatus = MutableLiveData<Boolean>()
    val ChatCallResponse = MutableLiveData<Chat_Call_Response>()
    val cancellationListResponse = MutableLiveData<CancellationByUserResponse>()

    fun ChatList(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.Chathome(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                ChatCallResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

    fun CallList(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.Callhome(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                ChatCallResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }


    fun cancellationByUserApi(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.cancellationByUserApi(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                cancellationListResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }


}