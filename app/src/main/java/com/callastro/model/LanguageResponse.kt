package com.callastro.model

import com.google.gson.annotations.SerializedName

data class LanguageResponse(
    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<LanguageResponseData> = arrayListOf()
)

data class LanguageResponseData(
    @SerializedName("id"       ) var id       : Int?    = null,
    @SerializedName("language" ) var language : String? = null
)