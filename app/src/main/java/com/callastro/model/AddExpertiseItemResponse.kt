package com.callastro.model

import com.google.gson.annotations.SerializedName

data class AddExpertiseItemResponse (

    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<AddExpertiseItemData> = arrayListOf()

)


data class AddExpertiseItemData (

    @SerializedName("id"             ) var id            : Int?    = null,
    @SerializedName("expertise_name" ) var expertiseName : String? = null

)