package com.callastro.model

import com.google.gson.annotations.SerializedName

data class CallRingResponse(
    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : CallRingResponseData?   = CallRingResponseData()
)
data class CallRingResponseData(
    @SerializedName("ring_status" ) var ringStatus : Int? = null
)