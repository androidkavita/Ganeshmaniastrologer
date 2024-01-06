package com.callastro.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callastro.data.MainRepository
import com.callastro.model.CommonResponse
import com.callastro.model.ConfirmationBookingResponse
import com.callastro.model.ConfirmationReportHistoryResponse
import com.callastro.model.FixSessionDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
    class ConfirmationBookingsViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

        val progressBarStatus = MutableLiveData<Boolean>()
        val confirmationBookingResponse = MutableLiveData<ConfirmationBookingResponse>()
        val confirmationReportHistoryResponse = MutableLiveData<ConfirmationReportHistoryResponse>()
        val fixsessionDetailResponse = MutableLiveData<FixSessionDetail>()
        val commonResponse = MutableLiveData<CommonResponse>()



    fun confirmation_booking_listApi(
            token: String,
            type: String
        ) {
            progressBarStatus.value = true
            viewModelScope.launch {

                val response =
                    mainRepository.confirmation_booking_listApi(token, type)
                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    confirmationBookingResponse.postValue(response.body())
                } else {
                    progressBarStatus.value = false
                    Log.d("TAG", response.body().toString())
                }
            }

        }

    fun confirmation_booking_report_detail(
        token: String,
        report_intakes_id: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.confirmation_booking_report_detail(token, report_intakes_id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                confirmationReportHistoryResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }


    fun fix_session_detail(
        token: String,
        user_detail_id:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.fix_session_detail(token, user_detail_id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                fixsessionDetailResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

    fun report_doc_upload(
        token:String,
        user_id: String,
        report_intake_id: String,
        text: String,
        file: MultipartBody.Part?
    ) {

        progressBarStatus.value = true
        viewModelScope.launch {
            val user_id: RequestBody = user_id.toRequestBody("text/plain".toMediaTypeOrNull())
            val report_intake_id: RequestBody = report_intake_id.toRequestBody("text/plain".toMediaTypeOrNull())
            val text: RequestBody = text.toRequestBody("text/plain".toMediaTypeOrNull())

            try {
                val response =
                    mainRepository.report_doc_upload(token, user_id,report_intake_id,text,file!!)
                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    commonResponse.postValue(response.body())
                } else {
                    progressBarStatus.value = false
                    Log.d("TAG", response.body().toString())
                }
            }catch (e:Exception){
                progressBarStatus.value = false
                e.printStackTrace()
            }
        }

    }







}