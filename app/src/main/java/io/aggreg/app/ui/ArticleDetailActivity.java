package io.aggreg.app.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import io.aggreg.app.R;
import io.aggreg.app.ui.fragment.ArticleDetailFragment;
import io.aggreg.app.utils.References;


public class ArticleDetailActivity extends AppCompatActivity implements ArticleDetailFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, ArticleDetailFragment.newInstance(getIntent().getStringExtra(References.ARG_KEY_ARTICLE_LINK)))
                    .commit();
        }
    }




    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
