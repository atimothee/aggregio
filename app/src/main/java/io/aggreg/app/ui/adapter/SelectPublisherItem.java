package io.aggreg.app.ui.adapter;

import android.database.Cursor;

import io.aggreg.app.provider.publisher.PublisherColumns;

/**
 * Created by Timo on 6/3/15.
 */
public class SelectPublisherItem {
    private String publisherName;
    private String publisherLogoUrl;
    private Boolean following;

    public Boolean getFollowing() {
        return following;
    }

    public void setFollowing(Boolean following) {
        this.following = following;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherLogoUrl() {
        return publisherLogoUrl;
    }

    public void setPublisherLogoUrl(String publisherLogoUrl) {
        this.publisherLogoUrl = publisherLogoUrl;
    }

    public static SelectPublisherItem fromCursor(Cursor cursor) {

        SelectPublisherItem item = new SelectPublisherItem();
        item.setPublisherName(cursor.getString(cursor.getColumnIndex(PublisherColumns.NAME)));
        item.setPublisherLogoUrl(cursor.getString(cursor.getColumnIndex(PublisherColumns.IMAGE_URL)));
        int selected = cursor.getInt(cursor.getColumnIndex(PublisherColumns.FOLLOWING));
        if(selected == 1){
            item.setFollowing(true);
        }else{
            item.setFollowing(false);
        }
        return item;
    }
}
