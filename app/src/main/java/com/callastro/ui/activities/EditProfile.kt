package com.callastro.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.callastro.DialogUtils
import com.callastro.MainActivity
import com.callastro.R
import com.callastro.adapters.CityListAdapter
import com.callastro.adapters.GetProfileLanguageAdapter
import com.callastro.adapters.GetProfileSkillsAdapter
import com.callastro.adapters.PinCodeListAdapter
import com.callastro.adapters.ReviewRatingAllAdapter
import com.callastro.adapters.ReviewRatingPinnedAdapter
import com.callastro.adapters.StateListAdapter
import com.callastro.databinding.ActivityEditProfileBinding
import com.callastro.model.AddExpertiseItemData
import com.callastro.model.AddSkillsItemData
import com.callastro.model.CityListData
import com.callastro.model.ExpertizeResponseData
import com.callastro.model.GetAddedExpertiseData
import com.callastro.model.GetAddedLanguageData
import com.callastro.model.GetAddedSkillsData
import com.callastro.model.GetAstroRatingReviewPinData
import com.callastro.model.GetAstroRatingReviewReviewRatings
import com.callastro.model.GetProfileExpertiseAdapter
import com.callastro.model.LanguageResponseData
import com.callastro.model.PincodeListData
import com.callastro.model.SkillsListData
import com.callastro.model.StateListData
import com.callastro.viewModels.ProfileViewModel
import com.callastro.viewModels.RatingReviewViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.maxtra.astrorahiastrologer.util.toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class EditProfile : BaseActivity(), GetProfileExpertiseAdapter.OnClick, GetProfileSkillsAdapter.OnClick, GetProfileLanguageAdapter.OnClick,ReviewRatingAllAdapter.OnClick/*, PopupItemClickListenerCountry*/ {
    lateinit var binding: ActivityEditProfileBinding
    var userimage: MultipartBody.Part? = null
    private val pickImageCamera = 1
    private val pickImageGallery = 2
    private val pickDocument1Camera = 3
    private val pickDocument1Gallery = 4
    private val pickDocument2Camera = 5
    private val pickDocument2Gallery = 6
    lateinit var currentPhotoPath: String
    var mPhotoFile: File? = null
    var photoURICamera: Uri?=null
    var flag:Boolean = false
    var choosenGender : String = "1"
    var photoURIDoc1Camera: Uri?=null
    var photoURIDoc2Camera: Uri?=null
    lateinit var currentPhotoPathDoc1: String
    lateinit var currentPhotoPathDoc2: String
    var userdocument1image: MultipartBody.Part? = null
    var userdocument2image: MultipartBody.Part? = null
    val FILE_BROWSER_CACHE_DIR = "file"
    var flag1:Boolean = false
    var flag2:Boolean = false
    var mPhotoFile1: File? = null
    var mPhotoFile2: File? = null
    var scpflag: String = ""
    private var stateAdapter : StateListAdapter?= null
    private var cityAdapter : CityListAdapter?= null
    private var pincodeAdapter : PinCodeListAdapter?= null
    var strStateId: String = ""
    var strCityId: String = ""
    var strPincodeId: String = ""
    private var statelistData = ArrayList<StateListData>()
    private var citylistData = ArrayList<CityListData>()
    private var pinCodelistData = ArrayList<PincodeListData>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var search: EditText
    lateinit var no_notification: LinearLayout
    lateinit var expertisegetaddDataId: ArrayList<GetAddedExpertiseData>
    lateinit var expertiseaddDataId: ArrayList<AddExpertiseItemData>
    lateinit var getexadapter : GetProfileExpertiseAdapter
    var ListExpertizedata: ArrayList<ExpertizeResponseData> = ArrayList()
    var selectedExpertizeId= ""
    var expertisenamedata :ArrayList<String> = arrayListOf()
    var idexpertisedata :ArrayList<String> = arrayListOf()

    lateinit var skillsgetaddDataId: ArrayList<GetAddedSkillsData>
    lateinit var skillsaddDataId: ArrayList<AddSkillsItemData>
    lateinit var getskillsadapter : GetProfileSkillsAdapter
    var ListSkillsdata: ArrayList<SkillsListData> = ArrayList()
    var selectedSkillsId= ""
    var skillsnamedata :ArrayList<String> = arrayListOf()
    var idSkillsdata :ArrayList<String> = arrayListOf()
    private val ratingReviewViewModel: RatingReviewViewModel by viewModels()
    lateinit var languagegetaddDataId: ArrayList<GetAddedLanguageData>
    var ListLanguagedata: ArrayList<LanguageResponseData> = ArrayList()
    lateinit var getlanguageadapter : GetProfileLanguageAdapter
    var languageaddDataId: ArrayList<String> = ArrayList()
    var selectedLanguageId= ""
    var languagenamedata :ArrayList<String> = arrayListOf()
    var idlanguagedata :ArrayList<String> = arrayListOf()

    var placesClient: PlacesClient? = null
    private val AUTOCOMPLETE_PLACE_REQUEST_CODE = 7
    var latLng: LatLng? = null
    var pickupLongitude = 0.0
    var pickupLatitude = 0.0
    var dropLongitude = 0.0
    var dropLatitude = 0.0
    var sourceLatLong: LatLng? = null
    var destLatLong: LatLng? = null
    var distance: Double? = null
    var distanceString: String? = null
    lateinit var reviewRatingPinnedDataId: ArrayList<GetAstroRatingReviewPinData>
    lateinit var getPinnedRatingAdapter : ReviewRatingPinnedAdapter
    lateinit var getAllRatingAdapter : ReviewRatingAllAdapter
    lateinit var reviewRatingAllDataId: ArrayList<GetAstroRatingReviewReviewRatings>
    private val viewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)


        expertiseaddDataId = ArrayList()
        expertisegetaddDataId = ArrayList()

        skillsaddDataId = ArrayList()
        skillsgetaddDataId = ArrayList()

        languageaddDataId = ArrayList()
        languagegetaddDataId = ArrayList()

        reviewRatingAllDataId = ArrayList()
        reviewRatingPinnedDataId = ArrayList()
