package com.callastro.model

import com.google.gson.annotations.SerializedName

data class EmailUs_Response(
    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : EmailUs_ResponseData?   = EmailUs_ResponseData()
)
data class EmailUs_ResponseData(
    @SerializedName("id"    ) var id    : Int?    = null,
    @SerializedName("email" ) var email : String? = null
)
