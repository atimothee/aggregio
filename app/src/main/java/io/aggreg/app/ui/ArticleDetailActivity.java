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
import io.aggreg.app.ui.fragment.ArticlesFragment;
import io.aggreg.app.utils.References;


public class ArticleDetailActivity extends AppCompatActivity implements ArticleDetailFragment.OnFragmentInteractionListener{

    Boolean isTablet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        isTablet = getResources().getBoolean(R.bool.isTablet);
        if(isTablet){

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.article_list_container, ArticlesFragment.newInstance(getIntent().getLongExtra(References.ARG_KEY_CATEGORY_ID, 0)))
                        .add(R.id.article_detail_container, ArticleDetailFragment.newInstance(getIntent().getStringExtra(References.ARG_KEY_ARTICLE_LINK), getIntent().getLongExtra(References.ARG_KEY_CATEGORY_ID, 0), getIntent().getLongExtra(References.ARG_KEY_ARTICLE_ID, 0)))
                        .commit();
            }
        }else {

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, ArticleDetailFragment.newInstance(getIntent().getStringExtra(References.ARG_KEY_ARTICLE_LINK), getIntent().getLongExtra(References.ARG_KEY_CATEGORY_ID, 0), getIntent().getLongExtra(References.ARG_KEY_ARTICLE_ID, 0)))
                                .commit();
            }
        }
    }




    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
