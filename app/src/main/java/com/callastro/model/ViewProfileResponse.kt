package com.callastro.model

import com.google.gson.annotations.SerializedName

data class ViewProfileResponse (
    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : ViewProfileResponseData?   = ViewProfileResponseData()
        )

data class ViewProfileResponseData (
    @SerializedName("id"                ) var id              : Int?    = null,
    @SerializedName("type"              ) var type            : Int?    = null,
    @SerializedName("name"              ) var name            : String? = null,
    @SerializedName("email"             ) var email           : String? = null,
    @SerializedName("mobile_no"         ) var mobileNo        : String? = null,
    @SerializedName("company_name"      ) var companyName     : String? = null,
    @SerializedName("calling_charg"      ) var calling_charg     : String? = null,
    @SerializedName("profile"           ) var profile         : String? = null,
    @SerializedName("wallet"            ) var wallet          : String? = null,
    @SerializedName("gender"            ) var gender          : Int?    = null,
    @SerializedName("dob"               ) var dob             : String? = null,
    @SerializedName("address"           ) var address         : String? = null,
    @SerializedName("state"             ) var state           : Int?    = null,
    @SerializedName("city"              ) var city            : Int?    = null,
    @SerializedName("pincode"           ) var pincode         : String? = null,
    @SerializedName("document1"         ) var document1       : String? = null,
    @SerializedName("document2"         ) var document2       : String? = null,
    @SerializedName("otp"               ) var otp             : String? = null,
    @SerializedName("about_us"          ) var aboutUs         : String? = null,
    @SerializedName("experence_id"      ) var experenceId     : Int?    = null,
    @SerializedName("birth_time"        ) var birthTime       : String? = null,
    @SerializedName("birth_place"       ) var birthPlace      : String? = null,
    @SerializedName("is_chat"           ) var isChat          : Int?    = null,
    @SerializedName("is_audio_call"     ) var isAudioCall     : Int?    = null,
    @SerializedName("is_video_call"     ) var isVideoCall     : Int?    = null,
    @SerializedName("is_live"           ) var isLive          : Int?    = null,
    @SerializedName("status"            ) var status          : Int?    = null,
    @SerializedName("login_status"      ) var loginStatus     : Int?    = null,
    @SerializedName("is_deleted"        ) var isDeleted       : Int?    = null,
    @SerializedName("email_verified_at" ) var emailVerifiedAt : String? = null,
    @SerializedName("password"          ) var password        : String? = null,
    @SerializedName("api_token"         ) var apiToken        : String? = null,
    @SerializedName("remember_token"    ) var rememberToken   : String? = null,
    @SerializedName("latitude"          ) var latitude        : String? = null,
    @SerializedName("longtitude"        ) var longtitude      : String? = null,
    @SerializedName("fixed_session_30min_charge"        ) var fixed_session_30min_charge      : String? = null,
    @SerializedName("fixed_session_60min_charge"        ) var fixed_session_60min_charge      : String? = null,
//    @SerializedName("longtitude"        ) var longtitude      : String? = null,
    @SerializedName("device_id"         ) var deviceId        : String? = null,
    @SerializedName("device_type"       ) var deviceType      : String? = null,
    @SerializedName("device_name"       ) var deviceName      : String? = null,
    @SerializedName("device_token"      ) var deviceToken     : String? = null,
    @SerializedName("created_at"        ) var createdAt       : String? = null,
    @SerializedName("updated_at"        ) var updatedAt       : String? = null

)