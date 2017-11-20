package com.zonk.fbtest.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linkedin.platform.utils.Scope;
import com.squareup.picasso.Picasso;
import com.zonk.fbtest.Api.ApiRequestHelper;
import com.zonk.fbtest.Model.Friends;
import com.zonk.fbtest.Model.Skill;
import com.zonk.fbtest.Model.User;
import com.zonk.fbtest.ProfileActivity;
import com.zonk.fbtest.ProfilePreviewActivity;
import com.zonk.fbtest.R;
import com.zonk.fbtest.Utils.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.swsv.CircularLayout;
import me.xiaopan.swsv.SpiderWebScoreView;

/**
 * Created by Ribbi on 11/19/2017.
 */

public class NearByDevicesAdapter extends RecyclerView.Adapter<NearByDevicesAdapter.ViewHolder>  {

    private final Context mContext;
    List<User> users;
    Activity activity;
    List<Skill> skillList;

    int pos;

    public NearByDevicesAdapter(List<User> users, Context context , Activity activity) {
        this.users= users;
        mContext=context;
        this.activity=activity;



    }

    @Override
    public NearByDevicesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nearby_item, parent, false);
        return new NearByDevicesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NearByDevicesAdapter.ViewHolder holder, final int position) {
        holder.photo = users.get(position);
        pos= position;


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, ProfileActivity.class);
                i.putExtra("user",users.get(position));

                activity.startActivity(i);
                i.putExtra("from","nearby");
            }
        });

        holder.name.setText(holder.photo.getName());
        Picasso.with(activity).load(holder.photo.getProfilePic()).into(holder.image);



        holder.spiderWebScoreView1.setLineColor(activity.getResources().getColor(R.color.border3));
        holder.spiderWebScoreView1.setScoreStrokeColor(activity.getResources().getColor(R.color.border3));
        holder.spiderWebScoreView1.setScoreColor(activity.getResources().getColor(R.color.neworange));
        skillList= new ArrayList<>();
        skillList= holder.photo.getSkills();
                ProfilePreviewActivity.Score[] scores = new ProfilePreviewActivity.Score[skillList.size()];

                for(int i=0;i<skillList.size();i++){
                    ProfilePreviewActivity.Score score= new ProfilePreviewActivity.Score(skillList.get(i).getValue(), skillList.get(i).getSkillName());
                    scores[i]= score;
                }

                setup(holder.spiderWebScoreView1, holder.circularLayout1, scores);




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
            TextView scoreTextView = (TextView) LayoutInflater.from(activity).inflate(R.layout.score, circularLayout, false);
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

    @Override
    public int getItemCount() {

        return users.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        private final com.zonk.fbtest.Utils.CircleImageView image;

        TextView name;
        CircularLayout circularLayout1;
        SpiderWebScoreView spiderWebScoreView1;


        public User photo;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            name=(TextView)view.findViewById(R.id.username);
            image = (CircleImageView) view.findViewById(R.id.photo);

            spiderWebScoreView1 = (SpiderWebScoreView) view.findViewById(R.id.score);
            circularLayout1 = (CircularLayout) view.findViewById(R.id.layout_mainActivity_circular1);



        }
    }
}

