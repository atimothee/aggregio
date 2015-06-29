package io.aggreg.app.provider.article;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.aggreg.app.provider.base.AbstractCursor;
import io.aggreg.app.provider.category.*;
import io.aggreg.app.provider.publisher.*;

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
     * Cannot be {@code null}.
     */
    @NonNull
    public String getTitle() {
        String res = getStringOrNull(ArticleColumns.TITLE);
        if (res == null)
            throw new NullPointerException("The value of 'title' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code link} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getLink() {
        String res = getStringOrNull(ArticleColumns.LINK);
        if (res == null)
            throw new NullPointerException("The value of 'link' in the database was null, which is not allowed according to the model definition");
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
     * Get the {@code book_marked} value.
     * Can be {@code null}.
     */
    @Nullable
    public Boolean getBookMarked() {
        Boolean res = getBooleanOrNull(ArticleColumns.BOOK_MARKED);
        return res;
    }

    /**
     * Get the {@code is_read} value.
     * Can be {@code null}.
     */
    @Nullable
    public Boolean getIsRead() {
        Boolean res = getBooleanOrNull(ArticleColumns.IS_READ);
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

    /**
     * Get the {@code order} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getCategoryOrder() {
        Integer res = getIntegerOrNull(CategoryColumns.ORDER);
        return res;
    }

    /**
     * Get the {@code publisher_id} value.
     */
    public long getPublisherId() {
        Long res = getLongOrNull(ArticleColumns.PUBLISHER_ID);
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
     * Get the {@code following} value.
     * Can be {@code null}.
     */
    @Nullable
    public Boolean getPublisherFollowing() {
        Boolean res = getBooleanOrNull(PublisherColumns.FOLLOWING);
        return res;
    }

    /**
     * Get the {@code order} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getPublisherOrder() {
        Integer res = getIntegerOrNull(PublisherColumns.ORDER);
        return res;
    }
}
