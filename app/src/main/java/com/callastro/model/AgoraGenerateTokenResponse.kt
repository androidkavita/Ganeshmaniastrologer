package com.callastro.model

import com.google.gson.annotations.SerializedName

data class AgoraGenerateTokenResponse(
    @SerializedName("status") var status: Int? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("data") var data: AgoraGenerateTokenData? = AgoraGenerateTokenData()
)

data class AgoraGenerateTokenData(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("user_id") var userId: Int? = null,
    @SerializedName("other_user") var otherUser: Int? = null,
    @SerializedName("agora_token") var agoraToken: String? = null,
    @SerializedName("expire_time") var expireTime: String? = null,
    @SerializedName("channel_name") var channelName: String? = null,
    @SerializedName("app_id") var appId: String? = null,
    @SerializedName("from_name") var from_name: String? = null,
    @SerializedName("from_profile") var from_profile: String? = null,
    @SerializedName("caller_id") var caller_id: String? = null,
    @SerializedName("to_name") var to_name: String? = null,
    @SerializedName("to_profile") var to_profile: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null
)