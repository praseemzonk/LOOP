package com.zonk.fbtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zonk.fbtest.Adapter.SkillsAdapter;
import com.zonk.fbtest.Adapter.SkillsLevelAdapter;
import com.zonk.fbtest.Api.ApiRequestHelper;
import com.zonk.fbtest.Model.Skill;
import com.zonk.fbtest.Views.SegmentedStepperSeekbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SelectSkillLevelActivity extends AppCompatActivity {


    SkillsLevelAdapter skillsAdapter;
    TextView done;
    RecyclerView tableList;
    LinearLayout loadingContainer;

    Fbtest fbtest;
    List<Skill> skills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fbtest= (Fbtest) getApplication();
        skills= new ArrayList<>();
        loadingContainer=(LinearLayout)findViewById(R.id.loading_conatainer);

        done=(TextView)findViewById(R.id.done);



        tableList=(RecyclerView)findViewById(R.id.tablelist);
        loadingContainer.setVisibility(View.VISIBLE);
        fbtest.getApiRequestHelper().getMySkills(new ApiRequestHelper.onRequestComplete(){
            @Override
            public void onSuccess(Object object) {
                loadingContainer.setVisibility(View.GONE);
                skills= (List<Skill>) object;
                skillsAdapter= new SkillsLevelAdapter(skills, SelectSkillLevelActivity.this, done);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                tableList.setLayoutManager(mLayoutManager);
                tableList.setItemAnimator(new DefaultItemAnimator());
                tableList.setItemAnimator(new DefaultItemAnimator());
                tableList.setAdapter(skillsAdapter);

            }

            @Override
            public void onFailure(com.zonk.fbtest.Api.ApiResponse apiResponse) {
                fbtest.showToast("some error occured");

                loadingContainer.setVisibility(View.GONE);
            }



        });


    }
}
