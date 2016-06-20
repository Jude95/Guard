package com.jude.chicken;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText(getString());
    }

    private String getString(){
        String text = "";
        text+=findClass("com.jude.sentry.hook.SentryHook");
        text+=findClass("de.robv.android.xposed.XposedBridge");
        text+=findClass("com.jude.chicken.MainActivity");
        return text;
    }

    private String findClass(String clazz){
        String text = "";
        try {
            Class.forName(clazz);
            text+="Get "+clazz+"\n";
        } catch (ClassNotFoundException e) {
            text+="没找到类"+clazz+"\n";
        }
        return text;
    }
}
