package io.aggreg.app.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import io.aggreg.app.BuildConfig;
import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.provider.articleimage.ArticleImageColumns;
import io.aggreg.app.provider.category.CategoryColumns;
import io.aggreg.app.provider.publisher.PublisherColumns;
import io.aggreg.app.provider.publishercategory.PublisherCategoryColumns;

public class AggregioSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = AggregioSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "aggregio.db";
    private static final int DATABASE_VERSION = 1;
    private static AggregioSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final AggregioSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_ARTICLE = "CREATE TABLE IF NOT EXISTS "
            + ArticleColumns.TABLE_NAME + " ( "
            + ArticleColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ArticleColumns.TITLE + " TEXT NOT NULL, "
            + ArticleColumns.LINK + " TEXT NOT NULL, "
            + ArticleColumns.IMAGE + " TEXT, "
            + ArticleColumns.PUB_DATE + " INTEGER, "
            + ArticleColumns.TEXT + " TEXT, "
            + ArticleColumns.BOOK_MARKED + " INTEGER, "
            + ArticleColumns.IS_READ + " INTEGER, "
            + ArticleColumns.CATEGORY_ID + " INTEGER NOT NULL, "
            + ArticleColumns.PUBLISHER_ID + " INTEGER NOT NULL "
            + ", CONSTRAINT fk_category_id FOREIGN KEY (" + ArticleColumns.CATEGORY_ID + ") REFERENCES category (_id) ON DELETE CASCADE"
            + ", CONSTRAINT fk_publisher_id FOREIGN KEY (" + ArticleColumns.PUBLISHER_ID + ") REFERENCES publisher (_id) ON DELETE CASCADE"
            + ", CONSTRAINT unique_link UNIQUE (link) ON CONFLICT REPLACE"
            + " );";

    public static final String SQL_CREATE_TABLE_ARTICLE_IMAGE = "CREATE TABLE IF NOT EXISTS "
            + ArticleImageColumns.TABLE_NAME + " ( "
            + ArticleImageColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ArticleImageColumns.IMAGE_URL + " TEXT NOT NULL, "
            + ArticleImageColumns.ARTICLE_ID + " INTEGER "
            + ", CONSTRAINT fk_article_id FOREIGN KEY (" + ArticleImageColumns.ARTICLE_ID + ") REFERENCES article (_id) ON DELETE CASCADE"
            + " );";

    public static final String SQL_CREATE_TABLE_CATEGORY = "CREATE TABLE IF NOT EXISTS "
            + CategoryColumns.TABLE_NAME + " ( "
            + CategoryColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CategoryColumns.NAME + " TEXT, "
            + CategoryColumns.IMAGE_URL + " TEXT, "
            + CategoryColumns.ORDER + " INTEGER "
            + " );";

    public static final String SQL_CREATE_TABLE_PUBLISHER = "CREATE TABLE IF NOT EXISTS "
            + PublisherColumns.TABLE_NAME + " ( "
            + PublisherColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PublisherColumns.IMAGE_URL + " TEXT, "
            + PublisherColumns.WEBSITE + " TEXT, "
            + PublisherColumns.NAME + " TEXT, "
            + PublisherColumns.COUNTRY + " TEXT, "
            + PublisherColumns.TAG_LINE + " TEXT, "
            + PublisherColumns.FOLLOWING + " INTEGER NOT NULL, "
            + PublisherColumns.ORDER + " INTEGER "
            + " );";

    public static final String SQL_CREATE_TABLE_PUBLISHER_CATEGORY = "CREATE TABLE IF NOT EXISTS "
            + PublisherCategoryColumns.TABLE_NAME + " ( "
            + PublisherCategoryColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PublisherCategoryColumns.PUBLISHER_ID + " INTEGER NOT NULL, "
            + PublisherCategoryColumns.CATEGORY_ID + " INTEGER NOT NULL "
            + ", CONSTRAINT fk_publisher_id FOREIGN KEY (" + PublisherCategoryColumns.PUBLISHER_ID + ") REFERENCES publisher (_id) ON DELETE CASCADE"
            + ", CONSTRAINT fk_category_id FOREIGN KEY (" + PublisherCategoryColumns.CATEGORY_ID + ") REFERENCES category (_id) ON DELETE CASCADE"
            + ", CONSTRAINT unique_name UNIQUE (category_id, publisher_id) ON CONFLICT REPLACE"
            + " );";

    // @formatter:on

    public static AggregioSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static AggregioSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static AggregioSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new AggregioSQLiteOpenHelper(context);
    }

    private AggregioSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new AggregioSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static AggregioSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new AggregioSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private AggregioSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new AggregioSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_ARTICLE);
        db.execSQL(SQL_CREATE_TABLE_ARTICLE_IMAGE);
        db.execSQL(SQL_CREATE_TABLE_CATEGORY);
        db.execSQL(SQL_CREATE_TABLE_PUBLISHER);
        db.execSQL(SQL_CREATE_TABLE_PUBLISHER_CATEGORY);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
