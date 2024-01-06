package com.callastro.model

import com.google.gson.annotations.SerializedName

data class GetAddedLanguageResponse (

    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<GetAddedLanguageData> = arrayListOf()

)

data class GetAddedLanguageData (

    @SerializedName("id"            ) var id           : Int?    = null,
    @SerializedName("language_name" ) var languageName : String? = null

)