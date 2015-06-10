package io.aggreg.app.ui;

import io.aggreg.app.ui.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import org.codechimp.apprater.AppRater;

import io.aggreg.app.R;

public class SplashscreenActivity extends AppCompatActivity {
    final String PREFS_NAME = "MyPrefsFile";
    final String KEY_FIRST_TIME_PREF = "first_time___";
    protected int _splashTime = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        AppRater.app_launched(this);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean(KEY_FIRST_TIME_PREF, true)) {
            //the app is being launched for first time, do something
            Log.d(SplashscreenActivity.class.getSimpleName(), "First time");

            // first time task

            // record the fact that the app has been started at least once
            settings.edit().putBoolean(KEY_FIRST_TIME_PREF, false).commit();
            Thread splashTread = new Thread() {
                @Override
                public void run() {
                    try {
                        synchronized(this){
                            wait(_splashTime);
                        }

                    } catch(InterruptedException e) {}
                    finally {
                        finish();

                        Intent i = new Intent();
                        i.setClass(SplashscreenActivity.this, IntroActivity.class);
                        startActivity(i);
                    }
                }
            };
            splashTread.start();
        }
        else{
            Intent i = new Intent();
            i.setClass(SplashscreenActivity.this, MainActivity.class);
            startActivity(i);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
