package com.callastro.model

import com.google.gson.annotations.SerializedName

data class GetAddedExpertiseResponse (

    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<GetAddedExpertiseData> = arrayListOf()

)

data class GetAddedExpertiseData (

    @SerializedName("id"             ) var id            : Int?    = null,
    @SerializedName("expertise_name" ) var expertiseName : String? = null

)