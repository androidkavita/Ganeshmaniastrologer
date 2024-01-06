package com.callastro.model

import com.google.gson.annotations.SerializedName

data class CallChatStatusResponse(
    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : CallChatStatusResponseData?   = CallChatStatusResponseData()
)
data class CallChatStatusResponseData(
    @SerializedName("chat_req" ) var chatReq : Int? = null,
    @SerializedName("call_req" ) var callReq : Int? = null,
    @SerializedName("fix_req" ) var fix_req : Int? = null
)