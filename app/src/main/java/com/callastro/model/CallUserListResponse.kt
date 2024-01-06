package com.callastro.model

import com.google.gson.annotations.SerializedName

data class CallUserListResponse (

    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<CallUserListData> = arrayListOf()

)

data class CallUserListData (

    @SerializedName("id"               ) var id             : Int?    = null,
    @SerializedName("user_id"          ) var userId         : Int?    = null,
    @SerializedName("name"             ) var name           : String? = null,
    @SerializedName("profile"          ) var profile        : String? = null,
    @SerializedName("is_live"          ) var isLive         : Int?    = null,
    @SerializedName("last_online_time" ) var lastOnlineTime : String? = null

)