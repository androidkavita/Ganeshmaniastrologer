package com.callastro.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callastro.data.MainRepository
import com.callastro.model.GetAstroRatingReviewListResponse
import com.callastro.model.GetAstroRatingReviewPinResponse
import com.callastro.model.ReviewPinUpdateResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatingReviewViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    val progressBarStatus = MutableLiveData<Boolean>()
    val ratingReviewAllResponse = MutableLiveData<GetAstroRatingReviewListResponse>()
    val ratingReviewPinnedResponse = MutableLiveData<GetAstroRatingReviewPinResponse>()
    val ratingReviewUpdateResponse = MutableLiveData<ReviewPinUpdateResponse>()

    fun astro_rating_review_listApi(
        token: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.astro_rating_review_listApi(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                ratingReviewAllResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }



    fun rating_review_pinselected_listApi(
        token: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.rating_review_pinselected_listApi(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                ratingReviewPinnedResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }



    fun review_pin_updateApi(
        token: String, id: String, type: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.review_pin_updateApi(token, id, type)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                ratingReviewUpdateResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }


}