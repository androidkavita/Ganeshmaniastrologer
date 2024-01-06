package com.callastro.model

import com.google.gson.annotations.SerializedName


data class ChatListMessageResponse (

    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<ChatListMessageData> = arrayListOf()

)

data class ChatListMessageData (

    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("user_id"    ) var userId    : Int?    = null,
    @SerializedName("to_userid"  ) var toUserid  : Int?    = null,
    @SerializedName("from_id"    ) var fromId    : Int?    = null,
    @SerializedName("to_id"      ) var toId      : Int?    = null,
    @SerializedName("message"    ) var message   : String? = null,
    @SerializedName("type"       ) var type      : String? = null,
    @SerializedName("token"      ) var token     : String? = null,
    @SerializedName("created_at" ) var createdAt : String? = null,
    @SerializedName("updated"    ) var updated   : String? = null

)

