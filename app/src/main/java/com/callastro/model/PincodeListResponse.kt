package com.callastro.model

import com.google.gson.annotations.SerializedName

data class PincodeListResponse (

    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<PincodeListData> = arrayListOf()

)

data class PincodeListData (

    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("city_id"    ) var cityId    : Int?    = null,
    @SerializedName("pincode"    ) var pincode   : String? = null,
    @SerializedName("created_at" ) var createdAt : String? = null,
    @SerializedName("updated_at" ) var updatedAt : String? = null

)