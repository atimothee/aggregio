package io.aggreg.app.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro2;

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
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getResources().getResourcePackageName(R.drawable.folded_newspaper_512) + '/' + getResources().getResourceTypeName(R.drawable.folded_newspaper_512) + '/' + getResources().getResourceEntryName(R.drawable.folded_newspaper_512));


        addSlide(SlideFragment.newInstance(getResources().getString(R.string.all_in_one_short),
                getResources().getString(R.string.all_in_one_long),
                imageUri));
        imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getResources().getResourcePackageName(R.drawable.download_from_cloud_512) + '/' + getResources().getResourceTypeName(R.drawable.download_from_cloud_512) + '/' + getResources().getResourceEntryName(R.drawable.download_from_cloud_512));


        addSlide(SlideFragment.newInstance(getResources().getString(R.string.without_internet_short),
                getResources().getString(R.string.without_internet_long),
                imageUri));
        imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getResources().getResourcePackageName(R.drawable.add_bookmark_solid_interface_symbol_512) + '/' + getResources().getResourceTypeName(R.drawable.add_bookmark_solid_interface_symbol_512) + '/' + getResources().getResourceEntryName(R.drawable.add_bookmark_solid_interface_symbol_512));

        addSlide(SlideFragment.newInstance(getResources().getString(R.string.bookmark_short),
                getResources().getString(R.string.bookmark_long),
                imageUri));
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
