package com.zonk.fbtest;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

import com.facebook.accountkit.AccountKit;
import com.zonk.fbtest.Api.ApiRequestHelper;
import com.zonk.fbtest.Utils.Logger;


public class Fbtest extends Application {

    private ApiRequestHelper apiRequestHelper;
    private Logger logger;
    private Preferences preferences;
    public static final String TAG = Fbtest.class
            .getSimpleName();

    private static Fbtest mInstance;

    public static synchronized Fbtest getInstance() {
        return mInstance;
    }





    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }



    @Override
    public void onCreate() {
        super.onCreate();
        doInit();
        AccountKit.initialize(getApplicationContext());

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        mInstance = this;
    }

    private void doInit() {
        apiRequestHelper = ApiRequestHelper.init(this);
        logger = Logger.init(this);
        preferences = new Preferences(this);
    }

    public synchronized ApiRequestHelper getApiRequestHelper() {
        return apiRequestHelper;
    }

    public synchronized Logger getLogger() {
        return logger;
    }

    public void showToast(String message) {
        if (null != message)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int stringResId) {
        String message = getString(stringResId);
        if (null != message)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    public synchronized Preferences getPreferences() {
        return preferences;
    }



}
