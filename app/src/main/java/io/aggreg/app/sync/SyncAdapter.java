package io.aggreg.app.sync;

/**
 * Created by Timo on 3/26/15.
 */

import android.accounts.Account;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import com.appspot.aggregio_web.aggregio.Aggregio;
import com.appspot.aggregio_web.aggregio.model.ApiAggregioArticleCollection;
import com.appspot.aggregio_web.aggregio.model.ApiAggregioArticleMessage;
import com.appspot.aggregio_web.aggregio.model.ApiAggregioCategoryCollectionMessage;
import com.appspot.aggregio_web.aggregio.model.ApiAggregioCategoryMessage;
import com.appspot.aggregio_web.aggregio.model.ApiAggregioPublisherCollectionMessage;
import com.appspot.aggregio_web.aggregio.model.ApiAggregioPublisherMessage;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.common.net.HttpHeaders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import io.aggreg.app.R;
import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.provider.category.CategoryColumns;
import io.aggreg.app.provider.publisher.PublisherColumns;
import io.aggreg.app.provider.publishercategory.PublisherCategoryColumns;
import io.aggreg.app.provider.publishercategory.PublisherCategoryCursor;
import io.aggreg.app.provider.publishercategory.PublisherCategorySelection;
import io.aggreg.app.utils.References;

