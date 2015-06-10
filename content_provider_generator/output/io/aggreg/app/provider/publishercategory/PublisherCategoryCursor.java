package io.aggreg.app.provider.publishercategory;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.aggreg.app.provider.base.AbstractCursor;
import io.aggreg.app.provider.publisher.*;
import io.aggreg.app.provider.category.*;

/**
 * Cursor wrapper for the {@code publisher_category} table.
 */
public class PublisherCategoryCursor extends AbstractCursor implements PublisherCategoryModel {
    public PublisherCategoryCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(PublisherCategoryColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code publisher_id} value.
     */
    public long getPublisherId() {
        Long res = getLongOrNull(PublisherCategoryColumns.PUBLISHER_ID);
        if (res == null)
            throw new NullPointerException("The value of 'publisher_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code image_url} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getPublisherImageUrl() {
        String res = getStringOrNull(PublisherColumns.IMAGE_URL);
        return res;
    }

    /**
     * Get the {@code website} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getPublisherWebsite() {
        String res = getStringOrNull(PublisherColumns.WEBSITE);
        return res;
    }

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getPublisherName() {
        String res = getStringOrNull(PublisherColumns.NAME);
        return res;
    }

    /**
     * Get the {@code country} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getPublisherCountry() {
        String res = getStringOrNull(PublisherColumns.COUNTRY);
        return res;
    }

    /**
     * Get the {@code tag_line} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getPublisherTagLine() {
        String res = getStringOrNull(PublisherColumns.TAG_LINE);
        return res;
    }

    /**
     * Get the {@code category_id} value.
     */
    public long getCategoryId() {
        Long res = getLongOrNull(PublisherCategoryColumns.CATEGORY_ID);
        if (res == null)
            throw new NullPointerException("The value of 'category_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getCategoryName() {
        String res = getStringOrNull(CategoryColumns.NAME);
        return res;
    }

    /**
     * Get the {@code image_url} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getCategoryImageUrl() {
        String res = getStringOrNull(CategoryColumns.IMAGE_URL);
        return res;
    }
}
