package com.zonk.fbtest;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.zonk.fbtest.Api.ApiRequestHelper;
import com.zonk.fbtest.Api.ApiResponse;

/**
 * Created by Belal on 5/27/2016.
 */


//Class extending FirebaseInstanceIdService
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    Fbtest fbtest;
    @Override
    public void onTokenRefresh() {

        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        fbtest= (Fbtest) getApplication();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        fbtest.getApiRequestHelper().moveToFcm(refreshedToken, new ApiRequestHelper.onRequestComplete() {
            @Override
            public void onSuccess(Object object) {


            }

            @Override
            public void onFailure(ApiResponse apiResponse) {

            }
        });

    }

    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
        //Not required for current project
    }
}
