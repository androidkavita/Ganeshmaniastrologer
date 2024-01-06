package com.callastro.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callastro.data.MainRepository
import com.callastro.model.CalenderList
import com.callastro.model.ManageCalendarScheduleResponse
import com.callastro.model.ScheduleCalendarDeleteResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalenderViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {
    val progressBarStatus = MutableLiveData<Boolean>()
    val CalenderListResponse = MutableLiveData<CalenderList>()
    val manageCalendarResponse = MutableLiveData<ManageCalendarScheduleResponse>()
    val manageDeleteResponse = MutableLiveData<ScheduleCalendarDeleteResponse>()

    fun CalenderList(
        token: String,
        date: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.GetCalender(token,date)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                CalenderListResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }


    fun manage_calendar_scheduleApi(
        token: String,
        date: String,
        from_time: String,
        to_time: String,

    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.manage_calendar_scheduleApi(token,date,from_time,to_time)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                manageCalendarResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }


    fun calendar_schedule_deleteApi(
        token: String,
        id: String,

        ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.calendar_schedule_deleteApi(token,id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                manageDeleteResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }





}