package com.callastro.model

import com.google.gson.annotations.SerializedName

data class AboutUsResponse(

    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : AboutUsResponseData?   = AboutUsResponseData()
)
data class AboutUsResponseData(
    @SerializedName("details" ) var details : String? = null
)