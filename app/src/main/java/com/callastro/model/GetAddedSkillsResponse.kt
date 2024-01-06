package com.callastro.model

import com.google.gson.annotations.SerializedName

data class GetAddedSkillsResponse (

    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<GetAddedSkillsData> = arrayListOf()

)

data class GetAddedSkillsData (

    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("skill_name" ) var skillName : String? = null

)