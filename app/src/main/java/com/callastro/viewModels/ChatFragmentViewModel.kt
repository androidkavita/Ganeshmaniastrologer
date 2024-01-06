package com.callastro.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callastro.data.MainRepository
import com.callastro.model.ChatUserListResponse
import com.callastro.model.CommonResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
    class ChatFragmentViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

        val progressBarStatus = MutableLiveData<Boolean>()
        val chatUserListResponse = MutableLiveData<ChatUserListResponse>()
        val commonResponse = MutableLiveData<CommonResponse>()


    fun chat_user_listApi(
            token: String
        ) {
            progressBarStatus.value = true
            viewModelScope.launch {

                val response =
                    mainRepository.chat_user_listApi(token)
                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    chatUserListResponse.postValue(response.body())
                } else {
                    progressBarStatus.value = false
                    Log.d("TAG", response.body().toString())
                }
            }

        }

    fun click_user_chat(
        token: String,
        user_id:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.click_user_chat(token,user_id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                commonResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }









}