package io.aggreg.app.ui;

import android.accounts.Account;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.suredigit.inappfeedback.FeedbackDialog;

import org.codechimp.apprater.AppRater;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import io.aggreg.app.R;
import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.provider.category.CategoryColumns;
import io.aggreg.app.provider.publishercategory.PublisherCategoryColumns;
import io.aggreg.app.provider.publishercategory.PublisherCategorySelection;
import io.aggreg.app.ui.fragment.ArticlesFragment;
import io.aggreg.app.utils.GeneralUtils;
import io.aggreg.app.utils.NetworkUtils;
import io.aggreg.app.utils.References;


public class MainActivity extends SyncActivity implements LoaderManager.LoaderCallbacks{

    private DrawerLayout mDrawerLayout;
    private Cursor publisherCategoriesCursor;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private SmoothProgressBar progressBar;
    private Tracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        progressBar = (SmoothProgressBar)findViewById(R.id.progress);

        if(new NetworkUtils(this).isInternetAvailable()){

        }else{
           Snackbar.make(viewPager, "No internet connection", Snackbar.LENGTH_LONG).setAction("OK", null).show();
        }

        publisherCategoriesCursor = null;
        getSupportLoaderManager().initLoader(References.PUBLISHER_LOADER, null, this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(toolbar);


        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(getResources().getString(R.string.app_name_general));
            ab.setSubtitle(getResources().getString(R.string.app_country));
            ab.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        new ShowcaseView.Builder(this)
                .setContentText("Click to manually refresh your news!")
                .setContentTitle("Refresh")
                .setTarget(new ViewTarget(toolbar.findViewById(R.id.action_refresh)))
                .build();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        new ShowcaseView.Builder(this)
                .setContentText("Manage sources")
                .setContentTitle("Menu")
                .setTarget(new ViewTarget(findViewById(R.id.nav_view)))
                .build();
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        if (viewPager != null) {
            viewPager.setAdapter(mSectionsPagerAdapter);
        }

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        AppRater.app_launched(this);
        AdView mAdView = (AdView) findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder()
        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        .addTestDevice("40F568795D1384A9EC06ABA81110930E")
                .build();
        mAdView.loadAd(adRequest);

        GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
        tracker = analytics.newTracker(getString(R.string.analytics_tracker_id));
        tracker.setScreenName("home screen");
    }

    @Override
    protected Account getAccount() {
        Account account = new GeneralUtils(getApplicationContext()).getSyncAccount();
        return account;
    }

    @Override
    protected void updateState(boolean isSynchronizing) {
            if(isSynchronizing){
                showProgress();
            }
        else{
                hideProgress();
            }
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
            case R.id.action_refresh:
                refreshArticles();
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
                            tracker.send(new HitBuilders.EventBuilder()
                                    .setCategory("UX")
                                    .setAction("click")
                                    .setLabel("manage sources nav action")
                                    .build());
                            startActivity(new Intent(MainActivity.this, ManagePublishersActivity.class));
                        } else if (menuItem.getItemId() == R.id.nav_settings) {
                            tracker.send(new HitBuilders.EventBuilder()
                                    .setCategory("UX")
                                    .setAction("click")
                                    .setLabel("settings nav action")
                                    .build());
                            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        } else if (menuItem.getItemId() == R.id.nav_bookmarks) {
                            tracker.send(new HitBuilders.EventBuilder()
                                    .setCategory("UX")
                                    .setAction("click")
                                    .setLabel("bookmarks nav action")
                                    .build());
                            startActivity(new Intent(MainActivity.this, BookmarksActivity.class));
                        } else if (menuItem.getItemId() == R.id.nav_help) {
                            FeedbackDialog feedBackDialog = new FeedbackDialog(MainActivity.this, getResources().getString(R.string.feedback_api_key));
                            feedBackDialog.show();
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
            publisherCategoriesCursor.moveToPosition(position);
            return ArticlesFragment.newInstance((publisherCategoriesCursor.getLong(publisherCategoriesCursor.getColumnIndex(PublisherCategoryColumns.CATEGORY_ID))));
        }

        @Override
        public int getCount() {
            if(publisherCategoriesCursor!=null) {
                return publisherCategoriesCursor.getCount();
            }else {
                return 0;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            publisherCategoriesCursor.moveToPosition(position);

            return publisherCategoriesCursor.getString(publisherCategoriesCursor.getColumnIndex(CategoryColumns.NAME));
        }
    }

    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {

        PublisherCategorySelection publisherCategorySelection = new PublisherCategorySelection();
        publisherCategorySelection.publisherFollowing(true);
        String COLUMNS[] = {"DISTINCT "+PublisherCategoryColumns.CATEGORY_ID, CategoryColumns.NAME, CategoryColumns.ORDER};
        return new CursorLoader(this, PublisherCategoryColumns.CONTENT_URI, COLUMNS, null, null, CategoryColumns.ORDER);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {


        publisherCategoriesCursor = (Cursor) data;
        mSectionsPagerAdapter.notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    public void refreshArticles(){
        showProgress();
        Long categoryId = null;

        if(publisherCategoriesCursor!=null){
            if(publisherCategoriesCursor.getCount() !=0){
                int position = viewPager.getCurrentItem();
                publisherCategoriesCursor.moveToPosition(position);
                categoryId = publisherCategoriesCursor.getLong(publisherCategoriesCursor.getColumnIndex(ArticleColumns.CATEGORY_ID));

            }
        }
        new GeneralUtils(this).SyncRefreshFirstTime();
    }


    public void hideProgress(){
        progressBar.setVisibility(View.GONE);
    }


    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        if(new NetworkUtils(this).isInternetAvailable()){

        }else{
            progressBar.setVisibility(View.INVISIBLE);
            Snackbar.make(viewPager, "No internet connection", Snackbar.LENGTH_LONG).show();
        }
    }







}
