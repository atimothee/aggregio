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

        // Add your slide's fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
        addSlide(new FirstSlide(), getApplicationContext());
        addSlide(new SecondSlide(), getApplicationContext());
        addSlide(new ThirdSlide(), getApplicationContext());

        // You can override bar/separator color if you want.
        //setBarColor(Color.parseColor("#3F51B5"));
        setBarColor(getResources().getColor(R.color.theme_accent_3));
        setSeparatorColor(Color.parseColor("#2196F3"));

        // You can also hide Skip button
        showSkipButton(true);
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(IntroActivity.this);
        tracker = analytics.newTracker(getString(R.string.ga_trackingId)); // Send hits to tracker id UA-XXXX-Y
//
// //All subsequent hits will be send with screen name = "main screen"
        tracker.setScreenName("intro screen");



 //Builder parameters can overwrite the screen name set on the tracker.
//        tracker.send(new HitBuilders.EventBuilder()
//                .setCategory("UX")
//                .setAction("click")
//                .setLabel("help popup")
//                .build());
    }

    @Override
    public void onSkipPressed() {
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("UX")
                .setAction("click")
                .setLabel("skip")
                .build());
        launch();
    }

    @Override
    public void onDonePressed() {
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("UX")
                .setAction("click")
                .setLabel("done")
                .build());
        launch();
    }

    private void launch(){
        SharedPreferences settings = getSharedPreferences(References.KEY_PREFERENCES, 0);
        settings.edit().putBoolean(References.KEY_INTRO_SHOWN, true).apply();
        Intent i = new Intent();
        i.setClass(IntroActivity.this, SelectPublishersActivity.class);
        startActivity(i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
