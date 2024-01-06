package com.callastro.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callastro.data.MainRepository
import com.callastro.model.ChatRequestCancelResponse
import com.callastro.model.FixedsessionResponseList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FixedSessionViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {
    val progressBarStatus = MutableLiveData<Boolean>()
    var fixedsessionResponseList = MutableLiveData<FixedsessionResponseList>()
    var chatRequestCancelResponse = MutableLiveData<ChatRequestCancelResponse>()

    fun fixedsessionListAPI(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.fixed_session_requests(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                fixedsessionResponseList.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }


    fun fixed_session_request_accecpt(
        token: String,id: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.fixed_session_request_accecpt(token, id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                chatRequestCancelResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }



}