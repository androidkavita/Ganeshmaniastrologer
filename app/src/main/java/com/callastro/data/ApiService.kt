package com.callastro.data


import com.callastro.model.*
import hilt_aggregated_deps._com_maxtra_astrorahiastrologer_util_Utils
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("astro_login")
    suspend fun login(
        @Field("country_code") country_code: String,
        @Field("mobile_no") mobile: String,
        @Field("device_id") device_id: String,
        @Field("device_type") device_type: String,
        @Field("device_name") device_name: String,
        @Field("device_token") device_token: String,
    ): Response<LoginResponse>

    @FormUrlEncoded
    @POST("astro_verify_otp")
    suspend fun login_verification(
        @Field("id") id: String,
        @Field("otp") otp: String,
    ): Response<LoginverificationResponse>


    @FormUrlEncoded
    @POST("astro_earning")
    suspend fun astro_earningApi(
        @Header("Authorization") authorization: String,
        @Field("filter") filter: String
    ): Response<AstroEarningResponse>


        @FormUrlEncoded
        @POST("request_money")
        suspend fun request_money(
            @Header("Authorization") authorization: String,
            @Field("money") money: String
        ): Response<CommonResponse>

    @GET("expertise_list")
    suspend fun Expertize(
        @Header("Authorization") authorization: String,
    ): Response<ExpertizeResponse>

    @GET("language_list")
    suspend fun Language(
        @Header("Authorization") authorization: String,
    ): Response<LanguageResponse>

    @GET("post_profile_detail")
    suspend fun post_profile_detailApi(
        @Header("Authorization") authorization: String,
    ): Response<PostProfileDetailResponse>

    @GET("state_list")
    suspend fun stateListApi(
        @Header("Authorization") authorization: String
    ): Response<StateListResponse>

    @FormUrlEncoded
    @POST("city_list")
    suspend fun cityListApi(
        @Header("Authorization") authorization: String,
        @Field("state_id") state_id: String
    ): Response<CityListResponse>

    @FormUrlEncoded
    @POST("pincode_list")
    suspend fun pinCodeListApi(
        @Header("Authorization") authorization: String,
        @Field("city_id") city_id: String
    ): Response<PincodeListResponse>

    @GET("skills_list")
    suspend fun skills_listApi(
        @Header("Authorization") authorization: String
    ): Response<SkillsListResponse>

    @Multipart
    @POST("add_astro_details")
    suspend fun AddAstroDetailsApi(
        @Header("Authorization") authorization: String,
        @Part("full_name") full_name: RequestBody,
        @Part("company_name") company_name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("address") address: RequestBody,
        @Part("state_id") state_id: RequestBody,
        @Part("city_id") city_id: RequestBody,
        @Part("pincode_id") pincode_id: RequestBody,
        @Part("expertise") expertise: RequestBody,
        @Part("language") language: RequestBody,
        @Part("skills") skills: RequestBody,
        @Part("bank_name") bank_name: RequestBody,
        @Part("account_no") account_no: RequestBody,
        @Part("acc_holder_name") acc_holder_name: RequestBody,
        @Part("ifsc_code") ifsc_code: RequestBody,
        @Part("branch") branch: RequestBody,
        @Part document1: MultipartBody.Part,
        @Part document2: MultipartBody.Part,
        @Part profile_image: MultipartBody.Part,
        @Part("calling_charg") calling_charge: RequestBody,
        @Part("about_us") about_us: RequestBody,
        @Part("fixed_session_30min_charge") fixed_session_30min_charge :RequestBody,
        @Part("fixed_session_60min_charge") fixed_session_60min_charge :RequestBody,
        @Part("experience") experience: RequestBody,
    ): Response<AddAstroDetailResponse>

    @FormUrlEncoded
    @POST("add_expertise")
    suspend fun add_expertiseApi(
        @Header("Authorization") authorization: String,
        @Field("expertise_id") expertise_id: String
    ): Response<AddExpertiseItemResponse>

    @GET("astro_banner")
    suspend fun banner(
        @Header("Authorization") authorization: String,
    ): Response<BannerResponse>

    @FormUrlEncoded
    @POST("delete_expertise")
    suspend fun delete_expertiseApi(
        @Header("Authorization") authorization: String,
        @Field("id") id: String
    ): Response<DeleteExpertiseItemResponse>

    @GET("get_added_skills")
    suspend fun get_added_skillsApi(
        @Header("Authorization") authorization: String,
    ): Response<GetAddedSkillsResponse>

    @FormUrlEncoded
    @POST("add_skills")
    suspend fun add_skillsApi(
        @Header("Authorization") authorization: String,
        @Field("skill_id") skill_id: String
    ): Response<AddSkillsItemResponse>

    @GET("get_added_expertise")
    suspend fun get_added_expertiseApi(
        @Header("Authorization") authorization: String,
    ): Response<GetAddedExpertiseResponse>

    @FormUrlEncoded
    @POST("astro_give_review")
    suspend fun astro_give_review(
        @Header("Authorization") authorization: String,
        @Field("user_id") astro_id: String,
        @Field("rating") rating: String,
        @Field("review") review: String,
    ): Response<GiveReviewResponse>

    @FormUrlEncoded
    @POST("recent_otp")
    suspend fun recent_otp(
        @Field("mobile_no") mobile_no: String,
        @Field("type") type: String,
    ): Response<LoginResponse>


    @FormUrlEncoded
    @POST("delete_skills")
    suspend fun delete_skillsApi(
        @Header("Authorization") authorization: String,
        @Field("id") id: String
    ): Response<DeleteSkillsItemResponse>


    @GET("get_added_language")
    suspend fun get_added_languageApi(
        @Header("Authorization") authorization: String,
    ): Response<GetAddedLanguageResponse>

    @GET("astro_bank_detail")
    suspend fun GetBankDetail(
        @Header("Authorization") authorization: String,
    ): Response<GetBankDetail>

    @FormUrlEncoded
    @POST("refund_money")
    suspend fun refund_money(
        @Header("Authorization") authorization: String,
        @Field("order_id") order_id: String,
    ): Response<CommonResponse>

    @FormUrlEncoded
    @POST("astro_update_bank")
    suspend fun UpdateBank(
        @Header("Authorization") authorization: String,
        @Field("bank_name") bank_name: String,
        @Field("account_no") account_no: String,
        @Field("acc_holder_name") acc_holder_name: String,
        @Field("ifsc_code") ifsc_code: String,
        @Field("branch") branch: String,
    ): Response<BankDetailResponse>

    @FormUrlEncoded
    @POST("add_language")
    suspend fun add_languageApi(
        @Header("Authorization") authorization: String,
        @Field("language_id") language_id: String
    ): Response<AddLanguageItemResponse>

    @FormUrlEncoded
    @POST("delete_language")
    suspend fun delete_languageApi(
        @Header("Authorization") authorization: String,
        @Field("id") id: String
    ): Response<DeleteLanguageItemResponse>

    @FormUrlEncoded
    @POST("post_profile_update")
    suspend fun post_profile_updateApi(
        @Header("Authorization") authorization: String,
        @Field("full_name") full_name: String,
        @Field("gender") gender: String,
        @Field("experience") experience: String
    ): Response<PostProfileUpdateResponse>

    @GET("experience_list")
    suspend fun experience_listApi(
        @Header("Authorization") authorization: String,
    ): Response<ExperienceListResponse>

