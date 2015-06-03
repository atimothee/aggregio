package io.aggreg.app.ui;

import android.database.Cursor;

import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.provider.publisher.PublisherColumns;

/**
 * Created by Timo on 6/3/15.
 */
public class PublisherItem {
    private String publisherName;
    private String publisherLogo;

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherLogo() {
        return publisherLogo;
    }

    public void setPublisherLogo(String publisherLogo) {
        this.publisherLogo = publisherLogo;
    }

    public static PublisherItem fromCursor(Cursor cursor) {

        PublisherItem item = new PublisherItem();
        item.setPublisherName(cursor.getString(cursor.getColumnIndex(PublisherColumns.NAME)));
        item.setPublisherLogo(cursor.getString(cursor.getColumnIndex(PublisherColumns.IMAGE_URL)));
        return item;
    }
}
