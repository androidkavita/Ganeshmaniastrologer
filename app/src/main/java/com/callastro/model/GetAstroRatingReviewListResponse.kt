package com.callastro.model

import com.google.gson.annotations.SerializedName

data class GetAstroRatingReviewListResponse (

    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : GetAstroRatingReviewData?   = GetAstroRatingReviewData()

)
data class GetAstroRatingReviewReviewRatings (
    @SerializedName("id"        ) var id       : Int?    = null,
    @SerializedName("rating"    ) var rating   : String? = null,
    @SerializedName("reveiw"    ) var reveiw   : String? = null,
    @SerializedName("user_name" ) var userName : String? = null,
    @SerializedName("profile"   ) var profile  : String? = null,
    @SerializedName("pin"       ) var pin      : Int?    = null
)

data class GetAstroRatingReviewCounts (
    @SerializedName("total_pin"    ) var totalPin    : Int? = null,
    @SerializedName("total_review" ) var totalReview : Int? = null
)

data class GetAstroRatingReviewData (
    @SerializedName("astro_rating"   ) var astroRating   : AstroRating?             = AstroRating(),
    @SerializedName("review_ratings" ) var reviewRatings : ArrayList<GetAstroRatingReviewReviewRatings> = arrayListOf(),
    @SerializedName("counts"         ) var counts        : GetAstroRatingReviewCounts? = GetAstroRatingReviewCounts()
)

data class AstroRating (

    @SerializedName("avg_rating" ) var avgRating : String? = null,
    @SerializedName("total"      ) var total     : Int?    = null,
    @SerializedName("five"       ) var five      : Int?    = null,
    @SerializedName("four"       ) var four      : Int?    = null,
    @SerializedName("three"      ) var three     : Int?    = null,
    @SerializedName("two"        ) var two       : Int?    = null,
    @SerializedName("one"        ) var one       : Int?    = null

)