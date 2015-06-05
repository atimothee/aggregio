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

        if(extras.getString(References.ARG_KEY_SYNC_TYPE).equalsIgnoreCase(References.SYNC_TYPE_PUBLISHER)){

            try {
                ApiAggregioPublisherCollectionMessage publisherCollectionMessage = service.publishers().list(countryName).execute();
                List<ContentValues> contentValuesList = new ArrayList<>();
                ContentValues contentValues = null;
                for(ApiAggregioPublisherMessage publisher: publisherCollectionMessage.getItems()){
                    contentValues = new ContentValues();
                    contentValues.put(PublisherColumns._ID, publisher.getId());
                    contentValues.put(PublisherColumns.NAME, publisher.getName());
                    contentValues.put(PublisherColumns.WEBSITE, publisher.getWebsite());
                    contentValuesList.add(contentValues);
                }
                mContentResolver.bulkInsert(PublisherColumns.CONTENT_URI, contentValuesList.toArray(new ContentValues[contentValuesList.size()]));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(extras.getString(References.ARG_KEY_SYNC_TYPE).equalsIgnoreCase(References.SYNC_TYPE_CATEGORY)){

            ApiAggregioCategoryCollectionMessage categoryCollectionMessage = null;
            try {
                categoryCollectionMessage = service.categories().list(countryName).execute();
                List<ContentValues> categoryContentValuesList = new ArrayList<>();
                List<ContentValues> publisherCategoryContentValuesList = null;
                ContentValues categoryContentValues = null;
                for (ApiAggregioCategoryMessage category: categoryCollectionMessage.getItems()){
                    categoryContentValues = new ContentValues();
                    categoryContentValues.put(CategoryColumns._ID, category.getId());
                    categoryContentValues.put(CategoryColumns.NAME, category.getName());

                    publisherCategoryContentValuesList = new ArrayList<>();
                    for(Long publisherId: category.getPublishers()){
                        ContentValues publisherCategoryContentValues = new ContentValues();
                        publisherCategoryContentValues.put(PublisherCategoryColumns.CATEGORY_ID, category.getId());
                        publisherCategoryContentValues.put(PublisherCategoryColumns.PUBLISHER_ID, publisherId);
                        publisherCategoryContentValuesList.add(publisherCategoryContentValues);
                    }
                    categoryContentValuesList.add(categoryContentValues);
                }
                mContentResolver.bulkInsert(CategoryColumns.CONTENT_URI, categoryContentValuesList.toArray(new ContentValues[categoryContentValuesList.size()]));
                mContentResolver.bulkInsert(CategoryColumns.CONTENT_URI, publisherCategoryContentValuesList.toArray(new ContentValues[publisherCategoryContentValuesList.size()]));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(extras.getString(References.ARG_KEY_SYNC_TYPE).equalsIgnoreCase(References.SYNC_TYPE_ARTICLE)){

            try {
                /*TODO: Use cursor endpoint instead if from scroll
                TODO: Use last sync datetime otherwise
                TODO: First check if theres an existing cursor in prefs
                 TODO: Save cursor in prefs
                 TODO: Save last sync datetime
                 */

                PublisherCategorySelection selection = new PublisherCategorySelection();
                PublisherCategoryCursor publisherCategoryCursor = selection.query(mContentResolver, null, null);
                publisherCategoryCursor.moveToFirst();
                Long categoryId = null;
                Long publisherId = null;
                do{
                    categoryId = publisherCategoryCursor.getCategoryId();
                    publisherId = publisherCategoryCursor.getPublisherId();
                    ApiAggregioArticleCollection articleCollection = service.articles().cursorList(categoryId, publisherId).execute();
                    List<ContentValues> contentValuesList = new ArrayList<>();
                    ContentValues contentValues = new ContentValues();
                    for(ApiAggregioArticleMessage s: articleCollection.getItems()){
                        contentValues = new ContentValues();
                        contentValues.put(ArticleColumns._ID, s.getId());
                        contentValues.put(ArticleColumns.TITLE, s.getTitle());
                        contentValues.put(ArticleColumns.TEXT, s.getText());
                        contentValues.put(ArticleColumns.LINK, s.getLink());
                        contentValues.put(ArticleColumns.CATEGORY_ID, s.getCategoryId());
                        contentValues.put(ArticleColumns.PUBLISHER_ID, s.getPublisherId());
                        contentValues.put(ArticleColumns.PUB_DATE, s.getPubDate().getValue());
                        try {
                            contentValues.put(ArticleColumns.IMAGE, s.getImageUrl().get(0));
                        }catch (Exception e){

                        }
                        contentValuesList.add(contentValues);
                    }
                    mContentResolver.bulkInsert(ArticleColumns.CONTENT_URI, contentValuesList.toArray(new ContentValues[contentValuesList.size()]));

                }
                while (publisherCategoryCursor.moveToNext());

                } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
