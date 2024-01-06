package com.callastro.model
import com.google.gson.annotations.SerializedName

data class BannerResponse(
    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<BannerResponseData> = arrayListOf()
)
data class BannerResponseData(
    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("image"      ) var image     : String? = null,
//    @SerializedName("status"     ) var status    : Int?    = null,
//    @SerializedName("created_at" ) var createdAt : String? = null,
//    @SerializedName("updated_at" ) var updatedAt : String? = null
)