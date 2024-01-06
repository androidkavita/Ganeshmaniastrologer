package com.callastro.model

import com.google.gson.annotations.SerializedName

data class FixSessionDetail(
    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : FixSessionDetailData = FixSessionDetailData()
)
data class FixSessionDetailData(
    @SerializedName("id"                      ) var id                    : Int?    = null,
    @SerializedName("user_id"                 ) var userId                : Int?    = null,
    @SerializedName("type"                    ) var type                  : Int?    = null,
    @SerializedName("name"                    ) var name                  : String? = null,
    @SerializedName("gender"                  ) var gender                : Int?    = null,
    @SerializedName("dob"                     ) var dob                   : String? = null,
    @SerializedName("birth_time"              ) var birthTime             : String? = null,
    @SerializedName("place_birth"             ) var placeBirth            : String? = null,
    @SerializedName("occupation"              ) var occupation            : String? = null,
    @SerializedName("maritial_status"         ) var maritialStatus        : String? = null,
    @SerializedName("topic_consultation"      ) var topicConsultation     : String? = null,
    @SerializedName("boy_name"                ) var boyName               : String? = null,
    @SerializedName("dob_boy"                 ) var dobBoy                : String? = null,
    @SerializedName("birth_time_boy"          ) var birthTimeBoy          : String? = null,
    @SerializedName("place_birth_boy"         ) var placeBirthBoy         : String? = null,
    @SerializedName("occupation_boy"          ) var occupationBoy         : String? = null,
    @SerializedName("maritial_status_boy"     ) var maritialStatusBoy     : String? = null,
    @SerializedName("topic_consultation_boy"  ) var topicConsultationBoy  : String? = null,
    @SerializedName("girl_name"               ) var girlName              : String? = null,
    @SerializedName("dob_girl"                ) var dobGirl               : String? = null,
    @SerializedName("birth_time_girl"         ) var birthTimeGirl         : String? = null,
    @SerializedName("place_birth_girl"        ) var placeBirthGirl        : String? = null,
    @SerializedName("occupation_girl"         ) var occupationGirl        : String? = null,
    @SerializedName("maritial_status_girl"    ) var maritialStatusGirl    : String? = null,
    @SerializedName("topic_consultation_girl" ) var topicConsultationGirl : String? = null,
    @SerializedName("state"                   ) var state                 : String? = null,
    @SerializedName("city"                    ) var city                  : String? = null,
    @SerializedName("created_at"              ) var createdAt             : String? = null,
    @SerializedName("updated_at"              ) var updatedAt             : String? = null,
    @SerializedName("language"                ) var language             : String? = null,
    @SerializedName("mobile_no"               ) var mobile_no             : String? = null,
    @SerializedName("fixed_session"           ) var fixedSession          : String? = null,
    @SerializedName("fixed_session_type"      ) var fixedSessionType      : Int?    = null,
    @SerializedName("calendar_schedules_id"   ) var calendarSchedulesId   : String? = null
)