package io.aggreg.app.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

import io.aggreg.app.R;
import io.aggreg.app.ui.fragment.SlideFragment;
import io.aggreg.app.utils.References;

/**
 * Created by Timo on 6/10/15.
 */
public class IntroActivity extends AppIntro2 {
    private static final String LOG_TAG  = IntroActivity.class.getSimpleName();
    @Override
    public void init(Bundle bundle) {
        Fresco.initialize(getApplicationContext());

        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.all_in_one_short), getResources().getString(R.string.all_in_one_long),
                R.drawable.folded_newspaper_512, Color.parseColor("#C2185B")));
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.without_internet_short), getResources().getString(R.string.without_internet_long),
                R.drawable.download_from_cloud_512, Color.parseColor("#C2185B")));

        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.bookmark_short), getResources().getString(R.string.bookmark_long),
                R.drawable.add_bookmark_solid_interface_symbol_512, Color.parseColor("#C2185B")));


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
