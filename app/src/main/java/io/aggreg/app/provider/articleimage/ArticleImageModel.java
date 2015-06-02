package io.aggreg.app.provider.articleimage;

import io.aggreg.app.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Data model for the {@code article_image} table.
 */
public interface ArticleImageModel extends BaseModel {

    /**
     * Get the {@code image_url} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getImageUrl();

    /**
     * Get the {@code article_id} value.
     * Can be {@code null}.
     */
    @Nullable
    Long getArticleId();
}
