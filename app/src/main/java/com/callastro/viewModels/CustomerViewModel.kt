package com.callastro.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callastro.data.MainRepository
import com.callastro.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {
    val progressBarStatus = MutableLiveData<Boolean>()
    val emailusResponse = MutableLiveData<EmailUs_Response>()
    val CommonResponse = MutableLiveData<CommonResponse>()
    val customerSupportChat = MutableLiveData<GetCustomerSupportChat>()
    val AboutUsResponse = MutableLiveData<AboutUsResponse>()
    val notificationListResponse = MutableLiveData<NotificationListResponse>()
    val FAQResponse = MutableLiveData<FAQResponse>()
    val NotificationResponse = MutableLiveData<NotificationResponse>()

    fun EmailUs(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.EmailUs(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                emailusResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }
    fun Notification(
        token: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.notification(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                NotificationResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }
    fun Callback(
        token: String,
        mobile :String,
        discription:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.callback_apply(token,mobile,discription)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                CommonResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

    fun AboutUs(
        token: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.aboutus(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                AboutUsResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }


    fun notificationApi(
        token: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.notificationApi(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                notificationListResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

    fun FAQ(
        token: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.faq(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                FAQResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

    fun send_chat_with_us(
        token: String,
        message: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.send_chat_with_us(token,message)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                CommonResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

    fun get_chat_with_us(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.get_chat_with_us(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                customerSupportChat.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }




}