package io.aggreg.app.provider.article;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.aggreg.app.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code article} table.
 */
public class ArticleCursor extends AbstractCursor implements ArticleModel {
    public ArticleCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(ArticleColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code title} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getTitle() {
        String res = getStringOrNull(ArticleColumns.TITLE);
        return res;
    }

    /**
     * Get the {@code link} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getLink() {
        String res = getStringOrNull(ArticleColumns.LINK);
        return res;
    }

    /**
     * Get the {@code pub_date} value.
     * Can be {@code null}.
     */
    @Nullable
    public Date getPubDate() {
        Date res = getDateOrNull(ArticleColumns.PUB_DATE);
        return res;
    }

    /**
     * Get the {@code text} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getText() {
        String res = getStringOrNull(ArticleColumns.TEXT);
        return res;
    }

    /**
     * Get the {@code image} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getImage() {
        String res = getStringOrNull(ArticleColumns.IMAGE);
        return res;
    }

    /**
     * Get the {@code category_id} value.
     */
    public long getCategoryId() {
        Long res = getLongOrNull(ArticleColumns.CATEGORY_ID);
        if (res == null)
            throw new NullPointerException("The value of 'category_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code news_source_id} value.
     */
    public long getNewsSourceId() {
        Long res = getLongOrNull(ArticleColumns.NEWS_SOURCE_ID);
        if (res == null)
            throw new NullPointerException("The value of 'news_source_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }
}
