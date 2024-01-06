package com.callastro.model

import com.google.gson.annotations.SerializedName

data class CallHistoryListResponse (

    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<CallHistoryListData> = arrayListOf()

)
data class CallHistoryListData (

    @SerializedName("id"        ) var id       : Int?    = null,
    @SerializedName("order_id"  ) var orderId  : Int?    = null,
    @SerializedName("user_name" ) var userName : String? = null,
    @SerializedName("date"      ) var date     : String? = null,
    @SerializedName("userid"    ) var userid     : String? = null,
    @SerializedName("time"      ) var time     : String? = null,
    @SerializedName("duration"  ) var duration : String? = null,
    @SerializedName("rate"      ) var rate     : String? = null,
    @SerializedName("amount"      ) var amount     : String? = null,
    @SerializedName("feedback"  ) var feedback : String? = null,
    @SerializedName("refund_money"    ) var refund_status   : Int? = null,
    @SerializedName("rating"    ) var rating   : Double? = null,
    @SerializedName("status"    ) var status   : String? = null,
    @SerializedName("nationality"    ) var nationality   : String? = null,
    @SerializedName("session_type"    ) var session_type   : String? = null

)