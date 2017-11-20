package com.zonk.fbtest;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    Bitmap icon;
    NotificationManager notificationManager;
    Notification myNotification;
    Context context;
    boolean offerR = false, hhR = false;
    Intent myIntent;
    private static final int MY_NOTIFICATION_ID = 1;
    String messageTypeText, titleText, bodyText,a;
    NotificationManager mNotificationManager;

    public void onMessageReceived(RemoteMessage remoteMessage) {


        mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        icon = BitmapFactory.decodeResource(Fbtest.getInstance().getResources(),
                R.drawable.logo_new);



        Bundle data = new Bundle();
        for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
            data.putString(entry.getKey(), entry.getValue());
        }
        myIntent = new Intent(this, Dashboard.class);
        myIntent.putExtra("push", data);

        String title = data.getString("title");
        String body = data.getString("body");




            if(!data.getString("type").equals("")){
                String type=data.getString("type");
                String data1=data.getString("obj");
                Log.d(TAG, "FCM Received:" + title + ":" +body + ":" + type+ ":" + data1);
                sendNotification(body, title, type, data1);

            }



    }
    private void sendNotification(String messageBody, String messageTitle, String messageType, String value) {


        context=this;
        a = value;
        messageTypeText= messageType;
        bodyText= messageBody;
        titleText= messageTitle;

        if (messageType.equals("skillfound")) {
            myIntent.putExtra("skillfound", a);
            myIntent.setAction("skillfound");

            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context, 0,
                    myIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.logo_new)
                    .setContentTitle(messageTitle)
                    .setContentText(messageBody)
                    .setLargeIcon(icon)
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_LIGHTS)
                    .setSound(defaultSoundUri)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    ;
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
            if (Build.VERSION.SDK_INT >= 21) notificationBuilder.setVibrate(new long[1]);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());

        }


        if (messageType.equals("connection")) {
            myIntent.putExtra("skillfound", a);
            myIntent.setAction("skillfound");

            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context, 0,
                    myIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.logo_new)
                    .setContentTitle(messageTitle)
                    .setContentText(messageBody)
                    .setLargeIcon(icon)
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_LIGHTS)
                    .setSound(defaultSoundUri)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    ;
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
            if (Build.VERSION.SDK_INT >= 21) notificationBuilder.setVibrate(new long[1]);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());

        }


        if (messageType.equals("message")) {
            myIntent.putExtra("message", "message");
            myIntent.setAction("message");

            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context, 0,
                    myIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.logo_new)
                    .setContentTitle(messageTitle)
                    .setContentText(messageBody)
                    .setLargeIcon(icon)
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_LIGHTS)
                    .setSound(defaultSoundUri)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    ;
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
            if (Build.VERSION.SDK_INT >= 21) notificationBuilder.setVibrate(new long[1]);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());

        }



    }}