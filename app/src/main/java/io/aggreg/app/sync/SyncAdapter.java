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

import com.appspot.afrinewscentral.afrinews.Afrinews;
import com.appspot.afrinewscentral.afrinews.model.BackendAfrinewsApiCategoryCollectionMessage;
import com.appspot.afrinewscentral.afrinews.model.BackendAfrinewsApiCategoryMessage;
import com.appspot.afrinewscentral.afrinews.model.BackendAfrinewsApiNewsSourceCollectionMessage;
import com.appspot.afrinewscentral.afrinews.model.BackendAfrinewsApiNewsSourceMessage;
import com.appspot.afrinewscentral.afrinews.model.BackendAfrinewsApiStoryCollection;
import com.appspot.afrinewscentral.afrinews.model.BackendAfrinewsApiStoryMessage;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.provider.category.CategoryColumns;
import io.aggreg.app.provider.newssource.NewsSourceColumns;

/**
 * Handle the transfer of data between a server and an
 * app, using the Android sync adapter framework.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    // Global variables
    // Define a variable to contain a content resolver instance
    ContentResolver mContentResolver;
    /**
     * Set up the sync adapter
     */
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();
    }

    /**
     * Set up the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
     */
    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();

    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

        Afrinews.Builder builder = new Afrinews.Builder(
                AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
        Afrinews service = builder.build();
        BackendAfrinewsApiCategoryCollectionMessage categoryCollectionMessage = null;
        try {
            categoryCollectionMessage = service.categories().list().execute();
            List<ContentValues> contentValuesList = new ArrayList<>();
            ContentValues contentValues = null;
            Log.d("SYNC", "size "+categoryCollectionMessage.getItems().size());
            for (BackendAfrinewsApiCategoryMessage category: categoryCollectionMessage.getItems()){
                contentValues = new ContentValues();
                Log.d("sync", "id "+category.getName());
                contentValues.put(CategoryColumns._ID, category.getId());
                contentValues.put(CategoryColumns.NAME, category.getName());
                contentValuesList.add(contentValues);
            }
            mContentResolver.bulkInsert(CategoryColumns.CONTENT_URI, contentValuesList.toArray(new ContentValues[contentValuesList.size()]));

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BackendAfrinewsApiNewsSourceCollectionMessage newsSourceCollectionMessage = service.newsSources().list().execute();
            List<ContentValues> contentValuesList = new ArrayList<>();
            ContentValues contentValues = null;
            for(BackendAfrinewsApiNewsSourceMessage newsSource: newsSourceCollectionMessage.getItems()){
                contentValues = new ContentValues();
                contentValues.put(NewsSourceColumns._ID, newsSource.getId());
                contentValues.put(NewsSourceColumns.NAME, newsSource.getName());
                contentValues.put(NewsSourceColumns.WEBSITE, newsSource.getWebsite());
                contentValues.put(NewsSourceColumns.COUNTRY, newsSource.getCountry());
                contentValuesList.add(contentValues);
            }
            mContentResolver.bulkInsert(NewsSourceColumns.CONTENT_URI, contentValuesList.toArray(new ContentValues[contentValuesList.size()]));

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BackendAfrinewsApiStoryCollection storyCollection = service.stories().list().execute();
            Log.d("sync", "size "+storyCollection.getItems().size());
            List<ContentValues> contentValuesList = new ArrayList<>();
            ContentValues contentValues = new ContentValues();
            for(BackendAfrinewsApiStoryMessage s: storyCollection.getItems()){
                contentValues = new ContentValues();
                contentValues.put(ArticleColumns._ID, s.getId());
                contentValues.put(ArticleColumns.TITLE, s.getTitle());
                contentValues.put(ArticleColumns.TEXT, s.getText());
                contentValues.put(ArticleColumns.LINK, " ");
                contentValues.put(ArticleColumns.CATEGORY_ID, s.getCategoryId());
                contentValues.put(ArticleColumns.NEWS_SOURCE_ID, s.getNewsSourceId());
                try {
                    contentValues.put(ArticleColumns.IMAGE, s.getImageUrl().get(0));
                }catch (Exception e){

                }
                contentValuesList.add(contentValues);
            }
            mContentResolver.bulkInsert(ArticleColumns.CONTENT_URI, contentValuesList.toArray(new ContentValues[contentValuesList.size()]));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
