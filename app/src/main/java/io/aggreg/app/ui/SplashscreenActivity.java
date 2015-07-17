package io.aggreg.app.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;

import io.aggreg.app.R;
import io.aggreg.app.utils.GeneralUtils;
import io.aggreg.app.utils.References;

public class SplashscreenActivity extends AppCompatActivity {
    protected int splashTime = 2000;
    private final String LOG_TAG = SplashscreenActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getApplicationContext());
        setContentView(R.layout.activity_splashscreen);
        SharedPreferences prefs = this.getSharedPreferences(References.KEY_PREFERENCES, MODE_PRIVATE);
        Boolean isFirstTime = prefs.getBoolean(References.KEY_HAS_INTRO_BEEN_SHOWN, false);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean shouldSyncFirstTime = settings.getBoolean(getString(R.string.pref_key_refresh_on_start), true);
        if(shouldSyncFirstTime) {
            new GeneralUtils(this).SyncRefreshArticles();
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(References.KEY_HAS_INTRO_BEEN_SHOWN, true);
        editor.commit();

        Log.d(LOG_TAG, "has intro been shown "+isFirstTime);

        if (!isFirstTime) {

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
