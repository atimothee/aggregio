package io.aggreg.app.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.aggreg.app.R;
import io.aggreg.app.utils.GeneralUtils;
import io.aggreg.app.utils.References;

public class SplashscreenActivity extends AppCompatActivity {
    protected int splashTime = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        SharedPreferences prefs = getSharedPreferences(References.KEY_PREFERENCES, MODE_PRIVATE);
        Boolean introShown = prefs.getBoolean(References.KEY_INTRO_SHOWN, false);
        if(prefs.getBoolean(References.FIRST_SYNC_COMPLETE, false)){
            new GeneralUtils(this).SyncRefreshArticles();

        }else{
            new GeneralUtils(this).SyncRefreshFirstTime();

        }


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
