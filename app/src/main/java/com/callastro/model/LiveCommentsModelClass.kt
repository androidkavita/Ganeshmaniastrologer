package com.callastro.model

import com.google.gson.annotations.SerializedName

data class LiveCommentsModelClass(
    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<LiveCommentsModelClassData> = arrayListOf()
)
data class LiveCommentsModelClassData(
    @SerializedName("id"           ) var id          : Int?    = null,
    @SerializedName("user_id"      ) var userId      : Int?    = null,
    @SerializedName("astro_id"     ) var astroId     : Int?    = null,
    @SerializedName("message"      ) var message     : String? = null,
    @SerializedName("channel_name" ) var channelName : String? = null,
    @SerializedName("type"         ) var type        : Int?    = null,
    @SerializedName("status"       ) var status      : Int?    = null,
    @SerializedName("created_at"   ) var createdAt   : String? = null,
    @SerializedName("updated_at"   ) var updatedAt   : String? = null,
    @SerializedName("gift_id"      ) var giftId      : String? = null,
    @SerializedName("name"         ) var name        : String? = null,
    @SerializedName("profile"      ) var profile     : String? = null,
    @SerializedName("image"        ) var image       : String? = null
)