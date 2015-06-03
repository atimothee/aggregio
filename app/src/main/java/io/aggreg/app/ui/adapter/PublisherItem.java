package io.aggreg.app.ui.adapter;

import android.database.Cursor;

import io.aggreg.app.provider.publisher.PublisherColumns;

/**
 * Created by Timo on 6/3/15.
 */
public class PublisherItem {
    private String publisherName;
    private String publisherLogo;

    public Boolean getFollowing() {
        return following;
    }

    public void setFollowing(Boolean following) {
        this.following = following;
    }

    private Boolean following;

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
        if(cursor.getString(cursor.getColumnIndex(PublisherColumns.COUNTRY))!=null){
            if(cursor.getString(cursor.getColumnIndex(PublisherColumns.COUNTRY)).equalsIgnoreCase("Uganda")){
            item.setFollowing(true);
        }}else{
            item.setFollowing(false);
        }
        item.setPublisherLogo(cursor.getString(cursor.getColumnIndex(PublisherColumns.IMAGE_URL)));
        return item;
    }
}
