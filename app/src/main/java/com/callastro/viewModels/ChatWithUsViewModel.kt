package com.callastro.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callastro.data.MainRepository
import com.callastro.model.*
import com.maxtra.astrorahiastrologer.util.ApiException
import com.maxtra.astrorahiastrologer.util.NoInternetException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class ChatWithUsViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel(){

    val error = MutableLiveData<Int>()
    val errorString = MutableLiveData<String>()
    val _progressBarVisibility = MutableLiveData<Boolean>()

    val callerendResponse= MutableLiveData<CallendbyuserResponse>()
    val checkChatEndResponse= MutableLiveData<CheckChatEndResponse>()
    val agoraChatListMessageResponse= MutableLiveData<ChatListMessageResponse>()
    val chatAgoraSendResponse= MutableLiveData<ChatAgoraResponse>()
    val commonResponse= MutableLiveData<CommonResponse>()
    val givereviewResponse = MutableLiveData<GiveReviewResponse>()



    fun chat_list_MessageApi(token: String, to_id: String
    ) {
       // _progressBarVisibility.value = true
        viewModelScope.launch {
            try {
                val response =
                    mainRepository.chat_list_MessageApi(token,to_id)
                if (response.isSuccessful) {
                    _progressBarVisibility.value = false
                    agoraChatListMessageResponse.postValue(response.body())
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
    fun call_end(token: String,timer: String,from_user: String,to_user: String,caller_id:String,type: String,fixed_session: String
    ) {
        // _progressBarVisibility.value = true
        viewModelScope.launch {
            try {
                val response =
                    mainRepository.chat_end(token,timer,from_user,to_user,caller_id,type,fixed_session)
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
    fun check_chat_end(
        token: String,
        caller_id: String
    ) {
//        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.check_chat_end(token, caller_id)
            if (response.isSuccessful) {
//                progressBarStatus.value = false
                checkChatEndResponse.postValue(response.body())
            } else {
//                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }
    fun chatagoraApi(
        token: String,
        userId: String,
        message: String,
        type: String
    ) {
      //  _progressBarVisibility.value = true
        viewModelScope.launch {
            try {
                val response =
                    mainRepository.chatagoraApi(token, userId, message, type)
                if (response.isSuccessful) {
                    _progressBarVisibility.value = false
                    chatAgoraSendResponse.postValue(response.body())
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


}