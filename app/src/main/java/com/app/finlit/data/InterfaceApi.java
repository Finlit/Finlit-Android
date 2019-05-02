package com.app.finlit.data;



import com.app.finlit.data.models.ActivePlanModel;
import com.app.finlit.data.models.BlogModel;
import com.app.finlit.data.models.ChatModel;
import com.app.finlit.data.models.DateStatusModel;
import com.app.finlit.data.models.GetCommentsModel;
import com.app.finlit.data.models.NotificationModel;
import com.app.finlit.data.models.QuestionsModel;
import com.app.finlit.data.models.StatusModel;
import com.app.finlit.data.models.SubscriptionModel;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.params.ImageModel;
import com.app.finlit.data.params.ValidateParams;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;


import java.util.Map;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface InterfaceApi {

    String BASE_URL = "http://3.16.159.98:3009/api/";


    @POST("users/signup")
    Call<DataResponse<UserModel>> signUp(@Body UserModel userModel);

    @POST("users/signin")
    Call<DataResponse<UserModel>> signIn(@Body UserModel userModel);

    @POST("users/forgetPassword")
    Call<DataResponse<UserModel>> forgetPassword(@Body UserModel model);

    @PUT("users/{id}")
    Call<DataResponse<UserModel>> updateProfile(@Path("id") String id, @Body UserModel model);


    @POST("users/verify")
    Call<DataResponse<UserModel>> validatePin(@Body ValidateParams params);

    @POST("users/resend")
    Call<DataResponse<UserModel>> resendCode(@Body UserModel userModel);


    @GET("questions")
    Call<PageResponse<QuestionsModel>> getQuestions(@Query("pageSize") int pageSize);

    @GET("users/list/search")
    Call<PageResponse<UserModel>> getUsers(@QueryMap Map<String, String> queries);

    @GET("users/date")
    Call<PageResponse<UserModel>> getDateFilter(@QueryMap Map<String, String> queries);

    @GET("users/{id}")
    Call<DataResponse<UserModel>> getUserById(@Path("id") String id);

    @Multipart
    @POST("users/upload/image/{id}")
    Call<DataResponse<ImageModel>> uploadUserImage(@Path("id") String id, @Part MultipartBody.Part... args);

    @GET("notifications")
    Call<PageResponse<NotificationModel>> getNotifications(@Query("pageSize") int pageSize, @Query("pageNo") int pageNo);

    @POST("users/favourite/{id}")
    Call<DataResponse> createFavourite(@Path("id") String userId);

    @POST("users/unFavourite/{id}")
    Call<DataResponse> createUnFavourite(@Path("id") String userId);

    @POST("users/change/status/{id}")
    Call<DataResponse> findMeDate(@Path("id") String userId,@Body StatusModel statusModel);

    @GET("users")
    Call<PageResponse<DateStatusModel>> getDates(@QueryMap Map<String, String> queries);


    @POST("users/usertruethank/{id}")
    Call<DataResponse> getCancelDates(@Path("id") String userId);

    @POST("users/userintrested/{id}")
    Call<DataResponse> getintrest(@Path("id") String userId);

    @POST("users/userconfirmthank/{id}")
    Call<DataResponse> geteditDates(@Path("id") String userId);


    @POST("users/userpending/{id}")
    Call<DataResponse> sendDates(@Path("id") String userId,@Body DateStatusModel model);

    @POST("users/userconfirmed/{id}")
    Call<DataResponse> getconfirmDates(@Path("id") String userId);


    @POST("chats")
    Call<DataResponse<ChatModel>> createChat(@Body ChatModel model);

    @GET("chats")
    Call<PageResponse<ChatModel>> getChats(@Query("pageSize") int pageSize, @Query("pageNo") int pageNo);

    @PUT("chats/setZeroUnreadCount/{id}")
    Call<DataResponse> setZeroUnreadCount(@Path("id") String id);

    @PUT("chats/incUnreadCount/{id}")
    Call<DataResponse> increaseUnreadCount(@Path("id") String id, @Body ChatModel model);

    @DELETE("chats/{id}")
    Call<DataResponse> deleteChat(@Path("id") String id);

    @PUT("chats/block/{id}")
    Call<DataResponse> blockUser(@Path("id") String id);

    @PUT("chats/unblock/{id}")
    Call<DataResponse> unBlockUser(@Path("id") String id);

    @GET("plans")
    Call<PageResponse<SubscriptionModel>> getSubscriptionDetail();

    @GET("blogs")
    Call<PageResponse<BlogModel>> getBlogs();

    @POST("blogs/like/{id}")
    Call<DataResponse> createLike(@Path("id") String postId);

    @POST("blogs/dislike/{id}")
    Call<DataResponse> createDislike(@Path("id") String postId);

    @POST("comments")
    Call<DataResponse<GetCommentsModel>> createComment( @Body GetCommentsModel model);

    @GET("comments")
    Call<PageResponse<GetCommentsModel>> getComments(@QueryMap Map<String, String> map);

    @POST("userPlans")
    Call<DataResponse> createPlan(@Body SubscriptionModel model);

    @GET("userPlans/active/current")
    Call<DataResponse<ActivePlanModel>> getActivePlan();


    @GET("users/block/search")
    Call<PageResponse<UserModel>> getBlockedUsers();

    @POST("users/update/block")
    Call<DataResponse> blockUser(@Body UserModel model);

    @POST("users/update/unblock")
    Call<DataResponse> unBlockUser(@Body UserModel model);


    @GET("users/pending")
    Call<PageResponse<DateStatusModel>> getPendingDates(@QueryMap Map<String, String> queries);

}
