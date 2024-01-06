package com.callastro.model

import com.google.gson.annotations.SerializedName

data class GetCustomerSupportChat(
    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<GetCustomerSupportChatData> = arrayListOf()
)

data class GetCustomerSupportChatData(
    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("user_id"    ) var userId    : Int?    = null,
    @SerializedName("message"    ) var message   : String? = null,
    @SerializedName("is_read"    ) var isRead    : Int?    = null,
    @SerializedName("type"       ) var type      : Int?    = null,
    @SerializedName("created_at" ) var createdAt : String? = null,
    @SerializedName("updated_at" ) var updatedAt : String? = null,
    @SerializedName("time"       ) var time      : String? = null,
    @SerializedName("timetwo"    ) var timetwo   : String? = null,
    @SerializedName("datetwo"    ) var datetwo   : String? = null
)
