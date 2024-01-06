package com.callastro.data

import com.callastro.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header

interface MainRepository {


    suspend fun login(country_code: String,mobile:String,device_id: String,device_type: String,device_name: String,device_token: String):
            Response<LoginResponse>

    suspend fun loginverification(id: String, otp: String):
            Response<LoginverificationResponse>

    suspend fun Expertize(token:String):
            Response<ExpertizeResponse>

    suspend fun Language(token:String):
            Response<LanguageResponse>

    suspend fun post_profile_detailApi(token:String):
            Response<PostProfileDetailResponse>

    suspend fun stateListApi(token:String):
            Response<StateListResponse>


    suspend fun cityListApi(token:String, state_id:String):
            Response<CityListResponse>

    suspend fun pinCodeListApi(token:String, city_id:String):
            Response<PincodeListResponse>

    suspend fun skills_listApi(token:String):
            Response<SkillsListResponse>

    suspend fun agora_generate_tokenApi(token:String,  astro_id: String, call_type: String):
            Response<AgoraGenerateTokenResponse>

    suspend fun add_comment(
        user_id: String,
        astro_id: String,
        message: String,
        type: String,
    ): Response<CommonResponse>

    suspend fun call_end_by_status(token:String,caller_id: String):
            Response<CallendbyuserResponse>

    suspend fun live_agora_generate_token(token:String,topic: String):
            Response<GoLiveResponse>

    suspend fun delete_live_astro(token:String):
            Response<CommonResponse>

    suspend fun checkOnlineStatusRepo(token:String):
            Response<CommonResponse>

    suspend fun live_agora_topic(token:String, topic:String):
            Response<GoLiveResponse>

    suspend fun astro_home(token:String):
            Response<CallChatStatusResponse>

    suspend fun chat_user_listApi(token:String):
            Response<ChatUserListResponse>

    suspend fun click_user_chat(token:String,user_id: String):
            Response<CommonResponse>

    suspend fun astro_give_review(token: String,astro_id: String,rating: String,review: String,):
            Response<GiveReviewResponse>

    suspend fun recent_otp(mobile_no: String,type: String,):
            Response<LoginResponse>

    suspend fun call_user_listApi(token:String):
            Response<CallUserListResponse>
    suspend fun banner(token:String):
            Response<BannerResponse>
    suspend fun AddAstroDetailsApi(token:String,
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
                                   experience :RequestBody
    ):
            Response<AddAstroDetailResponse>


    @GET("get_added_expertise")
    suspend fun get_added_expertiseApi(
        @Header("Authorization") authorization: String,
    ): Response<GetAddedExpertiseResponse>


    suspend fun add_expertiseApi(token:String, expertise_id:String):
            Response<AddExpertiseItemResponse>

    suspend fun delete_expertiseApi(token:String, id:String):
            Response<DeleteExpertiseItemResponse>

    suspend fun get_added_skillsApi(token:String):
            Response<GetAddedSkillsResponse>

    suspend fun add_skillsApi(token:String, skill_id:String):
            Response<AddSkillsItemResponse>

    suspend fun delete_skillsApi(token:String, id:String):
            Response<DeleteSkillsItemResponse>

    suspend fun get_added_languageApi(token:String):
            Response<GetAddedLanguageResponse>

    suspend fun add_languageApi(token:String, language_id:String):
            Response<AddLanguageItemResponse>

    suspend fun delete_languageApi(token:String, id:String):
            Response<DeleteLanguageItemResponse>

    suspend fun post_profile_updateApi(token:String, full_name:String, gender:String, experience:String):
            Response<PostProfileUpdateResponse>

    suspend fun experience_listApi(token:String):
            Response<ExperienceListResponse>

    suspend fun callHistoryListApi(token:String, /*dummy:String*/):
            Response<CallHistoryListResponse>

    suspend fun chatHistoryListApi(token:String,/* dummy:String*/):
            Response<ChatHistoryListResponse>

    suspend fun product_listApi(token:String):
            Response<ProductListResponse>

