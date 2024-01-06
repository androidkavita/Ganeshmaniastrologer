package com.callastro.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callastro.data.MainRepository
import com.callastro.model.CallUserListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
    class CallFragmentViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

        val progressBarStatus = MutableLiveData<Boolean>()
        val callUserListResponse = MutableLiveData<CallUserListResponse>()


    fun call_user_listApi(
            token: String
        ) {
            progressBarStatus.value = true
            viewModelScope.launch {

                val response =
                    mainRepository.call_user_listApi(token)
                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    callUserListResponse.postValue(response.body())
                } else {
                    progressBarStatus.value = false
                    Log.d("TAG", response.body().toString())
                }
            }

        }









}