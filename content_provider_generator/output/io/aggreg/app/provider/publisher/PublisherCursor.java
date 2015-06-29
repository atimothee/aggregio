package io.aggreg.app.provider.publisher;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.aggreg.app.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code publisher} table.
 */
public class PublisherCursor extends AbstractCursor implements PublisherModel {
    public PublisherCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(PublisherColumns._ID);
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
        String res = getStringOrNull(PublisherColumns.IMAGE_URL);
        return res;
    }

    /**
     * Get the {@code website} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getWebsite() {
        String res = getStringOrNull(PublisherColumns.WEBSITE);
        return res;
    }

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getName() {
        String res = getStringOrNull(PublisherColumns.NAME);
        return res;
    }

    /**
     * Get the {@code country} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getCountry() {
        String res = getStringOrNull(PublisherColumns.COUNTRY);
        return res;
    }

    /**
     * Get the {@code tag_line} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getTagLine() {
        String res = getStringOrNull(PublisherColumns.TAG_LINE);
        return res;
    }

    /**
     * Get the {@code following} value.
     * Can be {@code null}.
     */
    @Nullable
    public Boolean getFollowing() {
        Boolean res = getBooleanOrNull(PublisherColumns.FOLLOWING);
        return res;
    }

    /**
     * Get the {@code order} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getOrder() {
        Integer res = getIntegerOrNull(PublisherColumns.ORDER);
        return res;
    }
}
