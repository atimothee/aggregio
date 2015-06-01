package io.aggreg.app.ui;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.appspot.afrinewscentral.afrinews.Afrinews;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;

import io.aggreg.app.R;
import io.aggreg.app.provider.AggregioProvider;
import io.aggreg.app.provider.category.CategoryColumns;
import io.aggreg.app.ui.fragment.ArticleDetailFragment;
import io.aggreg.app.ui.fragment.ArticlesFragment;
import io.aggreg.app.utils.AccountUtils;


public class BrowseArticlesActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks, ArticlesFragment.OnFragmentInteractionListener, ActionBar.TabListener {
    private SharedPreferences settings;
    private GoogleAccountCredential credential;
    private Afrinews service;
    private static final int REQUEST_ACCOUNT_PICKER = 2;
    private String PREF_ACCOUNT_NAME = "account";
    private String accountName = "aggregio";
    public static int PUBLISHER_LOADER = 0;
    private Cursor categoriesCursor;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;


    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ACCOUNT_PICKER:
                if (data != null && data.getExtras() != null) {
                    String accountName =
                            data.getExtras().getString(
                                    AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        setSelectedAccountName(accountName);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.commit();
                        // User is authorized.
                    }
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoriesCursor = null;
        getSupportLoaderManager().initLoader(PUBLISHER_LOADER, null, this);
        setContentView(R.layout.activity_browse_articles);


        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        settings = getSharedPreferences(
                "Aggregio", 0);
        credential = GoogleAccountCredential.usingAudience(this,
                "server:712181347012-8fpgao7adir66k4sgf54k804c6qtb4dg.apps.googleusercontent.com");
        Afrinews.Builder builder = new Afrinews.Builder(
                AndroidHttp.newCompatibleTransport(), new GsonFactory(),
                credential);
        service = builder.build();

        if (credential.getSelectedAccountName() != null) {
            // Already signed in, begin app!
        } else {
            //chooseAccount();
            // Not signed in, show login window or request an account.
        }

        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        Account account = new AccountUtils(getApplicationContext()).getSyncAccount();
        //ContentResolver.setSyncAutomatically(account, AggregioProvider.AUTHORITY, true);
        //ContentResolver.requestSync(account, AggregioProvider.AUTHORITY, settingsBundle);

    }

    private void setSelectedAccountName(String accountName) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_ACCOUNT_NAME, accountName);
        editor.commit();
        credential.setSelectedAccountName(accountName);
        this.accountName = accountName;
    }

    private void chooseAccount() {
        startActivityForResult(credential.newChooseAccountIntent(),
                REQUEST_ACCOUNT_PICKER);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_browse_articles, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void openArticleDetail(Long articleId) {
        Intent i = new Intent(this, ArticleDetailActivity.class);
        i.putExtra(ArticleDetailFragment.ARG_ARTICLE_ID, articleId);
        startActivity(i);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

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
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

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

}
