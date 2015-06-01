package io.aggreg.app.provider.article;

import io.aggreg.app.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Data model for the {@code article} table.
 */
public interface ArticleModel extends BaseModel {

    /**
     * Get the {@code title} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getTitle();

    /**
     * Get the {@code link} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getLink();

    /**
     * Get the {@code image} value.
     * Can be {@code null}.
     */
    @Nullable
    String getImage();

    /**
     * Get the {@code pub_date} value.
     * Can be {@code null}.
     */
    @Nullable
    Date getPubDate();

    /**
     * Get the {@code text} value.
     * Can be {@code null}.
     */
    @Nullable
    String getText();

    /**
     * Get the {@code category_id} value.
     */
    long getCategoryId();

    /**
     * Get the {@code publisher_id} value.
     */
    long getPublisherId();
}
