package com.callastro.model

import com.google.gson.annotations.SerializedName

data class CalenderList(
    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("date"    ) var date    : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<CalenderListData> = arrayListOf()
)
data class CalenderListData(
    @SerializedName("id"        ) var id       : Int?    = null,
    @SerializedName("date"      ) var date     : String? = null,
    @SerializedName("from_time" ) var fromTime : String? = null,
    @SerializedName("to_time"   ) var toTime   : String? = null
)