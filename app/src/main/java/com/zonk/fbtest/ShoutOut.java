package com.zonk.fbtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.zonk.fbtest.Api.ApiRequestHelper;
import com.zonk.fbtest.Api.ApiResponse;

import java.util.Map;

/**
 * Created by Ribbi on 11/19/2017.
 */

public class ShoutOut extends Service {

    private static final long LOOP_TIME = 120 * 1000;
    private static final String TAG = ShoutOut.class.getSimpleName();
    private Fbtest fbtest;
    final Handler mHandler = new Handler();
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {





            fbtest.getApiRequestHelper().sendLocation(fbtest.getPreferences().getUserDTO().getLat(),fbtest.getPreferences().getUserDTO().getLon(),new ApiRequestHelper.onRequestComplete() {
                @Override
                public void onSuccess(final Object object) {

                }

                @Override
                public void onFailure(ApiResponse apiResponse) {


                }
            });

            ///




            mHandler.postDelayed(runnable,LOOP_TIME);

        }
    };



    @Override
    public void onCreate() {
        super.onCreate();



        fbtest = (Fbtest) getApplication();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);


        mHandler.post(runnable);

        return START_STICKY;
    }



    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub



        super.onDestroy();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}