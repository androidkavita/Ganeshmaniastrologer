package com.callastro.model

import com.google.gson.annotations.SerializedName

data class ChatCallCancelReasonResponse (

    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<ChatCallCancelReasonData> = arrayListOf()

)


data class ChatCallCancelReasonData (

    @SerializedName("id"     ) var id     : Int?    = null,
    @SerializedName("reason" ) var reason : String? = null

)