package com.zonk.fbtest.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zonk.fbtest.Api.ApiRequestHelper;
import com.zonk.fbtest.Fbtest;
import com.zonk.fbtest.Model.Friends;
import com.zonk.fbtest.Model.User;
import com.zonk.fbtest.ProfileActivity;
import com.zonk.fbtest.R;
import com.zonk.fbtest.Utils.CircleImageView;

import java.util.List;

/**
 * Created by Ribbi on 10/15/2017.
 */

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder>  {

    private final Context mContext;
    List<Friends> friends;
    Activity activity;
    Fbtest fbtest;
    int pos;

    public FriendsAdapter(List<Friends> friends, Context context , Activity activity) {
        this.friends= friends;
        mContext=context;
        this.activity=activity;

        fbtest= (Fbtest) activity.getApplication();


    }

    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friends_item, parent, false);
        return new FriendsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.photo = friends.get(position);
        pos= position;


        holder.name.setText(holder.photo.getName());
//        holder.image.setText(holder.photo);
        Picasso.with(activity).load(holder.photo.getProfilePic()).into(holder.image);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.photo.isDummy()){

                    fbtest.showToast("this is a dummy account for display purpose, click on a real friend for full functionality");
                }

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
                            i.putExtra("from","friends");
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

        return friends.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        private final com.zonk.fbtest.Utils.CircleImageView image;

        TextView name;
        public Friends photo;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            name=(TextView)view.findViewById(R.id.username);
            image = (CircleImageView) view.findViewById(R.id.photo);


        }
    }
    }

