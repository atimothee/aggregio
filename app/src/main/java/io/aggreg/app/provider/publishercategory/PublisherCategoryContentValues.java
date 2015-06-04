package io.aggreg.app.provider.publishercategory;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.aggreg.app.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code publisher_category} table.
 */
public class PublisherCategoryContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return PublisherCategoryColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable PublisherCategorySelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public PublisherCategoryContentValues putPublisherId(long value) {
        mContentValues.put(PublisherCategoryColumns.PUBLISHER_ID, value);
        return this;
    }


    public PublisherCategoryContentValues putCategoryId(long value) {
        mContentValues.put(PublisherCategoryColumns.CATEGORY_ID, value);
        return this;
    }

}
