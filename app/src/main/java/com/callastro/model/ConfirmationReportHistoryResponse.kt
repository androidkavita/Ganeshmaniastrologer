package com.callastro.model

import com.google.gson.annotations.SerializedName

data class ConfirmationReportHistoryResponse(
    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : ConfirmationReportHistoryResponseData?   = ConfirmationReportHistoryResponseData()
)
data class ConfirmationReportHistoryResponseData(
    @SerializedName("id"                 ) var id                : Int?    = null,
    @SerializedName("user_id"            ) var userId            : Int?    = null,
    @SerializedName("astro_id"           ) var astroId           : Int?    = null,
    @SerializedName("language_id"        ) var languageId        : String? = null,
    @SerializedName("report_id"          ) var reportId          : Int?    = null,
    @SerializedName("full_name"          ) var fullName          : String? = null,
    @SerializedName("gender"             ) var gender            : String? = null,
    @SerializedName("dob"                ) var dob               : String? = null,
    @SerializedName("birth_time"         ) var birthTime         : String? = null,
    @SerializedName("place_birth"        ) var placeBirth        : String? = null,
    @SerializedName("occupation"         ) var occupation        : String? = null,
    @SerializedName("maritial_status"    ) var maritialStatus    : String? = null,
    @SerializedName("topic_consultation" ) var topicConsultation : String? = null,
    @SerializedName("created_at"         ) var createdAt         : String? = null,
    @SerializedName("updated_at"         ) var updatedAt         : String? = null
)