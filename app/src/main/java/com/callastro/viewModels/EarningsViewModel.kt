package com.callastro.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callastro.data.MainRepository
import com.callastro.model.AstroEarningListResponse
import com.callastro.model.AstroEarningResponse
import com.callastro.model.CommonResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
    class EarningsViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {
        val progressBarStatus = MutableLiveData<Boolean>()
        val astroEarningResponse = MutableLiveData<AstroEarningResponse>()
        val commonResponse = MutableLiveData<CommonResponse>()
        val astroEarningListResponse = MutableLiveData<AstroEarningListResponse>()

    fun astro_earningApi(
            token: String,
            filter: String
        ) {
            progressBarStatus.value = true
            viewModelScope.launch {
                val response =
                    mainRepository.astro_earningApi(token, filter)
                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    astroEarningResponse.postValue(response.body())
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

    fun astro_history_wallets(
        token: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response =
                mainRepository.astro_history_wallets(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                astroEarningListResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
}