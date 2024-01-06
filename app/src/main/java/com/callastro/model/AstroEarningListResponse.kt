package com.callastro.model

import com.google.gson.annotations.SerializedName

data class AstroEarningListResponse(
    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<AstroEarningListResponseData> = arrayListOf()
)
data class AstroEarningListResponseData(
    @SerializedName("id"           ) var id          : Int?    = null,
    @SerializedName("type"         ) var type        : Int?    = null,
    @SerializedName("user_id"      ) var userId      : Int?    = null,
    @SerializedName("amount"       ) var amount      : String? = null,
    @SerializedName("created_at"   ) var createdAt   : String? = null,
    @SerializedName("created_time" ) var createdTime : String? = null,
    @SerializedName("user_name"    ) var userName    : String? = null,
    @SerializedName("content"      ) var content     : String? = null
)
