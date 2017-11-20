package com.zonk.fbtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eftimoff.androipathview.PathView;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.zonk.fbtest.Api.ApiRequestHelper;
import com.zonk.fbtest.Model.Friends;
import com.zonk.fbtest.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Splash extends Activity {




    private User userDto;
    private Fbtest fbtest;
    private List<Friends> myFriendList;
    private CallbackManager mCallbackManager;
    private AccessTokenTracker mTokenTracker;
    private ProfileTracker mProfileTracker;
    FacebookCallback<LoginResult> mFacebookCallback;
    private ConnectionResult mConnectionResult;



    int width, height;
    LinearLayout main;
    LinearLayout title;
    //replace package string with your package string
    RelativeLayout tv;
    LinearLayout loading;
    public static final String PACKAGE ="com.zonk.fbtest";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_splash);

        loading=(LinearLayout)findViewById(R.id.loading_conatainer);

        myFriendList= new ArrayList<>();
        userDto = new User();
        fbtest = (Fbtest) getApplication();


        if (fbtest.getPreferences().isLoggedIn()) {

            if (fbtest.getPreferences().OTPVerified()) {

                if (fbtest.getPreferences().skillSelected()) {

                    if (fbtest.getPreferences().skillLevelSelected()) {

                        if (fbtest.getPreferences().ProfileVerified()) {
                            startActivity(new Intent(Splash.this, Dashboard.class));
                            finish();

                        } else {
                            startActivity(new Intent(Splash.this, ProfilePreviewActivity.class));
                            finish();
                        }

                    } else {
                        startActivity(new Intent(Splash.this, SelectSkillLevelActivity.class));
                        finish();
                    }
                } else {

                    startActivity(new Intent(Splash.this, PrefActivity.class));
                    finish();

                }
            } else {
                startActivity(new Intent(Splash.this, VerifyPhnoActivity.class));
                finish();

            }
        }
        else {


        }
        setupTokenTracker();
        setupProfileTracker();
        mTokenTracker.startTracking();
        mProfileTracker.startTracking();
        setupLoginButton();



        generateHashkey();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height= displayMetrics.heightPixels;
        width= displayMetrics.widthPixels;
        Log.e("hieght",height+"   "+width);
        title=(LinearLayout) findViewById(R.id.title);
        main=(LinearLayout)findViewById(R.id.main);




        tv=(RelativeLayout) findViewById(R.id.tv);



        AlphaAnimation animation1 = new AlphaAnimation(0.0f, 1.0f);
        animation1.setDuration(800);
        animation1.setStartOffset(1800);
        title.startAnimation(animation1);


        tv.startAnimation(animation1);

        final PathView pathView = (PathView) findViewById(R.id.pathView);

        final Path path = makeConvexArrow(width/2, height/2);
        pathView.setPath(path);
        pathView.setFillAfter(true);
        pathView.useNaturalColors();
        pathView.setPathWidth(5);



        final PathView pathView8 = (PathView) findViewById(R.id.pathView8);

        final Path path8 = makeConvexArrow8(width/2, height/2);
        pathView8.setPath(path8);
        pathView8.setFillAfter(true);
        pathView8.useNaturalColors();
        pathView8.setPathWidth(5);









        final PathView pathView6 = (PathView) findViewById(R.id.pathView6);

        final Path path6 = makeConvexArrow6(width/2, height/2);
        pathView6.setPath(path6);
        pathView6.setFillAfter(true);
        pathView6.useNaturalColors();
        pathView6.setPathWidth(5);



        final PathView pathView2 = (PathView) findViewById(R.id.pathView2);

        final Path path2 = makeConvexArrow2(width/2, height/2);
        pathView2.setPath(path2);
        pathView2.setFillAfter(true);
        pathView2.useNaturalColors();
        pathView2.setPathWidth(5);


        final PathView pathView5 = (PathView) findViewById(R.id.pathView5);

        final Path path5 = makeConvexArrow5(width/2, height/2);
        pathView5.setPath(path5);
        pathView5.setFillAfter(true);
        pathView5.useNaturalColors();
        pathView5.setPathWidth(5);


        final PathView pathView4 = (PathView) findViewById(R.id.pathView4);

        final Path path4 = makeConvexArrow4(width/2, height/2);
        pathView4.setPath(path4);
        pathView4.setFillAfter(true);
        pathView4.useNaturalColors();
        pathView4.setPathWidth(5);
        final PathView circle1 = (PathView) findViewById(R.id.circle1);

        final Path path9 = makeCircle1(width/2, height/2);
        circle1.setPath(path9);
        circle1.setFillAfter(true);
        circle1.useNaturalColors();
        circle1.setPathWidth(5);

        final PathView circle2 = (PathView) findViewById(R.id.circle2);

        final Path path10 = makeCircle2(width/2, height/2);
        circle2.setPath(path10);
        circle2.setFillAfter(true);
        circle2.useNaturalColors();
        circle2.setPathWidth(5);

        final PathView circle3 = (PathView) findViewById(R.id.circle3);

        final Path path11 = makeCircle3(width/2, height/2);
        circle3.setPath(path11);
        circle3.setFillAfter(true);
        circle3.useNaturalColors();
        circle3.setPathWidth(5);

        final PathView circle4 = (PathView) findViewById(R.id.circle4);

        final Path path12 = makeCircle4(width/2, height/2);
        circle4.setPath(path12);
        circle4.setFillAfter(true);
        circle4.useNaturalColors();
        circle4.setPathWidth(5);



        final PathView circle5 = (PathView) findViewById(R.id.circle5);

        final Path path13 = makeCircle5(width/2, height/2);
        circle5.setPath(path13);
        circle5.setFillAfter(true);
        circle5.useNaturalColors();
        circle5.setPathWidth(5);

        final PathView circle6 = (PathView) findViewById(R.id.circle6);

        final Path path14 = makeCircle6(width/2, height/2);
        circle6.setPath(path14);
        circle6.setFillAfter(true);
        circle6.useNaturalColors();
        circle6.setPathWidth(5);




                pathView.getPathAnimator().
                        //pathView.getSequentialPathAnimator().
                                delay(200).
                        duration(1250).
                        interpolator(new AccelerateDecelerateInterpolator()).
                        start();

                pathView2.getPathAnimator().
                        //pathView.getSequentialPathAnimator().
                                delay(120).
                        duration(1400).
                        interpolator(new AccelerateDecelerateInterpolator()).
                        start();


                pathView5.getPathAnimator().
                        //pathView.getSequentialPathAnimator().
                                delay(50).
                        duration(1400).
                        interpolator(new AccelerateDecelerateInterpolator()).
                        start();

                pathView6.getPathAnimator().
                        //pathView.getSequentialPathAnimator().
                                delay(150).
                        duration(1000).
                        interpolator(new AccelerateDecelerateInterpolator()).
                        start();

                pathView4.getPathAnimator().
                        //pathView.getSequentialPathAnimator().
                                delay(250).
                        duration(1200).
                        interpolator(new AccelerateDecelerateInterpolator()).
                        start();
                pathView8.getPathAnimator().
                        //pathView.getSequentialPathAnimator().
                                delay(250).
                        duration(1300).
                        interpolator(new AccelerateDecelerateInterpolator()).
                        start();
                circle1.getPathAnimator().
                        //pathView.getSequentialPathAnimator().
                                delay(1450).
                        duration(300).
                        interpolator(new AccelerateDecelerateInterpolator()).
                        start();
                circle2.getPathAnimator().
                        //pathView.getSequentialPathAnimator().
                                delay(1450).
                        duration(300).
                        interpolator(new AccelerateDecelerateInterpolator()).
                        start();
                circle3.getPathAnimator().
                        //pathView.getSequentialPathAnimator().
                                delay(1450).
                        duration(300).
                        interpolator(new AccelerateDecelerateInterpolator()).
                        start();
                circle4.getPathAnimator().
                        //pathView.getSequentialPathAnimator().
                                delay(1450).
                        duration(300).
                        interpolator(new AccelerateDecelerateInterpolator()).
                        start();

                circle5.getPathAnimator().
                        //pathView.getSequentialPathAnimator().
                                delay(1450).
                        duration(300).
                        interpolator(new AccelerateDecelerateInterpolator()).
                        start();
                circle6.getPathAnimator().
                        //pathView.getSequentialPathAnimator().
                                delay(1450).
                        duration(300).
                        interpolator(new AccelerateDecelerateInterpolator()).
                        start();


    }
    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);

    }


    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    private void setupLoginButton() {
        LoginButton mButtonLogin = (LoginButton) findViewById(R.id.login_button);
        Typeface font = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        mButtonLogin.setTypeface(font);
        mButtonLogin.setCompoundDrawables(null, null, null, null);
        mButtonLogin.setReadPermissions("public_profile", "user_friends", "email");
        mButtonLogin.registerCallback(mCallbackManager, mFacebookCallback);
    }


    private void login(final Profile profile) {


        if (profile != null) {
            loading.setVisibility(View.VISIBLE);
            tv.setVisibility(View.GONE);
            userDto.setLoginId((profile.getId()));
            userDto.setName(profile.getName());
            userDto.setProfilePic("https://graph.facebook.com/" + userDto.getLoginId() + "/picture?type=normal&return_ssl_resources=1");
            final AccessToken accessToken = AccessToken.getCurrentAccessToken();
            GraphRequest request = GraphRequest.newMeRequest(
                    AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                            Log.v("LoginActivity", response.toString());
                            try {
                                userDto.setEmail(object.getString("email"));
                                userDto.setName(object.getString("name"));
                                userDto.setProfilePic("https://graph.facebook.com/" + userDto.getLoginId() + "/picture?type=normal&return_ssl_resources=1");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email");
            request.setParameters(parameters);
            request.executeAsync();
            GraphRequestBatch batch = new GraphRequestBatch(
                    GraphRequest.newMeRequest(
                            AccessToken.getCurrentAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(
                                        JSONObject jsonObject,
                                        GraphResponse response) {
                                    // Application code for user
                                    Log.v("LoginActivity1", jsonObject.toString());

                                }
                            }),
                    GraphRequest.newMyFriendsRequest(
                            AccessToken.getCurrentAccessToken(),
                            new GraphRequest.GraphJSONArrayCallback() {
                                @Override
                                public void onCompleted(
                                        JSONArray jsonArray,
                                        GraphResponse response) {
                                    // Application code for users friendsCircle
                                    Log.v("LoginActivity", jsonArray.toString());
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        Friends f = new Friends();
                                        try {
                                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                            f.setName(jsonObject.getString("name"));
                                            f.setLoginId((jsonObject.getString("id")));
                                            f.setProfilePic("https://graph.facebook.com/" + jsonObject.getString("id") + "/picture?type=normal&return_ssl_resources=1");
                                            Log.e("friend", jsonObject.getString("name"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                        myFriendList.add(i, f);
                                    }

                                    userDto.setFriendsList(myFriendList);

                                    try {

                                            String tkn = "";
                                            tkn = FirebaseInstanceId.getInstance().getToken();
                                            Log.e("token", tkn+"");
                                            userDto.setFcmId(tkn);

                                            loading.setVisibility(View.VISIBLE);
                                            tv.setVisibility(View.GONE);
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    fbtest.getApiRequestHelper().login(userDto, new ApiRequestHelper.onRequestComplete(){
                                                        @Override
                                                        public void onSuccess(Object object) {
                                                            fbtest.getPreferences().setAddAuthInHeader(true);
                                                            fbtest.getPreferences().setAuthToken((String) object);
                                                            fbtest.getPreferences().setUserDTO(userDto);
                                                            fbtest.getPreferences().setLoggedin(true);
                                                            new saveImage().execute();
                                                            User userDto1 = fbtest.getPreferences().getUserDTO();
                                                            LoginManager.getInstance().logOut();


                                                        }

                                                        @Override
                                                        public void onFailure(com.zonk.fbtest.Api.ApiResponse apiResponse) {
                                                            LoginManager.getInstance().logOut();
                                                            fbtest.showToast("some error occured");

                                                            loading.setVisibility(View.GONE);
                                                            tv.setVisibility(View.VISIBLE);
                                                        }














                                                    });
                                                }
                                            }, 800);

//

//


                                    }catch (Exception e) {
                                        LoginManager.getInstance().logOut();
                                        fbtest.showToast("something went wrong");
                                        e.printStackTrace();
                                        loading.setVisibility(View.GONE);
                                        tv.setVisibility(View.VISIBLE);
                                    }

                                }
                            })
            );
            batch.addCallback(new GraphRequestBatch.Callback() {
                @Override
                public void onBatchCompleted(GraphRequestBatch graphRequests) {
                    // Application code for when the batch finishes
                }
            });

            batch.executeAsync();


        }

    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    public static void saveFile(Context context, Bitmap b, String picName) {
        FileOutputStream fos;
        try {
            fos = context.openFileOutput(picName, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.PNG, 100, fos);

            fos.close();
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    class saveImage extends AsyncTask<Void, Integer, Void> {

        Bitmap bmp;

        @Override
        protected Void doInBackground(Void... params) {
            String s = fbtest.getPreferences().getUserDTO().getProfilePic();

            bmp = getBitmapFromURL(fbtest.getPreferences().getUserDTO().getProfilePic());
            try {
                saveFile(Splash.this, bmp, "profilepic");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);





            startActivity(new Intent(Splash.this, VerifyPhnoActivity.class));
            finish();



        }
    }

    private void setupTokenTracker() {
        mTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };
    }

    private void setupProfileTracker() {
        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                try {
                    if (null == fbtest.getPreferences().getAuthToken()) {
                        login(currentProfile);
                    }
                } catch (Exception e) {

                }
            }
        };
    }



    public void generateHashkey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    PACKAGE,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                Log.e("hash", Base64.encodeToString(md.digest(),
                                Base64.NO_WRAP));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("Name not found", e.getMessage(), e);

        } catch (NoSuchAlgorithmException e) {
            Log.d("Error", e.getMessage(), e);
        }
    }

    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS);
    }
    public void login(View view){
        LISessionManager.getInstance(getApplicationContext())
                .init(this, buildScope(), new AuthListener() {
                    @Override
                    public void onAuthSuccess() {

//                        Toast.makeText(getApplicationContext(), "success" +
//                                        LISessionManager
//                                                .getInstance(getApplicationContext())
//                                                .getSession().getAccessToken().toString(),
//                                Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onAuthError(LIAuthError error) {

                        Toast.makeText(getApplicationContext(), "failed "
                                        + error.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                }, true);
    }


//    @Override
//    protected void onActivityResult(int requestCode,
//                                    int resultCode, Intent data) {
//
//        LISessionManager.getInstance(getApplicationContext())
//                .onActivityResult(this,
//                        requestCode, resultCode, data);
//
//        Intent intent = new Intent(Splash.this, HomePage.class);
//        startActivity(intent);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);

    }

    private Path makeConvexArrow(float length, float height) {
        final Path path = new Path();
        path.moveTo(length/8f-14, height);
        path.lineTo(length/8f-14 , height/3);
        path.lineTo(length/2f, height/3f+1 );


        //path.lineTo( height, width );
//        path.lineTo(length * 4f, height);
//        path.lineTo(0.0f, height);
//        path.lineTo(length * 3f * 4f, height * 2f);
//        path.lineTo(0.0f, 0.0f);
        //path.close();
        return path;
    }




    private Path makeConvexArrow4(float length, float height) {
        final Path path = new Path();
        path.moveTo(0.0f, height);
        path.lineTo(length/2f , height);
        path.lineTo(length/2f, height/1.5f+1    );


        //path.lineTo( height, width );
//        path.lineTo(length * 4f, height);
//        path.lineTo(0.0f, height);
//        path.lineTo(length * 3f * 4f, height * 2f);
//        path.lineTo(0.0f, 0.0f);
        //path.close();
        return path;
    }

    private Path makeConvexArrow5(float length, float height) {
        final Path path = new Path();
        path.moveTo(0.0f, height*(7/8f)+17);
        path.lineTo(length/3f , height*(7/8f)+17);
        path.lineTo(length/3f, height/2f    );
        path.lineTo(length/1.5f, height/2f    );


        //path.lineTo( height, width );
//        path.lineTo(length * 4f, height);
//        path.lineTo(0.0f, height);
//        path.lineTo(length * 3f * 4f, height * 2f);
//        path.lineTo(0.0f, 0.0f);
        //path.close();
        return path;
    }



    private Path makeConvexArrow2(float length, float height) {
        final Path path = new Path();
        path.moveTo(length-length/8+14, height);
        path.lineTo(length-length/8+14, height-height/2);
        path.lineTo(length-length/2,    height-height/2 );


        //path.lineTo( height, width );
//        path.lineTo(length * 4f, height);
//        path.lineTo(0.0f, height);
//        path.lineTo(length * 3f * 4f, height * 2f);
//        path.lineTo(0.0f, 0.0f);
        //path.close();
        return path;
    }


    private Path makeConvexArrow8(float length, float height) {
        final Path path = new Path();
        path.moveTo(length, height);



        path.lineTo(length, height/4f);
        path.lineTo(length/3f,    height/4f );
        path.lineTo(length/3f,    height/6f );
        RectF oval2 = new RectF(130, 10, 230, 60);
        path.addOval(oval2, Path.Direction.CW);
        path.addArc(oval2, height, length);
        //path.lineTo( height, width );
//        path.lineTo(length * 4f, height);
//        path.lineTo(0.0f, height);
//        path.lineTo(length * 3f * 4f, height * 2f);
//        path.lineTo(0.0f, 0.0f);
        //path.close();
        return path;
    }
    private Path makeCircle1(float length, float height) {
        final Path path = new Path();
        path.moveTo(length, height);
        RectF oval2 = new RectF(length/3f-30, height/6f-60, length/3f+30,height/6f );
        path.addOval(oval2, Path.Direction.CCW);
        //path.lineTo( height, width );
//        path.lineTo(length * 4f, height);
//        path.lineTo(0.0f, height);
//        path.lineTo(length * 3f * 4f, height * 2f);
//        path.lineTo(0.0f, 0.0f);
        //path.close();
        return path;
    }

    private Path makeCircle2(float length, float height) {
        final Path path = new Path();
        path.moveTo(length, height);
        RectF oval2 = new RectF(length/2f, height/3f-30, length/2f+60,height/3f+30 );
        path.addOval(oval2, Path.Direction.CCW);
        //path.lineTo( height, width );
//        path.lineTo(length * 4f, height);
//        path.lineTo(0.0f, height);
//        path.lineTo(length * 3f * 4f, height * 2f);
//        path.lineTo(0.0f, 0.0f);
        //path.close();
        return path;
    }


    private Path makeCircle5(float length, float height) {
        final Path path = new Path();
        path.moveTo(length, height);

        RectF oval2 = new RectF(length/2f-60, height/2f-30, length/2f,height/2f+30 );
        path.addOval(oval2, Path.Direction.CCW);
        //path.lineTo( height, width );
//        path.lineTo(length * 4f, height);
//        path.lineTo(0.0f, height);
//        path.lineTo(length * 3f * 4f, height * 2f);
//        path.lineTo(0.0f, 0.0f);
        //path.close();
        return path;
    }
    private Path makeCircle6(float length, float height) {
        final Path path = new Path();
        path.moveTo(length, height);

        RectF oval2 = new RectF(length/2.5f-60, height/1.5f-30, length/2.5f,height/1.5f+30 );
        path.addOval(oval2, Path.Direction.CCW);
        //path.lineTo( height, width );
//        path.lineTo(length * 4f, height);
//        path.lineTo(0.0f, height);
//        path.lineTo(length * 3f * 4f, height * 2f);
//        path.lineTo(0.0f, 0.0f);
        //path.close();
        return path;
    }

    private Path makeCircle3(float length, float height) {
        final Path path = new Path();
        path.moveTo(length, height);
        RectF oval2 = new RectF(length/1.5f, height/2f-30, length/1.5f+60,height/2f+30 );
        path.addOval(oval2, Path.Direction.CCW);
        //path.lineTo( height, width );
//        path.lineTo(length * 4f, height);
//        path.lineTo(0.0f, height);
//        path.lineTo(length * 3f * 4f, height * 2f);
//        path.lineTo(0.0f, 0.0f);
        //path.close();
        return path;
    }

    private Path makeCircle4(float length, float height) {
        final Path path = new Path();
        path.moveTo(length, height);

        RectF oval2 = new RectF(length/2f-30, height/1.5f-60, length/2f+30,height/1.5f );
        path.addOval(oval2, Path.Direction.CCW);
        //path.lineTo( height, width );
//        path.lineTo(length * 4f, height);
//        path.lineTo(0.0f, height);
//        path.lineTo(length * 3f * 4f, height * 2f);
//        path.lineTo(0.0f, 0.0f);
        //path.close();
        return path;
    }


    private Path makeConvexArrow6(float length, float height) {
        final Path path = new Path();
        path.moveTo(length, height);
        path.lineTo(length/1.5f , height);
        path.lineTo(length/1.5f, height/1.5f    );

        path.lineTo(length/2.5f, height/1.5f    );



        //path.lineTo( height, width );
//        path.lineTo(length * 4f, height);
//        path.lineTo(0.0f, height);
//        path.lineTo(length * 3f * 4f, height * 2f);
//        path.lineTo(0.0f, 0.0f);
        //path.close();
        return path;
    }

}