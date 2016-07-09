package com.workspike.workspike;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.workspike.workspike.other_activities.TermsofserviceActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_register);


        EditText email= (EditText) findViewById(R.id.et_email);
        EditText password= (EditText) findViewById(R.id.et_password);
        EditText fullname= (EditText) findViewById(R.id.et_full_name);
        EditText lastname= (EditText) findViewById(R.id.et_lastname);

        Typeface font_style = Typeface.createFromAsset(getAssets(), "rio-glamour-personal-use.regular.otf");
        email.setTypeface(font_style);
        password.setTypeface(font_style);
        fullname.setTypeface(font_style);
        lastname.setTypeface(font_style);

        TextView termsofservice= (TextView) findViewById(R.id.register_termsof_service);
        termsofservice.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(), TermsofserviceActivity.class);
                startActivity(i);
            }
        });

        TextView workspikelink= (TextView) findViewById(R.id.worksikelink_2);
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






    }
}
