package com.callastro.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callastro.data.MainRepository
import com.callastro.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class FillDetailViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {
    val progressBarStatus = MutableLiveData<Boolean>()
    var expertizeResponse = MutableLiveData<ExpertizeResponse>()
    var languageResponse = MutableLiveData<LanguageResponse>()
    var languageResponseData = MutableLiveData<ArrayList<LanguageResponseData>>()
    var expertizeResponseData = MutableLiveData<ArrayList<ExpertizeResponseData>>()
    val _progressBarVisibility = MutableLiveData<Int>()


    var postProfileDetailResponse = MutableLiveData<PostProfileDetailResponse>()


    var stateListResponse = MutableLiveData<StateListResponse>()
    var cityListResponse = MutableLiveData<CityListResponse>()
    var pincodeListResponse = MutableLiveData<PincodeListResponse>()

    var skillsListResponse = MutableLiveData<SkillsListResponse>()
    var skillsListResponseData = MutableLiveData<ArrayList<SkillsListData>>()


    var addAstroDetailsResponse = MutableLiveData<AddAstroDetailResponse>()




    var getAddedExpertiseItemResponse = MutableLiveData<GetAddedExpertiseResponse>()
    var addExpertiseItemResponse = MutableLiveData<AddExpertiseItemResponse>()
    var deleteExpertiseItemResponse = MutableLiveData<DeleteExpertiseItemResponse>()


    var getAddedSkillsItemResponse = MutableLiveData<GetAddedSkillsResponse>()
    var addSkillsItemResponse = MutableLiveData<AddSkillsItemResponse>()
    var deleteSkillsItemResponse = MutableLiveData<DeleteSkillsItemResponse>()


    var getAddedLanguageItemResponse = MutableLiveData<GetAddedLanguageResponse>()
    var addLanguageItemResponse = MutableLiveData<AddLanguageItemResponse>()
    var deleteLanguageItemResponse = MutableLiveData<DeleteLanguageItemResponse>()


    var postProfileUpdateResponse = MutableLiveData<PostProfileUpdateResponse>()
    var experienceListResponse = MutableLiveData<ExperienceListResponse>()
    var experienceListResponseData = MutableLiveData<ArrayList<ExperienceListData>>()




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



    fun post_profile_detailApi(
        token: String
    ) {
        //    progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.post_profile_detailApi(token)
            if (response.isSuccessful) {
                //    progressBarStatus.value = false
                postProfileDetailResponse.postValue(response.body())
            } else {
                //       progressBarStatus.value = false
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


  /*  fun skills_listApi(
        token: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.skills_listApi(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                skillsListResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }
*/















    fun AddAstroDetailsApi(
        token: String,
        full_name: String,
        company_name: String,
        email: String,
        gender: String,
        address: String,
        state_id: String,
        city_id: String,
        pincode_id: String,
        expertise: String,
        language: String,
        skills: String,
        bank_name: String,
        account_no: String,
        acc_holder_name: String,
        ifsc_code: String,
        branch: String,
        document1: MultipartBody.Part,
        document2: MultipartBody.Part,
        profile_image: MultipartBody.Part,
        calling_charge:String,
        about_us:String,
        fixed_session_30min_charge :String,
        fixed_session_60min_charge :String,
        experience :String,
    ) {


        val full_nameN: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), full_name)
        val company_nameN: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), company_name)
        val emailN: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), email)
        val genderN: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), gender)
        val addressN: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), address)
        val state_idN: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), state_id)
        val city_idN: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), city_id)
        val pincode_idN: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), pincode_id)
        val expertiseN: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), expertise)
        val languageN: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), language)
        val skillsN: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), skills)
        val bank_nameN: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), bank_name)
        val account_noN: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), account_no)
        val acc_holder_nameN: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), acc_holder_name)
        val ifsc_codeN: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), ifsc_code)
        val branchN: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), branch)
        val calling_charge: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), calling_charge)
        val about_us: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), about_us)
        val fixed_session_30min_charge: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), fixed_session_30min_charge)
        val fixed_session_60min_charge: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), fixed_session_60min_charge)
        val experience : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), experience)

        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.AddAstroDetailsApi(token,full_nameN,company_nameN,emailN,genderN
                    ,addressN, state_idN, city_idN, pincode_idN, expertiseN, languageN ,skillsN,
                    bank_nameN, account_noN, acc_holder_nameN , ifsc_codeN, branchN, document1, document2, profile_image,
                    calling_charge,about_us,fixed_session_30min_charge,fixed_session_60min_charge,experience)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                addAstroDetailsResponse.postValue(response.body())
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













    fun post_profile_updateApi(
        token: String,  full_name:String, gender:String, experience:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.post_profile_updateApi(token, full_name, gender, experience )
            if (response.isSuccessful) {
                progressBarStatus.value = false
                postProfileUpdateResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

   /* fun experience_listApi(
        token: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.experience_listApi(token )
            if (response.isSuccessful) {
                progressBarStatus.value = false
                experienceListResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }*/

    fun experience_listApi(
        token: String,
    ): MutableLiveData<ExperienceListResponse> {
        if (experienceListResponse == null) {
            experienceListResponse = MutableLiveData()
        }
       // progressBarStatus.value = true
        viewModelScope.launch {
            try {
                val response = mainRepository.experience_listApi(token)

                if (response.isSuccessful) {
                 //   progressBarStatus.value = false
                    experienceListResponse.postValue(response.body())
                }
            }catch (e:Exception) {
                _progressBarVisibility.postValue(0)
                e.printStackTrace()
            }
        }
        return experienceListResponse
    }








}