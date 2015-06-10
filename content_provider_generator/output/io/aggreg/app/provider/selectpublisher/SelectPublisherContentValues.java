package io.aggreg.app.provider.selectpublisher;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.aggreg.app.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code select_publisher} table.
 */
public class SelectPublisherContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return SelectPublisherColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable SelectPublisherSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public SelectPublisherContentValues putImageUrl(@Nullable String value) {
        mContentValues.put(SelectPublisherColumns.IMAGE_URL, value);
        return this;
    }

    public SelectPublisherContentValues putImageUrlNull() {
        mContentValues.putNull(SelectPublisherColumns.IMAGE_URL);
        return this;
    }

    public SelectPublisherContentValues putSelected(@Nullable Boolean value) {
        mContentValues.put(SelectPublisherColumns.SELECTED, value);
        return this;
    }

    public SelectPublisherContentValues putSelectedNull() {
        mContentValues.putNull(SelectPublisherColumns.SELECTED);
        return this;
    }

    public SelectPublisherContentValues putWebsite(@Nullable String value) {
        mContentValues.put(SelectPublisherColumns.WEBSITE, value);
        return this;
    }

    public SelectPublisherContentValues putWebsiteNull() {
        mContentValues.putNull(SelectPublisherColumns.WEBSITE);
        return this;
    }

    public SelectPublisherContentValues putName(@Nullable String value) {
        mContentValues.put(SelectPublisherColumns.NAME, value);
        return this;
    }

    public SelectPublisherContentValues putNameNull() {
        mContentValues.putNull(SelectPublisherColumns.NAME);
        return this;
    }

    public SelectPublisherContentValues putCountry(@Nullable String value) {
        mContentValues.put(SelectPublisherColumns.COUNTRY, value);
        return this;
    }

    public SelectPublisherContentValues putCountryNull() {
        mContentValues.putNull(SelectPublisherColumns.COUNTRY);
        return this;
    }

    public SelectPublisherContentValues putTagLine(@Nullable String value) {
        mContentValues.put(SelectPublisherColumns.TAG_LINE, value);
        return this;
    }

    public SelectPublisherContentValues putTagLineNull() {
        mContentValues.putNull(SelectPublisherColumns.TAG_LINE);
        return this;
    }
}
