package com.callastro.model

import com.google.gson.annotations.SerializedName


data class PostProfileUpdateResponse (

    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : PostProfileUpdateData?   = PostProfileUpdateData()

)

data class PostProfileUpdateData (

    @SerializedName("id"      ) var id      : Int?    = null,
    @SerializedName("name"    ) var name    : String? = null,
    @SerializedName("email"   ) var email   : String? = null,
    @SerializedName("profile" ) var profile : String? = null,
    @SerializedName("is_new"  ) var isNew   : Int?    = null

)