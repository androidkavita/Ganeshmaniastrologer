package com.callastro.model

import com.google.gson.annotations.SerializedName


data class ChatHistoryListResponse (

    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<ChatHistoryListData> = arrayListOf()

)

data class ChatHistoryListData (

    @SerializedName("id"        ) var id       : Int?    = null,
    @SerializedName("order_id"  ) var orderId  : Int?    = null,
    @SerializedName("user_name" ) var userName : String? = null,
    @SerializedName("userid"    ) var userid   : String? = null,
    @SerializedName("date"      ) var date     : String? = null,
    @SerializedName("time"      ) var time     : String? = null,
    @SerializedName("duration"  ) var duration : String? = null,
    @SerializedName("amount"  ) var amount : String? = null,
    @SerializedName("rate"      ) var rate     : String? = null,
    @SerializedName("feedback"  ) var feedback : String? = null,
    @SerializedName("rating"    ) var rating   : Float? = null,
    @SerializedName("refund_money"    ) var refund_status   : Int? = null,
    @SerializedName("status"    ) var status   : String? = null,
    @SerializedName("nationality"    ) var nationality   : String? = null,
    @SerializedName("session_type"    ) var session_type   : String? = null

)