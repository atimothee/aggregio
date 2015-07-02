package io.aggreg.app.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.aggreg.app.R;
import io.aggreg.app.sync.ArticleDeleteService;
import io.aggreg.app.utils.GeneralUtils;
import io.aggreg.app.utils.References;

public class SplashscreenActivity extends AppCompatActivity {
    protected int splashTime = 2000;
    private final String LOG_TAG = SplashscreenActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        SharedPreferences prefs = getSharedPreferences(References.KEY_PREFERENCES, MODE_PRIVATE);
        Boolean introShown = prefs.getBoolean(References.KEY_INTRO_SHOWN, false);

//        if(prefs.getBoolean(References.ARG_IS_FIRST_TIME, false)){
//            Log.d(LOG_TAG, "first time sync has not completed");
//            new GeneralUtils(this).SyncRefreshFirstTime();
//
//        }else{
//            Log.d(LOG_TAG, "first time sync complete");
//            new GeneralUtils(this).SyncRefreshArticles();
//
//        }
        //new GeneralUtils(this).SyncRefreshFirstTime();
        prefs.edit().putBoolean(References.ARG_IS_FIRST_TIME, false).apply();

        Intent intent = new Intent(this, ArticleDeleteService.class);

        PendingIntent pendingIntent = PendingIntent.getService(this, References.REQUEST_CODE,
                intent, 0);
        int alarmType = AlarmManager.ELAPSED_REALTIME;
        final int FIFTEEN_SEC_MILLIS = 15000;
        AlarmManager alarmManager = (AlarmManager)
                this.getSystemService(this.ALARM_SERVICE);
//        alarmManager.setRepeating(alarmType, SystemClock.elapsedRealtime() + FIFTEEN_SEC_MILLIS,
//                FIFTEEN_SEC_MILLIS, pendingIntent);
        Log.i(LOG_TAG, "Alarm set.");


        if (!introShown) {

            Thread splashTread = new Thread() {
                @Override
                public void run() {
                    try {
                        synchronized(this){
                            wait(splashTime);
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
