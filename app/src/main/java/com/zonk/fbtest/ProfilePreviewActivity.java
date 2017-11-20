package com.zonk.fbtest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.zonk.fbtest.Adapter.FriendsAdapter;
import com.zonk.fbtest.Adapter.SkillsAdapter;
import com.zonk.fbtest.Adapter.SkillsLevelAdapter;
import com.zonk.fbtest.Api.ApiRequestHelper;
import com.zonk.fbtest.Model.Friends;
import com.zonk.fbtest.Model.Skill;
import com.zonk.fbtest.Model.User;
import com.zonk.fbtest.Utils.CircleImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.swsv.CircularLayout;
import me.xiaopan.swsv.SpiderWebScoreView;

/**
 * Created by Ribbi on 11/16/2017.
 */

public class ProfilePreviewActivity extends Activity {

    RecyclerView  recyclerFans;

    List<Skill> skillList;
    User user;
    CircularLayout circularLayout1;
    Fbtest  fbtest
            ;
    LinearLayout phnoCFontainer;
    LinearLayout linkedInBox, linkedInInfobox;
    TextView maybelater;
    TextView procedd;
    TextView headline, summary, position, location,industry;
    List<Friends> friends;

    CircleImageView image;
    TextView name, email, phno;
    SpiderWebScoreView spiderWebScoreView1;



    private static final String host = "api.linkedin.com";
    private static final String url = "https://" + host
            + "/v1/people/~:" +
            "(email-address,headline,location,industry,num-connections,summary,specialties,positions,formatted-name,phone-numbers,picture-urls::(original))";
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_preview);
        recyclerFans = (RecyclerView) findViewById(R.id.friends);
        industry=(TextView)
