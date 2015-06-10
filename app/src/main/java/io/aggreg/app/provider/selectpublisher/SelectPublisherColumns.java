package io.aggreg.app.provider.selectpublisher;

import android.net.Uri;
import android.provider.BaseColumns;

import io.aggreg.app.provider.AggregioProvider;
import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.provider.articleimage.ArticleImageColumns;
import io.aggreg.app.provider.category.CategoryColumns;
import io.aggreg.app.provider.publisher.PublisherColumns;
import io.aggreg.app.provider.publishercategory.PublisherCategoryColumns;
import io.aggreg.app.provider.selectpublisher.SelectPublisherColumns;

/**
 * Columns for the {@code select_publisher} table.
 */
public class SelectPublisherColumns implements BaseColumns {
    public static final String TABLE_NAME = "select_publisher";
    public static final Uri CONTENT_URI = Uri.parse(AggregioProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String IMAGE_URL = "image_url";

    public static final String SELECTED = "selected";

    public static final String WEBSITE = "website";

    public static final String NAME = "name";

    public static final String COUNTRY = "country";

    public static final String TAG_LINE = "tag_line";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            IMAGE_URL,
            SELECTED,
            WEBSITE,
            NAME,
            COUNTRY,
            TAG_LINE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(IMAGE_URL) || c.contains("." + IMAGE_URL)) return true;
            if (c.equals(SELECTED) || c.contains("." + SELECTED)) return true;
            if (c.equals(WEBSITE) || c.contains("." + WEBSITE)) return true;
            if (c.equals(NAME) || c.contains("." + NAME)) return true;
            if (c.equals(COUNTRY) || c.contains("." + COUNTRY)) return true;
            if (c.equals(TAG_LINE) || c.contains("." + TAG_LINE)) return true;
        }
        return false;
    }

}
