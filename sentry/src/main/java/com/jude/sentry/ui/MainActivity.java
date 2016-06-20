package com.jude.sentry.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jude.sentry.ui.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,new MainFragment()).commit();
    }
}
