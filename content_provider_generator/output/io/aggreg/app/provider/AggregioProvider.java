package io.aggreg.app.provider;

import java.util.Arrays;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import io.aggreg.app.BuildConfig;
import io.aggreg.app.provider.base.BaseContentProvider;
import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.provider.articleimage.ArticleImageColumns;
import io.aggreg.app.provider.category.CategoryColumns;
import io.aggreg.app.provider.publisher.PublisherColumns;

public class AggregioProvider extends BaseContentProvider {
    private static final String TAG = AggregioProvider.class.getSimpleName();

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "io.aggreg.app.provider";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    private static final int URI_TYPE_ARTICLE = 0;
    private static final int URI_TYPE_ARTICLE_ID = 1;

    private static final int URI_TYPE_ARTICLE_IMAGE = 2;
    private static final int URI_TYPE_ARTICLE_IMAGE_ID = 3;

    private static final int URI_TYPE_CATEGORY = 4;
    private static final int URI_TYPE_CATEGORY_ID = 5;

    private static final int URI_TYPE_PUBLISHER = 6;
    private static final int URI_TYPE_PUBLISHER_ID = 7;



    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, ArticleColumns.TABLE_NAME, URI_TYPE_ARTICLE);
        URI_MATCHER.addURI(AUTHORITY, ArticleColumns.TABLE_NAME + "/#", URI_TYPE_ARTICLE_ID);
        URI_MATCHER.addURI(AUTHORITY, ArticleImageColumns.TABLE_NAME, URI_TYPE_ARTICLE_IMAGE);
        URI_MATCHER.addURI(AUTHORITY, ArticleImageColumns.TABLE_NAME + "/#", URI_TYPE_ARTICLE_IMAGE_ID);
        URI_MATCHER.addURI(AUTHORITY, CategoryColumns.TABLE_NAME, URI_TYPE_CATEGORY);
        URI_MATCHER.addURI(AUTHORITY, CategoryColumns.TABLE_NAME + "/#", URI_TYPE_CATEGORY_ID);
        URI_MATCHER.addURI(AUTHORITY, PublisherColumns.TABLE_NAME, URI_TYPE_PUBLISHER);
        URI_MATCHER.addURI(AUTHORITY, PublisherColumns.TABLE_NAME + "/#", URI_TYPE_PUBLISHER_ID);
    }

    @Override
    protected SQLiteOpenHelper createSqLiteOpenHelper() {
        return AggregioSQLiteOpenHelper.getInstance(getContext());
    }

    @Override
    protected boolean hasDebug() {
        return DEBUG;
    }

    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_ARTICLE:
                return TYPE_CURSOR_DIR + ArticleColumns.TABLE_NAME;
            case URI_TYPE_ARTICLE_ID:
                return TYPE_CURSOR_ITEM + ArticleColumns.TABLE_NAME;

            case URI_TYPE_ARTICLE_IMAGE:
                return TYPE_CURSOR_DIR + ArticleImageColumns.TABLE_NAME;
            case URI_TYPE_ARTICLE_IMAGE_ID:
                return TYPE_CURSOR_ITEM + ArticleImageColumns.TABLE_NAME;

            case URI_TYPE_CATEGORY:
                return TYPE_CURSOR_DIR + CategoryColumns.TABLE_NAME;
            case URI_TYPE_CATEGORY_ID:
                return TYPE_CURSOR_ITEM + CategoryColumns.TABLE_NAME;

            case URI_TYPE_PUBLISHER:
                return TYPE_CURSOR_DIR + PublisherColumns.TABLE_NAME;
            case URI_TYPE_PUBLISHER_ID:
                return TYPE_CURSOR_ITEM + PublisherColumns.TABLE_NAME;

        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        return super.insert(uri, values);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        return super.bulkInsert(uri, values);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.update(uri, values, selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.delete(uri, selection, selectionArgs);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (DEBUG)
            Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
                    + " groupBy=" + uri.getQueryParameter(QUERY_GROUP_BY) + " having=" + uri.getQueryParameter(QUERY_HAVING) + " limit=" + uri.getQueryParameter(QUERY_LIMIT));
        return super.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    protected QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_ARTICLE:
            case URI_TYPE_ARTICLE_ID:
                res.table = ArticleColumns.TABLE_NAME;
                res.idColumn = ArticleColumns._ID;
                res.tablesWithJoins = ArticleColumns.TABLE_NAME;
                if (CategoryColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + CategoryColumns.TABLE_NAME + " AS " + ArticleColumns.PREFIX_CATEGORY + " ON " + ArticleColumns.TABLE_NAME + "." + ArticleColumns.CATEGORY_ID + "=" + ArticleColumns.PREFIX_CATEGORY + "." + CategoryColumns._ID;
                }
                if (PublisherColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + PublisherColumns.TABLE_NAME + " AS " + ArticleColumns.PREFIX_PUBLISHER + " ON " + ArticleColumns.TABLE_NAME + "." + ArticleColumns.PUBLISHER_ID + "=" + ArticleColumns.PREFIX_PUBLISHER + "." + PublisherColumns._ID;
                }
                res.orderBy = ArticleColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_ARTICLE_IMAGE:
            case URI_TYPE_ARTICLE_IMAGE_ID:
                res.table = ArticleImageColumns.TABLE_NAME;
                res.idColumn = ArticleImageColumns._ID;
                res.tablesWithJoins = ArticleImageColumns.TABLE_NAME;
                if (ArticleColumns.hasColumns(projection) || CategoryColumns.hasColumns(projection) || PublisherColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + ArticleColumns.TABLE_NAME + " AS " + ArticleImageColumns.PREFIX_ARTICLE + " ON " + ArticleImageColumns.TABLE_NAME + "." + ArticleImageColumns.ARTICLE_ID + "=" + ArticleImageColumns.PREFIX_ARTICLE + "." + ArticleColumns._ID;
                }
                if (CategoryColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + CategoryColumns.TABLE_NAME + " AS " + ArticleColumns.PREFIX_CATEGORY + " ON " + ArticleImageColumns.PREFIX_ARTICLE + "." + ArticleColumns.CATEGORY_ID + "=" + ArticleColumns.PREFIX_CATEGORY + "." + CategoryColumns._ID;
                }
                if (PublisherColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + PublisherColumns.TABLE_NAME + " AS " + ArticleColumns.PREFIX_PUBLISHER + " ON " + ArticleImageColumns.PREFIX_ARTICLE + "." + ArticleColumns.PUBLISHER_ID + "=" + ArticleColumns.PREFIX_PUBLISHER + "." + PublisherColumns._ID;
                }
                res.orderBy = ArticleImageColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_CATEGORY:
            case URI_TYPE_CATEGORY_ID:
                res.table = CategoryColumns.TABLE_NAME;
                res.idColumn = CategoryColumns._ID;
                res.tablesWithJoins = CategoryColumns.TABLE_NAME;
                res.orderBy = CategoryColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_PUBLISHER:
            case URI_TYPE_PUBLISHER_ID:
                res.table = PublisherColumns.TABLE_NAME;
                res.idColumn = PublisherColumns._ID;
                res.tablesWithJoins = PublisherColumns.TABLE_NAME;
                res.orderBy = PublisherColumns.DEFAULT_ORDER;
                break;

            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }

        switch (matchedId) {
            case URI_TYPE_ARTICLE_ID:
            case URI_TYPE_ARTICLE_IMAGE_ID:
            case URI_TYPE_CATEGORY_ID:
            case URI_TYPE_PUBLISHER_ID:
                id = uri.getLastPathSegment();
        }
        if (id != null) {
            if (selection != null) {
                res.selection = res.table + "." + res.idColumn + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = res.table + "." + res.idColumn + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }
}
