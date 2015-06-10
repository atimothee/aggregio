package io.aggreg.app.ui;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashSet;

import io.aggreg.app.R;
import io.aggreg.app.provider.AggregioProvider;
import io.aggreg.app.provider.category.CategoryColumns;
import io.aggreg.app.provider.publishercategory.PublisherCategoryColumns;
import io.aggreg.app.provider.publishercategory.PublisherCategorySelection;
import io.aggreg.app.ui.fragment.ArticlesFragment;
import io.aggreg.app.utils.AccountUtils;
import io.aggreg.app.utils.References;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks{

    private DrawerLayout mDrawerLayout;
    private Cursor publisherCategoriesCursor;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    //private FloatingActionButton fab;
    HashSet<Category> pagerTitles;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    class Category{
        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
                    // if deriving: appendSuper(super.hashCode()).
                    append(name).toHashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Category))
                return false;
            if (obj == this)
                return true;

            Category rhs = (Category) obj;
            return new EqualsBuilder().
                    // if deriving: appendSuper(super.equals(obj)).
                            append(name, rhs.name).
                    isEquals();
        }

        public Category(Long id, String name){
            this.id = id;
            this.name = name;
        }
        public Long id;
        public String name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        pagerTitles = new HashSet();
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        Account account = new AccountUtils(getApplicationContext()).getSyncAccount();
        ContentResolver.setSyncAutomatically(account, AggregioProvider.AUTHORITY, true);
        ContentResolver.requestSync(account, AggregioProvider.AUTHORITY, settingsBundle);
        publisherCategoriesCursor = null;
        getSupportLoaderManager().initLoader(References.PUBLISHER_LOADER, null, this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(getResources().getString(R.string.app_name_general));
            ab.setSubtitle(getResources().getString(R.string.app_country));
            ab.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            ab.setDisplayHomeAsUpEnabled(true);
        }


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
//        fab = (FloatingActionButton) findViewById(R.id.fab);
//        SharedPreferences prefs = getSharedPreferences(References.KEY_PREFERENCES, MODE_PRIVATE);
//        Boolean currentValue = prefs.getBoolean(References.KEY_TOGGLE_GRID, false);
//        if(currentValue) {
//
//                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_view_stream_white_24dp));
//        }else {
//            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_view_quilt_white_24dp));
//
//        }

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                SharedPreferences prefs = getSharedPreferences(References.KEY_PREFERENCES, MODE_PRIVATE);
//                Boolean oldValue = prefs.getBoolean(References.KEY_TOGGLE_GRID, false);
//                SharedPreferences.Editor editor = prefs.edit();
//                Boolean newValue = !oldValue;
//                editor.putBoolean(References.KEY_TOGGLE_GRID, newValue);
//                editor.apply();
//                if(newValue) {
//                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_view_stream_white_24dp));
//                }else {
//                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_view_quilt_white_24dp));
//
//                }
//                int currentPosition = viewPager.getCurrentItem();
//                viewPager.setAdapter(mSectionsPagerAdapter);
//                tabLayout.setupWithViewPager(viewPager);
//                if(currentPosition!=0){
//                    viewPager.setCurrentItem(currentPosition);
//                }
//            }
//        });

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        if (menuItem.getItemId() == R.id.nav_manage_sources) {
                            startActivity(new Intent(MainActivity.this, PublisherActivity.class));
                        } else if (menuItem.getItemId() == R.id.nav_settings) {
                            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        }
                        mDrawerLayout.closeDrawers();

                        return true;
                    }
                });
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ArticlesFragment.newInstance(((Category)pagerTitles.toArray()[position]).id);
        }

        @Override
        public int getCount() {
            return pagerTitles.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Log.d(LOG_TAG, "Pagertitles charseq count is "+pagerTitles.size());

            return ((Category) pagerTitles.toArray()[position]).name;
        }
    }

    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {

        //TODO: Select where category is unique
        PublisherCategorySelection selection = new PublisherCategorySelection();
        //selection.categoryIdGt()
        return new CursorLoader(this, PublisherCategoryColumns.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

        publisherCategoriesCursor = (Cursor) data;
        if(publisherCategoriesCursor != null) {
        if(publisherCategoriesCursor.getCount()!=0) {
            publisherCategoriesCursor.moveToFirst();
            do {
                pagerTitles.add(new Category(publisherCategoriesCursor.getLong(publisherCategoriesCursor.getColumnIndex(PublisherCategoryColumns._ID)),publisherCategoriesCursor.getString(publisherCategoriesCursor.getColumnIndex(CategoryColumns.NAME))));
//            categories.put(publisherCategoriesCursor.getLong(publisherCategoriesCursor.getColumnIndex(PublisherCategoryColumns.CATEGORY_ID)),
//                    publisherCategoriesCursor.getString(publisherCategoriesCursor.getColumnIndex(CategoryColumns.NAME)));

            } while (publisherCategoriesCursor.moveToNext());
        }
        }
        Log.d(LOG_TAG, "set size is " + pagerTitles.size());
        tabLayout.setupWithViewPager(viewPager);
        mSectionsPagerAdapter.notifyDataSetChanged();
        //tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

}
