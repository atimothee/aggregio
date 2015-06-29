package io.aggreg.app.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import io.aggreg.app.R;
import io.aggreg.app.ui.fragment.FirstSlide;
import io.aggreg.app.ui.fragment.SecondSlide;
import io.aggreg.app.ui.fragment.ThirdSlide;
import io.aggreg.app.utils.References;

/**
 * Created by Timo on 6/10/15.
 */
public class IntroActivity extends AppIntro{
    Tracker tracker;
    @Override
    public void init(Bundle bundle) {

        addSlide(new FirstSlide(), getApplicationContext());
        addSlide(new SecondSlide(), getApplicationContext());
        addSlide(new ThirdSlide(), getApplicationContext());
        setBarColor(getResources().getColor(R.color.theme_accent_3));
        setSeparatorColor(Color.parseColor("#2196F3"));
        showSkipButton(true);
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(IntroActivity.this);
        tracker = analytics.newTracker(getString(R.string.analytics_tracker_id));tracker.setScreenName("intro screen");

    }


    @Override
    public void onSkipPressed() {
        launch();
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("UX")
                .setAction("click")
                .setLabel("skip")
                .build());
    }

    @Override
    public void onDonePressed() {
        launch();
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("UX")
                .setAction("click")
                .setLabel("done")
                .build());
    }

    private void launch(){
        Intent i = new Intent();
        i.setClass(IntroActivity.this, MainActivity.class);
        startActivity(i);
        SharedPreferences settings = getSharedPreferences(References.KEY_PREFERENCES, 0);
        settings.edit().putBoolean(References.KEY_INTRO_SHOWN, true).apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
