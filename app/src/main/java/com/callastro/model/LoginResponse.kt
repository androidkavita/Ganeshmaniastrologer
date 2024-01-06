package com.callastro.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("approval"  ) var approval  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : LoginData?   = LoginData()
)

data class LoginData(
    @SerializedName("id"        ) var id       : Int?    = null,
    @SerializedName("mobile_no" ) var mobileNo : String? = null,
    @SerializedName("otp"       ) var otp      : String? = null,
    @SerializedName("is_new"    ) var is_new      : String? = null
)