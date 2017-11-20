package com.zonk.fbtest.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zonk.fbtest.Fbtest;
import com.zonk.fbtest.Model.Skill;
import com.zonk.fbtest.PrefActivity;
import com.zonk.fbtest.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Ribbi on 10/29/2017.
 */

public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.MyViewHolder> {

    int selectedCount=0;
    List<Skill> skills;
    private Activity activity;
    Fbtest fbtest;
    private LayoutInflater inflater;

//    Fml fml;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView count;
        LinearLayout main;
        public MyViewHolder(View view) {
            super(view);
            count=(TextView)view.findViewById(R.id.skill);
            main=(LinearLayout)view.findViewById(R.id.main);


        }
    }


    public SkillsAdapter(List<Skill> skills, Activity activity) {
        this.skills= skills;
        this.activity= activity;

        fbtest= (Fbtest) activity.getApplication();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.skill_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Skill skill= skills.get(position);
        final int pos=position;
        holder.count.setText(skill.getSkillName());
        holder.count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(skills.get(pos).isSelected()){
                    skills.get(pos).setSelected(false);

                    selectedCount--;
                    notifyDataSetChanged();
                    PrefActivity.getInstance().updateDoneButton(selectedCount);
                    List<Skill>  tempList= new ArrayList<>();
                    for(int i=0;i<skills.size();i++){
                        if(skills.get(i).isSelected()){
                            tempList.add(skills.get(i));
                        }
                    }
                    PrefActivity.getInstance().setSkill(tempList);


                }
                else {
                    if(selectedCount<5){
                    selectedCount++;
                    skills.get(pos).setSelected(true);
                        PrefActivity.getInstance().updateDoneButton(selectedCount);
                        List<Skill>  tempList= new ArrayList<>();
                        for(int i=0;i<skills.size();i++){
                            if(skills.get(i).isSelected()){
                                tempList.add(skills.get(i));
                            }
                        }
                        PrefActivity.getInstance().setSkill(tempList);

                        notifyDataSetChanged();
                    }
                    else {

                        fbtest.showToast("you have already selected five skills");
                    }

                }
            }
        });

        if(skill.isSelected()){

            holder.count.setTextColor(activity.getResources().getColor(R.color.white));

            holder.count.setBackground(activity.getResources().getDrawable(R.drawable.item3));

        }
        else {

            holder.count.setTextColor(activity.getResources().getColor(R.color.neworange));
            holder.count.setBackground(activity.getResources().getDrawable(R.drawable.item8));


        }


//
//        holder.main.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (tables.get(pos).isSelected()) {
//                    tables.get(pos).setSelected(false);
//                    SelectTableDrinksActivity.getInstance().unselectTable(holder.count.getText().toString());
//                    fml.getPreferences().setTABLE_NUMBER(null);
//
//                    notifyDataSetChanged();
//
//                } else {
//                    for (int i = 0; i < tables.size(); i++) {
//                        if (pos != i) {
//                            tables.get(i).setSelected(false);
//                            notifyDataSetChanged();
//
//                        }
//                    }
//
//                    tables.get(pos).setSelected(true);
//                    fml.getPreferences().setTABLE_NUMBER(tables.get(pos).getNumber());
//                    SelectTableDrinksActivity.getInstance().selectedTable(tables.get(pos).getNumber());
//                    notifyDataSetChanged();
//
//                }
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return skills.size();
    }
}


