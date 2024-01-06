package com.callastro.model

import com.google.gson.annotations.SerializedName


data class CityListResponse (

    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<CityListData> = arrayListOf()

)

data class CityListData (

    @SerializedName("id"        ) var id       : Int?    = null,
    @SerializedName("state_id"  ) var stateId  : Int?    = null,
    @SerializedName("city_name" ) var cityName : String? = null

)