package com.zonk.fbtest.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.PublishCallback;
import com.google.android.gms.nearby.messages.PublishOptions;
import com.google.android.gms.nearby.messages.Strategy;
import com.google.android.gms.nearby.messages.SubscribeCallback;
import com.google.android.gms.nearby.messages.SubscribeOptions;
import com.google.gson.Gson;
import com.zonk.fbtest.Adapter.NearByDevicesAdapter;
import com.zonk.fbtest.Adapter.NearByUsersAdapter;
import com.zonk.fbtest.Adapter.SkillsAdapter;
import com.zonk.fbtest.Api.ApiRequestHelper;
import com.zonk.fbtest.Fbtest;
import com.zonk.fbtest.Model.Skill;
import com.zonk.fbtest.Model.User;
import com.zonk.fbtest.PrefActivity;
import com.zonk.fbtest.ProfilePreviewActivity;
import com.zonk.fbtest.R;
import com.zonk.fbtest.Utils.DeviceMessage;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearbyFragment extends Fragment  implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener  {


    static String molo="";
    Fbtest fbtest;
    RecyclerView nearbyDevicesListView;
    List<User>  users;
    User user;
    private static final String TAG = NearbyFragment.class.getSimpleName();
    private static final int TTL_IN_SECONDS = 3 * 60; // Three minutes.

    // Key used in writing to and reading from SharedPreferences.
    private static final String KEY_UUID = "key_uuid";

    /**
     * Sets the time in seconds for a published message or a subscription to live. Set to three
     * minutes in this sample.
     */
    private static final Strategy PUB_SUB_STRATEGY = new Strategy.Builder()
            .setTtlSeconds(TTL_IN_SECONDS).build();

    /**
     * Creates a UUID and saves it to {@link android.content.SharedPreferences}. The UUID is added to the published
     * message to avoid it being undelivered due to de-duplication. See {@link com.zonk.fbtest.Utils.DeviceMessage} for
     * details.
     */
    private static String getUUID(SharedPreferences sharedPreferences) {
        String uuid = sharedPreferences.getString(KEY_UUID, "");
        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            sharedPreferences.edit().putString(KEY_UUID, uuid).apply();
        }
        return uuid;
    }

    /**
     * The entry point to Google Play Services.
     */
    private GoogleApiClient mGoogleApiClient;

    NearByDevicesAdapter nearByUsersAdapter;
    // Views.
    private SwitchCompat mPublishSwitch;

    View v;
    /**
     * The {@link android.os.Message} object used to broadcast information about the device to nearby devices.
     */
    private com.google.android.gms.nearby.messages.Message mPubMessage;

    /**
     * A {@link com.google.android.gms.nearby.messages.MessageListener} for processing messages from nearby devices.
     */
    private MessageListener mMessageListener;

    /**
     * Adapter for working with messages from nearby publishers.
     */


    public static NearbyFragment newInstance() {
        NearbyFragment fragment = new NearbyFragment();

        Bundle args = new Bundle();  fragment.setArguments(args);
        return fragment;
    }

    public NearbyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fbtest= (Fbtest) getActivity().getApplication();
        user= fbtest.getPreferences().getUserDTO();
        users= new ArrayList<>();
        molo= user.getLoginId().toString();
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v = inflater.inflate(R.layout.fragment_nearby, container, false);
    return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPublishSwitch = (SwitchCompat) v.findViewById(R.id.publish_switch);
        nearbyDevicesListView = (RecyclerView) v.findViewById(
                R.id.nearby_devices_list_view);
        nearByUsersAdapter= new NearByDevicesAdapter(users, getActivity(), getActivity());


        if (nearbyDevicesListView != null) {




            nearbyDevicesListView.setNestedScrollingEnabled(false);
            nearbyDevicesListView.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false));
            nearbyDevicesListView.setItemAnimator(new DefaultItemAnimator());
            nearbyDevicesListView.setAdapter(nearByUsersAdapter);

        }
        fbtest= (Fbtest) (getActivity()).getApplication();
        // Build the message that is going to be published. This contains the device name and a
        // UUID.
        mPubMessage = DeviceMessage.newNearbyMessage(getUUID(getActivity().getSharedPreferences(
                getActivity().getPackageName(), Context.MODE_PRIVATE)));

        mMessageListener = new MessageListener() {
            @Override
            public void onFound(final Message message) {
                // Called when a new message is found.
                String logId=DeviceMessage.fromNearbyMessage(message).getMessageBody();

                fbtest.getApiRequestHelper().getUser(logId,new ApiRequestHelper.onRequestComplete(){
                    @Override
                    public void onSuccess(Object object) {

                        User user= (User) object;
                        users.add(user);
                        nearByUsersAdapter.notifyDataSetChanged();
//                        nearbyDevicesListView.setAdapter(nearByUsersAdapter);


                    }

                    @Override
                    public void onFailure(com.zonk.fbtest.Api.ApiResponse apiResponse) {
                        fbtest.showToast("some error occured");

                    }



                });



            }

            @Override
            public void onLost(final Message message) {

                String logId=DeviceMessage.fromNearbyMessage(message).getMessageBody();
 for(int i=0;i<users.size();i++){
     if(users.get(i).getLoginId().equals(logId)){
         users.remove(users.get(i));
         nearByUsersAdapter.notifyDataSetChanged();

     }
 }
            }
        };



        mPublishSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // If GoogleApiClient is connected, perform pub actions in response to user action.
                // If it isn't connected, do nothing, and perform pub actions when it connects (see
                // onConnected()).
                if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                    if (isChecked) {
                        publish();
                        subscribe();
                    } else {
                        unpublish();
                        users.clear();
                        nearByUsersAdapter.notifyDataSetChanged();
                        unsubscribe();

                    }
                }
            }
        });


        buildGoogleApiClient();
    }
    private void buildGoogleApiClient() {
        if (mGoogleApiClient != null) {
            return;
        }
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Nearby.MESSAGES_API)
                .addConnectionCallbacks(this)
                .enableAutoManage(getActivity(), this)
                .build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mPublishSwitch.setEnabled(false);
        logAndShowSnackbar("Exception while connecting to Google Play services: " +
                connectionResult.getErrorMessage());
    }

    @Override
    public void onConnectionSuspended(int i) {
        logAndShowSnackbar("Connection suspended. Error code: " + i);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "GoogleApiClient connected");
        // We use the Switch buttons in the UI to track whether we were previously doing pub/sub (
        // switch buttons retain state on orientation change). Since the GoogleApiClient disconnects
        // when the activity is destroyed, foreground pubs/subs do not survive device rotation. Once
        // this activity is re-created and GoogleApiClient connects, we check the UI and pub/sub
        // again if necessary.
        if (mPublishSwitch.isChecked()) {
            publish();
            subscribe();

        }
    }

    /**
     * Subscribes to messages from nearby devices and updates the UI if the subscription either
     * fails or TTLs.
     */
    private void subscribe() {
        Log.i(TAG, "Subscribing");
        users.clear(); //clear list
        nearByUsersAdapter.notifyDataSetChanged();
        SubscribeOptions options = new SubscribeOptions.Builder()
                .setStrategy(PUB_SUB_STRATEGY)
                .setCallback(new SubscribeCallback() {
                    @Override
                    public void onExpired() {
                        super.onExpired();
                        Log.i(TAG, "No longer subscribing");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mPublishSwitch.setChecked(false);
                            }
                        });
                    }
                }).build();

        Nearby.Messages.subscribe(mGoogleApiClient, mMessageListener, options)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                            Log.i(TAG, "Subscribed successfully.");
                        } else {
                            logAndShowSnackbar("Could not subscribe, status = " + status);
                            mPublishSwitch.setChecked(false);
                        }
                    }
                });
    }

    /**
     * Publishes a message to nearby devices and updates the UI if the publication either fails or
     * TTLs.
     */
    private void publish() {
        Log.i(TAG, "Publishing");
        PublishOptions options = new PublishOptions.Builder()
                .setStrategy(PUB_SUB_STRATEGY)
                .setCallback(new PublishCallback() {
                    @Override
                    public void onExpired() {
                        super.onExpired();
                        Log.i(TAG, "No longer publishing");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mPublishSwitch.setChecked(false);
                            }
                        });
                    }
                }).build();

        Nearby.Messages.publish(mGoogleApiClient, mPubMessage, options)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                            Log.i(TAG, "Published successfully.");
                        } else {
                            logAndShowSnackbar("Could not publish, status = " + status);
                            mPublishSwitch.setChecked(false);
                        }
                    }
                });
    }

    /**
     * Stops subscribing to messages from nearby devices.
     */
    private void unsubscribe() {
        Log.i(TAG, "Unsubscribing.");
        Nearby.Messages.unsubscribe(mGoogleApiClient, mMessageListener);
    }

    /**
     * Stops publishing message to nearby devices.
     */
    private void unpublish() {
        Log.i(TAG, "Unpublishing.");
        Nearby.Messages.unpublish(mGoogleApiClient, mPubMessage);
    }

    /**
     * Logs a message and shows a {@link android.support.design.widget.Snackbar} using {@code text};
     *
     * @param text The text used in the Log message and the SnackBar.
     */
    private void logAndShowSnackbar(final String text) {
        Log.w(TAG, text);
        View container = v.findViewById(R.id.activity_main_container);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    public static class DeviceMessage {
        private static final Gson gson = new Gson();

        private final String mUUID;
        private final String mMessageBody;

        public static Message newNearbyMessage(String instanceId) {
            DeviceMessage deviceMessage = new DeviceMessage(instanceId);
            return new Message(gson.toJson(deviceMessage).getBytes(Charset.forName("UTF-8")));
        }

        /**
         * Creates a {@code DeviceMessage} object from the string used to construct the payload to a
         * {@code Nearby} {@code Message}.
         */
        public static com.zonk.fbtest.Utils.DeviceMessage fromNearbyMessage(Message message) {
            String nearbyMessageString = new String(message.getContent()).trim();
            return gson.fromJson(
                    (new String(nearbyMessageString.getBytes(Charset.forName("UTF-8")))),
                    com.zonk.fbtest.Utils.DeviceMessage.class);
        }

        private DeviceMessage(String uuid) {
            mUUID = uuid;
            mMessageBody =molo;

            // TODO(developer): add other fields that must be included in the Nearby Message payload.
        }

        public String getMessageBody() {
            return mMessageBody;
        }
    }
}
