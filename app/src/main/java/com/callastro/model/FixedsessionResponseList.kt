package com.callastro.model

import com.google.gson.annotations.SerializedName

data class FixedsessionResponseList(
    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<FixedsessionResponseListData> = arrayListOf()
)
data class FixedsessionResponseListData(
    @SerializedName("id"             ) var id            : Int?    = null,
    @SerializedName("user_detail_id" ) var userDetailId  : Int?    = null,
    @SerializedName("type"           ) var type          : Int?    = null,
    @SerializedName("user_id"        ) var userId        : Int?    = null,
    @SerializedName("astro_id"       ) var astroId       : Int?    = null,
    @SerializedName("language_id"    ) var languageId    : Int?    = null,
    @SerializedName("request_status" ) var requestStatus : Int?    = null,
    @SerializedName("reason"         ) var reason        : String? = null,
    @SerializedName("comment"        ) var comment       : String? = null,
    @SerializedName("unique_id"      ) var uniqueId      : String? = null,
    @SerializedName("action_by"      ) var actionBy      : String? = null,
    @SerializedName("created_at"     ) var createdAt     : String? = null,
    @SerializedName("updated_at"     ) var updatedAt     : String? = null,
    @SerializedName("user_name"      ) var userName      : String? = null,
    @SerializedName("mobile"         ) var mobile        : String? = null,
    @SerializedName("language"       ) var language      : String? = null,
    @SerializedName("profile"        ) var profile       : String? = null,
    @SerializedName("fix_session"    ) var fix_session       : String? = null,
    @SerializedName("fixed_session_type"    ) var fix_session_type       : Int? = null,
    @SerializedName("session_type"   ) var sessionType   : Int?    = null
)
