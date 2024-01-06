package com.callastro.model

import com.google.gson.annotations.SerializedName


data class ExperienceListResponse (

    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<ExperienceListData> = arrayListOf()

)


data class ExperienceListData (

    @SerializedName("id"         ) var id         : Int?    = null,
    @SerializedName("experience" ) var experience : String? = null

)