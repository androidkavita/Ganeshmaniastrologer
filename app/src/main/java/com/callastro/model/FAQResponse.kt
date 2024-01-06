package com.callastro.model

import com.google.gson.annotations.SerializedName

data class FAQResponse(
    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<FAQResponseData> = arrayListOf()
)
data class FAQResponseData(
    @SerializedName("type"      ) var type      : Int?    = null,
    @SerializedName("questions" ) var questions : String? = null,
    @SerializedName("answers"   ) var answers   : String? = null
)