//        experiencenamedata = ArrayList()

       // val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        if (userPref.getProfileImage().equals("")){

        }else{
            Glide.with(this).load(userPref.getProfileImage())
                .apply(RequestOptions.placeholderOf(R.drawable.user_image_place_holder))
                .apply(RequestOptions.errorOf(R.drawable.user_image_place_holder))
                .into(binding.ivPict)
        }

        binding.etFullName.setText(userPref.getName())
        binding.etPhone.setText(userPref.getmobile())
        binding.etEmailid.setText(userPref.getEmail())
        if(userPref.getGender() == "1"){
            binding.radioMale.isChecked = true
            //notifyDataSetChanged()
        }
        else if(userPref.getGender() == "2"){
            binding.radioFemale.isChecked = true
            //notifyDataSetChanged()
        }
        else if(userPref.getGender() == "0"){
            binding.radioOther.isChecked = true
            //notifyDataSetChanged()
        }
        if (CommonUtils.isInternetAvailable(this)) {
            viewModel.ViewProfile("Bearer "+userPref.getToken().toString())
        } else {
            toast(this,"Please check internet connection.")
        }
        viewModel.viewProfileResponse.observe(this) {
            if (it?.status == 1) {
                binding.etFullName.setText(it.data?.name.toString())
                binding.etPhone.setText(it.data?.mobileNo.toString())
                binding.etEmailid.setText(it.data?.email.toString())
                binding.companyname.setText(it.data?.companyName.toString())
                binding.etAddress.setText(it.data?.address.toString())
                binding.pincode.setText(it.data?.pincode.toString())
                binding.aboutme.setText(it.data?.aboutUs.toString())
                binding.callingcharge.setText(it.data?.calling_charg.toString())
                binding.thirtymincharge.setText(it.data?.fixed_session_30min_charge.toString())
                binding.sixtymincharge.setText(it.data?.fixed_session_60min_charge.toString())
                binding.experience.setText(it.data?.experenceId.toString())
                Glide.with(this).load(it.data!!.profile)
                    .apply(RequestOptions.placeholderOf(R.drawable.user_image_place_holder))
                    .apply(RequestOptions.errorOf(R.drawable.user_image_place_holder))
                    .into(binding.ivPict)
                userPref.setToken(it.data!!.apiToken)
                userPref.setName(it.data!!.name.toString())
                userPref.setMobile(it.data!!.mobileNo.toString())
                userPref.setEmail(it.data!!.email.toString())
                userPref.setGender(it.data!!.gender.toString())
                if (it.data!!.profile != null) {
                    userPref.setProfileImage(it.data!!.profile)
                    Log.e("@@image2", userPref.getProfileImage().toString())
                }else{
                    userPref.setProfileImage("")
                }
                if (it.data?.gender.toString() == "1"){
                    binding.radioMale.isChecked = true

//                    binding.llGender.visibility = View.VISIBLE
//                    binding.tvGender.text = "Male"

                }else if (it.data?.gender.toString() == "2"){
                    binding.radioFemale.isChecked = true
//                    binding.llGender.visibility = View.VISIBLE
//                    binding.tvGender.text = "Female"
                }
                else if (it.data?.gender.toString() == "0"){
                    binding.radioOther.isChecked = true
//                    binding.llGender.visibility = View.GONE
                }
            }else{
                snackbar(it.message.toString())
            }
        }

