package io.aggreg.app.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;

import io.aggreg.app.ui.fragment.FirstSlide;
import io.aggreg.app.ui.fragment.SecondSlide;
import io.aggreg.app.ui.fragment.ThirdSlide;

/**
 * Created by Timo on 6/10/15.
 */
public class IntroActivity extends AppIntro{
    @Override
    public void init(Bundle bundle) {

        // Add your slide's fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
        addSlide(new FirstSlide(), getApplicationContext());
        addSlide(new SecondSlide(), getApplicationContext());
        addSlide(new ThirdSlide(), getApplicationContext());

        // You can override bar/separator color if you want.
        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));

        // You can also hide Skip button
        showSkipButton(true);
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
}
