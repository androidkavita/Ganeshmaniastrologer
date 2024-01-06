package com.callastro.model

import com.google.gson.annotations.SerializedName

data class ChatRequestDetailResponse (

    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : ChatRequestDetailData?   = ChatRequestDetailData()

)

data class ChatRequestDetailProfileDetail (

    @SerializedName("type"      ) var type     : Int? = null,
    @SerializedName("username"      ) var username     : String? = null,
    @SerializedName("language"      ) var language     : String? = null,
    @SerializedName("mobile"        ) var mobile       : String? = null,
    @SerializedName("profile_image" ) var profileImage : String? = null

)


data class ChatRequestDetailReports (

//    @SerializedName("name"              ) var name             : String? = null,
//    @SerializedName("dob"              ) var dob             : String? = null,
//    @SerializedName("time_birth"       ) var timeBirth       : String? = null,
//    @SerializedName("place_birth"      ) var placeBirth      : String? = null,
//    @SerializedName("occupation"       ) var occupation      : String? = null,
//    @SerializedName("maritial_status"  ) var maritialStatus  : String? = null,
//    @SerializedName("topic_consultant" ) var topicConsultant : String? = null

    @SerializedName("name"                    ) var name                  : String? = null,
    @SerializedName("dob"                     ) var dob                   : String? = null,
    @SerializedName("time_birth"              ) var timeBirth             : String? = null,
    @SerializedName("place_birth"             ) var placeBirth            : String? = null,
    @SerializedName("occupation"              ) var occupation            : String? = null,
    @SerializedName("maritial_status"         ) var maritialStatus        : String? = null,
    @SerializedName("topic_consultant"        ) var topicConsultant       : String? = null,
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
    @SerializedName("fixed_session"           ) var fixedSession          : String? = null,
    @SerializedName("fixed_session_type"      ) var fixedSessionType      : String? = null,
    @SerializedName("calendar_schedules_id"   ) var calendarSchedulesId   : String? = null

)

data class ChatRequestDetailData (

    @SerializedName("profile_detail" ) var profileDetail : ChatRequestDetailProfileDetail? = ChatRequestDetailProfileDetail(),
    @SerializedName("reports"        ) var reports       : ChatRequestDetailReports?       = ChatRequestDetailReports()

)
