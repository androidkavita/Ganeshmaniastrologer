package com.callastro.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callastro.data.MainRepository
import com.callastro.model.AddExpertiseItemResponse
import com.callastro.model.AddLanguageItemResponse
import com.callastro.model.AddSkillsItemResponse
import com.callastro.model.BankDetailResponse
import com.callastro.model.CityListResponse
import com.callastro.model.DeleteExpertiseItemResponse
import com.callastro.model.DeleteLanguageItemResponse
import com.callastro.model.DeleteSkillsItemResponse
import com.callastro.model.ExpertizeResponse
import com.callastro.model.ExpertizeResponseData
import com.callastro.model.GetAddedExpertiseResponse
import com.callastro.model.GetAddedLanguageResponse
import com.callastro.model.GetAddedSkillsResponse
import com.callastro.model.GetBankDetail
import com.callastro.model.LanguageResponse
import com.callastro.model.LanguageResponseData
import com.callastro.model.LoginverificationResponse
import com.callastro.model.PincodeListResponse
import com.callastro.model.SkillsListData
import com.callastro.model.SkillsListResponse
import com.callastro.model.StateListResponse
import com.callastro.model.ViewProfileResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {
    val progressBarStatus = MutableLiveData<Boolean>()
    val viewProfileResponse = MutableLiveData<ViewProfileResponse>()
    val updateBankResponse = MutableLiveData<BankDetailResponse>()
    val getBankDetailResponse = MutableLiveData<GetBankDetail>()
    var stateListResponse = MutableLiveData<StateListResponse>()
    var cityListResponse = MutableLiveData<CityListResponse>()
    var pincodeListResponse = MutableLiveData<PincodeListResponse>()
    var skillsListResponse = MutableLiveData<SkillsListResponse>()
    var skillsListResponseData = MutableLiveData<ArrayList<SkillsListData>>()
    //    val commonResponse = MutableLiveData<CommonResponse>()
    val _progressBarVisibility = MutableLiveData<Int>()
    val updateUserProfileResponse = MutableLiveData<LoginverificationResponse>()
//    val locationDetailsResponse = MutableLiveData<AstroAddressDetailsResponse>()
//    val updatelocationDetailsResponse = MutableLiveData<AstroAddressDetailsUpdateResponse>()
var expertizeResponse = MutableLiveData<ExpertizeResponse>()
    var languageResponse = MutableLiveData<LanguageResponse>()
    var languageResponseData = MutableLiveData<ArrayList<LanguageResponseData>>()
    var expertizeResponseData = MutableLiveData<ArrayList<ExpertizeResponseData>>()



    var getAddedExpertiseItemResponse = MutableLiveData<GetAddedExpertiseResponse>()
    var addExpertiseItemResponse = MutableLiveData<AddExpertiseItemResponse>()
    var deleteExpertiseItemResponse = MutableLiveData<DeleteExpertiseItemResponse>()


    var getAddedSkillsItemResponse = MutableLiveData<GetAddedSkillsResponse>()
    var addSkillsItemResponse = MutableLiveData<AddSkillsItemResponse>()
    var deleteSkillsItemResponse = MutableLiveData<DeleteSkillsItemResponse>()


    var getAddedLanguageItemResponse = MutableLiveData<GetAddedLanguageResponse>()
    var addLanguageItemResponse = MutableLiveData<AddLanguageItemResponse>()
    var deleteLanguageItemResponse = MutableLiveData<DeleteLanguageItemResponse>()


    fun Expertize(
        token: String,
    ): MutableLiveData<ExpertizeResponse> {
        if (expertizeResponse == null) {
            expertizeResponse = MutableLiveData()
        }
        //    progressBarStatus.value = true
        viewModelScope.launch {
            try {
                val response = mainRepository.Expertize(token)

                if (response.isSuccessful) {
                    //       progressBarStatus.value = false
                    expertizeResponse.postValue(response.body())
                }
            }catch (e:Exception) {
                _progressBarVisibility.postValue(0)
                e.printStackTrace()
            }
        }
        return expertizeResponse
    }


    fun Language(
        token: String,
    ): MutableLiveData<LanguageResponse> {
        if (languageResponse == null) {
            languageResponse = MutableLiveData()
        }
        //  progressBarStatus.value = true
        viewModelScope.launch {
            try {
                val response = mainRepository.Language(token)

                if (response.isSuccessful) {
                    // progressBarStatus.value = false
                    languageResponse.postValue(response.body())
                }
            }catch (e:Exception) {
                _progressBarVisibility.postValue(0)
                e.printStackTrace()
            }
        }
        return languageResponse
    }



    fun add_skillsApi(
        token: String, skill_id: String
    ) {
//        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.add_skillsApi(token, skill_id)
            if (response.isSuccessful) {
//                progressBarStatus.value = false
                addSkillsItemResponse.postValue(response.body())
            } else {
//                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }


    fun delete_skillsApi(
        token: String, id: String
    ) {
//        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.delete_skillsApi(token, id)
            if (response.isSuccessful) {
//                progressBarStatus.value = false
                deleteSkillsItemResponse.postValue(response.body())
            } else {
//                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }




    fun get_added_languageApi(
        token: String
    ) {
//        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.get_added_languageApi(token)
            if (response.isSuccessful) {
//                progressBarStatus.value = false
                getAddedLanguageItemResponse.postValue(response.body())
            } else {
//                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }


    fun add_languageApi(
        token: String, language_id: String
    ) {
//        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.add_languageApi(token, language_id)
            if (response.isSuccessful) {
//                progressBarStatus.value = false
                addLanguageItemResponse.postValue(response.body())
            } else {
//                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }


    fun delete_languageApi(
        token: String, id: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.delete_languageApi(token, id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                deleteLanguageItemResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }


    fun get_added_skillsApi(
        token: String
    ) {
//        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.get_added_skillsApi(token)
            if (response.isSuccessful) {
//                progressBarStatus.value = false
                getAddedSkillsItemResponse.postValue(response.body())
            } else {
//                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

    fun skills_listApi(
        token: String,
    ): MutableLiveData<SkillsListResponse> {
        if (skillsListResponse == null) {
            skillsListResponse = MutableLiveData()
        }
        //  progressBarStatus.value = true
        viewModelScope.launch {
            try {
                val response = mainRepository.skills_listApi(token)

                if (response.isSuccessful) {
                    //   progressBarStatus.value = false
                    skillsListResponse.postValue(response.body())
                }
            }catch (e:Exception) {
                _progressBarVisibility.postValue(0)
                e.printStackTrace()
            }
        }
        return skillsListResponse
    }


    fun ViewProfile(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.ViewProfile(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                viewProfileResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }

    fun stateListApi(
        token: String
    ) {
        //    progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.stateListApi(token)
            if (response.isSuccessful) {
                //    progressBarStatus.value = false
                stateListResponse.postValue(response.body())
            } else {
                //   progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

    fun cityListApi(
        token: String, state_id: String
    ) {
        //    progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.cityListApi(token , state_id)
            if (response.isSuccessful) {
                //    progressBarStatus.value = false
                cityListResponse.postValue(response.body())
            } else {
                //    progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

    fun pinCodeListApi(
        token: String,  city_id: String
    ) {
        //  progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.pinCodeListApi(token,city_id)
            if (response.isSuccessful) {
                //  progressBarStatus.value = false
                pincodeListResponse.postValue(response.body())
            } else {
                //  progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

    fun UpdateBank(
        token:String,
        bank_name:String,
        account_no: String,
        acc_holder_name: String,
        ifsc_code: String,
        branch: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.UpdateBank(token,bank_name,account_no,acc_holder_name,ifsc_code,branch)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                updateBankResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }


    fun GeteBankDetails(
        token:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.GetBankDetail(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                getBankDetailResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
    fun get_added_expertiseApi(
        token: String
    ) {
//        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.get_added_expertiseApi(token)
            if (response.isSuccessful) {
//                progressBarStatus.value = false
                getAddedExpertiseItemResponse.postValue(response.body())
            } else {
//                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }


    fun add_expertiseApi(
        token: String, expertise_id: String
    ) {
//        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.add_expertiseApi(token, expertise_id)
            if (response.isSuccessful) {
//                progressBarStatus.value = false
                addExpertiseItemResponse.postValue(response.body())
            } else {
//                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }



    fun delete_expertiseApi(
        token: String, id: String
    ) {
//        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.delete_expertiseApi(token, id)
            if (response.isSuccessful) {
//                progressBarStatus.value = false
                deleteExpertiseItemResponse.postValue(response.body())
            } else {
//                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

    fun EditProfile(
        token: String,
        name: String,
        mobile_no: String,
        email: String,
        gender: String,
        profile: MultipartBody.Part,
        address: String,
        state_id: String,
        expertise: String,
        language: String,
        skills: String,
        company_name: String,
        city: String,
        pincode: String,
        calling_charg: String,
        document1: MultipartBody.Part,
        document2: MultipartBody.Part,
        about_us: String,
        fixed_session_30min_charge: String,
        fixed_session_60min_charge: String,
        experience: String,
    ) {
        val nameN: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
        val mobile_noN: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), mobile_no)
        val emailN: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), email)
        val genderN: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), gender)
        val address: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), address)
        val state_id: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), state_id)
        val expertise: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), expertise)
        val language: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), language)
        val skills: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), skills)
        val company_name: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), company_name)
        val city: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), city)
        val pincode: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), pincode)
        val calling_charg: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), calling_charg)
        val about_us: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), about_us)
        val fixed_session_30min_charge: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), fixed_session_30min_charge)
        val fixed_session_60min_charge: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), fixed_session_60min_charge)
        val experience: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), experience)

        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.EditProfile(token,nameN,mobile_noN,emailN,genderN,profile,address, state_id, /*expertise, language, skills,*/ company_name, city, pincode, calling_charg, document1, document2,about_us,fixed_session_30min_charge,fixed_session_60min_charge,experience)
            try {
                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    updateUserProfileResponse.postValue(response.body())
                } else {
                    progressBarStatus.value = false
                    Log.d("TAG", response.body().toString())
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    fun astro_address_detailsApi(
//        token:String
//    ) {
//        progressBarStatus.value = true
//        viewModelScope.launch {
//
//            val response =
//                mainRepository.astro_address_detailsApi(token)
//            if (response.isSuccessful) {
//                progressBarStatus.value = false
//                locationDetailsResponse.postValue(response.body())
//            } else {
//                progressBarStatus.value = false
//                Log.d("TAG", response.body().toString())
//            }
//        }
//    }
//
//    fun astro_address_updateApi(
//        token:String, address:String, state:String, city:String, pincode:String
//    ) {
//        progressBarStatus.value = true
//        viewModelScope.launch {
//
//            val response =
//                mainRepository.astro_address_updateApi(token, address, state, city, pincode)
//            if (response.isSuccessful) {
//                progressBarStatus.value = false
//                updatelocationDetailsResponse.postValue(response.body())
//            } else {
//                progressBarStatus.value = false
//                Log.d("TAG", response.body().toString())
//            }
//        }
//    }











}