//    @GET("astro_profile")
//    suspend fun experience_listApi(
//        @Header("Authorization") authorization: String,
//    ): Response<>


    @Multipart
    @POST("astro_profile_update")
    suspend fun Editprofile(
        @Header("Authorization") authorization: String,
        @Part("name") name: RequestBody,
        @Part("mobile_no") mobile_no: RequestBody,
        @Part("email") email: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part profile: MultipartBody.Part,
        @Part("address") address: RequestBody,
        @Part("state_id") state_id: RequestBody,
//        @Part("expertise") expertise: RequestBody,
//        @Part("language") language: RequestBody,
//        @Part("skills") skills: RequestBody,
        @Part("company_name") company_name: RequestBody,
        @Part("city") city: RequestBody,
        @Part("pincode") pincode: RequestBody,
        @Part("calling_charg") calling_charg: RequestBody,
        @Part document1: MultipartBody.Part,
        @Part document2: MultipartBody.Part,
        @Part("about_us") about_us: RequestBody,
        @Part("fixed_session_30min_charge") fixed_session_30min_charge: RequestBody,
        @Part("fixed_session_60min_charge") fixed_session_60min_charge: RequestBody,
        @Part("experence_id") experience: RequestBody,
    ): Response<LoginverificationResponse>

    @GET("astro_rating_review_list")
    suspend fun astro_rating_review_listApi(
        @Header("Authorization") authorization: String,
    ): Response<GetAstroRatingReviewListResponse>

    @FormUrlEncoded
    @POST("review_pin_update")
    suspend fun review_pin_updateApi(
        @Header("Authorization") authorization: String,
        @Field("id") id: String,
        @Field("type") type: String
    ): Response<ReviewPinUpdateResponse>

    @GET("rating_review_pinselected_list")
    suspend fun rating_review_pinselected_listApi(
        @Header("Authorization") authorization: String,
    ): Response<GetAstroRatingReviewPinResponse>

    @FormUrlEncoded
    @POST("confirmation_booking_report_detail")
    suspend fun confirmation_booking_report_detail(
        @Header("Authorization") authorization: String,
        @Field("report_intakes_id") report_intakes_id: String
    ): Response<ConfirmationReportHistoryResponse>

