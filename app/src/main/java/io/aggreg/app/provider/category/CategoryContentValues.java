package io.aggreg.app.provider.category;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.aggreg.app.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code category} table.
 */
public class CategoryContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return CategoryColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable CategorySelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public CategoryContentValues putName(@Nullable String value) {
        mContentValues.put(CategoryColumns.NAME, value);
        return this;
    }

    public CategoryContentValues putNameNull() {
        mContentValues.putNull(CategoryColumns.NAME);
        return this;
    }

    public CategoryContentValues putImageUrl(@Nullable String value) {
        mContentValues.put(CategoryColumns.IMAGE_URL, value);
        return this;
    }

    public CategoryContentValues putImageUrlNull() {
        mContentValues.putNull(CategoryColumns.IMAGE_URL);
        return this;
    }

    public CategoryContentValues putOrder(@Nullable Integer value) {
        mContentValues.put(CategoryColumns.ORDER, value);
        return this;
    }

    public CategoryContentValues putOrderNull() {
        mContentValues.putNull(CategoryColumns.ORDER);
        return this;
    }
}
