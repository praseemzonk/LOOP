package com.zonk.fbtest.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zonk.fbtest.Model.Shout;
import com.zonk.fbtest.R;

import java.util.List;

/**
 * Created by Ribbi on 10/15/2017.
 */

public class MyShoutAdapter extends RecyclerView.Adapter<MyShoutAdapter.ViewHolder>  {

    private final Context mContext;
    List<Shout> users;
    Activity activity;
    int pos;

    public MyShoutAdapter(List<Shout> users, Context context , Activity activity) {
        this.users= users;
        mContext=context;
        this.activity=activity;



    }

    @Override
    public MyShoutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shout_my_item, parent, false);
        return new MyShoutAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.photo = users.get(position);
        pos= position;
        holder.skill.setText(holder.photo.getSkill().getSkillName());
        holder.working.setText(holder.photo.getWorkingOn());

        Picasso.with(activity).load(activity.getResources().getString(R.string.server_url)+holder.photo.getSkill().getSkillIcon()).into(holder.logo);


    }


    @Override
    public int getItemCount() {

        return users.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        TextView skill, working;
        ImageView logo;
        public Shout photo;
        public ViewHolder(View view) {
            super(view);
            mView = view;


            logo=(ImageView)view.findViewById(R.id.logo);
            working=(TextView)view.findViewById(R.id.working);
            skill=(TextView)view.findViewById(R.id.skillname);


        }
    }
    }

