package io.aggreg.app.provider.selectpublisher;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.aggreg.app.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code select_publisher} table.
 */
public class SelectPublisherCursor extends AbstractCursor implements SelectPublisherModel {
    public SelectPublisherCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(SelectPublisherColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code image_url} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getImageUrl() {
        String res = getStringOrNull(SelectPublisherColumns.IMAGE_URL);
        return res;
    }

    /**
     * Get the {@code selected} value.
     * Can be {@code null}.
     */
    @Nullable
    public Boolean getSelected() {
        Boolean res = getBooleanOrNull(SelectPublisherColumns.SELECTED);
        return res;
    }

    /**
     * Get the {@code website} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getWebsite() {
        String res = getStringOrNull(SelectPublisherColumns.WEBSITE);
        return res;
    }

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getName() {
        String res = getStringOrNull(SelectPublisherColumns.NAME);
        return res;
    }

    /**
     * Get the {@code country} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getCountry() {
        String res = getStringOrNull(SelectPublisherColumns.COUNTRY);
        return res;
    }

    /**
     * Get the {@code tag_line} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getTagLine() {
        String res = getStringOrNull(SelectPublisherColumns.TAG_LINE);
        return res;
    }
}
