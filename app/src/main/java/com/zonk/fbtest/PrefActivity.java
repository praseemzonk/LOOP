package com.zonk.fbtest;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.zonk.fbtest.Adapter.SkillsAdapter;
import com.zonk.fbtest.Api.ApiRequestHelper;
import com.zonk.fbtest.Model.Skill;
import com.zonk.fbtest.Model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrefActivity extends Activity {

    SkillsAdapter skillsAdapter;
    TextView done;
    RecyclerView tableList;
    LinearLayout loadingContainer;
    public  static  PrefActivity prefActivity;
    Fbtest fbtest;
    List<Skill> selectedSkill;

    int [] arrays={R.drawable.item3,R.drawable.item3,R.drawable.item3,R.drawable.item3,R.drawable.item3,R.drawable.item3,R.drawable.item3};

    List<Skill>  skills;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref);
        loadingContainer=(LinearLayout)findViewById(R.id.loading_conatainer);
        fbtest=(Fbtest)getApplication() ;

        prefActivity= this;
        skills= new ArrayList<>();
        selectedSkill= new ArrayList<>();
                done=(TextView)findViewById(R.id.done);
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        loadingContainer.setVisibility(View.VISIBLE);
                        done.setVisibility(View.GONE);
                        fbtest.getApiRequestHelper().addSkills(selectedSkill,new ApiRequestHelper.onRequestComplete(){
                            @Override
                            public void onSuccess(Object object) {

                                fbtest.getPreferences().setSkillsselected(true);
                                finish();
                                loadingContainer.setVisibility(View.VISIBLE);
                                startActivity(new Intent(PrefActivity.this, SelectSkillLevelActivity.class));

                            }

                            @Override
                            public void onFailure(com.zonk.fbtest.Api.ApiResponse apiResponse) {
                                fbtest.showToast("some error occured");

                                loadingContainer.setVisibility(View.GONE);
                                done.setVisibility(View.VISIBLE);
                            }



                        });
                    }
                });



        loadingContainer.setVisibility(View.VISIBLE);
        fbtest.getApiRequestHelper().getSkills(new ApiRequestHelper.onRequestComplete(){
            @Override
            public void onSuccess(Object object) {


                loadingContainer.setVisibility(View.GONE);
                skills= (List<Skill>) object;

                skillsAdapter= new SkillsAdapter(skills, PrefActivity.this);
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(9, LinearLayoutManager.HORIZONTAL);
                tableList.setLayoutManager(staggeredGridLayoutManager); // set LayoutManager to RecyclerView
                tableList.setItemAnimator(new DefaultItemAnimator());
                tableList.setAdapter(skillsAdapter);

            }

            @Override
            public void onFailure(com.zonk.fbtest.Api.ApiResponse apiResponse) {
                fbtest.showToast("some error occured");

                loadingContainer.setVisibility(View.GONE);
            }



        });

        tableList=(RecyclerView)findViewById(R.id.tablelist);

    }
    public static PrefActivity getInstance(){

        return  prefActivity;
    }
    public  void  updateDoneButton(int i){

        if(i>=3){
            done.setVisibility(View.VISIBLE);
        }
        else {
            done.setVisibility(View.GONE);
        }
    }
    public  void  setSkill(List<Skill> skill){

        selectedSkill= skill;
    }
}
