package com.zonk.fbtest.Fragment;


import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.zonk.fbtest.Adapter.FriendsAdapter;
import com.zonk.fbtest.Adapter.NearByUsersAdapter;
import com.zonk.fbtest.Adapter.SkillsAdapter;
import com.zonk.fbtest.Adapter.SkillsAdapter2;
import com.zonk.fbtest.Api.ApiRequestHelper;
import com.zonk.fbtest.Fbtest;
import com.zonk.fbtest.Model.Friends;
import com.zonk.fbtest.Model.Shout;
import com.zonk.fbtest.Model.Skill;
import com.zonk.fbtest.Model.User;
import com.zonk.fbtest.PrefActivity;
import com.zonk.fbtest.ProfileActivity;
import com.zonk.fbtest.ProfilePreviewActivity;
import com.zonk.fbtest.R;
import com.zonk.fbtest.SelectSkillLevelActivity;
import com.zonk.fbtest.ShoutOut;
import com.zonk.fbtest.Utils.InfoWindowRefresh;
import com.zonk.fbtest.Views.CommonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import github.hellocsl.layoutmanager.gallery.GalleryLayoutManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConnectFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, LocationListener, GoogleApiClient.OnConnectionFailedListener, ResultCallback<LocationSettingsResult> {

    Marker marker;

    List<Skill>  skills;
    RecyclerView  recyclerFans;
    GalleryLayoutManager layoutManager;
    List<User> users;
    private GoogleMap map;
    Fbtest fbtest;
    boolean firstTime= true;
    private GoogleApiClient mGoogleApiClient;
    List<Shout>  shouts;
    LocationRequest mLocationRequest;
    RelativeLayout shout;
    Location mLastLocation;
    int curr;
    LocationManager locationManager;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    Location userLocation;

    RelativeLayout main, wait;
    private List<Marker> mapMarkers;
    private List<Polyline> mapPolyLines;


    //map variables
    public static ConnectFragment newInstance() {
        ConnectFragment fragment = new ConnectFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ConnectFragment() {
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
        View v = inflater.inflate(R.layout.fragment_connect, container, false);
        initGoogleApiClient();
        shouts=new ArrayList<>();
        mapMarkers = new ArrayList<>();
        mapPolyLines = new ArrayList<>();
        fbtest=(Fbtest)getActivity().getApplication();
        shout=(RelativeLayout)v.findViewById(R.id.shout);
        shout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skill selectedSkill;

                skills= new ArrayList<>();
                selectedSkill= null;
                final ProgressDialog progressDialog= new ProgressDialog(getActivity());
                progressDialog.setMessage("LOADING...");
                progressDialog.show();
                fbtest.getApiRequestHelper().getSkills(new ApiRequestHelper.onRequestComplete(){
                    @Override
                    public void onSuccess(Object object) {

                        progressDialog.dismiss();
                        skills= (List<Skill>) object;
                        final Dialog dialog = new Dialog(getActivity());
                        fbtest.getPreferences().setSkill("");
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog.setContentView(R.layout.make_shout_dialog);
                        SkillsAdapter2 skillsAdapter;
                        TextView done;
                        TextView name;
                        ImageView close;
                        final EditText workingOn;
                        RecyclerView tableList;
                        tableList=(RecyclerView)dialog.findViewById(R.id.tablelist);
                        name=(TextView)dialog.findViewById(R.id.name);

                        name.setText("HI"+ " " +fbtest.getPreferences().getUserDTO().getName());


                        close=(ImageView)dialog.findViewById(R.id.close);
                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        workingOn=(EditText)dialog.findViewById(R.id.workingon);
                        done=(TextView)dialog.findViewById(R.id.done);
                        SkillsAdapter2             skillsAdapter2= new SkillsAdapter2(skills, getActivity());

                        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.HORIZONTAL);
                        tableList.setLayoutManager(staggeredGridLayoutManager); // set LayoutManager to RecyclerView
                        tableList.setItemAnimator(new DefaultItemAnimator());
                        tableList.setAdapter(skillsAdapter2);


                        done.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(workingOn.getText().toString().equals("")){
                                    fbtest.showToast("describe what are you working on");
                                }
                                else {

                                    if(fbtest.getPreferences().getSkill().equals("")){
                                        fbtest.showToast("select what skills are you looking for");

                                    }
                                    else {
                                        //todo
                                        //api
                                        for(int i=0;i<skills.size();i++){
                                            if(skills.get(i).getSkillName().equals(fbtest.getPreferences().getSkill())){

                                                Shout shout= new Shout();
                                                shout.setLat(fbtest.getPreferences().getUserDTO().getLat());
                                                shout.setLoginId(fbtest.getPreferences().getUserDTO().getLoginId());
                                                shout.setLon(fbtest.getPreferences().getUserDTO().getLon());
                                                shout.setPersonName(fbtest.getPreferences().getUserDTO().getName());
                                                shout.setProfilePic(fbtest.getPreferences().getUserDTO().getProfilePic());
                                                shout.setSkill(skills.get(i));
                                                shout.setWorkingOn(workingOn.getText().toString());
                                                fbtest.getApiRequestHelper().makeshout(shout,new ApiRequestHelper.onRequestComplete(){
                                                    @Override
                                                    public void onSuccess(Object object) {

                                                        dialog.dismiss();

                                                        fbtest.showToast("shoutout is being broadcasted");
                                                        setUpList();

                                                    }

                                                    @Override
                                                    public void onFailure(com.zonk.fbtest.Api.ApiResponse apiResponse) {
                                                        fbtest.showToast("some error occured");

                                                        dialog
                                                                .dismiss();
                                                    }



                                                });
                                            }
                                        }
                                    }
                                }
                            }
                        });

                        dialog.show();
                        dialog.setCancelable(true);


                    }

                    @Override
                    public void onFailure(com.zonk.fbtest.Api.ApiResponse apiResponse) {
                        fbtest.showToast("some error occured");

                        progressDialog.dismiss();
                    }



                });



            }
        });
        recyclerFans = (RecyclerView)v.findViewById(R.id.friends);

        main=(RelativeLayout)v.findViewById(R.id.main);
        wait=(RelativeLayout)v.findViewById(R.id.wait);

        wait.setVisibility(View.VISIBLE);
        main.setVisibility(View.GONE);
        users= new ArrayList<>();



        return v;
    }



    private void initGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)

                .build();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
        setUpPage();

        layoutManager = new GalleryLayoutManager(GalleryLayoutManager.HORIZONTAL);
        layoutManager.setCallbackInFling(true);//should receive callback when flinging, default is false
        layoutManager.setItemTransformer(new com.zonk.fbtest.Views.ScaleTransformation());



        layoutManager.setOnItemSelectedListener(new GalleryLayoutManager.OnItemSelectedListener() {
            @Override
            public void onItemSelected(RecyclerView recyclerView, View item, int i) {
                   // placePosition=i;

                removeMapMarkers();
                curr=i;
                    addMarker(shouts.get(i));

            }
        });


        initMap();




    }



    private void removeMapMarkers() {

        if (mapMarkers != null) {

            for (Marker marker : mapMarkers) {
                marker.remove();
            }
            mapMarkers.clear();
        }

    }
    public void setUpPage(){
        Log.e("events", "setuppage called");
        if(haveNetworkConnection()){
            tryGettingLocation();

        }
        else {

        }
    }
    public  void  tryGettingLocation(){


      //  loadingText.setText("Getting Location...");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {

            Log.e("events", "api  marshmello");
            checkLocationPermission();
        }
        else
        {


            Log.e("events", "api below marshmello");
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {

                Log.e("events", "location service is off");
                showGPSDisabledAlertToUser();
            }
        }
    }

    private void showGPSDisabledAlertToUser()
    {

//        loadingText.setText("Getting Location...");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("GPS is disabled in your device. Please enable it?")
                .setCancelable(false)
                .setPositiveButton("Settings", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(callGPSSettingIntent,1);

                    }
                });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

                tryGettingLocation();
                //// TODO: 6/7/2017 location is not granted
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }




    public boolean checkLocationPermission() {

        Log.e("events", "check location permission given");
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("events", " location permission not  given");

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)) {


                Log.e("events", "request for permission");
                requestPermissions( new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                // No explanation needed, we can request the permission.
                requestPermissions( new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {


            Log.e("events", " location permission   given");

            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                showGPSDisabledAlertToUser();
                Log.e("events", "location service is off");


            }

            return true;
        }

    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }



    public void initMap() {


        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));

        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    map = googleMap;


                    map.setMapStyle(
                            MapStyleOptions.loadRawResourceStyle(
                                    getActivity(), R.raw.map_style2));

                    map.getUiSettings().setZoomControlsEnabled(false);

                }
            });
        }


    }






    @Override
    public void onPause() {
        super.onPause();

    }




    ////

    protected synchronized void buildGoogleApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle)
    {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(3000);
        mLocationRequest.setFastestInterval(3000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

    @Override
    public void onLocationChanged(Location location)
    {
        wait.setVisibility(View.GONE);
        main.setVisibility(View.VISIBLE);

        mLastLocation = location;
      User user=  fbtest.getPreferences().getUserDTO();
      user.setLat(location.getLatitude());
        user.setLon(location.getLongitude());
        fbtest.getPreferences().setUserDTO(user);

        fbtest.getApiRequestHelper().setUserLocation(location.getLatitude(), location.getLongitude(),new ApiRequestHelper.onRequestComplete(){
            @Override
            public void onSuccess(Object object) {

            }

            @Override
            public void onFailure(com.zonk.fbtest.Api.ApiResponse apiResponse) {
            }



        });



        setUpList();




        //// TODO: 6/7/2017 got location check if at outlet or not, call get outlet and match location
        Log.e("location","changed");
//        atOutletSetUp(location);
        userLocation=location;

        if (mGoogleApiClient != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }///


    public  void  setUpList(){
        fbtest.getApiRequestHelper().getShouts(new ApiRequestHelper.onRequestComplete(){
            @Override
            public void onSuccess(Object object) {
                shouts= (List<Shout>) object;

                NearByUsersAdapter nearByUsersAdapter= new NearByUsersAdapter(shouts,getActivity(),getActivity());
                recyclerFans.setNestedScrollingEnabled(false);

                layoutManager.attach(recyclerFans, shouts.size());
                recyclerFans.setItemAnimator(new DefaultItemAnimator());
                recyclerFans.setAdapter(nearByUsersAdapter);







                fbtest.getApiRequestHelper().myshoutsAvailable(new ApiRequestHelper.onRequestComplete(){
                    @Override
                    public void onSuccess(Object object) {
                        if((boolean)object)
                        {
                            Intent serviceIntent = new Intent(getActivity(), ShoutOut.class);
                            if(!CommonUtil.isServiceRunning(getActivity(), ShoutOut.class))
                                getActivity().startService(serviceIntent);

                        }
                        else {
                            Intent serviceIntent = new Intent(getActivity(), ShoutOut.class);
                            if(CommonUtil.isServiceRunning(getActivity(), ShoutOut.class))
                                getActivity().stopService(serviceIntent);
                        }
                    }

                    @Override
                    public void onFailure(com.zonk.fbtest.Api.ApiResponse apiResponse) {
                    }



                });

            }

            @Override
            public void onFailure(com.zonk.fbtest.Api.ApiResponse apiResponse) {
            }



        });





    }

    @Override
    public void onResume() {
        super.onResume();
        buildGoogleApiClient();

    }


    private void addMarker(Shout shout) {


        LatLng latLng = new LatLng(
                shout.getLat(),
                shout.getLon()
        );

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                latLng,
                6.0f
        ));

         marker = map.addMarker(new MarkerOptions()
                .position(latLng)
                .title(shout.getPersonName())
                .snippet(shout.getSkill().getSkillName())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mappointer2)));



        mapMarkers.add(marker);
            map.setInfoWindowAdapter(new MyInfoWindowAdapter());
        marker.showInfoWindow();

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {



                        final ProgressDialog progressDialog= new ProgressDialog(getActivity());
                        progressDialog.setMessage("LOADING.....");
                        progressDialog.show();

                        fbtest.getApiRequestHelper().getUser(shouts.get(curr).getLoginId(),new ApiRequestHelper.onRequestComplete(){
                            @Override
                            public void onSuccess(Object object) {

                                User user= (User) object;
                                progressDialog.dismiss();

                                Intent i = new Intent(getActivity(), ProfileActivity.class);
                                i.putExtra("user",user);
                                startActivity(i);
                                i.putExtra("from","shoutouts");
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
    public void onStop()
    {
        super.onStop();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }
    private void stopGoogleApiClient() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if ( (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) || (grantResults.length > 1 && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    showGPSDisabledAlertToUser();
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) || shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION))) {


                    tryGettingLocation();
                    //// TODO: 6/7/2017 not granted


                } else{

                }
            }
        } else {

            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {

    }



    public float getDistanceA(String lat, String lon, String placeLat, String placeLon) {

        Location locationA = new Location("point A");
        locationA.setLatitude(Double.valueOf(placeLat));
        locationA.setLongitude(Double.valueOf(placeLon));
        Location locationB = new Location("point B");
        locationB.setLatitude(Double.valueOf(lat));
        locationB.setLongitude(Double.valueOf(lon));
        return locationA.distanceTo(locationB);
    }




    class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{
        private final View mWindow;

        public MyInfoWindowAdapter() {
            mWindow = getActivity().getLayoutInflater().inflate(R.layout.custom_info_window,
                    null);


            final com.zonk.fbtest.Utils.CircleImageView logo= (com.zonk.fbtest.Utils.CircleImageView) mWindow.findViewById(R.id.logo);


            TextView tv= (TextView)mWindow.findViewById(R.id.title);
                tv.setText( shouts.get(curr).getPersonName());


            if (firstTime) {
                Picasso.with(getActivity()).load(shouts.get(curr).getProfilePic()).into(logo,new InfoWindowRefresh(marker));
                firstTime= false;

            } else {

                Picasso.with(getActivity()).load(shouts.get(curr).getProfilePic()).into(logo);
                }
        }
        @Override
        public View getInfoContents(Marker mark) {

            return null;
        }

        @Override
        public View getInfoWindow(Marker marker) {

            render(marker, mWindow);
            return mWindow;
        }

        private void render(Marker marker, View view) {
            int badge;
            // Use the equals() method on a Marker to check for equals. Do not
            // use ==.
            badge = R.drawable.icon_cross;



        }

    }}






