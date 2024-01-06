package com.callastro.model

import com.google.gson.annotations.SerializedName

data class AddSkillsItemResponse (

    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<AddSkillsItemData> = arrayListOf()

)


data class AddSkillsItemData (

    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("skill_name" ) var skillName : String? = null

)