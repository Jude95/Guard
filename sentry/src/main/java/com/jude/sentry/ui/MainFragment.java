package com.jude.sentry.ui;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.jude.sentry.R;

/**
 * Created by Mr.Jude on 2016/6/19.
 */
public class MainFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesMode(Context.MODE_WORLD_READABLE);
        getPreferenceManager().setSharedPreferencesName("Sentry");
        addPreferencesFromResource(R.xml.preferences);
    }
}
