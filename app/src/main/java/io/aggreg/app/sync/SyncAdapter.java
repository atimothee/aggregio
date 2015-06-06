package io.aggreg.app.sync;

/**
 * Created by Timo on 3/26/15.
 */

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.appspot.aggregio_web.aggregio.Aggregio;
import com.appspot.aggregio_web.aggregio.model.ApiAggregioArticleCollection;
import com.appspot.aggregio_web.aggregio.model.ApiAggregioArticleMessage;
import com.appspot.aggregio_web.aggregio.model.ApiAggregioCategoryCollectionMessage;
import com.appspot.aggregio_web.aggregio.model.ApiAggregioCategoryMessage;
import com.appspot.aggregio_web.aggregio.model.ApiAggregioPublisherCollectionMessage;
import com.appspot.aggregio_web.aggregio.model.ApiAggregioPublisherMessage;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    ContentResolver mContentResolver;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
    }
    //TODO: Add the constructor for lower

    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();

    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

        //TODO: Fetch, store cursor in prefs, also store last sync date for articles

        String countryName = getContext().getString(R.string.app_country);
        Aggregio.Builder builder = new Aggregio.Builder(
                AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
        Aggregio service = builder.build();

        // if(extras.getString(References.ARG_KEY_SYNC_TYPE).equalsIgnoreCase(References.SYNC_TYPE_PUBLISHER)){


        ApiAggregioPublisherCollectionMessage publisherCollectionMessage = null;
        try {
            publisherCollectionMessage = service.publishers().list(countryName).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<ContentValues> publisherContentValuesList = new ArrayList<>();
        ContentValues publisherContentValues = null;
        if (publisherCollectionMessage != null) {
            for (ApiAggregioPublisherMessage publisher : publisherCollectionMessage.getItems()) {
                publisherContentValues = new ContentValues();
                publisherContentValues.put(PublisherColumns._ID, publisher.getId());
                publisherContentValues.put(PublisherColumns.NAME, publisher.getName());
                publisherContentValues.put(PublisherColumns.WEBSITE, publisher.getWebsite());
                publisherContentValuesList.add(publisherContentValues);
            }
        }
        try {
            mContentResolver.bulkInsert(PublisherColumns.CONTENT_URI, publisherContentValuesList.toArray(new ContentValues[publisherContentValuesList.size()]));

        } catch (Exception e) {
            //e.printStackTrace();
        }
        //}else if(extras.getString(References.ARG_KEY_SYNC_TYPE).equalsIgnoreCase(References.SYNC_TYPE_CATEGORY)){

        ApiAggregioCategoryCollectionMessage categoryCollectionMessage = null;

        try {
            categoryCollectionMessage = service.categories().list(countryName).execute();
        } catch (IOException e) {
            //e.printStackTrace();
        }
        List<ContentValues> categoryContentValuesList = new ArrayList<>();
        List<ContentValues> publisherCategoryContentValuesList = new ArrayList<>();
        ContentValues categoryContentValues = null;
        if (categoryCollectionMessage != null) {
            for (ApiAggregioCategoryMessage category : categoryCollectionMessage.getItems()) {
                categoryContentValues = new ContentValues();
                categoryContentValues.put(CategoryColumns._ID, category.getId());
                categoryContentValues.put(CategoryColumns.NAME, category.getName());
                try {
                    mContentResolver.insert(CategoryColumns.CONTENT_URI, categoryContentValues);
                }catch (Exception e){

                }

                //publisherCategoryContentValuesList = new ArrayList<>();
                for (Long publisherId : category.getPublishers()) {
                    ContentValues publisherCategoryContentValues = new ContentValues();
                    publisherCategoryContentValues.put(PublisherCategoryColumns.CATEGORY_ID, category.getId());
                    publisherCategoryContentValues.put(PublisherCategoryColumns.PUBLISHER_ID, publisherId);
                    //publisherCategoryContentValuesList.add(publisherCategoryContentValues);
                    try {
                        mContentResolver.insert(PublisherCategoryColumns.CONTENT_URI, publisherCategoryContentValues);
                    }catch (Exception e){

                    }
                }
                categoryContentValuesList.add(categoryContentValues);
            }
        }
        try {
            //mContentResolver.bulkInsert(CategoryColumns.CONTENT_URI, categoryContentValuesList.toArray(new ContentValues[categoryContentValuesList.size()]));
        } catch (Exception e) {
            //e.printStackTrace();
        }
        try {
            //mContentResolver.bulkInsert(PublisherCategoryColumns.CONTENT_URI, publisherCategoryContentValuesList.toArray(new ContentValues[publisherCategoryContentValuesList.size()]));
        } catch (Exception e) {
            //e.printStackTrace();
        }


        //}else if(extras.getString(References.ARG_KEY_SYNC_TYPE).equalsIgnoreCase(References.SYNC_TYPE_ARTICLE)){


                /*TODO: Use cursor endpoint instead if from scroll
                TODO: Use last sync datetime otherwise
                TODO: First check if theres an existing cursor in prefs
                 TODO: Save cursor in prefs
                 TODO: Save last sync datetime
                 */
        Log.d(LOG_TAG, "Reached here");

        PublisherCategorySelection selection = new PublisherCategorySelection();
        PublisherCategoryCursor publisherCategoryCursor = selection.query(mContentResolver, null, null);
        publisherCategoryCursor.moveToFirst();
        Long categoryId = null;
        Long publisherId = null;
        if (publisherCategoryCursor != null) {
            if (publisherCategoryCursor.getCount() != 0) {


                do {
                    categoryId = publisherCategoryCursor.getCategoryId();
                    publisherId = publisherCategoryCursor.getPublisherId();
                    ApiAggregioArticleCollection articleCollection = null;
                    try {
                        articleCollection = service.articles().cursorList(publisherId, categoryId).execute();
                    } catch (IOException e) {
                        //e.printStackTrace();
                    }
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

                                }
                                articlesContentValuesList.add(articleContentValues);
                            }
                        }
                    }
                    try {
                        mContentResolver.bulkInsert(ArticleColumns.CONTENT_URI, articlesContentValuesList.toArray(new ContentValues[articlesContentValuesList.size()]));
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }

                }

                while (publisherCategoryCursor.moveToNext());
            }
        }


        // }

    }
}
