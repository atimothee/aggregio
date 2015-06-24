package io.aggreg.app.provider.articleimage;

import android.net.Uri;
import android.provider.BaseColumns;

import io.aggreg.app.provider.AggregioProvider;
import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.provider.articleimage.ArticleImageColumns;
import io.aggreg.app.provider.category.CategoryColumns;
import io.aggreg.app.provider.publisher.PublisherColumns;
import io.aggreg.app.provider.publishercategory.PublisherCategoryColumns;

/**
 * Columns for the {@code article_image} table.
 */
public class ArticleImageColumns implements BaseColumns {
    public static final String TABLE_NAME = "article_image";
    public static final Uri CONTENT_URI = Uri.parse(AggregioProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String IMAGE_URL = "article_image__image_url";

    public static final String ARTICLE_ID = "article_id";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            IMAGE_URL,
            ARTICLE_ID
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(IMAGE_URL) || c.contains("." + IMAGE_URL)) return true;
            if (c.equals(ARTICLE_ID) || c.contains("." + ARTICLE_ID)) return true;
        }
        return false;
    }

    public static final String PREFIX_ARTICLE = TABLE_NAME + "__" + ArticleColumns.TABLE_NAME;
}
