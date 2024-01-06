package com.callastro.model

import com.google.gson.annotations.SerializedName

data class StateListResponse (

    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<StateListData> = arrayListOf()

)

data class StateListData (

    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("state_name" ) var stateName : String? = null

)