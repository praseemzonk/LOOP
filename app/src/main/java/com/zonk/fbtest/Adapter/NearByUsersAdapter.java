package com.zonk.fbtest.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zonk.fbtest.Api.ApiRequestHelper;
import com.zonk.fbtest.Fbtest;
import com.zonk.fbtest.Model.Friends;
import com.zonk.fbtest.Model.Shout;
import com.zonk.fbtest.Model.User;
import com.zonk.fbtest.ProfileActivity;
import com.zonk.fbtest.R;
import com.zonk.fbtest.Utils.CircleImageView;
import com.zonk.fbtest.Utils.RelativeTimeTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Ribbi on 10/15/2017.
 */

public class NearByUsersAdapter extends RecyclerView.Adapter<NearByUsersAdapter.ViewHolder>  {

    private final Context mContext;
    List<Shout> users;
    Activity activity;
    Fbtest fbtest;
    int pos;

    public NearByUsersAdapter(List<Shout> users, Context context , Activity activity) {
        this.users= users;
        mContext=context;
        this.activity=activity;


        fbtest= (Fbtest) activity.getApplication();

    }

    @Override
    public NearByUsersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.users_item, parent, false);
        return new NearByUsersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.photo = users.get(position);
        pos= position;
        holder.name.setText(holder.photo.getSkill().getSkillName());

        holder.working.setText(holder.photo.getWorkingOn());
//        holder.image.setText(holder.photo);
        Picasso.with(activity).load(activity.getResources().getString(R.string.server_url)+holder.photo.getSkill().getSkillIcon()).into(holder.image);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.shout_dialog);

                final EditText text;
                TextView done;
                ImageView logo;

                TextView name, skillName, working;

                text=(EditText)dialog.findViewById(R.id.message);
                done=(TextView)dialog.findViewById(R.id.done);


                working=(TextView)dialog.findViewById(R.id.working);
                name=(TextView)dialog.findViewById(R.id.name);

                logo=(ImageView)dialog.findViewById(R.id.logo);
                skillName=(TextView)dialog.findViewById(R.id.skillname);

                skillName.setText(holder.photo.getSkill().getSkillName());

                working.setText(holder.photo.getWorkingOn());
                name.setText(holder.photo.getPersonName());

                Picasso.with(activity).load(activity.getResources().getString(R.string.server_url)+holder.photo.getSkill().getSkillIcon()).into(logo);

                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(text.getText().toString().length()>0){

                            final ProgressDialog progressDialog= new ProgressDialog(activity);
                            progressDialog.setMessage("Sending...");
                            progressDialog.show();
                            fbtest.getApiRequestHelper().addMessage(holder.photo.get_id(),text.getText().toString(),new ApiRequestHelper.onRequestComplete(){
                                @Override
                                public void onSuccess(Object object) {

                                    progressDialog.dismiss();
                                    dialog.dismiss();
                                    fbtest.showToast("message sent");
                                }

                                @Override
                                public void onFailure(com.zonk.fbtest.Api.ApiResponse apiResponse) {
                                    fbtest.showToast("some error occured");
                                    dialog.dismiss();
                                    progressDialog.dismiss();
                                }



                            });
                        }
                        else {
                            fbtest.showToast("enter message to post");
                        }
                    }
                });

                dialog.show();
                dialog.setCancelable(true);



            }
        });





    }


    @Override
    public int getItemCount() {

        return users.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        private final ImageView image;

        TextView name, working;
        public Shout photo;


        public ViewHolder(View view) {
            super(view);
            mView = view;


            working=(TextView)view.findViewById(R.id.working);

            name=(TextView)view.findViewById(R.id.username);
            image = (ImageView) view.findViewById(R.id.photo);


        }
    }
    }

