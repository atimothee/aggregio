package io.aggreg.app.provider.publisher;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.aggreg.app.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code publisher} table.
 */
public class PublisherContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return PublisherColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable PublisherSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public PublisherContentValues putImageUrl(@Nullable String value) {
        mContentValues.put(PublisherColumns.IMAGE_URL, value);
        return this;
    }

    public PublisherContentValues putImageUrlNull() {
        mContentValues.putNull(PublisherColumns.IMAGE_URL);
        return this;
    }

    public PublisherContentValues putWebsite(@Nullable String value) {
        mContentValues.put(PublisherColumns.WEBSITE, value);
        return this;
    }

    public PublisherContentValues putWebsiteNull() {
        mContentValues.putNull(PublisherColumns.WEBSITE);
        return this;
    }

    public PublisherContentValues putName(@Nullable String value) {
        mContentValues.put(PublisherColumns.NAME, value);
        return this;
    }

    public PublisherContentValues putNameNull() {
        mContentValues.putNull(PublisherColumns.NAME);
        return this;
    }

    public PublisherContentValues putCountry(@Nullable String value) {
        mContentValues.put(PublisherColumns.COUNTRY, value);
        return this;
    }

    public PublisherContentValues putCountryNull() {
        mContentValues.putNull(PublisherColumns.COUNTRY);
        return this;
    }

    public PublisherContentValues putTagLine(@Nullable String value) {
        mContentValues.put(PublisherColumns.TAG_LINE, value);
        return this;
    }

    public PublisherContentValues putTagLineNull() {
        mContentValues.putNull(PublisherColumns.TAG_LINE);
        return this;
    }
}
