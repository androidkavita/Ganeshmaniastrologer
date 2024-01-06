package com.callastro.model

import com.google.gson.annotations.SerializedName

data class CheckChatEndResponse(
    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : CheckChatEndResponseData = CheckChatEndResponseData()
)
data class CheckChatEndResponseData(
    @SerializedName("is_chat_end"  ) var is_chat_end  : Int? = null,
    @SerializedName("user_type"  ) var user_type  : Int? = null,
)
