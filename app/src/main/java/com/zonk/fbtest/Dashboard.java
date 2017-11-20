package com.zonk.fbtest;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.zonk.fbtest.Adapter.DashBoardAdapter;
import com.zonk.fbtest.Api.ApiRequestHelper;
import com.zonk.fbtest.Model.User;
import com.zonk.fbtest.Views.NonSwipeableViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Dashboard extends AppCompatActivity  {


    DashBoardAdapter dashboardAdapter;
    NonSwipeableViewPager viewPager;
    View indicator1,indicator3,indicator4;
    Intent offersIntent;

    Fbtest fbtest;
    TextView connect, nearby, profile;
    LinearLayout friendsButton,  invitesButton, checkinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        fbtest= (Fbtest) getApplication();

        initUI();
        initializePush();


    }

    @Override
    protected void onResume() {
        super.onResume();


    }


    public void initializePush() {
        if(getIntent()!=null) {
            offersIntent = getIntent();

        }


        if (offersIntent.hasExtra("message")) {


                viewPager.setCurrentItem(2);

        }


        //   Log.e("offerIntent"offersIntent.toString();
        if (offersIntent.hasExtra("skillfound")) {

            String notification = offersIntent.getStringExtra("skillfound");


            try {
                JSONObject jsonObject= new JSONObject(notification);

                Log.e("notificaTION",jsonObject.getString("loginId").toString());

                final ProgressDialog progressDialog= new ProgressDialog(Dashboard.this);
                progressDialog.setMessage("LOADING.....");
                progressDialog.show();
//
                fbtest.getApiRequestHelper().getUser(jsonObject.getString("loginId").toString(),new ApiRequestHelper.onRequestComplete(){
                    @Override
                    public void onSuccess(Object object) {

                        User user= (User) object;
                        progressDialog.dismiss();

                        Intent i = new Intent(Dashboard.this, ProfileActivity.class);
                        i.putExtra("user",user);
                        startActivity(i);
                        i.putExtra("from","shoutouts");
                    }

                    @Override
                    public void onFailure(com.zonk.fbtest.Api.ApiResponse apiResponse) {
                        fbtest.showToast("some error occured");

                        progressDialog.dismiss();
                    }



                });


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



    }

    private void initUI() {
        indicator1=(View)findViewById(R.id.indicator1);
        indicator3=(View)findViewById(R.id.indicator3);
        indicator4=(View)findViewById(R.id.indicator4);

        connect=(TextView)findViewById(R.id.connect_text);
        nearby=(TextView)findViewById(R.id.nearby_text);
        profile=(TextView)findViewById(R.id.profile_text);
        friendsButton=(LinearLayout)findViewById(R.id.friends);
        invitesButton=(LinearLayout)findViewById(R.id.invites);
        checkinButton=(LinearLayout)findViewById(R.id.checkins);
        viewPager = (NonSwipeableViewPager) findViewById(R.id.vp_horizontal_ntb);
        dashboardAdapter = new DashBoardAdapter(getSupportFragmentManager(), Dashboard.this);
        viewPager.setAdapter(dashboardAdapter);
        viewPager.setOffscreenPageLimit(2);
        setupButtons();

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {


                    connect.setTextColor(getResources().getColor(R.color.off_white2));
                    nearby.setTextColor(getResources().getColor(R.color.off_white1));
                    profile.setTextColor(getResources().getColor(R.color.off_white1));
                }
                if (position == 1) {
                    connect.setTextColor(getResources().getColor(R.color.off_white1));
                    nearby.setTextColor(getResources().getColor(R.color.off_white2));
                    profile.setTextColor(getResources().getColor(R.color.off_white1));

                }
                if (position == 2) {
                    connect.setTextColor(getResources().getColor(R.color.off_white1));
                    nearby.setTextColor(getResources().getColor(R.color.off_white1));
                    profile.setTextColor(getResources().getColor(R.color.off_white2));

                }


                }

            @Override
            public void onPageScrollStateChanged(int state) {

            }


        });

        int i=viewPager.getCurrentItem();
        if (i == 0) {

            indicator1.setBackgroundColor(getResources().getColor(R.color.green));
            indicator3.setBackgroundColor(getResources().getColor(R.color.white));
            indicator4.setBackgroundColor(getResources().getColor(R.color.white));
        }
        if (i == 1) {
            indicator3.setBackgroundColor(getResources().getColor(R.color.green));
            indicator1.setBackgroundColor(getResources().getColor(R.color.white));
            indicator4.setBackgroundColor(getResources().getColor(R.color.white));

        }
        if (i == 2) {
            indicator3.setBackgroundColor(getResources().getColor(R.color.white));
            indicator1.setBackgroundColor(getResources().getColor(R.color.white));
            indicator4.setBackgroundColor(getResources().getColor(R.color.green));

        }

    }


    public void setupButtons() {


        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewPager.setCurrentItem(0);
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(14);
            }
        });
        checkinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(5);
            }
        });


        invitesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(5);
            }
        });

    }


}

