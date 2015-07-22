package io.aggreg.app.ui;

import android.accounts.Account;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.util.LruCache;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdsManager;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.suredigit.inappfeedback.FeedbackDialog;

import org.codechimp.apprater.AppRater;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import io.aggreg.app.R;
import io.aggreg.app.provider.category.CategoryColumns;
import io.aggreg.app.provider.publishercategory.PublisherCategoryColumns;
import io.aggreg.app.provider.publishercategory.PublisherCategorySelection;
import io.aggreg.app.sync.ArticleDeleteService;
import io.aggreg.app.sync.PeriodicalSyncService;
import io.aggreg.app.ui.fragment.ArticlesFragment;
import io.aggreg.app.utils.GeneralUtils;
import io.aggreg.app.utils.NetworkUtils;
import io.aggreg.app.utils.References;
import io.aggreg.app.utils.TypefaceSpan;


public class MainActivity extends SyncActivity implements LoaderManager.LoaderCallbacks, ArticlesFragment.OnFragmentInteractionListener,
        NativeAdsManager.Listener, AdListener {

    private DrawerLayout mDrawerLayout;
    private Cursor publisherCategoriesCursor;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private SmoothProgressBar progressBar;
    private Tracker tracker;
    private Boolean isSyncing;
    private NativeAdsManager listNativeAdsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        progressBar = (SmoothProgressBar)findViewById(R.id.progress);

        publisherCategoriesCursor = null;
        getSupportLoaderManager().initLoader(References.PUBLISHER_LOADER, null, this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(toolbar);


        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(getResources().getString(R.string.app_name_general));
            ab.setSubtitle(getResources().getString(R.string.app_country));
            ab.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        TextView textView = (TextView)findViewById(R.id.nav_header_text);
        textView.setText(getGreeting());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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

        GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
        tracker = analytics.newTracker(getString(R.string.analytics_tracker_id));
        tracker.setScreenName("home screen");

        setUpPeriodicSyncService();
        setUpArticleDeleteService();
        new CheckInternetTask().execute();
        AdSettings.addTestDevice("73f8ce4641689c3367382c249e2e2979");
        listNativeAdsManager = new NativeAdsManager(this, "1621484961451837_1621525618114438", 5);
        listNativeAdsManager.setListener(this);
        listNativeAdsManager.loadAds();
        NativeAd nativeAd = new NativeAd(this, "1621484961451837_1621525618114438");
        nativeAd.loadAd();

    }

    @Override
    protected Account getAccount() {
        Account account = new GeneralUtils(getApplicationContext()).getSyncAccount();
        return account;
    }

    @Override
    protected void updateState(boolean isSynchronizing) {
        isSyncing = isSynchronizing;
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
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
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
                            tracker.send(new HitBuilders.EventBuilder()
                                    .setCategory("UX")
                                    .setAction("click")
                                    .setLabel("feedback action")
                                    .build());
                        }
                        mDrawerLayout.closeDrawers();

                        return true;
                    }
                });
    }

    @Override
    public void onAdClicked(Ad ad) {
        //Toast.makeText(MainActivity.this, "Ad Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdLoaded(Ad ad) { }

    @Override
    public void onAdsLoaded() {

    }

    public NativeAd getNativeAd(){
        NativeAd ad = this.listNativeAdsManager.nextNativeAd();
        ad.setAdListener(this);
        return ad;
    }

    @Override
    public void onAdError(AdError error) {
//        Toast.makeText(this, "Native ads manager failed to load: " +  error.getErrorMessage(),
//                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Ad ad, AdError error) {
//        Toast.makeText(this, "Ad failed to load: " +  error.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }


    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            publisherCategoriesCursor.moveToPosition(position);
            Bundle bundle = new Bundle();
            bundle.putLong(References.ARG_KEY_CATEGORY_ID, (publisherCategoriesCursor.getLong(publisherCategoriesCursor.getColumnIndex(PublisherCategoryColumns.CATEGORY_ID))));
            return ArticlesFragment.newInstance(bundle);
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

        Bundle savedState = new Bundle();
        if(viewPager!=null) {
            savedState.putParcelable(References.ARG_KEY_PARCEL, viewPager.onSaveInstanceState());
        }
        publisherCategoriesCursor = (Cursor) data;
        mSectionsPagerAdapter.notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);
        onRestoreInstanceState(savedState);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    public void refreshArticles(){
        showProgress();
        new GeneralUtils(this).SyncRefreshArticles();
    }


    public void hideProgress(){
        progressBar.setVisibility(View.GONE);
    }


    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        new CheckInternetTask().execute();

    }


    private String getGreeting() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            return "Good Morning!";
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            return "Good Afternoon!";
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            return "Good Evening!";
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            return "Good Night!";
        }
        return "";
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Parcelable state = viewPager.onSaveInstanceState();
        outState.putParcelable(References.ARG_KEY_PARCEL, state);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        try {
            viewPager.onRestoreInstanceState(savedInstanceState.getParcelable(References.ARG_KEY_PARCEL));
            super.onRestoreInstanceState(savedInstanceState);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setUpPeriodicSyncService(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String periodicSyncHoursString = settings.getString(getString(R.string.pref_key_refresh_interval), "720");
        Integer periodicSyncHours = Integer.valueOf(periodicSyncHoursString);

        Log.d(LOG_TAG, "refresh interval is " + periodicSyncHoursString);
        if(periodicSyncHours != -1) {
            Intent periodicSyncIntent = new Intent(this, PeriodicalSyncService.class);

            PendingIntent periodicSyncPendingIntent = PendingIntent.getService(this, References.REQUEST_CODE,
                    periodicSyncIntent, 0);
            int periodicSyncAlarmType = AlarmManager.ELAPSED_REALTIME;

            final long PERIODIC_SYNC_MILLIS = TimeUnit.HOURS.toMillis(periodicSyncHours);
            AlarmManager periodicSyncAlarmManager = (AlarmManager)
                    this.getSystemService(ALARM_SERVICE);
            periodicSyncAlarmManager.setRepeating(periodicSyncAlarmType, SystemClock.elapsedRealtime() + PERIODIC_SYNC_MILLIS,
                    PERIODIC_SYNC_MILLIS, periodicSyncPendingIntent);
        }
    }

    private void  setUpArticleDeleteService(){
        Intent deleteArticlesIntent = new Intent(this, ArticleDeleteService.class);

        PendingIntent deleteArticlesPendingIntent = PendingIntent.getService(this, References.REQUEST_CODE,
                deleteArticlesIntent, 0);
        int deleteArticlesAlarmType = AlarmManager.ELAPSED_REALTIME;
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        final long MILLIS = TimeUnit.HOURS.toMillis(12);
        AlarmManager alarmManager = (AlarmManager)
                this.getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(deleteArticlesAlarmType, SystemClock.elapsedRealtime() + MILLIS,
                MILLIS, deleteArticlesPendingIntent);
    }

    class CheckInternetTask extends AsyncTask<String, String, Boolean>{


        @Override
        protected Boolean doInBackground(String... strings) {
            return new NetworkUtils(MainActivity.this).isInternetAvailable();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                progressBar.setVisibility(View.VISIBLE);
            }
            else {
                progressBar.setVisibility(View.GONE);
                Snackbar.make(viewPager, "No internet connection", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public Boolean checkSyncStatus() {
        if(isSyncing != null) {
            return isSyncing;
        }else {
            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}
