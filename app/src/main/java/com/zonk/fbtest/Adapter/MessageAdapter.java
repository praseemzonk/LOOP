package com.zonk.fbtest.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zonk.fbtest.Api.ApiRequestHelper;
import com.zonk.fbtest.Fbtest;
import com.zonk.fbtest.Model.Message;
import com.zonk.fbtest.Model.Shout;
import com.zonk.fbtest.Model.User;
import com.zonk.fbtest.ProfileActivity;
import com.zonk.fbtest.R;

import java.util.List;

/**
 * Created by Ribbi on 11/19/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>  {

    List<Message> messages;
    Activity activity;
    int pos;
    Fbtest fbtest;

    public MessageAdapter(List<Message> messages, Activity activity) {
        this.messages= messages;

        this.activity=activity;
        fbtest= (Fbtest) activity.getApplication();



    }

    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);
        return new MessageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MessageAdapter.ViewHolder holder, int position) {
        holder.photo = messages.get(position);
        pos= position;
        holder.name.setText(holder.photo.getMessage());
        Picasso.with(activity).load(holder.photo.getProfilePic()).into(holder.logo);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog= new ProgressDialog(activity);
                progressDialog.setMessage("LOADING.....");
                progressDialog.show();

                fbtest.getApiRequestHelper().getUser(holder.photo.getLoginId(),new ApiRequestHelper.onRequestComplete(){
                    @Override
                    public void onSuccess(Object object) {

                        User user= (User) object;
                        progressDialog.dismiss();

                        Intent i = new Intent(activity, ProfileActivity.class);
                        i.putExtra("user",user);
                        activity.startActivity(i);
                        i.putExtra("from","message");
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

        return messages.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        TextView name;
        ImageView logo;
        public Message photo;
        public ViewHolder(View view) {
            super(view);
            mView = view;


            logo=(ImageView)view.findViewById(R.id.logo);
            name=(TextView)view.findViewById(R.id.name);


        }
    }
}

