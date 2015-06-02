package io.aggreg.app.provider.category;

import android.net.Uri;
import android.provider.BaseColumns;

import io.aggreg.app.provider.AggregioProvider;
import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.provider.articleimage.ArticleImageColumns;
import io.aggreg.app.provider.category.CategoryColumns;
import io.aggreg.app.provider.publisher.PublisherColumns;

/**
 * Columns for the {@code category} table.
 */
public class CategoryColumns implements BaseColumns {
    public static final String TABLE_NAME = "category";
    public static final Uri CONTENT_URI = Uri.parse(AggregioProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String NAME = "category__name";

    public static final String IMAGE_URL = "category__image_url";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            NAME,
            IMAGE_URL
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(NAME) || c.contains("." + NAME)) return true;
            if (c.equals(IMAGE_URL) || c.contains("." + IMAGE_URL)) return true;
        }
        return false;
    }

}
