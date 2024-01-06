package com.callastro.model

import com.google.gson.annotations.SerializedName


data class ChatRequestCancelResponse (

    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : ChatRequestCancelResponseData = ChatRequestCancelResponseData()

)
data class ChatRequestCancelResponseData (
    @SerializedName("unique_id" ) var unique_id : String? = null,
        )