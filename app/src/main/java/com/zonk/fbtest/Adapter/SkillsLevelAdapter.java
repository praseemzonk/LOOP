package com.zonk.fbtest.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xw.repo.BubbleSeekBar;
import com.zonk.fbtest.Api.ApiRequestHelper;
import com.zonk.fbtest.Fbtest;
import com.zonk.fbtest.Model.Skill;
import com.zonk.fbtest.PrefActivity;
import com.zonk.fbtest.ProfilePreviewActivity;
import com.zonk.fbtest.R;
import com.zonk.fbtest.SelectSkillLevelActivity;
import com.zonk.fbtest.Views.SegmentedStepperSeekbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ribbi on 10/29/2017.
 */

public class SkillsLevelAdapter extends RecyclerView.Adapter<SkillsLevelAdapter.MyViewHolder> {

    List<Skill> skills;
    private Activity activity;
    private LayoutInflater inflater;

    TextView done;
    Fbtest fbtest;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView count;
         BubbleSeekBar bubbleSeekBar1 ;

        LinearLayout main;

        public MyViewHolder(View view) {
            super(view);
            count=(TextView)view.findViewById(R.id.skill);
            main=(LinearLayout)view.findViewById(R.id.main);

           bubbleSeekBar1 = (BubbleSeekBar) view.findViewById(R.id.bar);
        }
    }


    public SkillsLevelAdapter(List<Skill> skills, Activity activity, TextView done) {
        this.skills= skills;

        this.done= done;
        this.activity= activity;
       fbtest= (Fbtest) activity.getApplication();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.skill_level_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Skill skill= skills.get(position);
        final int pos=position;
        holder.count.setText(skill.getSkillName());
        holder.bubbleSeekBar1.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

                skills.get(pos).setValue(progress);
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                skills.get(pos).setValue(progress);

            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("SETTING UP");
                progressDialog.show();

                fbtest.getApiRequestHelper().addSkillsLevel(skills,new ApiRequestHelper.onRequestComplete(){
                    @Override
                    public void onSuccess(Object object) {
                        progressDialog.dismiss();

                        fbtest.getPreferences().setskillLevelSelected(true);
                        activity.startActivity(new Intent(activity, ProfilePreviewActivity.class));
                        activity.finish();

                    }

                    @Override
                    public void onFailure(com.zonk.fbtest.Api.ApiResponse apiResponse) {
                        fbtest.showToast("some error occured");
progressDialog.dismiss();

                    }



                });

            }
        });



    }

    @Override
    public int getItemCount() {
        return skills.size();
    }
}