    suspend fun add_suggest_remedyApi(token:String, product_ids:String, user_id:String):
            Response<AddSuggestRemedyResponse>

    suspend fun suggest_remedy_listApi(token:String,user_id: String):
            Response<SuggestRemedyListResponse>

    suspend fun add_suggest_remedy_messageApi(token:String, user_id: String, message: String):
            Response<CommonResponse>

    suspend fun fix_session_detail(token:String,user_detail_id:String):
            Response<FixSessionDetail>

    suspend fun EmailUs(token:String):
            Response<EmailUs_Response>

    suspend fun callback_apply(token:String,mobile:String,discription:String):
            Response<CommonResponse>

    suspend fun aboutus(token:String):
            Response<AboutUsResponse>

    suspend fun get_chat_with_us(token:String):
            Response<GetCustomerSupportChat>

    suspend fun call_ring_status_save(token:String,channel_name: String):
            Response<call_ring_status_save_Response>

    suspend fun call_ring_end(token:String,channel_name: String):
            Response<CommonResponse>

    suspend fun send_chat_with_us(token:String,message:String):
            Response<CommonResponse>

    suspend fun notificationApi(token:String):
            Response<NotificationListResponse>

    suspend fun faq(token:String):
            Response<FAQResponse>

    suspend fun chat_list_MessageApi(token:String,  to_id: String):
            Response<ChatListMessageResponse>

    suspend fun call_end(token:String,timer:String,from_user: String,to_user: String,caller_id:String,type: String,fixed_session: String):
            Response<CommonResponse>
    suspend fun live_end(token:String,timer:String,from_user: String,to_user: String,type: String):
            Response<CommonResponse>
    suspend fun chat_end(token:String,timer:String,from_user: String,to_user: String,caller_id:String,type: String,fixed_session: String,):
            Response<CommonResponse>
    suspend fun check_chat_end(token:String,caller_id: String):
            Response<CheckChatEndResponse>
    suspend fun notification(token: String):
            Response<NotificationResponse>
    suspend fun chatagoraApi(token:String,  to_userId: String, message: String, type: String):
            Response<ChatAgoraResponse>

    suspend fun setting_detailsApi(token:String):
            Response<SettingDetailsGetResponse>

    suspend fun setting_updateApi(token:String, is_chat:String, is_audio_call:String, is_video_call:String):
            Response<SettingDetailsGetResponse>

    suspend fun GetCalender(token:String,date:String):
            Response<CalenderList>

    suspend fun manage_calendar_scheduleApi(token:String,  date: String, from_time: String, to_time: String):
            Response<ManageCalendarScheduleResponse>


    suspend fun calendar_schedule_deleteApi(token:String,  id: String):
            Response<ScheduleCalendarDeleteResponse>

    suspend fun call_request_detail_api(token:String,id:String):
            Response<CallRequestDetailResponse>

    suspend fun call_request_accecpt_api(token:String,id:String):
            Response<ChatRequestCancelResponse>

    suspend fun call_request_cancel_api(token:String,id:String,reason:String,comment:String,action_by: String,):
            Response<ChatRequestCancelResponse>

    suspend fun upcomingCompletedBookingsApi(token:String, type:String):
            Response<MyBookingsUpcomingCompletedResponse>

    suspend fun chat_request_detail_api(token:String,id:String):
            Response<ChatRequestDetailResponse>

   suspend fun ViewProfile(token:String):
           Response<ViewProfileResponse>

    suspend fun chat_request_accecpt_api(token:String,id:String):
            Response<ChatRequestCancelResponse>

    suspend fun chatcallReasonCancelListApi(token:String):
            Response<ChatCallCancelReasonResponse>

    suspend fun cancellationByUserApi(token:String):
            Response<CancellationByUserResponse>

    suspend fun chat_request_cancel_api(token:String,id:String,reason:String,comment:String):
            Response<ChatRequestCancelResponse>

    suspend fun Chathome(token:String):
            Response<Chat_Call_Response>

    suspend fun Callhome(token:String):
            Response<Chat_Call_Response>

