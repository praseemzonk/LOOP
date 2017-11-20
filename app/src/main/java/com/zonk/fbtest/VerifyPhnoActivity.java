package com.zonk.fbtest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.zonk.fbtest.Adapter.SkillsAdapter;
import com.zonk.fbtest.Api.ApiRequestHelper;
import com.zonk.fbtest.Model.Skill;
import com.zonk.fbtest.Model.User;

import java.util.List;

public class VerifyPhnoActivity extends AppCompatActivity {

    TextView proceed;
    LinearLayout doneLayout,abcd;
    LottieAnimationView  anima1, anioma2;
    RelativeLayout lay;
    int APP_REQUEST_CODE=1;
    Fbtest fbtest;
    TextView send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phno);


        fbtest= (Fbtest) getApplication();
        proceed=(TextView)findViewById(R.id.proceed);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initAccountKitSmsFlow();
            }
        });

    }
    /**
     * Initializes Facebook Account Kit Sms flow registration.
     */

    public void initAccountKitSmsFlow() {
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.TOKEN
        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage= "";
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
            } else {
                if (loginResult.getAccessToken() != null) {
                    toastMessage = "" ;
                    getAccount();
                }
            }
            // Surface the result to your user in an appropriate way.

        }
    }

    /**
     * Gets current account from Facebook Account Kit which include user's phone number.
     */
    private void getAccount(){
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {
                // Get Account Kit ID
                String accountKitId = account.getId();

                // Get phone number
                PhoneNumber phoneNumber = account.getPhoneNumber();
                final String phoneNumberString = phoneNumber.toString();

                // Surface the result to your user in an appropriate way.
                Toast.makeText(
                        VerifyPhnoActivity.this,
                        "phone number verified",
                        Toast.LENGTH_LONG)
                        .show();

                final ProgressDialog progressDialog = new ProgressDialog(VerifyPhnoActivity.this);
                progressDialog.setMessage("LOGGING IN");
                proceed.setVisibility(View.GONE);
                progressDialog.show();
                fbtest.getApiRequestHelper().submitPhno(phoneNumberString,new ApiRequestHelper.onRequestComplete(){
                    @Override
                    public void onSuccess(Object object) {


                        progressDialog.dismiss();
                        fbtest.getPreferences().setOtpverified(true);
                        User user= new User();
                        user= fbtest.getPreferences().getUserDTO();
                        user.setPhNo(phoneNumberString);
                        fbtest.getPreferences().setUserDTO(user);
//
                        startActivity(new Intent(VerifyPhnoActivity.this, PrefActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(com.zonk.fbtest.Api.ApiResponse apiResponse) {

                        progressDialog.dismiss();
                        fbtest.getPreferences().setOtpverified(true);
                        startActivity(new Intent(VerifyPhnoActivity.this, PrefActivity.class));
                        finish();

                    }



                });
            }

            @Override
            public void onError(final AccountKitError error) {
                Log.e("AccountKit",error.toString());
                // Handle Error
            }
        });
    }


}
