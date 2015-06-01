package io.aggreg.app.provider.publisher;

import android.net.Uri;
import android.provider.BaseColumns;

import io.aggreg.app.provider.AggregioProvider;
import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.provider.category.CategoryColumns;
import io.aggreg.app.provider.publisher.PublisherColumns;

/**
 * Columns for the {@code publisher} table.
 */
public class PublisherColumns implements BaseColumns {
    public static final String TABLE_NAME = "publisher";
    public static final Uri CONTENT_URI = Uri.parse(AggregioProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String IMAGE_URL = "publisher__image_url";

    public static final String WEBSITE = "website";

    public static final String NAME = "publisher__name";

    public static final String COUNTRY = "country";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            IMAGE_URL,
            WEBSITE,
            NAME,
            COUNTRY
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(IMAGE_URL) || c.contains("." + IMAGE_URL)) return true;
            if (c.equals(WEBSITE) || c.contains("." + WEBSITE)) return true;
            if (c.equals(NAME) || c.contains("." + NAME)) return true;
            if (c.equals(COUNTRY) || c.contains("." + COUNTRY)) return true;
        }
        return false;
    }

}