//        binding.state.setOnClickListener {
//            scpflag = "State"
//            openPopUp()
//        }
//        binding.city.setOnClickListener {
//            if ( userPref.getStateId().equals("0")) {
//                toast(this,"Please select state first.")
//
//            } else {
//                scpflag = "City"
//                openPopUp()
//            }
//        }
        binding.pincode.setOnClickListener {
            if ( userPref.getCityId().equals("0")) {
                toast(this, "Please select city first.")

            } else {
                scpflag = "Pincode"
                openPopUp()
            }
        }

        if(userPref.getGender() == "1"){
            binding.radioMale.isChecked = true
            //notifyDataSetChanged()
        }
       else if(userPref.getGender() == "2"){
            binding.radioFemale.isChecked = true
            //notifyDataSetChanged()
        }
        else if(userPref.getGender() == "0"){
            binding.radioOther.isChecked = true
            //notifyDataSetChanged()
        }




        binding.backArrow.setOnClickListener {
            finish()
        }
        binding.ivPict.setOnClickListener {
            selectImage()
        }
        binding.ivDoc1Def.setOnClickListener {
            selectDoc1()
        }
        binding.ivDoc2Def.setOnClickListener {
            selectDoc2()
        }
        binding.btnUpdate.setOnClickListener {

            if (userimage == null){
                userimage = MultipartBody.Part.createFormData("profile", "")
            }
            if (userdocument1image == null){
                userdocument1image = MultipartBody.Part.createFormData("document1", "")
            }
            if (userdocument2image == null){
                userdocument2image = MultipartBody.Part.createFormData("document2", "")
            }
            if (validate()){
                if (CommonUtils.isInternetAvailable(this@EditProfile)) {
                    viewModel.EditProfile("Bearer " + userPref.getToken().toString() ,
                        binding.etFullName.text.trim().toString(),
                        binding.etPhone.text.trim().toString(),
                        binding.etEmailid.text.trim().toString(),
                        choosenGender.toString(),
                        userimage!!,
                        binding.etAddress.text.toString(),
                        strStateId,
                        selectedExpertizeId,
                        selectedLanguageId,
                        selectedSkillsId,
                        binding.companyname.text.toString(),
                        strCityId,
                        binding.pincode.text.toString(),
                        binding.callingcharge.text.toString(),
                        userdocument1image!!,
                        userdocument2image!!,
                        binding.aboutme.text.toString(),
                        binding.thirtymincharge.text.toString(),
                        binding.sixtymincharge.text.toString(),
                        binding.experience.text.toString())
                } else {
                    toast(this@EditProfile,"Please check internet connection.")
                }


            }

        }
        binding.radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {
                    R.id.radioMale -> {
                        choosenGender = "1"
                    }
                    R.id.radioFemale -> {
                        choosenGender = "2"
                    }
                    R.id.radioOther -> {
                        choosenGender = "0"
                    }
                }
            }
        })
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.updateUserProfileResponse.observe(this, androidx.lifecycle.Observer {
            if (it.status == 1) {
                userPref.isLogin = true
                userPref.setToken(it.otpdata!!.apiToken.toString())
                userPref.setName(it.otpdata!!.name.toString())
                userPref.setEmail(it.otpdata!!.email.toString())
                userPref.setMobile(it.otpdata!!.mobileNo.toString())
                userPref.setAddress(it.otpdata!!.address.toString())
                userPref.setGender(it.otpdata!!.gender.toString())
                if (it.otpdata!!.profile != null) {
                    userPref.setProfileImage(it.otpdata!!.profile)
                    Log.e("@@image1", userPref.getProfileImage().toString())
                }
//                toast("Profile Updated Successfully...")
//                finish()
                ProfileUpdated()

            }else if (it.status == 2){
                    Alertdialog("Please wait for admin approval.")
//                hideProgressDialog()
//                toast(it.message!!)
            }else{
//                toast(this@EditProfile,it.message.toString())
                Alertdialog("Please wait for admin approval.")
            }
        })

        viewModel.getAddedExpertiseItemResponse.observe(this) {
            if (it.status == 1) {
                expertisegetaddDataId.clear()
                expertisegetaddDataId.addAll(it.data)
                getexadapter = GetProfileExpertiseAdapter(expertisegetaddDataId,this)
                binding.rvExpertise.adapter =getexadapter
                binding.rvExpertise.visibility = View.VISIBLE

            } else {
                binding.rvExpertise.visibility = View.GONE
                selectedExpertizeId = ""
            }
        }


        if (CommonUtils.isInternetAvailable(this@EditProfile)) {
            viewModel.get_added_expertiseApi( "Bearer " + userPref.getToken())
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this@EditProfile,"Please check internet connection.")
        }




        viewModel.addExpertiseItemResponse.observe(this) {
            if (it.status == 1) {
                if (CommonUtils.isInternetAvailable(this@EditProfile)) {
                    viewModel.get_added_expertiseApi( "Bearer " + userPref.getToken())
                } else {
                    Log.d("TAG", "onCreate: " + "else part")
                    toast(this@EditProfile,"Please check internet connection.")
                }

            } else {
            }
        }
        viewModel.deleteExpertiseItemResponse.observe(this){
            if (it.status==1){
                if (CommonUtils.isInternetAvailable(this@EditProfile)) {
                    viewModel.get_added_expertiseApi( "Bearer " + userPref.getToken())
                } else {
                    Log.d("TAG", "onCreate: " + "else part")
                    toast(this@EditProfile,"Please check internet connection.")
                }


            }else{
                binding.spinnerChooseExpertise.setItems(expertisenamedata)
            }
        }
        binding.spinnerChooseExpertise.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newText ->
            // toast(requireContext(), truckTypeListData[newIndex].id.toString())
            selectedExpertizeId = ListExpertizedata[newIndex].id.toString()
            //  viewModel.truckFeetAndTonApi("Bearer " + userPref.user.api_token, truckTypeListData[newIndex].id.toString())
            if (CommonUtils.isInternetAvailable(this@EditProfile)) {
                viewModel.add_expertiseApi( "Bearer " + userPref.getToken(), selectedExpertizeId)
            } else {
                Log.d("TAG", "onCreate: " + "else part")
                toast(this@EditProfile,"Please check internet connection.")
            }


        }

        /*-------------------------------------Skills-----------------------------------------------------*/
        binding.spinnerSkills.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newText ->
            selectedSkillsId = ListSkillsdata[newIndex].id.toString()
            if (CommonUtils.isInternetAvailable(this@EditProfile)) {
                viewModel.add_skillsApi( "Bearer " + userPref.getToken(), selectedSkillsId)
            } else {
                Log.d("TAG", "onCreate: " + "else part")
                toast(this@EditProfile,"Please check internet connection.")
            }


        }
        viewModel.getAddedSkillsItemResponse.observe(this) {
            if (it.status == 1) {
                skillsgetaddDataId.clear()
                skillsgetaddDataId.addAll(it.data)
                getskillsadapter = GetProfileSkillsAdapter(skillsgetaddDataId,this)
                binding.rvSkills.adapter =getskillsadapter
                binding.rvSkills.visibility = View.VISIBLE
            } else {
                binding.rvSkills.visibility = View.GONE
                selectedSkillsId = ""
            }
        }

        if (CommonUtils.isInternetAvailable(this@EditProfile)) {
            viewModel.get_added_skillsApi( "Bearer " + userPref.getToken())
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this@EditProfile,"Please check internet connection.")
        }


        viewModel.addSkillsItemResponse.observe(this) {
            if (it.status == 1) {
                if (CommonUtils.isInternetAvailable(this@EditProfile)) {
                    viewModel.get_added_skillsApi( "Bearer " + userPref.getToken())
                } else {
                    toast(this@EditProfile,"Please check internet connection.")
                }

            } else {
            }
        }
        viewModel.deleteSkillsItemResponse.observe(this){
            if (it.status==1){
                //  it.message?.let { it1 -> toast(it1) }
                if (CommonUtils.isInternetAvailable(this@EditProfile)) {
                    viewModel.get_added_skillsApi( "Bearer " + userPref.getToken())
                } else {
                    toast(this@EditProfile,"Please check internet connection.")
                }

            }else{
            }
        }



        /*-------------------------------------Language-----------------------------------------------------*/

        viewModel.getAddedLanguageItemResponse.observe(this) {
            if (it.status == 1) {
                languagegetaddDataId.clear()
                languagegetaddDataId.addAll(it.data)
                getlanguageadapter= GetProfileLanguageAdapter(languagegetaddDataId,this)
                binding.rvLanguage.adapter =getlanguageadapter
                binding.rvLanguage.visibility = View.VISIBLE
            } else {
                binding.rvLanguage.visibility = View.GONE
                selectedLanguageId = ""
            }
        }

        if (CommonUtils.isInternetAvailable(this@EditProfile)) {
            viewModel.get_added_languageApi( "Bearer " + userPref.getToken())
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this@EditProfile,"Please check internet connection.")
        }


        viewModel.addLanguageItemResponse.observe(this) {
            if (it.status == 1) {
                if (CommonUtils.isInternetAvailable(this@EditProfile)) {
                    viewModel.get_added_languageApi( "Bearer " + userPref.getToken())
                } else {
                    Log.d("TAG", "onCreate: " + "else part")
                    toast(this@EditProfile,"Please check internet connection.")
                }
            } else {
                // toast(it.message!!)
            }
        }
        viewModel.deleteLanguageItemResponse.observe(this){
            if (it.status==1){
                if (CommonUtils.isInternetAvailable(this@EditProfile)) {
                    viewModel.get_added_languageApi( "Bearer " + userPref.getToken())
                } else {
                    Log.d("TAG", "onCreate: " + "else part")
                    toast(this@EditProfile,"Please check internet connection.")
                }
            }
        }

        binding.spinnerLanguage.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newText ->
            // toast(requireContext(), truckTypeListData[newIndex].id.toString())
            selectedLanguageId = ListLanguagedata[newIndex].id.toString()
            if (CommonUtils.isInternetAvailable(this@EditProfile)) {
                viewModel.add_languageApi( "Bearer " + userPref.getToken(), selectedLanguageId)
            } else {
                Log.d("TAG", "onCreate: " + "else part")
                toast(this@EditProfile,"Please check internet connection.")
            }
        }

        if (CommonUtils.isInternetAvailable(this@EditProfile)) {
            ExpertiseAPI()
            LanguageAPI()
            SkillsAPI()
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this@EditProfile,"Please check internet connection.")
        }
        val apiKey = getString(R.string.api_key)
        if (!Places.isInitialized()) {
            Places.initialize(this, apiKey)
        }

        placesClient = Places.createClient(this)
        binding.etAddress.setOnClickListener {
            placesAPiCall(AUTOCOMPLETE_PLACE_REQUEST_CODE)
        }

        ratingReviewViewModel.ratingReviewAllResponse.observe(this) {
            if (it.status == 1) {
                reviewRatingAllDataId.clear()
                reviewRatingAllDataId.addAll(it.data!!.reviewRatings)
                getAllRatingAdapter = ReviewRatingAllAdapter(this,reviewRatingAllDataId,this)
                binding.rvReviews.adapter =getAllRatingAdapter
                binding.rvReviews.visibility = View.VISIBLE
                binding.llViewAll.visibility = View.VISIBLE
                // toast("data")

            } else {
                // toast(it.message!!)
                binding.rvReviews.visibility = View.GONE
                binding.llViewAll.visibility = View.GONE
            }
        }

        ratingReviewViewModel.astro_rating_review_listApi( "Bearer " + userPref.getToken())

        ratingReviewViewModel.ratingReviewPinnedResponse.observe(this) {
            if (it.status == 1) {
                reviewRatingPinnedDataId.clear()
                reviewRatingPinnedDataId.addAll(it.data)
                getPinnedRatingAdapter = ReviewRatingPinnedAdapter(this,reviewRatingPinnedDataId)
                binding.rvReviews.adapter =getPinnedRatingAdapter
                binding.rvReviews.visibility = View.VISIBLE
                binding.llPinnedReviews.visibility = View.VISIBLE

                // toast("data")

            } else {
                // toast(it.message!!)
                binding.rvReviews.visibility = View.GONE
                binding.llPinnedReviews.visibility = View.GONE
            }
        }
        ratingReviewViewModel.rating_review_pinselected_listApi( "Bearer " + userPref.getToken())

        binding.llPinnedReviews.setBackgroundDrawable(resources.getDrawable(R.drawable.yellowbackgroundlessradius))
        binding.llViewAll.setOnClickListener {
            binding.llViewAll.setBackgroundDrawable(resources.getDrawable(R.drawable.yellowbackgroundlessradius))
            binding.llPinnedReviews.setBackgroundDrawable(resources.getDrawable(R.drawable.focus))
            ratingReviewViewModel.astro_rating_review_listApi( "Bearer " + userPref.getToken())
        }

        binding.llPinnedReviews.setOnClickListener {
            binding.llPinnedReviews.setBackgroundDrawable(resources.getDrawable(R.drawable.yellowbackgroundlessradius))
            binding.llViewAll.setBackgroundDrawable(resources.getDrawable(R.drawable.focus))
            ratingReviewViewModel.rating_review_pinselected_listApi( "Bearer " + userPref.getToken())
        }

        binding.llAddPinnedReviews.setOnClickListener {
            val intent = Intent (this, RatingReviewsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        ratingReviewViewModel.astro_rating_review_listApi( "Bearer " + userPref.getToken())
        binding.llViewAll.setBackgroundDrawable(resources.getDrawable(R.drawable.yellowbackgroundlessradius))
        binding.llPinnedReviews.setBackgroundDrawable(resources.getDrawable(R.drawable.focus))
//        ratingReviewViewModel.rating_review_pinselected_listApi( "Bearer " + userPref.getToken())
    }

    fun ProfileUpdated(){
        val buinder = AlertDialog.Builder(this)
        buinder.setMessage("Profile has been successfully updated, and it is send to admin for review, once reviewed it will be published.")
//        buinder.setIcon(R.drawable.alert)
        buinder.setTitle("Profile Updated")
        buinder.setPositiveButton("OK") { dialogInterface, which ->
            var intent = Intent(this@EditProfile, MainActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        val alertDialog: AlertDialog = buinder.create()
        alertDialog.setCancelable(false)

        try {
            alertDialog.show()
        }
        catch (e: WindowManager.BadTokenException) {
            e.printStackTrace()
        }
//        alertDialog.show()

    }
    private fun placesAPiCall(requestCode: Int) {
        val fields = listOf(
            Place.Field.ID,
            Place.Field.NAME,
            Place.Field.ADDRESS,
            Place.Field.LAT_LNG
        )
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .build(this)
        startActivityForResult(intent, requestCode)
    }
//    private fun Alertdialog(message:String){
//        val buinder = AlertDialog.Builder(this)
//
//        buinder.setMessage(message)
//        buinder.setIcon(R.drawable.alert)
//        buinder.setTitle("Alert")
//
//        buinder.setPositiveButton("Yes") { dialogInterface, which ->
//        }
//        val alertDialog: AlertDialog = buinder.create()
//        alertDialog.setCancelable(true)
//        alertDialog.show()
//    }
    fun ExpertiseAPI(){
        viewModel.Expertize(
            "Bearer "+userPref.getToken().toString()
        ).observe(this) {
            if (it!!.status == 1) {
                ListExpertizedata.clear()
                ListExpertizedata.addAll(it!!.data)
                viewModel.expertizeResponseData.value = it.data
                for (i in 0 until it.data.size) {
                    expertisenamedata.add(it.data[i].name.toString())
                    idexpertisedata.add(it.data[i].id.toString())
                }
                binding.spinnerChooseExpertise.setItems(expertisenamedata)

            }
        }
    }

    fun LanguageAPI(){
        viewModel.Language(
            "Bearer "+userPref.getToken().toString()
        ).observe(this) {
            if (it!!.status == 1) {
                ListLanguagedata.clear()
                // languagenamedata.clear()
                ListLanguagedata.addAll(it!!.data)
                viewModel.languageResponseData.value = it.data
                for (i in 0 until it.data.size) {
                    languagenamedata.add(it.data[i].language.toString())
                    idlanguagedata.add(it.data[i].id.toString())
                }
                binding.spinnerLanguage.setItems(languagenamedata)

            }
        }
    }


    fun SkillsAPI(){
        viewModel.skills_listApi(
            "Bearer "+userPref.getToken().toString()
        ).observe(this) {
            if (it!!.status == 1) {
                ListSkillsdata.clear()
//                skillsnamedata.clear()
                ListSkillsdata.addAll(it!!.data)
                viewModel.skillsListResponseData.value = it.data
                for (i in 0 until it.data.size) {
                    skillsnamedata.add(it.data[i].skillName.toString())
                    idSkillsdata.add(it.data[i].id.toString())
                }
                binding.spinnerSkills.setItems(skillsnamedata)

            }
        }
    }

    fun openPopUp() {

        try {
            val binding = LayoutInflater.from(this).inflate(R.layout.dialog_list, null)
            dialog = DialogUtils().createDialog(this, binding.rootView, 0)!!
            recyclerView = binding.findViewById(R.id.popup_recyclerView)

            search = binding.findViewById(R.id.search_bar_edittext_popuplist)
            no_notification = binding.findViewById(R.id.no_notification)
            recyclerView.layoutManager = LinearLayoutManager(this)
//            progressbarpopup = binding.findViewById(R.id.progressbar_pop)
            var dialougTitle = binding.findViewById<TextView>(R.id.popupTitle)
            var dialougbackButton = binding.findViewById<ImageView>(R.id.BackButton)
            var SearchEditText = binding.findViewById<EditText>(R.id.search_bar_edittext_popuplist)

            SearchEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(text: Editable?) {
                    filterData(text.toString(), scpflag)
                }

            })
            dialougbackButton.setOnClickListener { dialog!!.dismiss() }


//            var SearchEditText = binding.findViewById<EditText>(R.id.search_bar_edittext_popuplist)

            search.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {

//                    CN_SEARCH(s.toString())

                }
            })


            if (scpflag.equals("State")) {

                dialougTitle.setText(scpflag)
                // stateViewModel.StateListApi()
                if (CommonUtils.isInternetAvailable(this@EditProfile)) {
                    viewModel.stateListApi("Bearer " +  userPref.getToken())
                } else {
                    Log.d("TAG", "onCreate: " + "else part")
                    toast(this@EditProfile,"Please check internet connection.")
                }


            }else if (scpflag.equals("City")) {

                dialougTitle.setText(scpflag)
                // stateViewModel.city_list(userPref.getStateId().toString())
                if (CommonUtils.isInternetAvailable(this@EditProfile)) {
                    viewModel.cityListApi("Bearer " + userPref.getToken(),userPref.getStateId().toString() )
                } else {
                    Log.d("TAG", "onCreate: " + "else part")
                    toast(this@EditProfile,"Please check internet connection.")
                }


                Log.d(ContentValues.TAG, "openPopUpCity: "+userPref.getStateId().toString())
            }
            else if (scpflag.equals("Pincode")) {

                dialougTitle.setText(scpflag)
                // stateViewModel.city_list(userPref.getStateId().toString())
                if (CommonUtils.isInternetAvailable(this@EditProfile)) {
                    viewModel.pinCodeListApi("Bearer " + userPref.getToken(),userPref.getCityId().toString() )
                } else {
                    Log.d("TAG", "onCreate: " + "else part")
                    toast(this@EditProfile,"Please check internet connection.")
                }

                Log.d(ContentValues.TAG, "openPopUpPin: "+userPref.getCityId().toString())

            }
            dialog!!.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
//        viewModel.stateListResponse.observe(this) {
//            if (it?.status == 1) {
//
//
//
//                statelistData = it.data
//                setStateAdapter()
//
//            } else {
//                //toast(it.message)
//                snackbar(it?.message!!)
//            }
//        }
//        viewModel.cityListResponse.observe(this) {
//            if (it?.status == 1) {
//                citylistData=it.data
//                setCityAdapter()
//
//
//            }else {
//                snackbar(it?.message!!)
//            }
//        }
//        viewModel.pincodeListResponse.observe(this) {
//            if (it?.status == 1) {
//                pinCodelistData=it.data
//                setPincodeAdapter()
//
//
//            }else {
//                snackbar(it?.message!!)
//            }
//        }

    }


   /* fun setStateAdapter() {
        stateAdapter = this.let { StateListAdapter(this, statelistData, scpflag, this) }
        recyclerView.adapter = stateAdapter
    }
    fun setCityAdapter() {
        cityAdapter = this.let { CityListAdapter(this, citylistData, scpflag, this) }
        recyclerView.adapter = cityAdapter
    }

    fun setPincodeAdapter() {
        pincodeAdapter = this.let { PinCodeListAdapter(this, pinCodelistData, scpflag, this) }
        recyclerView.adapter = pincodeAdapter
    }
*/

    private fun Alertdialog(message:String){
        val buinder = AlertDialog.Builder(this)

        buinder.setMessage(message)
        buinder.setIcon(R.drawable.alert)
        buinder.setTitle("Alert")

        buinder.setPositiveButton("OK") { dialogInterface, which ->
            var intent = Intent(this@EditProfile, MainActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        val alertDialog: AlertDialog = buinder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
    private fun selectDoc1() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose From Files", "Cancel")
        val pm: PackageManager = packageManager
        val builder =
            android.app.AlertDialog.Builder(this, R.style.AlertDialogTheme)
        builder.setTitle("Select Option")
        builder.setItems(
            options
        ) { dialog: DialogInterface, item: Int ->
            if (options[item] == "Take Photo") {
                dialog.dismiss()
                requestStoragePermission1(true)
            } else if (options[item] == "Choose From Files") {
                dialog.dismiss()
                requestStoragePermission1(false)
            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }

    private fun selectDoc2() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose From Files", "Cancel")
        val pm: PackageManager = packageManager
        val builder =
            android.app.AlertDialog.Builder(this, R.style.AlertDialogTheme)
        builder.setTitle("Select Option")
        builder.setItems(
            options
        ) { dialog: DialogInterface, item: Int ->
            if (options[item] == "Take Photo") {
                dialog.dismiss()
                requestStoragePermission2(true)
            } else if (options[item] == "Choose From Files") {
                dialog.dismiss()
                requestStoragePermission2(false)
            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }


    private fun requestStoragePermission1(isCamera1: Boolean) {
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    // check if all permissions are granted
                    if (report.areAllPermissionsGranted()) {
                        if (isCamera1) {
                            openCamera1()
                        } else {
                            // openGallery1()
                            selectPdf1()
                        }
                    }
                    // check for permanent denial of any permission
                    if (report.isAnyPermissionPermanentlyDenied) {
                        // show alert dialog navigating to Settings
                        showSettingsDialog()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token!!.continuePermissionRequest()
                }
            })
            .withErrorListener { error ->

            }
            .onSameThread()
            .check()
    }
    private fun selectPdf1() {
        val pickPhotoDocument1 = Intent(Intent.ACTION_GET_CONTENT)
        pickPhotoDocument1.type = "application/pdf"
        pickPhotoDocument1.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(pickPhotoDocument1, 4)
    }


    private fun selectPdf2() {
        val pickPhotoDocument2 = Intent(Intent.ACTION_GET_CONTENT)
        pickPhotoDocument2.type = "application/pdf"
        pickPhotoDocument2.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(pickPhotoDocument2, 6)
    }
    private fun requestStoragePermission2(isCamera2: Boolean) {
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    // check if all permissions are granted
                    if (report.areAllPermissionsGranted()) {
                        if (isCamera2) {
                            openCamera2()
                        } else {
                            // openGallery2()
                            selectPdf2()
                        }
                    }
                    // check for permanent denial of any permission
                    if (report.isAnyPermissionPermanentlyDenied) {
                        // show alert dialog navigating to Settings
                        showSettingsDialog()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token!!.continuePermissionRequest()
                }
            })
            .withErrorListener { error ->

            }
            .onSameThread()
            .check()
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile1(): File {
        val timeStamp1: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir1: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp1}_", /* prefix */
            ".jpg", /* suffix */
            storageDir1 /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPathDoc1 = absolutePath
        }
    }


    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile2(): File {
        val timeStamp2: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir2: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp2}_", /* prefix */
            ".jpg", /* suffix */
            storageDir2 /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPathDoc2 = absolutePath
        }
    }
    private fun openCamera1() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {

                val docFile1: File? = try {
                    createImageFile1()
                } catch (ex: IOException) {

                    null
                }

                docFile1?.also {
                    val photoURI1: Uri =
                        FileProvider.getUriForFile(
                            this,
                            "com.callastro.myUniquefileprovider",
                            it
                        )
                    mPhotoFile1 = docFile1
                    photoURIDoc1Camera = photoURI1
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI1)
                    startActivityForResult(takePictureIntent, pickDocument1Camera)
                }
            }
        }

    }
    private fun openCamera2() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {

                val docFile2: File? = try {
                    createImageFile2()
                } catch (ex: IOException) {

                    null
                }

                docFile2?.also {
                    val photoURI2: Uri =
                        FileProvider.getUriForFile(
                            this,
                            "com.callastro.myUniquefileprovider",
                            it
                        )
                    mPhotoFile2 = docFile2
                    photoURIDoc2Camera = photoURI2
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI2)
                    startActivityForResult(takePictureIntent, pickDocument2Camera)
                }
            }
        }

    }
    @SuppressLint("Range")
    @Nullable
    private fun getFileDisplayName(uri: Uri): String? {
        var displayName: String? = null
        contentResolver
            .query(uri, null, null, null, null, null).use { cursor ->
                if (cursor != null && cursor.moveToFirst()) {
                    displayName =
                        cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            }
        return displayName
    }


    private fun selectImage() {
        val options =
            arrayOf<CharSequence>("Take Photo", "Choose From Gallery", "Cancel")
        val pm: PackageManager = packageManager
        val builder =
            android.app.AlertDialog.Builder(this, R.style.AlertDialogTheme)
        builder.setTitle("Select Option")
        builder.setItems(
            options
        ) { dialog: DialogInterface, item: Int ->
            if (options[item] == "Take Photo") {
                dialog.dismiss()
                requestStoragePermission(true)
            } else if (options[item] == "Choose From Gallery") {
                dialog.dismiss()
                requestStoragePermission(false)
            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImageCamera) {
            binding.ivPict.setImageURI(photoURICamera)
            val file = File(currentPhotoPath)
            val requestFile = RequestBody.create("image/jpg".toMediaTypeOrNull(), file)

            userimage = MultipartBody.Part.createFormData("profile", file.name, requestFile)
        } else if (requestCode == pickImageGallery && data != null) {
            val selectedImage = data.data
            try {
                binding.ivPict.setImageURI(selectedImage)
                val file = File(getPath(selectedImage!!))
                val requestFile = RequestBody.create("image/jpg".toMediaTypeOrNull(), file)
                userimage = MultipartBody.Part.createFormData("profile", file.name, requestFile)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }else if (requestCode == pickDocument1Camera && resultCode == RESULT_OK) {
            binding.ivDoc1.setImageURI(photoURIDoc1Camera)
            binding.ivDoc1Def.visibility = View.GONE
            binding.ivDoc1.visibility = View.VISIBLE
            binding.ivDoc1Remove.visibility = View.VISIBLE
            binding.ivDoc1Remove.setOnClickListener {
                binding.ivDoc1.setImageDrawable(null)
                userdocument1image = MultipartBody.Part.createFormData("document1", "")
                binding.ivDoc1Remove.visibility = View.GONE
                binding.ivDoc1Done.visibility = View.GONE
                binding.tvPdf1Name.visibility = View.GONE

                binding.ivDoc1Def.visibility = View.VISIBLE
                binding.ivDoc1.visibility = View.GONE
            }

            val fileDoc1 = File(currentPhotoPathDoc1)
            val requestFile1 = RequestBody.create("document1".toMediaTypeOrNull(), fileDoc1)
            userdocument1image = MultipartBody.Part.createFormData("document1", fileDoc1.name, requestFile1)
            flag1 = true
        } else if (requestCode == pickDocument1Gallery && data != null) {
            val selectedImageDoc1 = data.data
            try {
                binding.ivDoc1.setImageURI(selectedImageDoc1)
                binding.ivDoc1Def.visibility = View.GONE
                binding.ivDoc1.visibility = View.GONE
                binding.ivDoc1Done.visibility = View.VISIBLE
                binding.tvPdf1Name.visibility = View.VISIBLE
                binding.ivDoc1Remove.visibility = View.VISIBLE
                binding.ivDoc1Remove.setOnClickListener {
                    binding.ivDoc1.setImageDrawable(null)
                    userdocument1image = MultipartBody.Part.createFormData("document1", "")
                    binding.ivDoc1Remove.visibility = View.GONE
                    binding.ivDoc1Done.visibility = View.GONE
                    binding.tvPdf1Name.visibility = View.GONE

                    binding.ivDoc1Def.visibility = View.VISIBLE
                    binding.ivDoc1.visibility = View.GONE
                    Log.d(ContentValues.TAG, "onActivityResult__doc11: "+userdocument1image)
                }

                val fileUris = data.data
//            Glide.with(this).load(R.drawable.ic_baseline_picture_as_pdf_24).into(binding.ivPict)
                var path = writeFileContent(fileUris!!)
                var fileSelected = File(path)
                binding.tvPdf1Name.text = fileSelected.name
                val requestFile: RequestBody = fileSelected
                    .asRequestBody(/* "multipart/form-data".toMediaTypeOrNull()*/"multipart/form-data".toMediaTypeOrNull())
                userdocument1image = MultipartBody.Part.createFormData("document1", fileSelected.name, requestFile)
                flag1 = true
//                val fileDoc1 = File(getPath(selectedImageDoc1!!))
//                val requestFile1 = RequestBody.create("document1".toMediaTypeOrNull(), fileDoc1)
//                userdocument1image = MultipartBody.Part.createFormData("document1", fileDoc1.name, requestFile1)
//                flag1 = true
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        if (requestCode == pickDocument2Camera && resultCode == RESULT_OK) {
            binding.ivDoc2.setImageURI(photoURIDoc2Camera)
            binding.ivDoc2Def.visibility = View.GONE
            binding.ivDoc2.visibility = View.VISIBLE
            //   binding.ivDoc2Done.visibility = View.VISIBLE
            binding.ivDoc2Remove.visibility = View.VISIBLE
            binding.ivDoc2Remove.setOnClickListener {
                binding.ivDoc2.setImageDrawable(null)
                userdocument2image = MultipartBody.Part.createFormData("document2", "")
                binding.ivDoc2Remove.visibility = View.GONE
                binding.ivDoc2Done.visibility = View.GONE
                binding.tvPdf2Name.visibility = View.GONE
                binding.ivDoc2Def.visibility = View.VISIBLE
                binding.ivDoc2.visibility = View.GONE
                Log.d(ContentValues.TAG, "onActivityResult__cam12: "+userdocument2image)
            }

            val fileDoc2 = File(currentPhotoPathDoc2)
            val requestFile2 = RequestBody.create("document2".toMediaTypeOrNull(), fileDoc2)
            userdocument2image = MultipartBody.Part.createFormData("document2", fileDoc2.name, requestFile2)
            flag2 = true
        } else if (requestCode == pickDocument2Gallery && data != null) {
            val selectedImageDoc2 = data.data
            try {
                binding.ivDoc2.setImageURI(selectedImageDoc2)
                binding.ivDoc2Def.visibility = View.GONE
                binding.ivDoc2.visibility = View.GONE
                binding.ivDoc2Done.visibility = View.VISIBLE
                binding.tvPdf2Name.visibility = View.VISIBLE
                binding.ivDoc2Remove.visibility = View.VISIBLE
                binding.ivDoc2Remove.setOnClickListener {
                    binding.ivDoc2.setImageDrawable(null)
                    userdocument2image = MultipartBody.Part.createFormData("document2", "")
                    binding.ivDoc2Remove.visibility = View.GONE
                    binding.ivDoc2Done.visibility = View.GONE
                    binding.tvPdf2Name.visibility = View.GONE
                    binding.ivDoc2Def.visibility = View.VISIBLE
                    binding.ivDoc2.visibility = View.GONE
                    Log.d(ContentValues.TAG, "onActivityResult__cam12: $userdocument2image")
                }

                val fileUris2 = data.data
//            Glide.with(this).load(R.drawable.ic_baseline_picture_as_pdf_24).into(binding.ivPict)
                var path2 = writeFileContent2(fileUris2!!)
                var fileSelected2 = File(path2)
                binding.tvPdf2Name.text = fileSelected2.name
                val requestFile2: RequestBody = fileSelected2
                    .asRequestBody(/* "multipart/form-data".toMediaTypeOrNull()*/"multipart/form-data".toMediaTypeOrNull())
                userdocument2image = MultipartBody.Part.createFormData("document2", fileSelected2.name, requestFile2)

                flag2 = true




                /*val fileDoc2 = File(getPath(selectedImageDoc2!!))
                val requestFile2 = RequestBody.create("document2".toMediaTypeOrNull(), fileDoc2)
                userdocument2image = MultipartBody.Part.createFormData("document2", fileDoc2.name, requestFile2)
                flag2 = true*/
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        if (requestCode == AUTOCOMPLETE_PLACE_REQUEST_CODE) {
            when (resultCode) {     //binding.etAmount.text = it.data!!.rate
                Activity.RESULT_OK -> {
                    val place = Autocomplete.getPlaceFromIntent(data!!)
                    Log.i("TAG", "Place: " + place.name + ", " + place.id)
                    latLng = place.latLng
                    pickupLongitude = latLng!!.longitude
                    pickupLatitude = latLng!!.latitude

//                    SavedPrefManager.saveStringPreferences(
//                        this,SavedPrefManager.PICLAT,
//                        pickupLatitude.toString()
//                    )
//                    SavedPrefManager.saveStringPreferences(
//                        this,SavedPrefManager.PICLNG,
//                        pickupLongitude.toString()
//                    )

                    binding.etAddress.text = place.address
                    sourceLatLong = LatLng(pickupLatitude, pickupLongitude)
                    Log.e("@@pickupLatitude", pickupLatitude.toString())
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    val status = Autocomplete.getStatusFromIntent(data!!)
                    Log.i("TAG", status.statusMessage!!)
                }
                Activity.RESULT_CANCELED -> {
                }
            }
            return
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onExpertiseItemDeleteClicked(getAddedExpertiseData: GetAddedExpertiseData) {
        if (CommonUtils.isInternetAvailable(this@EditProfile)) {
            viewModel.delete_expertiseApi("Bearer " + userPref.getToken(), getAddedExpertiseData.id.toString())
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this@EditProfile,"Please check internet connection.")
        }

        Log.d(ContentValues.TAG, "errResponse: "+"12")
        getexadapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onSkillsItemDeleteClicked(getAddedSkillsData: GetAddedSkillsData) {
        if (CommonUtils.isInternetAvailable(this@EditProfile)) {
            viewModel.delete_skillsApi("Bearer " + userPref.getToken(), getAddedSkillsData.id.toString())
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this@EditProfile,"Please check internet connection.")
        }

        Log.d(ContentValues.TAG, "errResponse: "+"12")
        getskillsadapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onLanguageItemDeleteClicked(getAddedLanguageData: GetAddedLanguageData) {
        if (CommonUtils.isInternetAvailable(this@EditProfile)) {
            viewModel.delete_languageApi("Bearer " + userPref.getToken(), getAddedLanguageData.id.toString())
        } else {
            Log.d("TAG", "onCreate: " + "else part")
            toast(this@EditProfile,"Please check internet connection.")
        }

        Log.d(ContentValues.TAG, "errResponse: "+"12")
        getlanguageadapter.notifyDataSetChanged()
    }
    @Throws(IOException::class)
    private fun writeFileContent(uri: Uri): String? {
        val selectedFileInputStream = contentResolver.openInputStream(uri)
        if (selectedFileInputStream != null) {

            val mediaDir = externalMediaDirs.firstOrNull()?.let {
                File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
            }

            val certCacheDir = File(mediaDir, FILE_BROWSER_CACHE_DIR)
            var isCertCacheDirExists = certCacheDir.exists()
            if (!isCertCacheDirExists) {
                isCertCacheDirExists = certCacheDir.mkdirs()
            }
            if (isCertCacheDirExists) {
                val fileName: String? = getFileDisplayName(uri)
                fileName?.replace("[^a-zA-Z0-9]", " ")
                val filePath = certCacheDir.absolutePath.toString() + "/" + fileName
                val selectedFileOutPutStream: OutputStream = FileOutputStream(filePath)
                val buffer = ByteArray(1024)
                var length: Int
                while (selectedFileInputStream.read(buffer).also { length = it } > 0) {
                    selectedFileOutPutStream.write(buffer, 0, length)
                }
                selectedFileOutPutStream.flush()
                selectedFileOutPutStream.close()
                return filePath
            }
            selectedFileInputStream.close()
        }
        return null
    }
    private fun filterData(searchText: String, scpflag: String) {
        var filteredStateList: ArrayList<StateListData> = ArrayList()
        var filteredCityList: ArrayList<CityListData> = ArrayList()
        var filteredPincodeList: ArrayList<PincodeListData> = ArrayList()

        if (scpflag.equals("State")) {
            for (item in statelistData) {
                try {
                    if (item.stateName!!.toLowerCase().contains(searchText.toLowerCase())) {
                        filteredStateList.add(item)
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }

        }  else if (scpflag.equals("City")) {
            for (item in citylistData) {
                try {
                    if (item.cityName!!.toLowerCase().contains(searchText.toLowerCase())) {
                        filteredCityList.add(item)
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }
        else if (scpflag.equals("Pincode")) {
            for (item in pinCodelistData) {
                try {
                    if (item.pincode!!.toLowerCase().contains(searchText.toLowerCase())) {
                        filteredPincodeList.add(item)
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }

        try {
            if (scpflag.equals("State")) {
                stateAdapter?.filterList(filteredStateList)
            }
            else if (scpflag.equals("City")) {
                cityAdapter?.filterList(filteredCityList)
            }
            else if (scpflag.equals("Pincode")) {
                pincodeAdapter?.filterList(filteredPincodeList)
            }

        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }


//    override fun getCountry(name: String, scpflag: String, id: Int) {
//        if (scpflag.equals("State")) {
//            userPref.setStateId(id.toString())
//            binding.state.text = name
//            strStateId = id.toString()
//            binding.city.text="Select"
//            dialog?.dismiss()
//        } else if (scpflag.equals("City")) {
//            userPref.setCityId(id.toString())
//            binding.city.text = name
//            strCityId = id.toString()
//            binding.pincode.text="Select"
//
//            dialog?.dismiss()
//        }
//        else if (scpflag.equals("Pincode")) {
//            binding.pincode.text = name
//            strPincodeId = id.toString()
//            dialog?.dismiss()
//        }
//    }
    @Throws(IOException::class)
    private fun writeFileContent2(uri2: Uri): String? {
        val selectedFileInputStream = contentResolver.openInputStream(uri2)
        if (selectedFileInputStream != null) {

            val mediaDir = externalMediaDirs.firstOrNull()?.let {
                File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
            }

            val certCacheDir = File(mediaDir, FILE_BROWSER_CACHE_DIR)
            var isCertCacheDirExists = certCacheDir.exists()
            if (!isCertCacheDirExists) {
                isCertCacheDirExists = certCacheDir.mkdirs()
            }
            if (isCertCacheDirExists) {
                val fileName2: String? = getFileDisplayName(uri2)
                fileName2?.replace("[^a-zA-Z0-9]", " ")
                val filePath2 = certCacheDir.absolutePath.toString() + "/" + fileName2
                val selectedFileOutPutStream: OutputStream = FileOutputStream(filePath2)
                val buffer = ByteArray(1024)
                var length: Int
                while (selectedFileInputStream.read(buffer).also { length = it } > 0) {
                    selectedFileOutPutStream.write(buffer, 0, length)
                }
                selectedFileOutPutStream.flush()
                selectedFileOutPutStream.close()
                return filePath2
            }
            selectedFileInputStream.close()
        }
        return null
    }
    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->

            takePictureIntent.resolveActivity(this.packageManager)?.also {

                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }

                photoFile?.also {
                    val photoURI: Uri =
                        FileProvider.getUriForFile(
                            this, 
                            "com.callastro.myUniquefileprovider",
                            it
                        )
                    mPhotoFile = photoFile
                    photoURICamera = photoURI
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, pickImageCamera)
                }
            }
        }

    }

    private fun getPath(uri: Uri): String {
        val data = arrayOf(MediaStore.Images.Media.DATA)
        val loader = androidx.loader.content.CursorLoader(this, uri, data, null, null, null)
        val cursor = loader.loadInBackground()
        val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        Log.d("image path", cursor.getString(column_index))
        return cursor.getString(column_index)
    }

    private fun requestStoragePermission(isCamera: Boolean) {
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    // check if all permissions are granted
                    if (report.areAllPermissionsGranted()) {
                        if (isCamera) {
                            openCamera()
                        } else {
                            openGallery()
                        }
                    }
                    // check for permanent denial of any permission
                    if (report.isAnyPermissionPermanentlyDenied) {
                        // show alert dialog navigating to Settings
                        showSettingsDialog()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?, token: PermissionToken?) {
                    token!!.continuePermissionRequest()
                }
            })
            .withErrorListener {
            }
            .onSameThread()
            .check()
    }

    private fun openGallery() {
        val pickPhoto =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickPhoto, pickImageGallery)
    }

    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Need Permissions")
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton(
            "GOTO SETTINGS"
        ) { dialog: DialogInterface, which: Int ->
            openSettings()
            dialog.cancel()
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog: DialogInterface, which: Int -> dialog.cancel() }
        builder.show()
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", "com.maxtra.astrorahiastrologer", null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }

    fun validate() : Boolean {

        if (binding.etFullName.text.trim().isEmpty()) {
            binding.etFullName.error = "Please enter your name"
            binding.etFullName.requestFocus()
            return false
        }
        else if (binding.etPhone.text.isNullOrEmpty()){
            snackbar("Please enter mobile number.")
            binding.etPhone.requestFocus()
            return false
        }else if (binding.etPhone.text.length < 10){
            snackbar("Please enter valid number.")
            binding.etPhone.requestFocus()
            return false
        }else if (binding.callingcharge.text.isNullOrEmpty()){
            snackbar("Please enter calling charge.")
            binding.etPhone.requestFocus()
            return false
        }
       /*else if (binding.etEmailid.text.trim().isEmpty()) {
            binding.etEmailid.error = "Please enter valid Email"
            binding.etEmailid.requestFocus()
            return false
        }*/
        else if (binding.etEmailid.text.toString() == "" || !binding.etEmailid.text.toString().trim().matches(emailPattern.toRegex())){
            Toast.makeText(this, "Please enter your valid email address.", Toast.LENGTH_SHORT).show()
            binding.etEmailid.requestFocus()
            return false
        }

        return true
    }

    override fun onRatingItemClicked(getAstroRatingReviewReviewRatings: GetAstroRatingReviewReviewRatings) {

    }

}