package io.aggreg.app.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.Window;

import io.aggreg.app.R;
import io.aggreg.app.ui.fragment.ArticleDetailFragment;
import io.aggreg.app.ui.fragment.ArticlesFragment;
import io.aggreg.app.utils.References;


public class ArticleDetailActivity extends AppCompatActivity implements ArticleDetailFragment.OnFragmentInteractionListener, ArticlesFragment.OnFragmentInteractionListener{

    Boolean isTablet;
    Toolbar mainToolbar;
    FloatingActionButton bookmarkFab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        isTablet = getResources().getBoolean(R.bool.isTablet);
        if(isTablet){
            mainToolbar = (Toolbar)findViewById(R.id.main_toolbar);
            mainToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
            mainToolbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            mainToolbar.setTitleTextColor(getResources().getColor(R.color.primary_text_default_material_dark));
            mainToolbar.setSubtitle(getResources().getString(R.string.app_country));
            mainToolbar.setSubtitleTextColor(getResources().getColor(R.color.secondary_text_default_material_dark));
            bookmarkFab = (FloatingActionButton)findViewById(R.id.bookmark_fab);


            if (savedInstanceState == null) {
                Bundle articlesBundle = getIntent().getExtras();
                articlesBundle.putBoolean(References.ARG_KEY_IS_TAB_TWO_PANE, true);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.article_list_container, ArticlesFragment.newInstance(articlesBundle))
                        .add(R.id.article_detail_container, ArticleDetailFragment.newInstance(getIntent().getExtras()))
                        .commit();
            }
        }else {

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, ArticleDetailFragment.newInstance(getIntent().getExtras()))
                                .commit();
            }
        }
    }



    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void updateTitle(String title){
        if(isTablet) {
            if(mainToolbar != null) {
                mainToolbar.setTitle(title);
            }
        }
    }

    @Override
    public FloatingActionButton getBookmarkFab() {
        return bookmarkFab;
    }

    @Override
    public Boolean checkSyncStatus() {
        return false;
    }
}
