/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.aggreg.app.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.aggreg.app.R;
import io.aggreg.app.provider.category.CategoryColumns;
import io.aggreg.app.ui.fragment.ArticleDetailFragment;
import io.aggreg.app.ui.fragment.ArticlesFragment;

/**
 * TODO
 */
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks, ArticlesFragment.OnFragmentInteractionListener{

    private DrawerLayout mDrawerLayout;
    private Cursor categoriesCursor;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public static int PUBLISHER_LOADER = 0;
    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoriesCursor = null;
        getSupportLoaderManager().initLoader(PUBLISHER_LOADER, null, this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setSubtitle("Uganda");
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            viewPager.setAdapter(mSectionsPagerAdapter);
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        Boolean currentValue = prefs.getBoolean("view_as_grid", false);
        if(currentValue) {

                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_view_stream_white_48dp));
        }else {
            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_view_quilt_white_48dp));

        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
                Boolean oldValue = prefs.getBoolean("view_as_grid", false);
                SharedPreferences.Editor editor = prefs.edit();
                Boolean newValue = !oldValue;
                editor.putBoolean("view_as_grid", newValue);
                editor.commit();
                if(newValue) {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_view_stream_white_48dp));
                }else {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_view_quilt_white_48dp));

                }
                //mSectionsPagerAdapter.notifyDataSetChanged();
                viewPager.setAdapter(mSectionsPagerAdapter);
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabs);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_browse_articles, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    private void setupViewPager(ViewPager viewPager) {
//        Adapter adapter = new Adapter(getSupportFragmentManager());
//        adapter.addFragment(new CheeseListFragment(), "Category 1");
//        adapter.addFragment(new CheeseListFragment(), "Category 2");
//        adapter.addFragment(new CheeseListFragment(), "Category 3");
//        viewPager.setAdapter(adapter);
//    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                if(menuItem.getItemId() == R.id.nav_manage_sources){
                    startActivity(new Intent(MainActivity.this, PublisherActivity.class));
                }
                mDrawerLayout.closeDrawers();

                return true;
            }
        });
    }

    @Override
    public void openArticleDetail(String articleId) {
        Intent i = new Intent(this, ArticleDetailActivity.class);
        i.putExtra(ArticleDetailFragment.ARG_ARTICLE_ID, articleId);
        startActivity(i);
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if(categoriesCursor != null) {
                categoriesCursor.moveToPosition(position);
                Long categoryId = categoriesCursor.getLong(categoriesCursor.getColumnIndex(CategoryColumns._ID));
                return ArticlesFragment.newInstance(categoryId);
            }
            return ArticlesFragment.newInstance(Long.valueOf(0));
        }

        @Override
        public int getCount() {

            if(categoriesCursor != null) {
                return categoriesCursor.getCount();
            }
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if(categoriesCursor != null) {
                categoriesCursor.moveToPosition(position);
                return categoriesCursor.getString(categoriesCursor.getColumnIndex(CategoryColumns.NAME)).toUpperCase();
            }
            return "Category";
        }
    }

    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {

        //TODO: Get only required columns

        return new CursorLoader(this, CategoryColumns.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

        categoriesCursor = (Cursor) data;
        final ActionBar actionBar = getSupportActionBar();
        mSectionsPagerAdapter.notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

}