    suspend fun confirmation_booking_listApi(token:String, type:String):
            Response<ConfirmationBookingResponse>

    suspend fun report_doc_upload(token:String,
                                  user_id: RequestBody,
                                  report_intake_id: RequestBody,
                                  text: RequestBody,
                                  file: MultipartBody.Part,
    ): Response<CommonResponse>

    suspend fun astro_earningApi(token:String, filter:String):
            Response<AstroEarningResponse>

    suspend fun fixed_session_requests(token:String):
            Response<FixedsessionResponseList>


    suspend fun request_money(token:String, money:String):
            Response<CommonResponse>


    suspend fun fixed_session_request_accecpt(token:String,id:String):
            Response<ChatRequestCancelResponse>

    suspend fun UpdateBank(token:String,bank_name:String,account_no: String,acc_holder_name: String,ifsc_code: String,branch: String):
            Response<BankDetailResponse>

    suspend fun GetBankDetail(token:String):
            Response<GetBankDetail>

    suspend fun get_live_comments(
        token: String,
        channel_name: String,
        ): Response<LiveCommentsModelClass>

//   suspend fun Expertize(token:String):
//           Response<ExpertizeResponse>
//
//   suspend fun Liveastrologersviewall(token:String):
//           Response<LiveastrologerviewallResponse>
//
//   suspend fun RemedySuggested(token:String):
//           Response<RemedySuggestedResponse>
//
//   suspend fun RemedyProduct(token:String):
//           Response<RemedyProductResponse>
//
//   suspend fun ShopwithusViewall(token:String):
//           Response<ShopwithusViewallResponse>
//
//   suspend fun Chat(token:String):
//           Response<ChatFragmentResponse>
//
//   suspend fun CategoryList(token:String,id:String):
//           Response<CategoryProductResponse>
//
//   suspend fun ProductDetails(token:String,id:String):
//           Response<ProductResponse>
//
//   suspend fun Call(token:String):
//           Response<ChatFragmentResponse>
//   suspend fun EditProfile(token:String, name: RequestBody, mobile_no: RequestBody, email: RequestBody, dob: RequestBody, profile: MultipartBody.Part):
//           Response<LoginverificationResponse>
    suspend fun confirmation_booking_report_detail(token:String,report_intakes_id: String):
        Response<ConfirmationReportHistoryResponse>

    suspend fun astro_rating_review_listApi(token:String):
            Response<GetAstroRatingReviewListResponse>

    suspend fun rating_review_pinselected_listApi(token:String):
            Response<GetAstroRatingReviewPinResponse>

    suspend fun review_pin_updateApi(token:String, id:String, type:String):
            Response<ReviewPinUpdateResponse>


    suspend fun astro_history_wallets(token:String):
            Response<AstroEarningListResponse>

