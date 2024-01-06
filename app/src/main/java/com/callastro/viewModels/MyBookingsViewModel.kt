package com.callastro.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callastro.data.MainRepository
import com.callastro.model.MyBookingsUpcomingCompletedResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyBookingsViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {
    val progressBarStatus = MutableLiveData<Boolean>()
    var upcomingCompletedBookingsResponse = MutableLiveData<MyBookingsUpcomingCompletedResponse>()


    fun upcomingCompletedBookingsApi(
        token: String, type:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.upcomingCompletedBookingsApi(token, type)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                upcomingCompletedBookingsResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }













}