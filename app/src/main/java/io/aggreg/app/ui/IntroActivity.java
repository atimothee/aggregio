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
    @Override
    public void init(Bundle bundle) {

        addSlide(new FirstSlide(), getApplicationContext());
        addSlide(new SecondSlide(), getApplicationContext());
        addSlide(new ThirdSlide(), getApplicationContext());
        setBarColor(getResources().getColor(R.color.theme_accent_3));
        setSeparatorColor(Color.parseColor("#2196F3"));
        showSkipButton(true);
        SharedPreferences prefs = getSharedPreferences(References.KEY_PREFERENCES, MODE_PRIVATE);
        prefs.edit().putBoolean(References.KEY_HAS_INTRO_BEEN_SHOWN, true).apply();
    }


    @Override
    public void onSkipPressed() {
        launch();
    }

    @Override
    public void onDonePressed() {
        launch();
    }

    private void launch(){
        Intent i = new Intent();
        i.setClass(IntroActivity.this, MainActivity.class);
        startActivity(i);


    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
