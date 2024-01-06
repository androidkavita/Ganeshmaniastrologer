package com.callastro.data


import com.callastro.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.Part
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val apiService: ApiService) : MainRepository {


    override suspend fun login(
        country_code: String,
        mobile: String,
        device_id: String,
        device_type: String,
        device_name: String,
        device_token: String
    ):
            Response<LoginResponse> =
        apiService.login(country_code,mobile, device_id, device_type, device_name, device_token)

    override suspend fun loginverification(id: String, otp: String):
            Response<LoginverificationResponse> = apiService.login_verification(id, otp)

    override suspend fun Expertize(token:String):
            Response<ExpertizeResponse> = apiService.Expertize(token)

    override suspend fun Language(token:String):
            Response<LanguageResponse> = apiService.Language(token)

    override suspend fun post_profile_detailApi(token:String):
            Response<PostProfileDetailResponse> = apiService.post_profile_detailApi(token)

    override suspend fun stateListApi(token:String):
            Response<StateListResponse> = apiService.stateListApi(token)

    override suspend fun cityListApi(token:String, state_id:String):
            Response<CityListResponse> = apiService.cityListApi(token,state_id)

    override suspend fun pinCodeListApi(token:String, city_id:String):
            Response<PincodeListResponse> = apiService.pinCodeListApi(token, city_id)

    override suspend fun skills_listApi(token:String):
            Response<SkillsListResponse> = apiService.skills_listApi(token)

    override suspend fun astro_earningApi(token:String, filter:String):
            Response<AstroEarningResponse> = apiService.astro_earningApi(token, filter)

    override suspend fun request_money(token:String, money:String):
            Response<CommonResponse> = apiService.request_money(token, money)


    override suspend fun chat_user_listApi(token:String):
            Response<ChatUserListResponse> = apiService.chat_user_listApi(token)

    override suspend fun click_user_chat(token:String,user_id: String):
            Response<CommonResponse> = apiService.click_user_chat(token,user_id)

    override suspend fun agora_generate_tokenApi(token:String,  astro_id: String, call_type: String):
            Response<AgoraGenerateTokenResponse> = apiService.agora_generate_tokenApi(token, astro_id, call_type)

    override suspend fun call_end_by_status(token:String,caller_id: String):
            Response<CallendbyuserResponse> = apiService.call_end_by_status(token,caller_id)

    override suspend fun live_agora_generate_token(token:String,topic: String):
            Response<GoLiveResponse> = apiService.live_agora_generate_token(token,topic)

    override suspend fun upcomingCompletedBookingsApi(token:String, type:String):
            Response<MyBookingsUpcomingCompletedResponse> = apiService.upcomingCompletedBookingsApi(token, type)

    override suspend fun delete_live_astro(token:String):
            Response<CommonResponse> = apiService.delete_live_astro(token)

    override suspend fun checkOnlineStatusRepo(token: String): Response<CommonResponse> = apiService.checkOnlineStatusApi(token)

    override suspend fun astro_history_wallets(token:String):
            Response<AstroEarningListResponse> = apiService.astro_history_wallets(token)

    override suspend fun live_agora_topic(token:String, topic:String):
            Response<GoLiveResponse> = apiService.live_agora_topic(token, topic)

    override suspend fun astro_home(token:String):
            Response<CallChatStatusResponse> = apiService.astro_home(token)

    override suspend fun AddAstroDetailsApi(token:String,
                                            full_name: RequestBody,
                                            company_name: RequestBody,
                                            email: RequestBody,
                                            gender: RequestBody,
                                            address: RequestBody,
                                            state_id: RequestBody,
                                            city_id: RequestBody,
                                            pincode_id: RequestBody,
                                            expertise: RequestBody,
                                            language: RequestBody,
                                            skills: RequestBody,
                                            bank_name: RequestBody,
                                            account_no: RequestBody,
                                            acc_holder_name: RequestBody,
                                            ifsc_code: RequestBody,
                                            branch: RequestBody,
                                            document1: MultipartBody.Part,
                                            document2: MultipartBody.Part,
                                            profile_image: MultipartBody.Part,
                                            calling_charge:RequestBody,
                                            about_us: RequestBody,
                                            fixed_session_30min_charge :RequestBody,
                                            fixed_session_60min_charge :RequestBody,
                                            experience :RequestBody,):
            Response<AddAstroDetailResponse> = apiService.AddAstroDetailsApi(token,
        full_name, company_name, email, gender, address ,state_id , city_id, pincode_id,expertise , language,
        skills, bank_name,account_no , acc_holder_name,ifsc_code , branch,document1, document2,profile_image,calling_charge,about_us,fixed_session_30min_charge,fixed_session_60min_charge,experience )

    override suspend fun add_expertiseApi(token:String, expertise_id:String):
            Response<AddExpertiseItemResponse> = apiService.add_expertiseApi(token, expertise_id)
    override suspend fun banner(token: String):
            Response<BannerResponse> = apiService.banner(token)
    override suspend fun delete_expertiseApi(token:String, id:String):
            Response<DeleteExpertiseItemResponse> = apiService.delete_expertiseApi(token, id)

    override suspend fun get_added_skillsApi(token:String):
            Response<GetAddedSkillsResponse> = apiService.get_added_skillsApi(token)

    override suspend fun add_skillsApi(token:String, skill_id:String):
            Response<AddSkillsItemResponse> = apiService.add_skillsApi(token, skill_id)

    override suspend fun get_added_expertiseApi(token:String):
            Response<GetAddedExpertiseResponse> = apiService.get_added_expertiseApi(token)


    override suspend fun astro_give_review(token: String,astro_id: String,rating: String,review: String,):
            Response<GiveReviewResponse> = apiService.astro_give_review(token, astro_id,rating,review)

    override suspend fun recent_otp(mobile_no: String,type: String,):
            Response<LoginResponse> = apiService.recent_otp(mobile_no,type)

    override suspend fun delete_skillsApi(token:String, id:String):
            Response<DeleteSkillsItemResponse> = apiService.delete_skillsApi(token, id)

    override suspend fun get_added_languageApi(token:String):
            Response<GetAddedLanguageResponse> = apiService.get_added_languageApi(token)

    override suspend fun add_languageApi(token:String, language_id:String):
            Response<AddLanguageItemResponse> = apiService.add_languageApi(token, language_id)

    override suspend fun delete_languageApi(token:String, id:String):
            Response<DeleteLanguageItemResponse> = apiService.delete_languageApi(token, id)

    override suspend fun post_profile_updateApi(token:String, full_name:String, gender:String, experience:String):
            Response<PostProfileUpdateResponse> = apiService.post_profile_updateApi(token, full_name, gender, experience)

    override suspend fun experience_listApi(token:String):
            Response<ExperienceListResponse> = apiService.experience_listApi(token)

    override suspend fun confirmation_booking_report_detail(token:String,report_intakes_id: String):
            Response<ConfirmationReportHistoryResponse> = apiService.confirmation_booking_report_detail(token,report_intakes_id)


    override suspend fun astro_rating_review_listApi(token:String):
            Response<GetAstroRatingReviewListResponse> = apiService.astro_rating_review_listApi(token)

    override suspend fun review_pin_updateApi(token:String, id:String, type:String):
            Response<ReviewPinUpdateResponse> = apiService.review_pin_updateApi(token, id, type)

    override suspend fun rating_review_pinselected_listApi(token:String):
            Response<GetAstroRatingReviewPinResponse> = apiService.rating_review_pinselected_listApi(token)

    override suspend fun EditProfile(token:String, name: RequestBody, mobile_no: RequestBody, email: RequestBody, gender: RequestBody, profile: MultipartBody.Part,
                                     address: RequestBody,
                                     state_id: RequestBody,
//                                     expertise: RequestBody,
//                                     language: RequestBody,
//                                     skills: RequestBody,
                                     company_name: RequestBody,
                                     city: RequestBody,
                                     pincode: RequestBody,
                                     calling_charg: RequestBody,
                                     document1: MultipartBody.Part,
                                     document2: MultipartBody.Part,
                                     about_us: RequestBody,
                                     fixed_session_30min_charge: RequestBody,
                                     fixed_session_60min_charge: RequestBody,
                                     experience: RequestBody,):
            Response<LoginverificationResponse> = apiService.Editprofile(token,
        name,
        mobile_no,
        email,gender,
        profile,address, state_id, /*expertise, language, skills, */company_name, city, pincode, calling_charg, document1, document2,about_us,fixed_session_30min_charge,fixed_session_60min_charge,experience)












    override suspend fun callHistoryListApi(token:String,/* dummy:String*/):
            Response<CallHistoryListResponse> = apiService.callHistoryListApi(token, /*dummy*/)

    override suspend fun chatHistoryListApi(token:String, /*dummy:String*/):
            Response<ChatHistoryListResponse> = apiService.chatHistoryListApi(token,/*dummy*/)

    override suspend fun EmailUs(token:String):
            Response<EmailUs_Response> = apiService.EmailUs(token)

    override suspend fun product_listApi(token:String):
            Response<ProductListResponse> = apiService.product_listApi(token)

    override suspend fun add_suggest_remedyApi(token:String, product_ids:String, user_id:String):
            Response<AddSuggestRemedyResponse> = apiService.add_suggest_remedyApi(token, product_ids, user_id)

    override suspend fun suggest_remedy_listApi(token:String, user_id: String):
            Response<SuggestRemedyListResponse> = apiService.suggest_remedy_listApi(token, user_id)


    override suspend fun add_suggest_remedy_messageApi(token:String, user_id: String, message: String):
            Response<CommonResponse> = apiService.add_suggest_remedy_messageApi(token, user_id, message)

    override suspend fun callback_apply(token:String,mobile:String,discription:String):
            Response<CommonResponse> = apiService.callback_apply(token,mobile,discription)
    override suspend fun aboutus(token:String):
            Response<AboutUsResponse> = apiService.AboutUs(token)

    override suspend fun notificationApi(token:String):
            Response<NotificationListResponse> = apiService.notificationApi(token)

    override suspend fun faq(token:String):
            Response<FAQResponse> = apiService.FAQ(token)

    override suspend fun chat_list_MessageApi(token:String,  to_id: String):
            Response<ChatListMessageResponse> = apiService.chat_list_MessageApi(token, to_id)

    override suspend fun call_end(token:String,timer:String,from_user: String,to_user: String,caller_id:String,type: String,fixed_session: String,):
            Response<CommonResponse> = apiService.call_end(token,timer,from_user,to_user,caller_id,type,fixed_session)

    override suspend fun chat_end(token:String,timer:String,from_user: String,to_user: String,caller_id:String,type: String,fixed_session: String,):
            Response<CommonResponse> = apiService.chat_end(token,timer,from_user,to_user,caller_id,type,fixed_session)

    override suspend fun live_end(token:String,timer:String,from_user: String,to_user: String,type: String):
            Response<CommonResponse> = apiService.live_end(token,timer,from_user,to_user,type)



    override suspend fun send_chat_with_us(token:String,message:String):
            Response<CommonResponse> = apiService.send_chat_with_us(token,message)

    override suspend fun get_chat_with_us(token:String):
            Response<GetCustomerSupportChat> = apiService.get_chat_with_us(token)


    override suspend fun notification(token: String):
            Response<NotificationResponse> = apiService.Notification(token)

    override suspend fun call_ring_status_save(token:String,channel_name: String):
            Response<call_ring_status_save_Response> = apiService.call_ring_status_save(token,channel_name)

    override suspend fun call_ring_end(token:String,channel_name: String):
            Response<CommonResponse> = apiService.call_ring_end(token,channel_name)

    override suspend fun call_ring(token: String,astro_id:String,user_id:String,request_id: String):
            Response<CallRingResponse> = apiService.call_ring(token,astro_id,user_id,request_id)

    override suspend fun check_chat_end(token:String,caller_id: String):
            Response<CheckChatEndResponse> = apiService.check_chat_end(token,caller_id)

    override suspend fun chatagoraApi(token:String,  to_userId: String, message: String, type: String):
            Response<ChatAgoraResponse> = apiService.chatagoraApi(token, to_userId, message, type)

    override suspend fun setting_detailsApi(token:String):
            Response<SettingDetailsGetResponse> = apiService.setting_detailsApi(token)

    override suspend fun setting_updateApi(token:String, is_chat:String, is_audio_call:String, is_video_call:String):
            Response<SettingDetailsGetResponse> = apiService.setting_updateApi(token, is_chat, is_audio_call, is_video_call)

    override suspend fun GetCalender(token:String,date:String):
            Response<CalenderList> = apiService.GetCalender(token,date)

    override suspend fun manage_calendar_scheduleApi(token:String,  date: String, from_time: String, to_time: String):
            Response<ManageCalendarScheduleResponse> = apiService.manage_calendar_scheduleApi(token, date, from_time, to_time)

    override suspend fun calendar_schedule_deleteApi(token:String,  id: String):
            Response<ScheduleCalendarDeleteResponse> = apiService.calendar_schedule_deleteApi(token, id)

    override suspend fun call_request_detail_api(token:String,id:String):
            Response<CallRequestDetailResponse> = apiService.call_request_detail_api(token,id)


    override suspend fun call_request_accecpt_api(token:String,id:String):
            Response<ChatRequestCancelResponse> = apiService.call_request_accecpt_api(token,id)

    override suspend fun call_request_cancel_api(token:String,id:String,reason:String,comment:String,action_by: String,):
            Response<ChatRequestCancelResponse> = apiService.call_request_cancel_api(token,id,reason,comment,action_by)

    override suspend fun chat_request_detail_api(token:String,id:String):
            Response<ChatRequestDetailResponse> = apiService.chat_request_detail_api(token,id)

    override suspend fun chat_request_accecpt_api(token:String,id:String):
            Response<ChatRequestCancelResponse> = apiService.chat_request_accecpt_api(token,id)

    override suspend fun chatcallReasonCancelListApi(token:String):
            Response<ChatCallCancelReasonResponse> = apiService.chatcallReasonCancelListApi(token)

    override suspend fun cancellationByUserApi(token:String):
            Response<CancellationByUserResponse> = apiService.cancellationByUserApi(token)

    override suspend fun chat_request_cancel_api(token:String,id:String,reason:String,comment:String):
            Response<ChatRequestCancelResponse> = apiService.chat_request_cancel_api(token,id,reason,comment)

    override suspend fun Chathome(token:String):
            Response<Chat_Call_Response> = apiService.Chathome(token)

    override suspend fun Callhome(token:String):
            Response<Chat_Call_Response> = apiService.Callhome(token)

    override suspend fun confirmation_booking_listApi(token:String, type:String):
            Response<ConfirmationBookingResponse> = apiService.confirmation_booking_listApi(token, type)


    override suspend fun report_doc_upload(token:String,
                                           user_id: RequestBody,
                                           report_intake_id: RequestBody,
                                           text: RequestBody,
                                           file: MultipartBody.Part,
    ):
            Response<CommonResponse> = apiService.report_doc_upload(
        token,
        user_id,
        report_intake_id,
        text,
        file)

    override suspend fun call_user_listApi(token:String):
            Response<CallUserListResponse> = apiService.call_user_listApi(token)

    override suspend fun ViewProfile(token: String):
            Response<ViewProfileResponse> = apiService.ViewProfile(token)

    override suspend fun add_comment(
        user_id: String,
        astro_id: String,
        message: String,
        type: String,
    ): Response<CommonResponse> =
        apiService.add_comment(user_id, astro_id, message, type)



    override suspend fun fixed_session_requests(token:String):
            Response<FixedsessionResponseList> = apiService.fixed_session_requests(token)

    override suspend fun fixed_session_request_accecpt(token:String,id:String):
            Response<ChatRequestCancelResponse> = apiService.fixed_session_request_accecpt(token,id)

    override suspend fun fix_session_detail(token:String,user_detail_id:String):
            Response<FixSessionDetail> = apiService.fix_session_detail(token,user_detail_id)



    override suspend fun UpdateBank(token:String,bank_name:String,account_no: String,acc_holder_name: String,ifsc_code: String,branch: String):
            Response<BankDetailResponse> = apiService.UpdateBank(token,bank_name,account_no,acc_holder_name,ifsc_code,branch)

    override suspend fun GetBankDetail(token:String):
            Response<GetBankDetail> = apiService.GetBankDetail(token)

    override suspend fun refund_money(token:String,order_id: String):
            Response<CommonResponse> = apiService.refund_money(token,order_id)



    override suspend fun get_live_comments(
        token: String,
        channel_name: String,
        ): Response<LiveCommentsModelClass> =
        apiService.get_live_comments(token,channel_name)


}