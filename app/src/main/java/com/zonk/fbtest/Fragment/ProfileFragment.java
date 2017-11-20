package com.zonk.fbtest.Fragment;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linkedin.platform.utils.Scope;
import com.zonk.fbtest.Adapter.ContactsAdapter;
import com.zonk.fbtest.Adapter.FriendsAdapter;
import com.zonk.fbtest.Adapter.MessageAdapter;
import com.zonk.fbtest.Adapter.MyShoutAdapter;
import com.zonk.fbtest.Adapter.NearByUsersAdapter;
import com.zonk.fbtest.Adapter.ShoutAdapter;
import com.zonk.fbtest.Adapter.SkillsAdapter2;
import com.zonk.fbtest.Api.ApiRequestHelper;
import com.zonk.fbtest.Fbtest;
import com.zonk.fbtest.Model.Friends;
import com.zonk.fbtest.Model.Shout;
import com.zonk.fbtest.Model.Skill;
import com.zonk.fbtest.Model.User;
import com.zonk.fbtest.ProfileActivity;
import com.zonk.fbtest.ProfilePreviewActivity;
import com.zonk.fbtest.R;
import com.zonk.fbtest.Utils.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import github.hellocsl.layoutmanager.gallery.GalleryLayoutManager;
import me.xiaopan.swsv.CircularLayout;
import me.xiaopan.swsv.SpiderWebScoreView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    LinearLayout shoutContainer, contactsContainer , friendsContainer;
    RecyclerView recyclerFans, contacts, friends;
    GalleryLayoutManager layoutManager;
    RecyclerView messageList;
    TextView messageText;
    View v;
    List<Skill> skillList;
    List<User> contactList;
    SwipeRefreshLayout swipeRefreshLayout;
    Fbtest fbtest;
    List<Shout> shouts;
    List<Friends>  friendList;
    private LinearLayoutManager linearLayoutManager;
    SpiderWebScoreView spiderWebScoreView1;


    CircularLayout circularLayout1;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();  fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_profile, container, false);
    return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        contactsContainer=(LinearLayout)v.findViewById(R.id.contact_container);
        swipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setUp();
            }
        });
        spiderWebScoreView1 = (SpiderWebScoreView) v.findViewById(R.id.score);
        fbtest = (Fbtest) getActivity().getApplication();

        circularLayout1 = (CircularLayout) v.findViewById(R.id.layout_mainActivity_circular1);

        spiderWebScoreView1.setLineColor(getResources().getColor(R.color.border3));
        spiderWebScoreView1.setScoreStrokeColor(getResources().getColor(R.color.border3));
        spiderWebScoreView1.setScoreColor(getResources().getColor(R.color.neworange));
        skillList= new ArrayList<>();
        fbtest.getApiRequestHelper().getMySkills(new ApiRequestHelper.onRequestComplete(){
            @Override
            public void onSuccess(Object object) {
                skillList= (List<Skill>) object;
                ProfilePreviewActivity.Score[] scores = new ProfilePreviewActivity.Score[skillList.size()];

                for(int i=0;i<skillList.size();i++){
                    ProfilePreviewActivity.Score score= new ProfilePreviewActivity.Score(skillList.get(i).getValue(), skillList.get(i).getSkillName());
                    scores[i]= score;
                }

                setup(spiderWebScoreView1, circularLayout1, scores);
            }

            @Override
            public void onFailure(com.zonk.fbtest.Api.ApiResponse apiResponse) {
            }



        });


        friendsContainer=(LinearLayout)v.findViewById(R.id.friends_container);

        contactList= new ArrayList<>();
        friendList=new ArrayList<>();

        messageText=(TextView)v.findViewById(R.id.messageText);
        shoutContainer = (LinearLayout) v.findViewById(R.id.shout_container);
        recyclerFans = (RecyclerView) v.findViewById(R.id.shouts);
        contacts = (RecyclerView) v.findViewById(R.id.contacts);
        messageList = (RecyclerView) v.findViewById(R.id.message_list);

        friends = (RecyclerView) v.findViewById(R.id.friends);
        shoutContainer = (LinearLayout) v.findViewById(R.id.shout_container);
        shouts = new ArrayList<>();




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
            TextView scoreTextView = (TextView) LayoutInflater.from(getActivity().getBaseContext()).inflate(R.layout.score, circularLayout, false);
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
    public void onResume() {
        super.onResume();
        setUp();


    }
