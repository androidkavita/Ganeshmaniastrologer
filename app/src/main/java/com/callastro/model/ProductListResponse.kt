package com.callastro.model

import com.google.gson.annotations.SerializedName


data class ProductListResponse (

    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<ProductListData> = arrayListOf()

)


data class ProductListData (

    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("name"       ) var name      : String? = null,
    @SerializedName("main_image" ) var mainImage : String? = null

)


