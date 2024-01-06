package com.callastro.model

import com.google.gson.annotations.SerializedName

data class SuggestRemedyListResponse (

    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : SuggestRemedyListData?   = SuggestRemedyListData()

)

data class SuggestRemedyListSuggestedMsgt (

    @SerializedName("id"      ) var id      : Int?    = null,
    @SerializedName("suggest" ) var suggest : String? = null

)

data class SuggestRemedyListProducts (

    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("main_image" ) var mainImage : String? = null,
    @SerializedName("name" ) var name : String? = null

)

data class SuggestRemedyListData (

    @SerializedName("suggested_msgt" ) var suggestedMsgt : ArrayList<SuggestRemedyListSuggestedMsgt> = arrayListOf(),
    @SerializedName("products"       ) var products      : ArrayList<SuggestRemedyListProducts>      = arrayListOf()

)
