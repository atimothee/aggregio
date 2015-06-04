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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import io.aggreg.app.R;
import io.aggreg.app.provider.AggregioProvider;
import io.aggreg.app.provider.category.CategoryColumns;
import io.aggreg.app.ui.fragment.ArticlesFragment;
import io.aggreg.app.utils.AccountUtils;
import io.aggreg.app.utils.References;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks{

    private DrawerLayout mDrawerLayout;
    private Cursor categoriesCursor;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        Account account = new AccountUtils(getApplicationContext()).getSyncAccount();
        ContentResolver.setSyncAutomatically(account, AggregioProvider.AUTHORITY, true);
        ContentResolver.requestSync(account, AggregioProvider.AUTHORITY, settingsBundle);
        categoriesCursor = null;
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
        fab = (FloatingActionButton) findViewById(R.id.fab);
        SharedPreferences prefs = getSharedPreferences(References.KEY_PREFERENCES, MODE_PRIVATE);
        Boolean currentValue = prefs.getBoolean(References.KEY_TOGGLE_GRID, false);
        if(currentValue) {

                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_view_stream_white_24dp));
        }else {
            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_view_quilt_white_24dp));

        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences prefs = getSharedPreferences(References.KEY_PREFERENCES, MODE_PRIVATE);
                Boolean oldValue = prefs.getBoolean(References.KEY_TOGGLE_GRID, false);
                SharedPreferences.Editor editor = prefs.edit();
                Boolean newValue = !oldValue;
                editor.putBoolean(References.KEY_TOGGLE_GRID, newValue);
                editor.apply();
                if(newValue) {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_view_stream_white_24dp));
                }else {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_view_quilt_white_24dp));

                }
                viewPager.setAdapter(mSectionsPagerAdapter);
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabs);

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

            if(categoriesCursor != null) {
                categoriesCursor.moveToPosition(position);
                Long categoryId = categoriesCursor.getLong(categoriesCursor.getColumnIndex(CategoryColumns._ID));
                return ArticlesFragment.newInstance(categoryId);
            }
            return ArticlesFragment.newInstance((long) 0);
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
            return null;
        }
    }

    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {

        String[] COLUMNS = {CategoryColumns._ID, CategoryColumns.NAME};

        return new CursorLoader(this, CategoryColumns.CONTENT_URI, COLUMNS, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

        categoriesCursor = (Cursor) data;
        mSectionsPagerAdapter.notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

}
