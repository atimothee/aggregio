package io.aggreg.app.ui.adapter;

import android.database.Cursor;

import io.aggreg.app.provider.publisher.PublisherColumns;
import io.aggreg.app.provider.selectpublisher.SelectPublisherColumns;

/**
 * Created by Timo on 6/3/15.
 */
public class SelectPublisherItem {
    private String publisherName;
    private String publisherLogoUrl;
    private Boolean selected;

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
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
        item.setPublisherName(cursor.getString(cursor.getColumnIndex(SelectPublisherColumns.NAME)));
        item.setPublisherLogoUrl(cursor.getString(cursor.getColumnIndex(SelectPublisherColumns.IMAGE_URL)));
        int selected = cursor.getInt(cursor.getColumnIndex(SelectPublisherColumns.SELECTED));
        if(selected == 1){
            item.setSelected(true);
        }else{
            item.setSelected(false);
        }
        return item;
    }
}