public  void  setUp(){                            messageList.setVisibility(View.GONE);

    if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    fbtest.getApiRequestHelper().getMyShout(new ApiRequestHelper.onRequestComplete(){
        @Override
        public void onSuccess(Object object) {

            shouts = (List<Shout>) object;
            if(shouts.size()>0) {


                shoutContainer.setVisibility(View.VISIBLE);

                layoutManager = new GalleryLayoutManager(GalleryLayoutManager.HORIZONTAL);
                layoutManager.setCallbackInFling(true);//should receive callback when flinging, default is false
                layoutManager.setItemTransformer(new com.zonk.fbtest.Views.ScaleTransformation());


                layoutManager.setOnItemSelectedListener(new GalleryLayoutManager.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(RecyclerView recyclerView, View item, int i) {
                        // placePosition=i;
//
//                            removeMapMarkers();
//                            curr=i;
//                            addMarker(shouts.get(i));

                        if(shouts.get(i).getMessages().size()>0) {

                            messageList.setVisibility(View.VISIBLE);

                            messageText.setText("Replies");
                            linearLayoutManager = new LinearLayoutManager(getActivity());
                            messageList.setLayoutManager(linearLayoutManager);
                            messageList.setAdapter(new MessageAdapter(shouts.get(i).getMessages(), getActivity()));
                        }
                        else {

                            messageList.setVisibility(View.GONE);
                            messageText.setText("No Replies");
                        }


                    }
                });

                MyShoutAdapter fansAdapter = new MyShoutAdapter(shouts, getActivity(), getActivity());
                recyclerFans.setNestedScrollingEnabled(false);
                recyclerFans.setOnFlingListener(null);
                layoutManager.attach(recyclerFans, 0);
                recyclerFans.setItemAnimator(new DefaultItemAnimator());
                recyclerFans.setAdapter(fansAdapter);

            }
            else {
                shoutContainer.setVisibility(View.GONE);
            }
        }

        @Override
        public void onFailure(com.zonk.fbtest.Api.ApiResponse apiResponse) {
            fbtest.showToast("some error occured");

        }



    });


    fbtest.getApiRequestHelper().getMyCopntacts(new ApiRequestHelper.onRequestComplete(){
        @Override
        public void onSuccess(Object object) {

            contactList= (List<User>) object;

            if(contactList.size()>0) {

                contactsContainer.setVisibility(View.VISIBLE);
                ContactsAdapter contactsAdapter= new ContactsAdapter(contactList, getActivity(), getActivity());
                contacts.setNestedScrollingEnabled(false);
                contacts.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false));
                contacts.setItemAnimator(new DefaultItemAnimator());
                contacts.setAdapter(contactsAdapter);
            }
            else {
                contactsContainer.setVisibility(View.GONE);
            }
        }

        @Override
        public void onFailure(com.zonk.fbtest.Api.ApiResponse apiResponse) {
            fbtest.showToast("some error occured");

        }



    });

    fbtest.getApiRequestHelper().getMyFriends(new ApiRequestHelper.onRequestComplete(){
        @Override
        public void onSuccess(Object object) {
            friendList = (List<Friends>) object;


            if(friendList.size()>0) {

                friendsContainer.setVisibility(View.VISIBLE);
                FriendsAdapter friendsAdapter= new FriendsAdapter(friendList, getActivity(), getActivity());
                friends.setNestedScrollingEnabled(false);
                friends.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false));
                friends.setItemAnimator(new DefaultItemAnimator());
                friends.setAdapter(friendsAdapter);
            }
            else {
                friendsContainer.setVisibility(View.GONE);
            }
        }

        @Override
        public void onFailure(com.zonk.fbtest.Api.ApiResponse apiResponse) {
            fbtest.showToast("some error occured");

        }



    });

}
}
