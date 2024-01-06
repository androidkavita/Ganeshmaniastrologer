package com.callastro.model

import com.google.gson.annotations.SerializedName

data class CallendbyuserResponse(
    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : CallendbyuserResponseData?   = CallendbyuserResponseData()
)
data class CallendbyuserResponseData(
    @SerializedName("call_end_status" ) var callEndStatus : Int? = null
)
