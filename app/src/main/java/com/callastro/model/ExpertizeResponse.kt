package com.callastro.model

import com.google.gson.annotations.SerializedName

data class ExpertizeResponse(
    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<ExpertizeResponseData> = arrayListOf()
)
data class ExpertizeResponseData(
    @SerializedName("id"   ) var id   : Int?    = null,
    @SerializedName("name" ) var name : String? = null

)