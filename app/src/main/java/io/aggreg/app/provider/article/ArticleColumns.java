package io.aggreg.app.provider.article;

import android.net.Uri;
import android.provider.BaseColumns;

import io.aggreg.app.provider.AggregioProvider;
import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.provider.articleimage.ArticleImageColumns;
import io.aggreg.app.provider.category.CategoryColumns;
import io.aggreg.app.provider.publisher.PublisherColumns;
import io.aggreg.app.provider.publishercategory.PublisherCategoryColumns;

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

    public static final String IMAGE = "image";

    public static final String PUB_DATE = "pub_date";

    public static final String TEXT = "text";

    public static final String BOOK_MARKED = "book_marked";

    public static final String CATEGORY_ID = "category_id";

    public static final String PUBLISHER_ID = "publisher_id";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            TITLE,
            LINK,
            IMAGE,
            PUB_DATE,
            TEXT,
            BOOK_MARKED,
            CATEGORY_ID,
            PUBLISHER_ID
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(TITLE) || c.contains("." + TITLE)) return true;
            if (c.equals(LINK) || c.contains("." + LINK)) return true;
            if (c.equals(IMAGE) || c.contains("." + IMAGE)) return true;
            if (c.equals(PUB_DATE) || c.contains("." + PUB_DATE)) return true;
            if (c.equals(TEXT) || c.contains("." + TEXT)) return true;
            if (c.equals(BOOK_MARKED) || c.contains("." + BOOK_MARKED)) return true;
            if (c.equals(CATEGORY_ID) || c.contains("." + CATEGORY_ID)) return true;
            if (c.equals(PUBLISHER_ID) || c.contains("." + PUBLISHER_ID)) return true;
        }
        return false;
    }

    public static final String PREFIX_CATEGORY = TABLE_NAME + "__" + CategoryColumns.TABLE_NAME;
    public static final String PREFIX_PUBLISHER = TABLE_NAME + "__" + PublisherColumns.TABLE_NAME;
}
