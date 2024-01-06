package com.callastro.model

import com.google.gson.annotations.SerializedName


data class ChatAgoraResponse (

    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : ChatAgoraData?   = ChatAgoraData()

)

/*data class ChatAgoraSubData (

    @SerializedName("22" ) var 22 : String? = null

)*/
//  //  @SerializedName("data"            ) var data            : ChatAgoraSubData?   = ChatAgoraSubData(),
data class ChatAgoraData (

    @SerializedName("path"            ) var path            : String? = null,
    @SerializedName("uri"             ) var uri             : String? = null,
    @SerializedName("timestamp"       ) var timestamp       : Long?    = null,
    @SerializedName("organization"    ) var organization    : String? = null,
    @SerializedName("application"     ) var application     : String? = null,
    @SerializedName("caller_id"     ) var caller_id     : String? = null,
    @SerializedName("action"          ) var action          : String? = null,
    @SerializedName("duration"        ) var duration        : Int?    = null,
    @SerializedName("applicationName" ) var applicationName : String? = null

)