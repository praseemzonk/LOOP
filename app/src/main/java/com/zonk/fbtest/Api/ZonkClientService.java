package com.zonk.fbtest.Api;


import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Query;


public interface ZonkClientService {

    @POST(value = "/clientsignup")
    void signup(@Body Object object, Callback<ApiResponse> callback);



    @PUT(value = "/user/addconnect")
    void addContact(@Query(value = "loginId") String lat,Callback<ApiResponse> callback);

    @GET(value = "/user/activeshoutout")
    void myshoutsAvailable(Callback<ApiResponse> callback);




    @PUT(value = "/user/addphno")
    void submitPhno(@Query(value = "phNo") String lat,Callback<ApiResponse> callback);



    @PUT(value = "/user/addmessage")
    void addMessage(@Query(value = "_id") String lat,@Query(value = "message") String laat,Callback<ApiResponse> callback);



    @GET(value = "/user/mycontacts")
    void getMyCopntacts(Callback<ApiResponse> callback);

    @GET(value = "/user/myshoutouts")
    void getMyShout(Callback<ApiResponse> callback);



    @GET(value = "/user/userprofile")
    void getUser(@Query(value = "loginId") String lat,Callback<ApiResponse> callback);

    @GET(value = "/user/allshoutouts")
    void getShouts(Callback<ApiResponse> callback);


    @POST(value = "/user/shoutout")
    void makeshout(@Body Object object, Callback<ApiResponse> callback);



    @PUT(value = "/user/linkedin")
    void setLN(@Body Object object, Callback<ApiResponse> callback);



    @PUT(value = "/user/userloc")
    void setUserLocation(@Query(value = "lat") Double lat,@Query(value = "lon") Double lon,Callback<ApiResponse> callback);




    @PUT(value = "/user/shoutoutusernew")
    void sendLocation(@Query(value = "lat") Double lat,@Query(value = "lon") Double lon,Callback<ApiResponse> callback);


    @GET(value = "/user/myfriends")
    void getMyFriends(Callback<ApiResponse> callback);


    @PUT(value = "/user/addskillslevel")
    void addSkillsLevel(@Body Object object, Callback<ApiResponse> callback);



    @PUT(value = "/user/addskills")
    void addSkills(@Body Object object, Callback<ApiResponse> callback);

    @POST(value = "/login")
    void login(@Body Object object, Callback<ApiResponse> callback);

    @GET(value = "/user/skills")
    void getSkills(Callback<ApiResponse> callback);

    @GET(value = "/user/myskills")
    void getMySkills(Callback<ApiResponse> callback);




    @PUT("/user/movetofcm")
    void moveToFcm(@Query(value = "fcmId") String fcmId, Callback<ApiResponse> callback);



}