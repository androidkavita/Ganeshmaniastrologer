package com.callastro.model

import com.google.gson.annotations.SerializedName

data class SettingDetailsGetResponse (

    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : SettingDetailsGetData?   = SettingDetailsGetData()

)

data class SettingDetailsGetData (

    @SerializedName("id"            ) var id          : Int? = null,
    @SerializedName("is_chat"       ) var isChat      : Int? = null,
    @SerializedName("is_audio_call" ) var isAudioCall : Int? = null,
    @SerializedName("is_video_call" ) var isVideoCall : Int? = null

)