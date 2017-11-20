package com.zonk.fbtest;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkedin.platform.utils.Scope;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.zonk.fbtest.Adapter.FriendsAdapter;
import com.zonk.fbtest.Adapter.ShoutAdapter;
import com.zonk.fbtest.Api.ApiRequestHelper;
import com.zonk.fbtest.Model.Friends;
import com.zonk.fbtest.Model.Shout;
import com.zonk.fbtest.Model.Skill;
import com.zonk.fbtest.Model.User;
import com.zonk.fbtest.Utils.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.swsv.CircularLayout;
import me.xiaopan.swsv.SpiderWebScoreView;

public class ProfileActivity extends AppCompatActivity {

    LinearLayout linkedInContainer;
    LinearLayout shoutContainer;



    private final static int REQUEST_CALL = 1;

    TextView addContact;
    RecyclerView recyclerFans;

    List<Skill> skillList;
    User user;
    CircularLayout circularLayout1;
    Fbtest fbtest;
    LinearLayout callLL, emailLL;
    TextView headline, summary, position, location, industry;
    List<Shout> shouts;
    CircleImageView image;
    TextView name, email, phno;
    LinearLayout phnocontainer;
    SpiderWebScoreView spiderWebScoreView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        linkedInContainer = (LinearLayout) findViewById(R.id.linkedin_info_box);
        shoutContainer = (LinearLayout) findViewById(R.id.shout_container);

        emailLL = (LinearLayout) findViewById(R.id.emailLL);
        callLL = (LinearLayout) findViewById(R.id.callLL);


        callLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(ProfileActivity.this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    makeCall();
                } else {
                    if (shouldShowRequestPermissionRationale(android.Manifest.permission.CALL_PHONE)) {
                        Toast.makeText(ProfileActivity.this, "call permission required", Toast.LENGTH_SHORT).show();

                    }
                    requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                }
            }
        });

        emailLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(ProfileActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.email_layout);

                final EditText subject, message;
                TextView done, name;

                message=(EditText)dialog.findViewById(R.id.message);
                subject=(EditText)dialog.findViewById(R.id.subject);
                done=(TextView)dialog.findViewById(R.id.done);
                name=(TextView)dialog.findViewById(R.id.name);

                name.setText(user.getName());

                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(message.getText().toString().equals("")||subject.getText().toString().equals("")){

                        }
                        else {


                            Intent email = new Intent(Intent.ACTION_SEND);
                            email.putExtra(Intent.EXTRA_EMAIL, new String[]{ user.getEmail()});
                            email.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
                            email.putExtra(Intent.EXTRA_TEXT, message.getText().toString());

                            //need this to prompts email client only
                            email.setType("message/rfc822");

                            startActivity(Intent.createChooser(email, "Choose an Email client :"));
                        }
                    }
                });

                dialog.show();
                dialog.setCancelable(true);

            }
        });



        addContact=(TextView)findViewById(R.id.contact);
        fbtest = (Fbtest) getApplication();
        recyclerFans = (RecyclerView) findViewById(R.id.shouts);

        phnocontainer = (LinearLayout) findViewById(R.id.phnocontainer);
        shoutContainer = (LinearLayout) findViewById(R.id.shout_container);
        user = (User) getIntent().getSerializableExtra("user");

        phno = (TextView) findViewById(R.id.phno);

        if(fbtest.getPreferences().getUserDTO().getLoginId().equals(user.getLoginId())){

            callLL.setVisibility(View.GONE);
            emailLL.setVisibility(View.GONE);
            addContact.setVisibility(View.GONE);
            phno.setText(fbtest.getPreferences().getUserDTO().getPhNo());
            phnocontainer.setVisibility(View.VISIBLE);
        }
        else {
        if(user.isFriend()){


            callLL.setVisibility(View.VISIBLE);
            emailLL.setVisibility(View.VISIBLE);
            phno.setText(user.getPhNo());
            phnocontainer.setVisibility(View.VISIBLE);
            addContact.setClickable(false);
            addContact.setVisibility(View.VISIBLE);
            addContact.setText("Friend from Facebook");
            addContact.setTextColor(getResources().getColor(R.color.neworange));
        }
        else {
            if(user.isConnect()){

                callLL.setVisibility(View.VISIBLE);
                emailLL.setVisibility(View.VISIBLE);
                phno.setText(user.getPhNo());
                phnocontainer.setVisibility(View.VISIBLE);
                addContact.setClickable(false);
                addContact.setVisibility(View.VISIBLE);
                addContact.setText("In My Contacts");
                addContact.setTextColor(getResources().getColor(R.color.neworange));
            }
            else {

                callLL.setVisibility(View.GONE);
                emailLL.setVisibility(View.VISIBLE);
              //  phno.setText(fbtest.getPreferences().getUserDTO().getPhNo());
                phnocontainer.setVisibility(View.GONE);
                addContact.setClickable(true);
                addContact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        final ProgressDialog progressDialog= new ProgressDialog(ProfileActivity.this);
                        progressDialog.setMessage("Adding contact.....");
                        progressDialog.show();

                        fbtest.getApiRequestHelper().addContact(user.getLoginId(),new ApiRequestHelper.onRequestComplete(){
                            @Override
                            public void onSuccess(Object object) {

                                progressDialog.dismiss();
                                addContact.setText("In My Contacts");
                                addContact.setClickable(false);
                                addContact.setTextColor(getResources().getColor(R.color.neworange));
                            }

                            @Override
                            public void onFailure(com.zonk.fbtest.Api.ApiResponse apiResponse) {
                                fbtest.showToast("some error occured");


                                progressDialog.dismiss();
                            }



                        });



                    }
                });
                addContact.setVisibility(View.VISIBLE);
                addContact.setText("Add Contact");
                addContact.setTextColor(getResources().getColor(R.color.white));

            }
        }}
        industry = (TextView)
                findViewById(R.id.industry);
        headline = (TextView)
                findViewById(R.id.headline);
        summary = (TextView)
                findViewById(R.id.summary);
        position = (TextView)
                findViewById(R.id.position);
        location = (TextView)
                findViewById(R.id.location);

        image = (CircleImageView) findViewById(R.id.image);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        Picasso.with(ProfileActivity.this).load(user.getProfilePic()).fit().centerCrop().placeholder(R.color.white).into(image, new Callback() {

            @Override
            public void onSuccess() {
//                MaterialImageLoading.animate(viewHolder.friendPic).setDuration(2000).start();
            }

            @Override
            public void onError() {

            }
        });


        name.setText(user.getName());
        phno.setText(user.getPhNo());
        email.setText(user.getEmail());
        shouts = new ArrayList<>();
        spiderWebScoreView1 = (SpiderWebScoreView) findViewById(R.id.score);

        if(user.getShouts().size()>0) {
            ShoutAdapter fansAdapter = new ShoutAdapter(user.getShouts(), ProfileActivity.this, ProfileActivity.this);
            recyclerFans.setNestedScrollingEnabled(false);
            recyclerFans.setLayoutManager(new GridLayoutManager(ProfileActivity.this, 1, GridLayoutManager.HORIZONTAL, false));
            recyclerFans.setItemAnimator(new DefaultItemAnimator());
            recyclerFans.setAdapter(fansAdapter);
        }
        else {
            shoutContainer.setVisibility(View.GONE);
        }

        circularLayout1 = (CircularLayout) findViewById(R.id.layout_mainActivity_circular1);
        spiderWebScoreView1.setLineColor(getResources().getColor(R.color.border3));
        spiderWebScoreView1.setScoreStrokeColor(getResources().getColor(R.color.border3));
        spiderWebScoreView1.setScoreColor(getResources().getColor(R.color.neworange));
        skillList = new ArrayList<>();

        skillList = user.getSkills();
        ProfilePreviewActivity.Score[] scores = new ProfilePreviewActivity.Score[skillList.size()];
        for (int i = 0; i < skillList.size(); i++) {
            ProfilePreviewActivity.Score score = new ProfilePreviewActivity.Score(skillList.get(i).getValue(), skillList.get(i).getSkillName());
            scores[i] = score;
        }

        setup(spiderWebScoreView1, circularLayout1, scores);
        if (user.getHeadline().equals("null")) {

            linkedInContainer.setVisibility(View.GONE);
        } else {

            linkedInContainer.setVisibility(View.VISIBLE);
            headline.setText(user.getHeadline());
            industry.setText(user.getIndustry());
            position.setText(user.getTitle());
            location.setText(user.getCity());
            summary.setText(user.getSummary());

        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall();
            } else {

                Toast.makeText(ProfileActivity.this, "call permission has not been granted, cannot make call...", Toast.LENGTH_SHORT).show();

            }
        }


        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void makeCall() {
        if (null != user.getPhNo()) {
            String number = "tel:" + user.getPhNo();
            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
            startActivity(callIntent);
        }
    }


    private void setup(SpiderWebScoreView spiderWebScoreView, CircularLayout circularLayout, ProfilePreviewActivity.Score... scores) {
        float[] scoreArray = new float[scores.length];
        for (int w = 0; w < scores.length; w++) {
            scoreArray[w] = scores[w].score;
        }
        spiderWebScoreView.setScores(5, scoreArray);

        int i=0;
        circularLayout.removeAllViews();
        for (ProfilePreviewActivity.Score score : scores) {
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

}
