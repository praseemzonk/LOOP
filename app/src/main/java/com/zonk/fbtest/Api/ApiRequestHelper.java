package com.zonk.fbtest.Api;

import com.zonk.fbtest.Fbtest;
import com.zonk.fbtest.Model.Shout;
import com.zonk.fbtest.Model.Skill;
import com.zonk.fbtest.Model.User;
import com.zonk.fbtest.R;
import com.zonk.fbtest.Utils.DataMapParser;
import com.zonk.fbtest.Utils.Logger;

import org.codehaus.jackson.map.ObjectMapper;

import java.net.HttpRetryException;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;


public class ApiRequestHelper {

    private static final String LOG_TAG = "ApiRequestHelper";
    private static ApiRequestHelper instance;
    private ZonkClientService zonkClientService;
    private Fbtest application;


    public static synchronized ApiRequestHelper init(Fbtest application) {
        if (null == instance) {
            instance = new ApiRequestHelper();
            instance.setApplication(application);
            instance.createRestAdapter();
        }
        return instance;
    }





    public void submitPhno( String id,final onRequestComplete onRequestComplete) {
        zonkClientService.submitPhno(id,new Callback<ApiResponse>() {

            public void success(ApiResponse apiResponse, Response response) {
                if (apiResponse.isSuccess()) {
                    Map<String, Object> responseMap = (Map<String, Object>) apiResponse.getData();
                    onRequestComplete.onSuccess(null);
                } else {
                    onRequestComplete.onFailure(apiResponse);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                onRequestComplete.onFailure(new ApiResponse().setError(ApiResponse.ApiError.COMMUNICATION_ERROR));
            }
        });
    }




    public void addMessage( String id, String message,final onRequestComplete onRequestComplete) {
        zonkClientService.addMessage(id, message,new Callback<ApiResponse>() {

            public void success(ApiResponse apiResponse, Response response) {
                if (apiResponse.isSuccess()) {
                    Map<String, Object> responseMap = (Map<String, Object>) apiResponse.getData();
                    onRequestComplete.onSuccess(null);
                } else {
                    onRequestComplete.onFailure(apiResponse);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                onRequestComplete.onFailure(new ApiResponse().setError(ApiResponse.ApiError.COMMUNICATION_ERROR));
            }
        });
    }







    public void getMyCopntacts( final onRequestComplete onRequestComplete) {
        zonkClientService.getMyCopntacts(new Callback<ApiResponse>() {

            public void success(ApiResponse apiResponse, Response response) {
                if (apiResponse.isSuccess()) {
                    Map<String, Object> responseMap = (Map<String, Object>) apiResponse.getData();
                    onRequestComplete.onSuccess(DataMapParser.parseUsers(responseMap));
                } else {
                    onRequestComplete.onFailure(apiResponse);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                onRequestComplete.onFailure(new ApiResponse().setError(ApiResponse.ApiError.COMMUNICATION_ERROR));
            }
        });
    }

    public void getMyShout( final onRequestComplete onRequestComplete) {
        zonkClientService.getMyShout(new Callback<ApiResponse>() {

            public void success(ApiResponse apiResponse, Response response) {
                if (apiResponse.isSuccess()) {
                    Map<String, Object> responseMap = (Map<String, Object>) apiResponse.getData();
                    onRequestComplete.onSuccess(DataMapParser.parseShouts(responseMap));
                } else {
                    onRequestComplete.onFailure(apiResponse);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                onRequestComplete.onFailure(new ApiResponse().setError(ApiResponse.ApiError.COMMUNICATION_ERROR));
            }
        });
    }

    public void addContact(String a, final onRequestComplete onRequestComplete) {
        zonkClientService.addContact(a,new Callback<ApiResponse>() {

            public void success(ApiResponse apiResponse, Response response) {
                if (apiResponse.isSuccess()) {
                    Map<String, Object> responseMap = (Map<String, Object>) apiResponse.getData();
                    onRequestComplete.onSuccess(DataMapParser.parseUser(responseMap));
                } else {
                    onRequestComplete.onFailure(apiResponse);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                onRequestComplete.onFailure(new ApiResponse().setError(ApiResponse.ApiError.COMMUNICATION_ERROR));
            }
        });
    }


    public void getUser(String a, final onRequestComplete onRequestComplete) {
        zonkClientService.getUser(a,new Callback<ApiResponse>() {

            public void success(ApiResponse apiResponse, Response response) {
                if (apiResponse.isSuccess()) {
                    Map<String, Object> responseMap = (Map<String, Object>) apiResponse.getData();
                    onRequestComplete.onSuccess(DataMapParser.parseUser(responseMap));
                } else {
                    onRequestComplete.onFailure(apiResponse);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                onRequestComplete.onFailure(new ApiResponse().setError(ApiResponse.ApiError.COMMUNICATION_ERROR));
            }
        });
    }



    public void sendLocation(double lat, double lon, final onRequestComplete onRequestComplete) {
        zonkClientService.sendLocation(lat, lon,new Callback<ApiResponse>() {

            public void success(ApiResponse apiResponse, Response response) {
                if (apiResponse.isSuccess()) {
                    Map<String, Object> responseMap = (Map<String, Object>) apiResponse.getData();
                    onRequestComplete.onSuccess(null);
                } else {
                    onRequestComplete.onFailure(apiResponse);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                onRequestComplete.onFailure(new ApiResponse().setError(ApiResponse.ApiError.COMMUNICATION_ERROR));
            }
        });
    }

    public void myshoutsAvailable(final onRequestComplete onRequestComplete) {
        zonkClientService.myshoutsAvailable(new Callback<ApiResponse>() {

            public void success(ApiResponse apiResponse, Response response) {
                if (apiResponse.isSuccess()) {
                    Map<String, Object> responseMap = (Map<String, Object>) apiResponse.getData();
                    onRequestComplete.onSuccess(DataMapParser.parseshoutAvailable(responseMap));
                } else {
                    onRequestComplete.onFailure(apiResponse);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                onRequestComplete.onFailure(new ApiResponse().setError(ApiResponse.ApiError.COMMUNICATION_ERROR));
            }
        });
    }

    public void getShouts(final onRequestComplete onRequestComplete) {
        zonkClientService.getShouts(new Callback<ApiResponse>() {

            public void success(ApiResponse apiResponse, Response response) {
                if (apiResponse.isSuccess()) {
                    Map<String, Object> responseMap = (Map<String, Object>) apiResponse.getData();
                    onRequestComplete.onSuccess(DataMapParser.parseShouts(responseMap));
                } else {
                    onRequestComplete.onFailure(apiResponse);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                onRequestComplete.onFailure(new ApiResponse().setError(ApiResponse.ApiError.COMMUNICATION_ERROR));
            }
        });
    }

    public void makeshout(Shout shout, final onRequestComplete onRequestComplete) {
        zonkClientService.makeshout(shout,new Callback<ApiResponse>() {

            public void success(ApiResponse apiResponse, Response response) {
                if (apiResponse.isSuccess()) {
                    Map<String, Object> responseMap = (Map<String, Object>) apiResponse.getData();
                    onRequestComplete.onSuccess(null);
                } else {
                    onRequestComplete.onFailure(apiResponse);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                onRequestComplete.onFailure(new ApiResponse().setError(ApiResponse.ApiError.COMMUNICATION_ERROR));
            }
        });
    }
    public void setUserLocation(double lat, double lon, final onRequestComplete onRequestComplete) {
        zonkClientService.setUserLocation(lat, lon,new Callback<ApiResponse>() {

            public void success(ApiResponse apiResponse, Response response) {
                if (apiResponse.isSuccess()) {
                    Map<String, Object> responseMap = (Map<String, Object>) apiResponse.getData();
                    onRequestComplete.onSuccess(null);
                } else {
                    onRequestComplete.onFailure(apiResponse);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                onRequestComplete.onFailure(new ApiResponse().setError(ApiResponse.ApiError.COMMUNICATION_ERROR));
            }
        });
    }
    public void getMyFriends(final onRequestComplete onRequestComplete) {
        zonkClientService.getMyFriends(new Callback<ApiResponse>() {

            public void success(ApiResponse apiResponse, Response response) {
                if (apiResponse.isSuccess()) {
                    Map<String, Object> responseMap = (Map<String, Object>) apiResponse.getData();
                    onRequestComplete.onSuccess(DataMapParser.parseFriends(responseMap));
                } else {
                    onRequestComplete.onFailure(apiResponse);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                onRequestComplete.onFailure(new ApiResponse().setError(ApiResponse.ApiError.COMMUNICATION_ERROR));
            }
        });
    }
    public void setLN(User user, final onRequestComplete onRequestComplete) {
        zonkClientService.setLN(user, new Callback<ApiResponse>() {

            public void success(ApiResponse apiResponse, Response response) {
                if (apiResponse.isSuccess()) {
                    Map<String, Object> responseMap = (Map<String, Object>) apiResponse.getData();
                    onRequestComplete.onSuccess(null);
                } else {
                    onRequestComplete.onFailure(apiResponse);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                onRequestComplete.onFailure(new ApiResponse().setError(ApiResponse.ApiError.COMMUNICATION_ERROR));
            }
        });
    }
    public void addSkillsLevel(List<Skill> skills, final onRequestComplete onRequestComplete) {
        zonkClientService.addSkillsLevel(skills, new Callback<ApiResponse>() {

            public void success(ApiResponse apiResponse, Response response) {
                if (apiResponse.isSuccess()) {
                    Map<String, Object> responseMap = (Map<String, Object>) apiResponse.getData();
                    onRequestComplete.onSuccess(null);
                } else {
                    onRequestComplete.onFailure(apiResponse);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                onRequestComplete.onFailure(new ApiResponse().setError(ApiResponse.ApiError.COMMUNICATION_ERROR));
            }
        });
    }


    public void addSkills(List<Skill> skills, final onRequestComplete onRequestComplete) {
        zonkClientService.addSkills(skills, new Callback<ApiResponse>() {

            public void success(ApiResponse apiResponse, Response response) {
                if (apiResponse.isSuccess()) {
                    Map<String, Object> responseMap = (Map<String, Object>) apiResponse.getData();
                    onRequestComplete.onSuccess(null);
                } else {
                    onRequestComplete.onFailure(apiResponse);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                onRequestComplete.onFailure(new ApiResponse().setError(ApiResponse.ApiError.COMMUNICATION_ERROR));
            }
        });
    }



    public void getMySkills( final onRequestComplete onRequestComplete) {
        zonkClientService.getMySkills( new Callback<ApiResponse>() {

            public void success(ApiResponse apiResponse, Response response) {
                if (apiResponse.isSuccess()) {
                    Map<String, Object> responseMap = (Map<String, Object>) apiResponse.getData();
                    onRequestComplete.onSuccess(DataMapParser.parseSkills(responseMap));
                } else {
                    onRequestComplete.onFailure(apiResponse);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                onRequestComplete.onFailure(new ApiResponse().setError(ApiResponse.ApiError.COMMUNICATION_ERROR));
            }
        });
    }
    public void getSkills( final onRequestComplete onRequestComplete) {
        zonkClientService.getSkills( new Callback<ApiResponse>() {

            public void success(ApiResponse apiResponse, Response response) {
                if (apiResponse.isSuccess()) {
                    Map<String, Object> responseMap = (Map<String, Object>) apiResponse.getData();
                    onRequestComplete.onSuccess(DataMapParser.parseSkills(responseMap));
                } else {
                    onRequestComplete.onFailure(apiResponse);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                onRequestComplete.onFailure(new ApiResponse().setError(ApiResponse.ApiError.COMMUNICATION_ERROR));
            }
        });
    }
    public void moveToFcm(String id, final onRequestComplete onRequestComplete) {
        zonkClientService.moveToFcm(id, new Callback<ApiResponse>() {

            public void success(ApiResponse apiResponse, Response response) {
                if (apiResponse.isSuccess()) {
                    Map<String, Object> responseMap = (Map<String, Object>) apiResponse.getData();
                    onRequestComplete.onSuccess(null);
                } else {
                    onRequestComplete.onFailure(apiResponse);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                onRequestComplete.onFailure(new ApiResponse().setError(ApiResponse.ApiError.COMMUNICATION_ERROR));
            }
        });
    }

    public void login(User userDto, final onRequestComplete onRequestComplete) {
        zonkClientService.login(userDto, new Callback<ApiResponse>() {

            public void success(ApiResponse apiResponse, Response response) {
                if (apiResponse.isSuccess()) {
                    Map<String, Object> responseMap = (Map<String, Object>) apiResponse.getData();
                    onRequestComplete.onSuccess(DataMapParser.parseAuthToken(responseMap));
                } else {
                    onRequestComplete.onFailure(apiResponse);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                onRequestComplete.onFailure(new ApiResponse().setError(ApiResponse.ApiError.COMMUNICATION_ERROR));
            }
        });
    }


    /**
     * Add all the api request's here
     */









//
//
//    public void getFbInsight( final onRequestComplete onRequestComplete) {
//        zonkClientService.getFbInsight(new Callback<ApiResponse>() {
//
//            public void success(ApiResponse apiResponse, Response response) {
//                if (apiResponse.isSuccess()) {
//                    Map<String, Object> responseMap = (Map<String, Object>) apiResponse.getData();
//                    onRequestComplete.onSuccess(DataMapParser.parseFbuser(responseMap));
//                } else {
//                    onRequestComplete.onFailure(apiResponse);
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError retrofitError) {
//                onRequestComplete.onFailure(new ApiResponse().setError(ApiResponse.ApiError.COMMUNICATION_ERROR));
//            }
//        });
//    }




    /**
     * REST Adapter Configuration
     */
    private void createRestAdapter() {
        ObjectMapper objectMapper = new ObjectMapper();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setConverter(new JacksonConverter(objectMapper))
                .setErrorHandler(new ErrorHandler() {
                    @Override
                    public Throwable handleError(RetrofitError error) {
                        application.getLogger().debug("REST Error");
                        final Response response = error.getResponse();
                        if (response != null) {
                            int statusCode = response.getStatus();
                            if (error.isNetworkError() || (500 <= statusCode && statusCode < 600)) {
                                return new HttpRetryException(Logger.TAG, statusCode);
                            }
                        }
                        return error;
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog(Logger.TAG))
                .setEndpoint(application.getResources().getString(R.string.server_url))
                .setRequestInterceptor(getRequestInterceptor())
                .build();

        zonkClientService = restAdapter.create(ZonkClientService.class);
    }


//
//    public void  getPlaceSearchResult(String searchTerm, final onRequestComplete onRequestComplete) {
//        zonkClientService.getPlaceSearchResults(searchTerm, new Callback<ApiResponse>() {
//            @Override
//            public void success(ApiResponse apiResponse, Response response) {
//                if (apiResponse.isSuccess()) {
//                    Map<String, Object> dataMap = (Map<String, Object>) apiResponse.getData();
//                    onRequestComplete.onSuccess(DataMapParser.parsePlaces(dataMap));
//                } else {
//                    onRequestComplete.onFailure(apiResponse);
//                }
//            }
//            @Override
//            public void failure(RetrofitError retrofitError) {
//                onRequestComplete.onFailure(new ApiResponse().setError(ApiResponse.ApiError.COMMUNICATION_ERROR));
//            }
//        });
//    }

    /**
     * End api requests
     */

    private RequestInterceptor getRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                //Add Headers here
                request.addHeader("Accept", "application/json");
//                request.addHeader("api_key", application.getString(R.string.api_key));

                if (application.getPreferences().addAuthInHeader()) {
                    String authToken = application.getPreferences().getAuthToken();
                    request.addHeader("auth_token", authToken);
                }
            }
        };
    }

    /**
     * End REST Adapter Configuration
     */

    public void setZonkClientService(ZonkClientService zonkClientService) {
        this.zonkClientService = zonkClientService;
    }

    public Fbtest getApplication() {
        return application;
    }

    public void setApplication(Fbtest application) {
        this.application = application;
    }

    public interface onRequestComplete {
        void onSuccess(Object object);

        void onFailure(ApiResponse apiResponse);
    }

}