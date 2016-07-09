package com.workspike.workspike;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.workspike.workspike.other_activities.HelpActivity;
import com.workspike.workspike.other_activities.TermsofserviceActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by Chamli Priyashan on 7/5/2016.
 */


public class LoginActivity extends Activity {
    private TextView info;
    private LoginButton loginButton;
    ImageView profilepicture;
    private ProfileTracker mProfileTracker;

    private CallbackManager callbackManager;

    boolean loggedIn = false;
    private static final String HOME_ACTIVITIES = "com.workspike.workspike.DashboardActivity";
    // Request Code
    private static final int HOME_ACTIVITIES_REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        System.out.println("goda");
        boolean loggedin_status = loadSavedPreferences("CheckBox_Value");
        System.out.println(loggedin_status);
        if (loggedin_status == true) {
            System.out.println("start main activity");
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }
        //  printhash();
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        loggedIn = isFacebookLoggedIn();


        setContentView(R.layout.login_activity);
        //info = (TextView) findViewById(R.id.info);
        loginButton = (LoginButton) findViewById(R.id.login_button);

        ImageButton closebutton= (ImageButton) findViewById(R.id.closeButton);
        closebutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
               finish();
            }
        });

        ImageButton helpbutton= (ImageButton) findViewById(R.id.helpButton);
        helpbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),HelpActivity.class);
                startActivity(i);
            }
        });

        ImageButton signupbutton= (ImageButton) findViewById(R.id.signupButton);
        signupbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(i);
            }
        });

        TextView termsofservice= (TextView) findViewById(R.id.termsofservice);
        termsofservice.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),TermsofserviceActivity.class);
                startActivity(i);
            }
        });

        TextView workspikelink= (TextView) findViewById(R.id.workspikelink);
        workspikelink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String url = "http://www.workspike.com";
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setPackage("com.android.chrome");
                try {
                    startActivity(i);
                } catch (ActivityNotFoundException e) {
                    // Chrome is probably not installed
                    // Try with the default browser
                    i.setPackage(null);
                    startActivity(i);
                }
            }
        });








        Typeface font_style = Typeface.createFromAsset(getAssets(), "rio-glamour-personal-use.regular.otf");
        loginButton.setTypeface(font_style);
      // Imagebutton loginButtonimage = (LoginButton) findViewById(R.id.imageButton);
        //profilepicture = (ImageView) findViewById(R.id.profile_image);
        loginButton.setReadPermissions(Arrays.asList(
                "user_status", "public_profile", "email", "user_birthday", "user_friends","user_location"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accesstoken = loginResult.getAccessToken();
                //Profile profile = Profile.getCurrentProfile();
                String userID = loginResult.getAccessToken().getUserId();

                if (Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                            // profile2 is the new profile
                            Log.v("facebook - profile", profile2.getFirstName());
                            mProfileTracker.stopTracking();

                            String name = profile2.getName();
                            String id=profile2.getId();
                            String lastname=profile2.getLastName();
                            String firstname=profile2.getFirstName();

                            savePreferences("NAME",name);
                            savePreferences("ID",id);
                            savePreferences("FIRSTNAME",lastname);
                            savePreferences("LASTNAME",firstname);
                            System.out.println(name);
                        }
                    };
                    // no need to call startTracking() on mProfileTracker
                    // because it is called by its constructor, internally.
                } else {
                    Profile profile = Profile.getCurrentProfile();
                    Log.v("facebook - profile", profile.getFirstName());

                    String name = profile.getName();
                    String id=profile.getId();
                    String lastname=profile.getLastName();
                    String firstname=profile.getFirstName();

                    savePreferences("NAME",name);
                    savePreferences("ID",id);
                    savePreferences("FIRSTNAME",lastname);
                    savePreferences("LASTNAME",firstname);
                    System.out.println(name);
                }


                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                // Application code
                                try {
                                    String email = object.getString("email");
                                    System.out.println(email);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    String gender = object.getString("gender");
                                    System.out.println(gender);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    String birthday = object.getString("birthday"); // 01/31/1980 format
                                    System.out.println(birthday);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields","id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();


                String profileImgUrl = "https://graph.facebook.com/" + userID + "/picture?type=large";
                System.out.println("this ids from login activity"+profileImgUrl);
                savePreferences("PROFILEIMAGE", profileImgUrl);
//                info.setText(
//                        "User ID: "
//                                + loginResult.getAccessToken().getUserId()
//                                + "\n" +
//                                "Auth Token: "
//                                + loginResult.getAccessToken().getToken()
//                );

                savePreferences("CheckBox_Value", true);
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancel() {
              //  info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
              //  info.setText("Login attempt failed");
            }
        });

    }

    public boolean isFacebookLoggedIn() {
        System.out.println(AccessToken.getCurrentAccessToken());
        return AccessToken.getCurrentAccessToken() != null;

    }









    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }




    private boolean loadSavedPreferences(String x) {

        SharedPreferences preferences = getSharedPreferences("your_file_name", MODE_PRIVATE);
        boolean checkBoxValue = preferences.getBoolean(x, false);//false is the default value...Dont worry
        System.out.println("login checkbox value=" + checkBoxValue);
        return checkBoxValue;

    }


    private void savePreferences(String key, boolean value) {
        SharedPreferences preferences = getSharedPreferences("your_file_name", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }


    private void savePreferences(String key, String value) {
        SharedPreferences preferences = getSharedPreferences("your_file_name", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();

    }







    private void printhash(){
        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.workspike.workspike", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }
}
