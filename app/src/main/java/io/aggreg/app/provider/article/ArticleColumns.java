package io.aggreg.app.provider.article;

import android.net.Uri;
import android.provider.BaseColumns;

import io.aggreg.app.provider.AggregioProvider;

/**
 * Columns for the {@code article} table.
 */
public class ArticleColumns implements BaseColumns {
    public static final String TABLE_NAME = "article";
    public static final Uri CONTENT_URI = Uri.parse(AggregioProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String TITLE = "title";

    public static final String LINK = "link";

    public static final String PUB_DATE = "pub_date";

    public static final String TEXT = "text";

    public static final String IMAGE = "image";

    public static final String CATEGORY_ID = "category_id";

    public static final String NEWS_SOURCE_ID = "news_source_id";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            TITLE,
            LINK,
            PUB_DATE,
            TEXT,
            IMAGE,
            CATEGORY_ID,
            NEWS_SOURCE_ID
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(TITLE) || c.contains("." + TITLE)) return true;
            if (c.equals(LINK) || c.contains("." + LINK)) return true;
            if (c.equals(PUB_DATE) || c.contains("." + PUB_DATE)) return true;
            if (c.equals(TEXT) || c.contains("." + TEXT)) return true;
            if (c.equals(IMAGE) || c.contains("." + IMAGE)) return true;
            if (c.equals(CATEGORY_ID) || c.contains("." + CATEGORY_ID)) return true;
            if (c.equals(NEWS_SOURCE_ID) || c.contains("." + NEWS_SOURCE_ID)) return true;
        }
        return false;
    }

}
