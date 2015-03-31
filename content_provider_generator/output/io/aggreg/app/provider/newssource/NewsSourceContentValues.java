package io.aggreg.app.provider.newssource;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.aggreg.app.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code news_source} table.
 */
public class NewsSourceContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return NewsSourceColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable NewsSourceSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public NewsSourceContentValues putImageUrl(@Nullable String value) {
        mContentValues.put(NewsSourceColumns.IMAGE_URL, value);
        return this;
    }

    public NewsSourceContentValues putImageUrlNull() {
        mContentValues.putNull(NewsSourceColumns.IMAGE_URL);
        return this;
    }

    public NewsSourceContentValues putWebsite(@Nullable String value) {
        mContentValues.put(NewsSourceColumns.WEBSITE, value);
        return this;
    }

    public NewsSourceContentValues putWebsiteNull() {
        mContentValues.putNull(NewsSourceColumns.WEBSITE);
        return this;
    }

    public NewsSourceContentValues putName(@Nullable String value) {
        mContentValues.put(NewsSourceColumns.NAME, value);
        return this;
    }

    public NewsSourceContentValues putNameNull() {
        mContentValues.putNull(NewsSourceColumns.NAME);
        return this;
    }

    public NewsSourceContentValues putCountry(@Nullable String value) {
        mContentValues.put(NewsSourceColumns.COUNTRY, value);
        return this;
    }

    public NewsSourceContentValues putCountryNull() {
        mContentValues.putNull(NewsSourceColumns.COUNTRY);
        return this;
    }
}
