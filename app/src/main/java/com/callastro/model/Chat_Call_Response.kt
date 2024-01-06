package com.callastro.model

import com.google.gson.annotations.SerializedName

data class Chat_Call_Response(
    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<Chat_Call_ResponseData> = arrayListOf()
)
data class Chat_Call_ResponseData(
    @SerializedName("id"             ) var id            : Int?    = null,
    @SerializedName("type"           ) var type          : Int?    = null,
    @SerializedName("user_id"        ) var userId        : Int?    = null,
    @SerializedName("astro_id"       ) var astroId       : Int?    = null,
    @SerializedName("request_status" ) var requestStatus : Int?    = null,
    @SerializedName("created_at"     ) var createdAt     : String? = null,
    @SerializedName("updated_at"     ) var updatedAt     : String? = null,
    @SerializedName("user_name"      ) var userName      : String? = null,
    @SerializedName("unique_id"      ) var unique_id      : String? = null,
    @SerializedName("language"       ) var language      : String? = null,
    @SerializedName("profile"        ) var profile       : String? = null,
    @SerializedName("mobile"        ) var mobile       : String? = null
)