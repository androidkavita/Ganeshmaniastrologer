package com.callastro.model

import com.google.gson.annotations.SerializedName

data class call_ring_status_save_Response(
    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : call_ring_status_save_ResponseData?   = call_ring_status_save_ResponseData()
)

data class call_ring_status_save_ResponseData(
    @SerializedName("status" ) var status : Int? = null
)