package io.aggreg.app.ui;

import android.accounts.Account;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import org.codechimp.apprater.AppRater;

import io.aggreg.app.R;
import io.aggreg.app.provider.AggregioProvider;
import io.aggreg.app.utils.AccountUtils;
import io.aggreg.app.utils.References;

public class SplashscreenActivity extends AppCompatActivity {
    protected int _splashTime = 2000;
    public static GoogleAnalytics analytics;
    public static Tracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        analytics = GoogleAnalytics.getInstance(this);
        analytics.setLocalDispatchPeriod(1800);

        //tracker = analytics.newTracker("UA-63988121-1"); // Replace with actual tracker/property Id
        tracker = analytics.newTracker(getString(R.string.analytics_tracker_id)); // Replace with actual tracker/property Id
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);

    // Enable Advertising Features.
        tracker.enableAdvertisingIdCollection(true);
        AppRater.app_launched(this);

        SharedPreferences settings = getSharedPreferences(References.KEY_PREFERENCES, 0);

        if (settings.getBoolean(References.KEY_PREF_FIRST_TIME, true)) {
            //the app is being launched for first time, do something
            Bundle settingsBundle = new Bundle();
            settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
            settingsBundle.putBoolean(
                    ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
            settingsBundle.putString(References.ARG_KEY_SYNC_TYPE, References.SYNC_TYPE_PUBLISHER);
            Account account = new AccountUtils(getApplicationContext()).getSyncAccount();
            ContentResolver.setSyncAutomatically(account, AggregioProvider.AUTHORITY, true);
            ContentResolver.requestSync(account, AggregioProvider.AUTHORITY, settingsBundle);

            // first time task


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
