package io.aggreg.app.provider.article;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.aggreg.app.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code article} table.
 */
public class ArticleContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return ArticleColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable ArticleSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public ArticleContentValues putTitle(@Nullable String value) {
        mContentValues.put(ArticleColumns.TITLE, value);
        return this;
    }

    public ArticleContentValues putTitleNull() {
        mContentValues.putNull(ArticleColumns.TITLE);
        return this;
    }

    public ArticleContentValues putLink(@Nullable String value) {
        mContentValues.put(ArticleColumns.LINK, value);
        return this;
    }

    public ArticleContentValues putLinkNull() {
        mContentValues.putNull(ArticleColumns.LINK);
        return this;
    }

    public ArticleContentValues putPubDate(@Nullable Date value) {
        mContentValues.put(ArticleColumns.PUB_DATE, value == null ? null : value.getTime());
        return this;
    }

    public ArticleContentValues putPubDateNull() {
        mContentValues.putNull(ArticleColumns.PUB_DATE);
        return this;
    }

    public ArticleContentValues putPubDate(@Nullable Long value) {
        mContentValues.put(ArticleColumns.PUB_DATE, value);
        return this;
    }

    public ArticleContentValues putText(@Nullable String value) {
        mContentValues.put(ArticleColumns.TEXT, value);
        return this;
    }

    public ArticleContentValues putTextNull() {
        mContentValues.putNull(ArticleColumns.TEXT);
        return this;
    }

    public ArticleContentValues putImage(@Nullable String value) {
        mContentValues.put(ArticleColumns.IMAGE, value);
        return this;
    }

    public ArticleContentValues putImageNull() {
        mContentValues.putNull(ArticleColumns.IMAGE);
        return this;
    }

    public ArticleContentValues putCategoryId(long value) {
        mContentValues.put(ArticleColumns.CATEGORY_ID, value);
        return this;
    }


    public ArticleContentValues putNewsSourceId(long value) {
        mContentValues.put(ArticleColumns.NEWS_SOURCE_ID, value);
        return this;
    }

}
