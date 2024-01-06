package com.callastro.model

import com.google.gson.annotations.SerializedName

data class GoLiveResponse(
    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : GoLiveResponseData?   = GoLiveResponseData()
)

data class GoLiveResponseData(
    @SerializedName("id"           ) var id          : Int?    = null,
    @SerializedName("user_id"      ) var userId      : Int?    = null,
    @SerializedName("topic"        ) var topic       : String? = null,
    @SerializedName("agora_token"  ) var agoraToken  : String? = null,
    @SerializedName("channel_name" ) var channelName : String? = null,
    @SerializedName("appId"        ) var appId       : String? = null,
    @SerializedName("expire_time"  ) var expireTime  : String? = null,
    @SerializedName("created_at"   ) var createdAt   : String? = null,
    @SerializedName("updated_at"   ) var updatedAt   : String? = null
)