findViewById(R.id.industry);
        headline=(TextView)
                findViewById(R.id.headline);
        summary=(TextView)
                findViewById(R.id.summary);
        position=(TextView)
                findViewById(R.id.position);
        location=(TextView)
                findViewById(R.id.location);
        linkedInBox=(LinearLayout)findViewById(R.id.linkedin_box);
        linkedInInfobox=(LinearLayout)findViewById(R.id.linkedin_info_box);
        procedd=(TextView)findViewById(R.id.procedd);
        procedd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbtest.getPreferences().setUserDTO(user);
                final ProgressDialog progressDialog = new ProgressDialog(ProfilePreviewActivity.this);
                progressDialog.setMessage("Loading");
                progressDialog.show();
                fbtest.getApiRequestHelper().setLN(user,new ApiRequestHelper.onRequestComplete(){
                    @Override
                    public void onSuccess(Object object) {

                        fbtest.getPreferences().setProfileverified(true);
                        startActivity(new Intent(ProfilePreviewActivity.this, Dashboard.class));
                        progressDialog.dismiss();
                        finish();}

                    @Override
                    public void onFailure(com.zonk.fbtest.Api.ApiResponse apiResponse) {
                    progressDialog.dismiss();
                    }



                });



            }
        });

        maybelater=(TextView)findViewById(R.id.maybelater);
        maybelater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                fbtest.getPreferences().setProfileverified(true);

                startActivity(new Intent(ProfilePreviewActivity.this, Dashboard.class));
            }
        });
        image= (CircleImageView) findViewById(R.id.image);
        name= (TextView) findViewById(R.id.name);
        phno = (TextView) findViewById(R.id.phno);
        phnoCFontainer=(LinearLayout)findViewById(R.id.phnocontainer);
        email= (TextView) findViewById(R.id.email);

        fbtest= (Fbtest) getApplication();

        Picasso.with(ProfilePreviewActivity.this).load(fbtest.getPreferences().getUserDTO().getProfilePic()).fit().centerCrop().placeholder(R.color.white).into(image, new Callback() {

            @Override
            public void onSuccess() {
//                MaterialImageLoading.animate(viewHolder.friendPic).setDuration(2000).start();
            }

            @Override
            public void onError() {

            }
        });


        name.setText(fbtest.getPreferences().getUserDTO().getName());

        if(null!=fbtest.getPreferences().getUserDTO().getPhNo()){

            phnoCFontainer.setVisibility(View.VISIBLE);
            phno.setText(fbtest.getPreferences().getUserDTO().getPhNo());

        }
        else {
            phnoCFontainer.setVisibility(View.GONE);

        }
        email.setText(fbtest.getPreferences().getUserDTO().getEmail());
        friends= new ArrayList<>();



        friends= fbtest.getPreferences().getUserDTO().getFriendsList();
         spiderWebScoreView1 = (SpiderWebScoreView) findViewById(R.id.score);



        fbtest.getApiRequestHelper().getMyFriends(new ApiRequestHelper.onRequestComplete(){
            @Override
            public void onSuccess(Object object) {
                friends= (List<Friends>) object;
                FriendsAdapter fansAdapter = new FriendsAdapter(friends,ProfilePreviewActivity.this,ProfilePreviewActivity.this);
                recyclerFans.setNestedScrollingEnabled(false);
                recyclerFans.setLayoutManager(new GridLayoutManager(ProfilePreviewActivity.this, 1, GridLayoutManager.HORIZONTAL, false));
                recyclerFans.setItemAnimator(new DefaultItemAnimator());
                recyclerFans.setAdapter(fansAdapter);
            }

            @Override
            public void onFailure(com.zonk.fbtest.Api.ApiResponse apiResponse) {
            }



        });



         circularLayout1 = (CircularLayout) findViewById(R.id.layout_mainActivity_circular1);

        spiderWebScoreView1.setLineColor(getResources().getColor(R.color.border3));
        spiderWebScoreView1.setScoreStrokeColor(getResources().getColor(R.color.border3));
        spiderWebScoreView1.setScoreColor(getResources().getColor(R.color.neworange));
         skillList= new ArrayList<>();
        fbtest.getApiRequestHelper().getMySkills(new ApiRequestHelper.onRequestComplete(){
            @Override
            public void onSuccess(Object object) {
                skillList= (List<Skill>) object;
                Score[] scores = new Score[skillList.size()];

                for(int i=0;i<skillList.size();i++){
                    Score  score= new Score(skillList.get(i).getValue(), skillList.get(i).getSkillName());
                    scores[i]= score;
                }

                setup(spiderWebScoreView1, circularLayout1, scores);
            }

            @Override
            public void onFailure(com.zonk.fbtest.Api.ApiResponse apiResponse) {
            }



        });

    }

    private void setup(SpiderWebScoreView spiderWebScoreView, CircularLayout circularLayout, Score... scores) {
        float[] scoreArray = new float[scores.length];
        for (int w = 0; w < scores.length; w++) {
            scoreArray[w] = scores[w].score;
        }
        spiderWebScoreView.setScores(5, scoreArray);

        int i=0;
        circularLayout.removeAllViews();
        for (Score score : scores) {
            TextView scoreTextView = (TextView) LayoutInflater.from(getBaseContext()).inflate(R.layout.score, circularLayout, false);
                scoreTextView.setText(score.name);

            i++;
            if (score.iconId != 0) {
                scoreTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, score.iconId, 0);
            }
            circularLayout.addView(scoreTextView);
        }
    }

    public static class Score {
        public int score;
        public int iconId;
        public  String name;

        public Score(int score, int iconId) {
            this.score = score;
            this.iconId = iconId;
        }


        public Score(int score, String name) {
            this.score = score;
            this.name = name;        }
        public Score(int score) {
            this.score = score;
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


    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {

        LISessionManager.getInstance(getApplicationContext())
                .onActivityResult(this,
                        requestCode, resultCode, data);

        setUpLinkedInPanel();

    }

    public  void  setUpLinkedInPanel(){
        progress= new ProgressDialog(this);
        progress.setMessage("Retrieve data...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();

        linkededinApiHelper();
    }
    public void linkededinApiHelper(){
        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.getRequest(ProfilePreviewActivity.this, url, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse result) {
                try {
                    showResult(result.getResponseDataAsJson());
                    progress.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onApiError(LIApiError error) {

            }
        });
    }

    public  void  showResult(JSONObject response){


        String company, from,position1;
        Log.e("data",response.toString());

        try {
            user= fbtest.getPreferences().getUserDTO();
            user.setIndustry(response.get("industry").toString());
            user.setHeadline(response.get("headline").toString());


            headline.setText(response.get("headline").toString());
            industry.setText(response.get("industry").toString());
            summary.setText(response.get("summary").toString());
            user.setSummary(response.get("summary").toString());

            JSONObject jsonObject = response.getJSONObject("location");

            JSONObject jsonObject1 = response.getJSONObject("positions");

                JSONArray jsonArray= jsonObject1.getJSONArray("values");
                JSONObject jsonObject2= jsonArray.getJSONObject(0);
                JSONObject jsonObject3= jsonObject2.getJSONObject("company");

                company= jsonObject3.get("name").toString();
                position1= jsonObject2.get("title").toString();


                position.setText(position1+" "+company);
            user.setTitle(position1+" "+company);

            location.setText(jsonObject.getString("name"));
            user.setCity(jsonObject.getString("name"));

            linkedInBox.setVisibility(View.GONE);
            linkedInInfobox.setVisibility(View.VISIBLE);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}