package com.callastro.model

import com.google.gson.annotations.SerializedName


data class NotificationListResponse (

    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<NotificationListData> = arrayListOf()

)
data class NotificationListData (

    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("is_read"    ) var isRead    : Int?    = null,
    @SerializedName("created_at" ) var createdAt : String? = null,
    @SerializedName("time"       ) var time      : String? = null,
    @SerializedName("title"      ) var title     : String? = null,

)