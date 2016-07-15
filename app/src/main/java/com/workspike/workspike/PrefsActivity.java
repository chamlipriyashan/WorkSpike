package com.workspike.workspike;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Chamli Priyashan on 7/11/2016.
 */
public class PrefsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
    }
}