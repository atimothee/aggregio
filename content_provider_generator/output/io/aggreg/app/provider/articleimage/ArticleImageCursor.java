package io.aggreg.app.provider.articleimage;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.aggreg.app.provider.base.AbstractCursor;
import io.aggreg.app.provider.article.*;
import io.aggreg.app.provider.category.*;
import io.aggreg.app.provider.publisher.*;

/**
 * Cursor wrapper for the {@code article_image} table.
 */
public class ArticleImageCursor extends AbstractCursor implements ArticleImageModel {
    public ArticleImageCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(ArticleImageColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code image_url} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getImageUrl() {
        String res = getStringOrNull(ArticleImageColumns.IMAGE_URL);
        if (res == null)
            throw new NullPointerException("The value of 'image_url' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code article_id} value.
     * Can be {@code null}.
     */
    @Nullable
    public Long getArticleId() {
        Long res = getLongOrNull(ArticleImageColumns.ARTICLE_ID);
        return res;
    }

    /**
     * Get the {@code title} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getArticleTitle() {
        String res = getStringOrNull(ArticleColumns.TITLE);
        return res;
    }

    /**
     * Get the {@code link} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getArticleLink() {
        String res = getStringOrNull(ArticleColumns.LINK);
        return res;
    }

    /**
     * Get the {@code image} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getArticleImage() {
        String res = getStringOrNull(ArticleColumns.IMAGE);
        return res;
    }

    /**
     * Get the {@code pub_date} value.
     * Can be {@code null}.
     */
    @Nullable
    public Date getArticlePubDate() {
        Date res = getDateOrNull(ArticleColumns.PUB_DATE);
        return res;
    }

    /**
     * Get the {@code text} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getArticleText() {
        String res = getStringOrNull(ArticleColumns.TEXT);
        return res;
    }

    /**
     * Get the {@code book_marked} value.
     * Can be {@code null}.
     */
    @Nullable
    public Boolean getArticleBookMarked() {
        Boolean res = getBooleanOrNull(ArticleColumns.BOOK_MARKED);
        return res;
    }

    /**
     * Get the {@code is_read} value.
     * Can be {@code null}.
     */
    @Nullable
    public Boolean getArticleIsRead() {
        Boolean res = getBooleanOrNull(ArticleColumns.IS_READ);
        return res;
    }

    /**
     * Get the {@code category_id} value.
     * Can be {@code null}.
     */
    @Nullable
    public Long getArticleCategoryId() {
        Long res = getLongOrNull(ArticleColumns.CATEGORY_ID);
        return res;
    }

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getArticleCategoryName() {
        String res = getStringOrNull(CategoryColumns.NAME);
        return res;
    }

    /**
     * Get the {@code image_url} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getArticleCategoryImageUrl() {
        String res = getStringOrNull(CategoryColumns.IMAGE_URL);
        return res;
    }

    /**
     * Get the {@code order} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getArticleCategoryOrder() {
        Integer res = getIntegerOrNull(CategoryColumns.ORDER);
        return res;
    }

    /**
     * Get the {@code publisher_id} value.
     */
    public long getArticlePublisherId() {
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
    public String getArticlePublisherImageUrl() {
        String res = getStringOrNull(PublisherColumns.IMAGE_URL);
        return res;
    }

    /**
     * Get the {@code website} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getArticlePublisherWebsite() {
        String res = getStringOrNull(PublisherColumns.WEBSITE);
        return res;
    }

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getArticlePublisherName() {
        String res = getStringOrNull(PublisherColumns.NAME);
        return res;
    }

    /**
     * Get the {@code country} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getArticlePublisherCountry() {
        String res = getStringOrNull(PublisherColumns.COUNTRY);
        return res;
    }

    /**
     * Get the {@code tag_line} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getArticlePublisherTagLine() {
        String res = getStringOrNull(PublisherColumns.TAG_LINE);
        return res;
    }

    /**
     * Get the {@code following} value.
     */
    public boolean getArticlePublisherFollowing() {
        Boolean res = getBooleanOrNull(PublisherColumns.FOLLOWING);
        if (res == null)
            throw new NullPointerException("The value of 'following' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code order} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getArticlePublisherOrder() {
        Integer res = getIntegerOrNull(PublisherColumns.ORDER);
        return res;
    }
}