    suspend fun EditProfile(token:String, name: RequestBody, mobile_no: RequestBody, email: RequestBody, gender: RequestBody, profile: MultipartBody.Part,
                            address: RequestBody,
                            state_id: RequestBody,
                            /*expertise: RequestBody,
                            language: RequestBody,
                            skills: RequestBody,*/
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
            Response<LoginverificationResponse>



    suspend fun refund_money(token:String,order_id: String):
            Response<CommonResponse>

    suspend fun call_ring(token: String,astro_id:String,user_id:String,request_id: String):
            Response<CallRingResponse>


//   suspend fun StateList(): Response<StateList>
//
//   suspend fun CityList(id:Int): Response<CityList>
//
//   suspend fun banner(token:String):
//           Response<BannerResponse>
//
//   suspend fun OrderDetails(token:String,product_id:String,address_id:String): Response<MyCartResponse>
//
//   suspend fun HistoryWallet(token: String):
//           Response<HistoryWallet>
//
//   suspend fun history_call(token: String):
//           Response<CallHistoryResponse>
//
//   suspend fun history_chat(token: String):
//           Response<ChatHistoryResponse>
//   suspend fun history_reports(token: String):
//           Response<ReportsHistoryResponse>
//   suspend fun OrderPlaced(
//      token: String,
//      product_id: String,
//      product_price: String,
//      shipping_chard: String,
//      coupon_discount: String,
//      grand_total: String,
//      coupon_code: String,
//      address_id: String,
//      qty: String,
//      transaction_id: String,
//      payment_status: String,
//      payment_type: String,
//   ): Response<PlaceOrderResponse>
//
//   suspend fun history_products(token: String,type:String):
//           Response<HistoryProductSuggestedResponse>
//
//   suspend fun user_my_reveiw(token: String):
//           Response<MyReviewsResponse>
//
//   suspend fun my_orders(token: String,type:String):
//           Response<MyOrdersEcommersProductResponse>
//
//   suspend fun booking_detail(token: String,order_id:String):
//           Response<BookingDetailsResponse>
//
//   suspend fun strologer_details(token: String, id: String,):
//           Response<AstrorahiResponse>
//
//   suspend fun consultancy_order(token: String):
//           Response<ConsultancyOrderResponse>
//
//   suspend fun consultancy_order_detail(token: String, id: String):
//           Response<ConsultancyDetail>
//
//
//   suspend fun user_give_review(token: String,astro_id: String,rating: String,review: String,):
//           Response<GiveReviewResponse>
//
//   suspend fun strologer_availability(token: String,id: String,):Response<AvailabilityResponse>
//
//   suspend fun MatchMaking(
//      token: String,
//      dob_boy: String,
//      birth_time_boy: String,
//      place_birth_boy: String,
//      occupation_boy: String,
//      maritial_status_boy: String,
//      topic_consultation_boy: String,
//      dob_girl: String,
//      birth_time_girl: String,
//      place_birth_girl: String,
//      occupation_girl: String,
//      maritial_status_girl: String,
//      topic_consultation_girl: String,
//   ): Response<MatchMakingResponse>
//
//   suspend fun Language(token:String):
//           Response<LanguageResponse>
//   suspend fun add_user_details_first(
//      token: String,
//      dob: String,
//      birth_time: String,
//      place_birth: String,
//      occupation: String,
//      maritial_status: String,
//      topic_consultation: String,
//      language_id: String,
//      astro_id: String,
//      request_type: String,
//   ):
//           Response<IntakeResponse>
//
//
//   suspend fun agora_generate_tokenApi(token:String,  astro_id: String, call_type: String):
//           Response<AgoraGenerateTokenResponse>
//
//   suspend fun agora_create_userApi(token:String, to_userId:String, nickname:String):
//           Response<AgoraCreateUserResponse>
//
//   suspend fun chat_list_MessageApi(token:String,  to_id: String):
//           Response<ChatListMessageResponse>
//
//   suspend fun chatagoraApi(token:String,  to_userId: String, message: String, type: String):
//           Response<ChatAgoraResponse>
//
//   suspend fun reason_cancel_list(token: String):
//           Response<CancelReasonResponse>
//   suspend fun strologers_list(token: String):
//           Response<AstrologersListViewAllResponse>
//
//   suspend fun request_for_report(
//      token: String,
//      dob: String,
//      birth_time: String,
//      place_birth: String,
//      occupation: String,
//      maritial_status: String,
//      topic_consultation: String,
//      astro_id: String,
//   ):
//           Response<CommonResponse>
//
//   suspend fun delete_address(token:String,id:String):
//           Response<CommonResponse>
//
//   suspend fun cancel_order(token:String,id:String,reason_ids: String,write_reason: String,):
//           Response<CommonResponse>
//
//   suspend fun call_end(token:String,timer:String,from_user: String,to_user: String,caller_id:String):
//           Response<CommonResponse>
//
//   suspend fun when_call_start(token:String,astro_id: String):
//           Response<CallStartResponse>
//
//   suspend fun add_money(token:String,amount: String,payment_status: String,transaction_id: String,):
//           Response<CommonResponse>
//
//   suspend fun get_report_astro_list(token: String):
//           Response<AstrologersListViewAllResponse>
//
//   suspend fun get_report_astro_details(token: String,astro_id:String):
//           Response<GetAstroDetailsResponse>



}




