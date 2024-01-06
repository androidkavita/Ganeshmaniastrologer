package com.callastro.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callastro.data.MainRepository
import com.callastro.model.SettingDetailsGetResponse
import com.maxtra.astrorahiastrologer.util.NoInternetException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch


import javax.inject.Inject
@HiltViewModel
class SettingsViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    val progressBarStatus = MutableLiveData<Boolean>()
    val getSettingsResponse = MutableLiveData<SettingDetailsGetResponse>()
    val updateSettingsResponse = MutableLiveData<SettingDetailsGetResponse>()

    fun setting_detailsApi(
        token: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.setting_detailsApi(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                getSettingsResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }


    fun setting_updateApi(
        token: String,
        is_chat: String,
        is_audio_call: String,
        is_video_call: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            try {
                val response =
                    mainRepository.setting_updateApi(token, is_chat, is_audio_call, is_video_call )
                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    updateSettingsResponse.postValue(response.body())
                } else {
                    progressBarStatus.value = false
                    Log.d("TAG", response.body().toString())
                }
            }catch (e:Exception){
                e.printStackTrace()
            }catch (e:NoInternetException){
                e.printStackTrace()
            }


        }
    }


}