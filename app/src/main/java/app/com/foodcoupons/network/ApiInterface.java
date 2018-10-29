package app.com.foodcoupons.network;

import app.com.foodcoupons.models.CategoryModel;
import app.com.foodcoupons.models.DealModel;
import app.com.foodcoupons.models.FilterModel;
import app.com.foodcoupons.models.GooglePlaceModal;
import app.com.foodcoupons.models.NearbyPlaceModel;
import app.com.foodcoupons.models.UserModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface ApiInterface {

    //    //API Method to get Questions From API

    @GET
    public Call<GooglePlaceModal> getGooglePlaces(@Url String url);

    @GET
    public Call<NearbyPlaceModel> getGoogleNearByPlaces(@Url String url);

    @FormUrlEncoded
    @POST("phone_registration")
    Call<UserModel> phone_registration(@Field("login_via") String login_via,
                                       @Field("country_code") String country_code,
                                       @Field("phone_number") String phone_number,
                                       @Field("device_token") String device_token,
                                       @Field("device_type") String device_type);

    @FormUrlEncoded
    @POST("verify_otp")
    Call<UserModel> verify_otp(@Field("access_token") String access_token,
                               @Field("otp") String otp);


    @FormUrlEncoded
    @POST("resend_otp")
    Call<UserModel> resend_otp(@Field("access_token") String access_token);


    @FormUrlEncoded
    @POST("social_login")
    Call<UserModel> social_login(@Field("login_via") String login_via,
                                 @Field("social_media_id") String country_code,
                                 @Field("device_token") String device_token,
                                 @Field("device_type") String device_type,
                                 @Field("name") String name,
                                 @Field("email") String email,
                                 @Field("profile_pic") String profile_pic);

    @FormUrlEncoded
    @POST("get_all_deal")
    Call<DealModel> get_all_deal(@Field("access_token") String access_token,
                                 @Field("longitude") String longitude,
                                 @Field("latitude") String latitude,
                                 @Field("sort") String sort,
                                 @Field("store") String store_id,
                                 @Field("brand_id") String brand_id,
                                 @Field("max") String max,
                                 @Field("min") String min,
                                 @Field("offset") String offset,
                                 @Field("category") String category,
                                 @Field("search") String search,
                                 @Field("type") String type);

    @FormUrlEncoded
    @POST("get_top_deal")
    Call<DealModel> get_top_deal(@Field("access_token") String access_token,
                                 @Field("longitude") String longitude,
                                 @Field("latitude") String latitude);

    @Multipart
    @POST("create_profile")
    Call<UserModel> create_profile(@Part("name") RequestBody name,
                                   @Part("access_token") RequestBody access_token,
                                   @Part("email") RequestBody email,
                                   @Part MultipartBody.Part profile_pic);

    @FormUrlEncoded
    @POST("get_brands")
    Call<FilterModel> get_brands(@Field("access_token") String access_token);

    @FormUrlEncoded
    @POST("deal_detail_by_id")
    Call<DealModel> deal_detail_by_id(@Field("access_token") String access_token,
                                                @Field("deal_id") String deal_id);

    @GET("get_category")
    Call<CategoryModel> get_category();


//    @FormUrlEncoded
//    @PUT("/api/user/getUserDocuments")
//    Call<ActsModel> getActsUserDocuments(@Field("category") String category);
//
//    @FormUrlEncoded
//    @PUT("/api/user/getUserDocuments")
//    Call<BillsModel> getBillsUserDocuments(@Field("category") String category);
//
//    @GET("/api/user/getUserDirectory")
//    Call<SenatorDetailModel> getUserDirectory();
//

//
//    @FormUrlEncoded
//    @PUT("/api/user/sendEmail")
//    Call<SenatorDetailModel> sendEmail(@Field("senator") String senator,
//                                       @Field("message") String message,
//                                       @Field("NAME") String NAME,
//                                       @Field("EMAIL") String EMAIL);
//
//    @GET
//    Call<YoutubeModel> getYoutubeVideos(@Url String url);
//
//    @GET("/api/user/getInformation")
//    Call<InformationModel> getInformation();
//
//
//    @GET("/api/user/getDeletedDocuments")
//    Call<DeleteDataModel> getDeletedDocuments();

}