public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private static final String LOG_TAG = SyncAdapter.class.getSimpleName();
    private static final String WEB_CLIENT_ID = "651183289184-druu7pqpe1ghlf6smeehefk0oo7nfvht.apps.googleusercontent.com";
    ContentResolver mContentResolver;
    String countryName;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
        this.countryName = context.getString(R.string.app_country);
    }

    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();
        this.countryName = context.getString(R.string.app_country);

    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

        Aggregio.Builder builder = new Aggregio.Builder(
                AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
        Aggregio service = builder.build();

        try {
            String syncType = (String) extras.get(References.ARG_KEY_SYNC_TYPE);
            Log.d(LOG_TAG, "sync extras " + extras.toString());

            if (syncType.equalsIgnoreCase(References.SYNC_TYPE_PUBLISHER_CATEGORY)) {
                Log.d(LOG_TAG, "sync " + extras.getString(References.ARG_KEY_SYNC_TYPE));

                setUpPublishers(service);
                setUpCategories(service);

            } else if (syncType.equalsIgnoreCase(References.SYNC_TYPE_ARTICLE_REFRESH)) {
                SharedPreferences prefs = getContext().getSharedPreferences(References.KEY_PREFERENCES, Context.MODE_PRIVATE);
                Boolean isPublisherSetupComplete = prefs.getBoolean(References.PUBLISHERS_SETUP_COMPLETE, false);
                Boolean isCategorySetupComplete = prefs.getBoolean(References.CATEGORY_SETUP_COMPLETE, false);
                if(!isPublisherSetupComplete){
                    setUpPublishers(service);
                }
                if(!isCategorySetupComplete){
                    setUpCategories(service);
                }



                PublisherCategorySelection selection = new PublisherCategorySelection();
                Log.d(LOG_TAG, "category id is " + extras.getLong(References.ARG_KEY_CATEGORY_ID));
                if (extras.getLong(References.ARG_KEY_CATEGORY_ID) == 0) {

                } else {
                    Long categoryId = extras.getLong(References.ARG_KEY_CATEGORY_ID);
                    selection.categoryId(categoryId);
                    selection.and();
                    selection.publisherFollowing(true);
                }
                PublisherCategoryCursor publisherCategoryCursor = selection.query(mContentResolver, null, CategoryColumns.ORDER+" ASC");
                if (publisherCategoryCursor != null) {
                    publisherCategoryCursor.moveToFirst();
                    if (publisherCategoryCursor.getCount() != 0) {
                        do {
                            refreshArticles(service, publisherCategoryCursor.getPublisherId(), publisherCategoryCursor.getCategoryId());

                        } while (publisherCategoryCursor.moveToNext());
                    }else {
                        setUpPublishers(service);
                        setUpCategories(service);
                    }
                }


                Log.d(LOG_TAG, "sync type " + References.SYNC_TYPE_ARTICLE_REFRESH);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshArticles(Aggregio service, Long publisherId, Long categoryId){

        SharedPreferences prefs = getContext().getSharedPreferences(References.KEY_PREFERENCES, Context.MODE_PRIVATE);

        String key = References.KEY_LAST_SYNC + categoryId + publisherId;
        Long lastSyncDate = prefs.getLong(key, 0);
        SharedPreferences.Editor editor = prefs.edit();
        Long minutes = null;

        if (lastSyncDate == 0) {
            lastSyncDate = null;
            minutes = null;
        }else {
            int timeZoneOffset = TimeZone.getDefault().getOffset(lastSyncDate);
           minutes = TimeUnit.MILLISECONDS.toMinutes(timeZoneOffset);
        }

        ApiAggregioArticleCollection articleCollection = null;
        try {

            articleCollection = service.articles().cursorList(publisherId, categoryId)
                    .setMilliseconds(lastSyncDate)
                    .setTimeZoneOffset(minutes)
                    .execute();

        editor.putLong(key, new Date().getTime()).apply();
        saveArticles(articleCollection);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void saveArticles(ApiAggregioArticleCollection articleCollection) {
        List<ContentValues> articlesContentValuesList = new ArrayList<>();
        ContentValues articleContentValues;
        if (articleCollection != null) {
            if (articleCollection.getItems() != null) {
                for (ApiAggregioArticleMessage s : articleCollection.getItems()) {
                    articleContentValues = new ContentValues();
                    articleContentValues.put(ArticleColumns._ID, s.getId());
                    articleContentValues.put(ArticleColumns.TITLE, s.getTitle());
                    articleContentValues.put(ArticleColumns.TEXT, s.getText());
                    articleContentValues.put(ArticleColumns.LINK, s.getLink());
                    articleContentValues.put(ArticleColumns.CATEGORY_ID, s.getCategoryId());
                    articleContentValues.put(ArticleColumns.PUBLISHER_ID, s.getPublisherId());
                    articleContentValues.put(ArticleColumns.PUB_DATE, s.getPubDate().getValue());
                    try {
                        articleContentValues.put(ArticleColumns.IMAGE, s.getImageUrl().get(0));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    articlesContentValuesList.add(articleContentValues);
                }
            }
        }
        try {
            mContentResolver.bulkInsert(ArticleColumns.CONTENT_URI, articlesContentValuesList.toArray(new ContentValues[articlesContentValuesList.size()]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpPublishers(Aggregio service) {
        Boolean publishersSetUp = true;
        ApiAggregioPublisherCollectionMessage publisherCollectionMessage = null;
        try {
            publisherCollectionMessage = service.publishers().list(countryName).execute();
        } catch (IOException e) {
            e.printStackTrace();
            publishersSetUp = false;
        }
        List<ContentValues> publisherContentValuesList = new ArrayList<>();
        ContentValues publisherContentValues = null;
        if (publisherCollectionMessage != null) {
            for (ApiAggregioPublisherMessage publisher : publisherCollectionMessage.getItems()) {
                publisherContentValues = new ContentValues();
                publisherContentValues.put(PublisherColumns._ID, publisher.getId());
                publisherContentValues.put(PublisherColumns.NAME, publisher.getName());
                publisherContentValues.put(PublisherColumns.WEBSITE, publisher.getWebsite());
                publisherContentValues.put(PublisherColumns.IMAGE_URL, publisher.getImageUrl());
                publisherContentValues.put(PublisherColumns.FOLLOWING, true);
                publisherContentValues.put(PublisherColumns.ORDER, publisher.getOrder());
                publisherContentValuesList.add(publisherContentValues);
            }
        }
        try {
            mContentResolver.bulkInsert(PublisherColumns.CONTENT_URI, publisherContentValuesList.toArray(new ContentValues[publisherContentValuesList.size()]));

        } catch (Exception e) {
            e.printStackTrace();
        }
        SharedPreferences prefs = getContext().getSharedPreferences(References.KEY_PREFERENCES, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(References.PUBLISHERS_SETUP_COMPLETE, publishersSetUp).apply();
    }

    private void setUpCategories(Aggregio service) {
        Boolean completedCategorySetup = true;
        ApiAggregioCategoryCollectionMessage categoryCollectionMessage = null;

        try {
            categoryCollectionMessage = service.categories().list(countryName).execute();
        } catch (IOException e) {
            e.printStackTrace();
            completedCategorySetup = false;
        }
        List<ContentValues> publisherCategoryContentValuesList = new ArrayList<>();
        ContentValues categoryContentValues = null;
        if (categoryCollectionMessage != null) {
            for (ApiAggregioCategoryMessage category : categoryCollectionMessage.getItems()) {
                categoryContentValues = new ContentValues();
                categoryContentValues.put(CategoryColumns._ID, category.getId());
                categoryContentValues.put(CategoryColumns.NAME, category.getName());
                categoryContentValues.put(CategoryColumns.ORDER, category.getOrder());
                try {
                    mContentResolver.insert(CategoryColumns.CONTENT_URI, categoryContentValues);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                publisherCategoryContentValuesList = new ArrayList<>();
                ContentValues publisherCategoryContentValues;
                for (Long publisherId : category.getPublishers()) {
                    publisherCategoryContentValues = new ContentValues();
                    publisherCategoryContentValues.put(PublisherCategoryColumns.CATEGORY_ID, category.getId());
                    publisherCategoryContentValues.put(PublisherCategoryColumns.PUBLISHER_ID, publisherId);
                    publisherCategoryContentValuesList.add(publisherCategoryContentValues);
                }
                try {
                    mContentResolver.bulkInsert(PublisherCategoryColumns.CONTENT_URI, publisherCategoryContentValuesList.toArray(new ContentValues[publisherCategoryContentValuesList.size()]));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        SharedPreferences prefs = getContext().getSharedPreferences(References.KEY_PREFERENCES, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(References.CATEGORY_SETUP_COMPLETE, completedCategorySetup).apply();
    }


}

