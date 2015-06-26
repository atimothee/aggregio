package io.aggreg.app.ui.adapter;

import android.database.Cursor;
import android.text.Html;

import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.provider.publisher.PublisherColumns;

/**
 * Created by Timo on 6/3/15.
 */
public class ArticleItem {
    private String title;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String text;
    private Long timeAgo;
    private String publisherName;
    private String image;
    private String publisherLogo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTimeAgo() {
        return timeAgo;
    }

    public void setTimeAgo(Long timeAgo) {
        this.timeAgo = timeAgo;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPublisherLogo() {
        return publisherLogo;
    }

    public void setPublisherLogo(String publisherLogo) {
        this.publisherLogo = publisherLogo;
    }

    public static ArticleItem fromCursor(Cursor cursor) {

        ArticleItem item = new ArticleItem();
        item.setTitle(cursor.getString(cursor.getColumnIndex(ArticleColumns.TITLE)));
        item.setText(cursor.getString(cursor.getColumnIndex(ArticleColumns.TEXT)));
        item.setPublisherName(cursor.getString(cursor.getColumnIndex(PublisherColumns.NAME)));
        item.setImage(cursor.getString(cursor.getColumnIndex(ArticleColumns.IMAGE)));
        item.setPublisherLogo(cursor.getString(cursor.getColumnIndex(PublisherColumns.IMAGE_URL)));
        item.setTimeAgo(cursor.getLong(cursor.getColumnIndex(ArticleColumns.PUB_DATE)));
        return item;
    }
}
