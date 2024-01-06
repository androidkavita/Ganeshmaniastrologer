package com.callastro.model

import com.google.gson.annotations.SerializedName


data class ScheduleCalendarDeleteResponse (

    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("date"    ) var date    : String? = null

)