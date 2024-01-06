package com.callastro.model

import com.google.gson.annotations.SerializedName

data class AddLanguageItemResponse (

    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<AddLanguageItemData> = arrayListOf()

)

data class AddLanguageItemData (

    @SerializedName("id"            ) var id           : Int?    = null,
    @SerializedName("language_name" ) var languageName : String? = null

)