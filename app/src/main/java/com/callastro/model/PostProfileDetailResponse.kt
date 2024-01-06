package com.callastro.model

import com.google.gson.annotations.SerializedName

data class PostProfileDetailResponse (

@SerializedName("status"  ) var status  : Int?    = null,
@SerializedName("message" ) var message : String? = null,
@SerializedName("data"    ) var data    : PostProfileDetaiData?   = PostProfileDetaiData()

)


data class Language (

    @SerializedName("id"       ) var id       : Int?    = null,
    @SerializedName("language" ) var language : String? = null

)


data class Skills (

    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("skill_name" ) var skillName : String? = null

)

data class Expertises (

    @SerializedName("id"   ) var id   : Int?    = null,
    @SerializedName("name" ) var name : String? = null

)

data class RatingRaview (

    @SerializedName("id"      ) var id      : Int?    = null,
    @SerializedName("rating"  ) var rating  : String? = null,
    @SerializedName("reveiw"  ) var reveiw  : String? = null,
    @SerializedName("name"    ) var name    : String? = null,
    @SerializedName("profile" ) var profile : String? = null

)



data class PostProfileDetaiData (

    @SerializedName("id"            ) var id           : Int?                    = null,
    @SerializedName("name"          ) var name         : String?                 = null,
    @SerializedName("gender"        ) var gender       : Int?                    = null,
    @SerializedName("experence_id"  ) var experenceId  : Int?                    = null,
    @SerializedName("language"      ) var language     : ArrayList<Language>     = arrayListOf(),
    @SerializedName("skills"        ) var skills       : ArrayList<Skills>       = arrayListOf(),
    @SerializedName("expertises"    ) var expertises   : ArrayList<Expertises>   = arrayListOf(),
    @SerializedName("experence"     ) var experence    : String?                 = null,
    @SerializedName("rating_raview" ) var ratingRaview : ArrayList<RatingRaview> = arrayListOf()

)