//    @FormUrlEncoded
    @GET("call_history")
    suspend fun callHistoryListApi(
        @Header("Authorization") authorization: String,
//        @Field("dummy") dummy: String
    ): Response<CallHistoryListResponse>

//    @FormUrlEncoded
    @GET("chart_history")
    suspend fun chatHistoryListApi(
        @Header("Authorization") authorization: String,
//        @Field("dummy") dummy: String
    ): Response<ChatHistoryListResponse>

    @GET("product_list")
    suspend fun product_listApi(
        @Header("Authorization") authorization: String,
    ): Response<ProductListResponse>

    @FormUrlEncoded
    @POST("add_suggest_remedy")
    suspend fun add_suggest_remedyApi(
        @Header("Authorization") authorization: String,
        @Field("product_ids") product_ids: String,
        @Field("user_id") user_id: String,
    ): Response<AddSuggestRemedyResponse>

    @FormUrlEncoded
    @POST("suggest_remedy_list")
    suspend fun suggest_remedy_listApi(
        @Header("Authorization") authorization: String,
        @Field("user_id") user_id: String,
    ): Response<SuggestRemedyListResponse>

    @FormUrlEncoded
    @POST("agora_generate_token")
    suspend fun agora_generate_tokenApi(
        @Header("Authorization") authorization: String,
        @Field("astro_id") astro_id: String,
        @Field("call_type") call_type: String,  // 1-audiocall, 2-videocall
    ): Response<AgoraGenerateTokenResponse>

    @FormUrlEncoded
    @POST("call_end_by_status")
    suspend fun call_end_by_status(
        @Header("Authorization") authorization: String,
        @Field("caller_id") caller_id: String,
    ): Response<CallendbyuserResponse>

    @FormUrlEncoded
    @POST("live_agora_generate_token")
    suspend fun live_agora_generate_token(
        @Header("Authorization") authorization: String,
        @Field("topic") topic: String,
    ): Response<GoLiveResponse>

    @GET("delete_live_astro")
    suspend fun delete_live_astro(
        @Header("Authorization") authorization: String,
    ): Response<CommonResponse>


    @GET("astro_history_wallets")
    suspend fun astro_history_wallets(
        @Header("Authorization") authorization: String,
    ): Response<AstroEarningListResponse>



    @FormUrlEncoded
    @POST("live_agora_topic")
    suspend fun live_agora_topic(
        @Header("Authorization") authorization: String,
        @Field("topic") topic: String
    ): Response<GoLiveResponse>

    @GET("astro_home")
    suspend fun astro_home(
        @Header("Authorization") authorization: String,
    ): Response<CallChatStatusResponse>

    @FormUrlEncoded
    @POST("add_suggest_remedy_message")
    suspend fun add_suggest_remedy_messageApi(
        @Header("Authorization") authorization: String,
        @Field("user_id") user_id: String,
        @Field("message") message: String,
    ): Response<CommonResponse>

    @GET("customer_support_email")
    suspend fun EmailUs(
        @Header("Authorization") authorization: String,
    ): Response<EmailUs_Response>

    @FormUrlEncoded
    @POST("callback_apply")
    suspend fun callback_apply(
        @Header("Authorization") authorization: String,
        @Field("mobile") mobile: String,
        @Field("message") message: String,
    ): Response<CommonResponse>

    @GET("about_us")
    suspend fun AboutUs(
        @Header("Authorization") authorization: String,
    ): Response<AboutUsResponse>

    @GET("astro_profile")
    suspend fun ViewProfile(
        @Header("Authorization") authorization: String,
    ): Response<ViewProfileResponse>

    @GET("notification")
    suspend fun notificationApi(
        @Header("Authorization") authorization: String,
    ): Response<NotificationListResponse>

    @GET("faq")
    suspend fun FAQ(
        @Header("Authorization") authorization: String,
    ): Response<FAQResponse>

    @FormUrlEncoded
    @POST("chat_list")
    suspend fun chat_list_MessageApi(
        @Header("Authorization") authorization: String,
        @Field("to_id") to_id: String,
    ): Response<ChatListMessageResponse>

    @FormUrlEncoded
    @POST("call_end")
    suspend fun call_end(
        @Header("Authorization") authorization: String,
        @Field("timer") timer: String,
        @Field("from_user") from_user: String,
        @Field("to_user") to_user: String,
        @Field("caller_id") caller_id: String,
        @Field("type") type: String,
        @Field("unique_id") fixed_session: String,
    ): Response<CommonResponse>

    @FormUrlEncoded
    @POST("chat_end")
    suspend fun chat_end(
        @Header("Authorization") authorization: String,
        @Field("timer") timer: String,
        @Field("from_user") from_user: String,
        @Field("to_user") to_user: String,
        @Field("caller_id") caller_id: String,
        @Field("type") type: String,
        @Field("unique_id") fixed_session: String,
    ): Response<CommonResponse>

    @FormUrlEncoded
    @POST("live_end")
    suspend fun live_end(
        @Header("Authorization") authorization: String,
        @Field("timer") timer: String,
        @Field("from_user") from_user: String,
        @Field("to_user") to_user: String,
        @Field("type") type: String,
    ): Response<CommonResponse>

    @FormUrlEncoded
    @POST("send_chat_with_us")
    suspend fun send_chat_with_us(
        @Header("Authorization") authorization: String,
        @Field("message") caller_id: String,
    ): Response<CommonResponse>

    @GET("get_chat_with_us")
    suspend fun get_chat_with_us(
        @Header("Authorization") authorization: String,
    ): Response<GetCustomerSupportChat>

    @FormUrlEncoded
    @POST("check_chat_end")
    suspend fun check_chat_end(
        @Header("Authorization") authorization: String,
        @Field("caller_id") caller_id: String,
    ): Response<CheckChatEndResponse>

    @GET("notification")
    suspend fun Notification(
        @Header("Authorization") authorization: String,
    ): Response<NotificationResponse>


    @FormUrlEncoded
    @POST("call_ring_status_save")
    suspend fun call_ring_status_save(
        @Header("Authorization") authorization: String,
        @Field("channel_name") channel_name: String,
    ): Response<call_ring_status_save_Response>


    @FormUrlEncoded
    @POST("call_ring_end")
    suspend fun call_ring_end(
        @Header("Authorization") authorization: String,
        @Field("channel_name") channel_name: String,
    ): Response<CommonResponse>


    @FormUrlEncoded
    @POST("call_ring")
    suspend fun call_ring(
        @Header("Authorization") authorization: String,
        @Field("astro_id") astro_id: String,
        @Field("user_id") user_id: String,
        @Field("request_id") request_id: String,
    ): Response<CallRingResponse>

    @FormUrlEncoded
    @POST("chatagora")
    suspend fun chatagoraApi(
        @Header("Authorization") authorization: String,
        @Field("to_userId") to_userId: String,
        @Field("message") message: String,
        @Field("type") type: String,
    ): Response<ChatAgoraResponse>

    @GET("setting_details")
    suspend fun setting_detailsApi(
        @Header("Authorization") authorization: String,
    ): Response<SettingDetailsGetResponse>

    @GET("update_online_status")
    suspend fun checkOnlineStatusApi(
        @Header("Authorization") authorization: String,
    ): Response<CommonResponse>

    @FormUrlEncoded
    @POST("setting_update")
    suspend fun setting_updateApi(
        @Header("Authorization") authorization: String,
        @Field("is_chat") is_chat: String,
        @Field("is_audio_call") is_audio_call: String,
        @Field("is_video_call") is_video_call: String
    ): Response<SettingDetailsGetResponse>

    @FormUrlEncoded
    @POST("get_cls_date_wise")
    suspend fun GetCalender(
        @Header("Authorization") authorization: String,
        @Field("date") date: String,
    ): Response<CalenderList>

    @FormUrlEncoded
    @POST("manage_calendar_schedule")
    suspend fun manage_calendar_scheduleApi(
        @Header("Authorization") authorization: String,
        @Field("date") date: String,
        @Field("from_time") from_time: String,
        @Field("to_time") to_time: String,
    ): Response<ManageCalendarScheduleResponse>

    @FormUrlEncoded
    @POST("calendar_schedule_delete")
    suspend fun calendar_schedule_deleteApi(
        @Header("Authorization") authorization: String,
        @Field("id") id: String,
    ): Response<ScheduleCalendarDeleteResponse>


    @FormUrlEncoded
    @POST("call_request_detail")
    suspend fun call_request_detail_api(
        @Header("Authorization") authorization: String,
        @Field("id") id: String
    ): Response<CallRequestDetailResponse>

    @FormUrlEncoded
    @POST("call_request_accecpt")
    suspend fun call_request_accecpt_api(
        @Header("Authorization") authorization: String,
        @Field("id") id: String
    ): Response<ChatRequestCancelResponse>

    @FormUrlEncoded
    @POST("call_request_cancel")
    suspend fun call_request_cancel_api(
        @Header("Authorization") authorization: String,
        @Field("id") id: String,
        @Field("reason") reason: String,
        @Field("comment") comment: String,
        @Field("action_by") action_by: String,
    ): Response<ChatRequestCancelResponse>

    @FormUrlEncoded
    @POST("click_user_chat")
    suspend fun click_user_chat(
        @Header("Authorization") authorization: String,
        @Field("user_id") user_id: String,
    ): Response<CommonResponse>

    @GET("chat_user_list")
    suspend fun chat_user_listApi(
        @Header("Authorization") authorization: String,
    ): Response<ChatUserListResponse>

    @FormUrlEncoded
    @POST("chat_request_detail")
    suspend fun chat_request_detail_api(
        @Header("Authorization") authorization: String,
        @Field("id") id: String
    ): Response<ChatRequestDetailResponse>

    @GET("call_user_list")
    suspend fun call_user_listApi(
        @Header("Authorization") authorization: String,
    ): Response<CallUserListResponse>

    @FormUrlEncoded
    @POST("chat_request_accecpt")
    suspend fun chat_request_accecpt_api(
        @Header("Authorization") authorization: String,
        @Field("id") id: String
    ): Response<ChatRequestCancelResponse>

    @GET("reason_cancel_list")
    suspend fun chatcallReasonCancelListApi(
        @Header("Authorization") authorization: String,
    ): Response<ChatCallCancelReasonResponse>

    @GET("cancellation_by_user")
    suspend fun cancellationByUserApi(
        @Header("Authorization") authorization: String,
    ): Response<CancellationByUserResponse>

    @FormUrlEncoded
    @POST("chat_request_cancel")
    suspend fun chat_request_cancel_api(
        @Header("Authorization") authorization: String,
        @Field("id") id: String,
        @Field("reason") reason: String,
        @Field("comment") comment: String
    ): Response<ChatRequestCancelResponse>

    @GET("chat_request_list")
    suspend fun Chathome(
        @Header("Authorization") authorization: String,
    ): Response<Chat_Call_Response>

    @GET("call_request_list")
    suspend fun Callhome(
        @Header("Authorization") authorization: String,
    ): Response<Chat_Call_Response>

    @FormUrlEncoded
    @POST("add_comment")
    suspend fun add_comment(
        @Field("user_id") user_id: String,
        @Field("astro_id") astro_id: String,
        @Field("message") message: String,
        @Field("type") type: String,
    ): Response<CommonResponse>

    @FormUrlEncoded
    @POST("get_live_comments")
    suspend fun get_live_comments(
        @Header("Authorization") authorization: String,
        @Field("channel_name") channel_name: String,
    ): Response<LiveCommentsModelClass>

    @FormUrlEncoded
    @POST("confirmation_booking_list")
    suspend fun confirmation_booking_listApi(
        @Header("Authorization") authorization: String,
        @Field("type") type: String
    ): Response< ConfirmationBookingResponse>

    @Multipart
    @POST("report_doc_upload")
    suspend fun report_doc_upload(
        @Header("Authorization") authorization: String,
        @Part("user_id") user_id: RequestBody,
        @Part("report_intake_id") report_intake_id: RequestBody,
        @Part("text") text: RequestBody,
        @Part file: MultipartBody.Part,
    ): Response<CommonResponse>

    @GET("fixed_session_requests")
    suspend fun fixed_session_requests(
        @Header("Authorization") authorization: String,
    ): Response<FixedsessionResponseList>


    @FormUrlEncoded
    @POST("fixed_session_request_accecpt")
    suspend fun fixed_session_request_accecpt(
        @Header("Authorization") authorization: String,
        @Field("id") id: String
    ): Response<ChatRequestCancelResponse>



    @FormUrlEncoded
    @POST("fix_session_detail")
    suspend fun fix_session_detail(
        @Header("Authorization") authorization: String,
        @Field("user_detail_id") user_detail_id: String
    ): Response<FixSessionDetail>

    @FormUrlEncoded
    @POST("my_bookings")
    suspend fun upcomingCompletedBookingsApi(
        @Header("Authorization") authorization: String,
        @Field("type") type: String,
    ): Response<MyBookingsUpcomingCompletedResponse>
}