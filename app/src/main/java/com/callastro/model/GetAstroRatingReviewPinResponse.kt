package com.callastro.model

import com.google.gson.annotations.SerializedName


data class GetAstroRatingReviewPinResponse (

    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<GetAstroRatingReviewPinData> = arrayListOf()

)
data class GetAstroRatingReviewPinData (

    @SerializedName("id"        ) var id       : Int?    = null,
    @SerializedName("rating"    ) var rating   : String? = null,
    @SerializedName("reveiw"    ) var reveiw   : String? = null,
    @SerializedName("user_name" ) var userName : String? = null,
    @SerializedName("profile"   ) var profile  : String? = null,
    @SerializedName("pin"       ) var pin      : Int?    = null

)