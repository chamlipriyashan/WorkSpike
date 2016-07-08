package com.workspike.workspike;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView profilepicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // profilepicture = (ImageView) findViewById(R.id.dashboardprofilepic);

        setContentView(R.layout.activity_dashboard);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView imageView = (ImageView) findViewById(R.id.dashboard);
        setSupportActionBar(toolbar);
        FacebookSdk.sdkInitialize(getApplicationContext());


        System.out.println(" this is from Dashboard activity" + loadSavedimagePreferences("PROFILEIMAGE"));
        System.out.println(loadSavedstringPreferences("NAME", "DEFAULT"));
        System.out.println(loadSavedstringPreferences("ID", "DEFAULT"));
        System.out.println(loadSavedstringPreferences("FIRSTNAME", "DEFAULT"));
        System.out.println(loadSavedstringPreferences("LASTNAME", "DEFAULT"));
        System.out.println(loadSavedstringPreferences("NAME", "DEFAULT"));

        Glide.with(DashboardActivity.this)
                .load(loadSavedimagePreferences("PROFILEIMAGE"))
                .into(imageView);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

        } else if (id == R.id.action_logout) {
            new AlertDialog.Builder(this)
                    .setTitle("Are you sure?").setMessage("Do you realy want to Log out?").setIcon(android.R.drawable.ic_dialog_alert).
                    setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            savePreferences("CheckBox_Value", false);
                            LoginManager.getInstance().logOut();
                            finish();
                        }
                    })

                    .setNegativeButton(android.R.string.no, null).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            // Handle the camera action
        } else if (id == R.id.nav_inbox) {

        } else if (id == R.id.nav_notification) {

        } else if (id == R.id.nav_favourits) {

        } else if (id == R.id.nav_brows_projects) {

        } else if (id == R.id.nav_browse_spikers) {

        } else if (id == R.id.nav_my_projects) {

        } else if (id == R.id.nav_sales) {

        } else if (id == R.id.nav_my_spikes) {

        } else if (id == R.id.nav_revenues) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_feedback) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_sign_out) {

            new AlertDialog.Builder(this)
                    .setTitle("Are you sure?").setMessage("Do you realy want to Log out?").setIcon(android.R.drawable.ic_dialog_alert).

                    setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            savePreferences("CheckBox_Value", false);
                            LoginManager.getInstance().logOut();
                            finish();
                        }
                    })

                    .setNegativeButton(android.R.string.no, null).show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private String loadSavedimagePreferences(String x) {
        SharedPreferences preferences = getSharedPreferences("your_file_name", MODE_PRIVATE);
        // boolean checkBoxValue = preferences.getBoolean("CheckBox_Value", false);
        String string = preferences.getString("PROFILEIMAGE", "http://goo.gl/gEgYUd");
        System.out.println("returened image=" + "PROFILEIMAGE" + "value=" + string);
        return string;
    }

    private String loadSavedstringPreferences(String key, String dummy) {
        SharedPreferences preferences = getSharedPreferences("your_file_name", MODE_PRIVATE);
        String string = preferences.getString(key, dummy);
        System.out.println("returened string=" + key + "value=" + string);
        return string;
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


}
