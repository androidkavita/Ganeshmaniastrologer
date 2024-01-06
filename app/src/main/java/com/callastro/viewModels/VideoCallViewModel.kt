package com.callastro.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callastro.data.MainRepository
import com.callastro.model.AgoraGenerateTokenResponse
import com.callastro.model.BannerResponse
import com.callastro.model.CallChatStatusResponse
import com.callastro.model.CallRingResponse
import com.callastro.model.CallendbyuserResponse
import com.callastro.model.CommonResponse
import com.callastro.model.GiveReviewResponse
import com.callastro.model.GoLiveResponse
import com.callastro.model.LiveCommentsModelClass
import com.callastro.model.call_ring_status_save_Response
import com.maxtra.astrorahiastrologer.util.ApiException
import com.maxtra.astrorahiastrologer.util.NoInternetException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class VideoCallViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel(){

    val error = MutableLiveData<Int>()
    val errorString = MutableLiveData<String>()
    val _progressBarVisibility = MutableLiveData<Boolean>()
    var call_ring_status_save_Response = MutableLiveData<call_ring_status_save_Response>()
    val agoraGenerateTokenResponse= MutableLiveData<AgoraGenerateTokenResponse>()
    val goLiveResponse= MutableLiveData<GoLiveResponse>()
    val callerendResponse= MutableLiveData<CallendbyuserResponse>()
    val progressBarStatus = MutableLiveData<Boolean>()
    val callchatstatusResponse= MutableLiveData<CallChatStatusResponse>()
    var commonResponse = MutableLiveData<CommonResponse>()
    val liveCommentsModelClass = MutableLiveData<LiveCommentsModelClass>()
    val bannerResponse = MutableLiveData<BannerResponse>()
    val callRingResponse= MutableLiveData<CallRingResponse>()
    val givereviewResponse = MutableLiveData<GiveReviewResponse>()

    fun agora_generate_tokenApi(
        token: String,
        astro_id: String,
        call_type: String
    ) {
        // _progressBarVisibility.value = true
        viewModelScope.launch {
            try {
                val response =
                    mainRepository.agora_generate_tokenApi(token,astro_id,call_type)
                if (response.isSuccessful) {
                    _progressBarVisibility.value = false
                    agoraGenerateTokenResponse.postValue(response.body())
                }
                _progressBarVisibility.value = false
            } catch (e: ApiException) {
                _progressBarVisibility.value = false
                errorString.postValue(e.message!!)
            } catch (e: NoInternetException) {
                _progressBarVisibility.value = false
                errorString.postValue(e.message!!)
            } catch (e: SocketTimeoutException) {
                _progressBarVisibility.value = false
                errorString.postValue(e.message!!)
            } catch (e: Exception) {
                _progressBarVisibility.value = false
                e.printStackTrace()
            }
        }
    }

    fun add_comment(
        user_id: String,
        astro_id: String,
        message: String,
        type: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.add_comment(user_id,
                    astro_id,
                    message,
                    type)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                commonResponse.postValue(response.body())
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

    fun get_live_comments(
        token: String,
        channel_name: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.get_live_comments(
                    token,
                    channel_name,)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                liveCommentsModelClass.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

    fun call_end_by_status(
        token: String,
        caller_id: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            try {
                val response =
                    mainRepository.call_end_by_status(token, caller_id)
                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    callerendResponse.postValue(response.body())
                } else {
                    progressBarStatus.value = false
                    Log.d("TAG", response.body().toString())
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }

    }

    fun call_end(token: String,timer: String,from_user: String,to_user: String,caller_id:String,type: String,fixed_session: String
    ) {
        // _progressBarVisibility.value = true
        viewModelScope.launch {
            try {
                val response =
                    mainRepository.call_end(token,timer,from_user,to_user,caller_id,type,fixed_session)
                if (response.isSuccessful) {
                    _progressBarVisibility.value = false
                    commonResponse.postValue(response.body())
                }
                _progressBarVisibility.value = false
            } catch (e: ApiException) {
                _progressBarVisibility.value = false
                errorString.postValue(e.message!!)
            } catch (e: NoInternetException) {
                _progressBarVisibility.value = false
                errorString.postValue(e.message!!)
            } catch (e: SocketTimeoutException) {
                _progressBarVisibility.value = false
                errorString.postValue(e.message!!)
            } catch (e: Exception) {
                _progressBarVisibility.value = false
                e.printStackTrace()
            }
        }
    }

    fun live_end(token: String,timer: String,from_user: String,to_user: String,type: String
    ) {
        // _progressBarVisibility.value = true
        viewModelScope.launch {
            try {
                val response =
                    mainRepository.live_end(token,timer,from_user,to_user,type)
                if (response.isSuccessful) {
                    _progressBarVisibility.value = false
                    commonResponse.postValue(response.body())
                }
                _progressBarVisibility.value = false
            } catch (e: ApiException) {
                _progressBarVisibility.value = false
                errorString.postValue(e.message!!)
            } catch (e: NoInternetException) {
                _progressBarVisibility.value = false
                errorString.postValue(e.message!!)
            } catch (e: SocketTimeoutException) {
                _progressBarVisibility.value = false
                errorString.postValue(e.message!!)
            } catch (e: Exception) {
                _progressBarVisibility.value = false
                e.printStackTrace()
            }
        }
    }

    fun live_agora_generate_token(
        token: String,
        topic: String
    ) {
        // _progressBarVisibility.value = true
        viewModelScope.launch {
            try {
                val response =
                    mainRepository.live_agora_generate_token(token,topic)
                if (response.isSuccessful) {
                    _progressBarVisibility.value = false
                    goLiveResponse.postValue(response.body())
                }
                _progressBarVisibility.value = false
            } catch (e: ApiException) {
                _progressBarVisibility.value = false
                errorString.postValue(e.message!!)
            } catch (e: NoInternetException) {
                _progressBarVisibility.value = false
                errorString.postValue(e.message!!)
            } catch (e: SocketTimeoutException) {
                _progressBarVisibility.value = false
                errorString.postValue(e.message!!)
            } catch (e: Exception) {
                _progressBarVisibility.value = false
                e.printStackTrace()
            }
        }
    }

    fun astro_give_review(
        token: String,
        astro_id: String,
        rating: String,
        review: String,
    ) {
        viewModelScope.launch {
            val response =
                mainRepository.astro_give_review(token,astro_id,rating,review)
            if (response.isSuccessful) {
                givereviewResponse.postValue(response.body())
            } else {
                Log.d("TAG", response.body().toString())
            }
        }
    }

    fun delete_live_astro(
        token: String,
    ) {
        // _progressBarVisibility.value = true
        viewModelScope.launch {
            try {
                val response =
                    mainRepository.delete_live_astro(token)
                if (response.isSuccessful) {
                    _progressBarVisibility.value = false
                    commonResponse.postValue(response.body())
                }
                _progressBarVisibility.value = false
            } catch (e: ApiException) {
                _progressBarVisibility.value = false
                errorString.postValue(e.message!!)
            } catch (e: NoInternetException) {
                _progressBarVisibility.value = false
                errorString.postValue(e.message!!)
            } catch (e: SocketTimeoutException) {
                _progressBarVisibility.value = false
                errorString.postValue(e.message!!)
            } catch (e: Exception) {
                _progressBarVisibility.value = false
                e.printStackTrace()
            }
        }
    }


    fun live_agora_topic(
        token: String,
        type: String,
    ) {
        // _progressBarVisibility.value = true
        viewModelScope.launch {
            try {
                val response =
                    mainRepository.live_agora_topic(token,type)
                if (response.isSuccessful) {
                    _progressBarVisibility.value = false
                    goLiveResponse.postValue(response.body())
                }
                _progressBarVisibility.value = false
            } catch (e: ApiException) {
                _progressBarVisibility.value = false
                errorString.postValue(e.message!!)
            } catch (e: NoInternetException) {
                _progressBarVisibility.value = false
                errorString.postValue(e.message!!)
            } catch (e: SocketTimeoutException) {
                _progressBarVisibility.value = false
                errorString.postValue(e.message!!)
            } catch (e: Exception) {
                _progressBarVisibility.value = false
                e.printStackTrace()
            }
        }
    }

    fun astro_home(
        token: String,
    ) {
        // _progressBarVisibility.value = true
        viewModelScope.launch {
            try {
                val response =
                    mainRepository.astro_home(token)
                if (response.isSuccessful) {
                    _progressBarVisibility.value = false
                    callchatstatusResponse.postValue(response.body())
                }
                _progressBarVisibility.value = false
            } catch (e: ApiException) {
                _progressBarVisibility.value = false
                errorString.postValue(e.message!!)
            } catch (e: NoInternetException) {
                _progressBarVisibility.value = false
                errorString.postValue(e.message!!)
            } catch (e: SocketTimeoutException) {
                _progressBarVisibility.value = false
                errorString.postValue(e.message!!)
            } catch (e: Exception) {
                _progressBarVisibility.value = false
                e.printStackTrace()
            }
        }
    }

    fun Banner(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            try {
                val response =
                    mainRepository.banner(token)
                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    bannerResponse.postValue(response.body())
                } else {
                    progressBarStatus.value = false
                    Log.d("TAG", response.body().toString())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }catch (e: NoInternetException) {
                e.printStackTrace()
            }

        }

    }

    fun checkOnlineStatusModel(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            try {
                val response =
                    mainRepository.checkOnlineStatusRepo(token)
                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    commonResponse.postValue(response.body())
                } else {
                    progressBarStatus.value = false
                    Log.d("TAG", response.body().toString())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }catch (e: NoInternetException) {
                e.printStackTrace()
            }
        }
    }

    fun call_ring_status_save(
        token: String,
        channel_name: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.call_ring_status_save(token,
                    channel_name)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                call_ring_status_save_Response.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }


    fun call_ring_end(
        token: String,
        channel_name: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.call_ring_end(token,
                    channel_name)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                commonResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

    fun call_ring(
        token: String,
        astro_id: String,
        user_id: String,
        request_id: String
    ) {
//        progressBarStatus.value = true
        viewModelScope.launch {
            val response =
                mainRepository.call_ring(token, astro_id,user_id,request_id)
            if (response.isSuccessful) {
//                progressBarStatus.value = false
                callRingResponse.postValue(response.body())
            } else {
//                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }


//    fun dummy_audioVideoCallApi(
//        token:String,  user_id: String, booking_id: String, type: String, channelName: String
//    ) {
//        _progressBarVisibility.value = true
//
//        viewModelScope.launch {
//            try {
//                val response =
//                    mainRepository.dummy_audioVideoCallApi(token, user_id, booking_id, type, channelName )
//                if (response.isSuccessful) {
//                    _progressBarVisibility.value = false
//                    audioVideoCallResponse.postValue(response.body())
//                }
//                _progressBarVisibility.value = false
//            } catch (e: ApiException) {
//                _progressBarVisibility.value = false
//                errorString.postValue(e.message!!)
//            } catch (e: NoInternetException) {
//                _progressBarVisibility.value = false
//                errorString.postValue(e.message!!)
//            } catch (e: SocketTimeoutException) {
//                _progressBarVisibility.value = false
//                errorString.postValue(e.message!!)
//            } catch (e: Exception) {
//                _progressBarVisibility.value = false
//                e.printStackTrace()
//            }
//        }
//    }

}