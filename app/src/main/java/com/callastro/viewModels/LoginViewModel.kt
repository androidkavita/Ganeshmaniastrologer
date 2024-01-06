package com.callastro.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callastro.data.MainRepository
import com.callastro.model.LoginResponse
import com.callastro.model.LoginverificationResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.http.Field
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    val progressBarStatus = MutableLiveData<Boolean>()
    val loginResponse = MutableLiveData<LoginResponse>()
    val loginVerificationResponse = MutableLiveData<LoginverificationResponse>()
//    val agoraCreateUserResponse = MutableLiveData<AgoraCreateUserResponse>()

    fun Login(
        country_code: String,mobile: String,device_id: String,device_type: String,device_name: String,device_token: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.login(country_code,mobile,device_id,device_type,device_name,device_token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                loginResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

    fun Recent_otp(
        mobile: String,
        type: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.recent_otp(mobile,type)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                loginResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

    fun LoginVerification(
        id: String,
        otp: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.loginverification(id,otp)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                loginVerificationResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }






//    fun agora_create_userApi(
//        token:String,  to_userId: String, nickname: String
//    ) {
//        try {
//            progressBarStatus.value = true
//            viewModelScope.launch {
//
//                val response =
//                    mainRepository.agora_create_userApi(token, to_userId, nickname)
//                if (response.isSuccessful) {
//                    progressBarStatus.value = false
//                    agoraCreateUserResponse.postValue(response.body())
//                } else {
//                    progressBarStatus.value = false
//                    Log.d("TAG", response.body().toString())
//                }
//            }
//        }catch (e:Exception){
//            e.printStackTrace()
//        }


//    }







}