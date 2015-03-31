package io.aggreg.app.provider.newssource;

import io.aggreg.app.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Data model for the {@code news_source} table.
 */
public interface NewsSourceModel extends BaseModel {

    /**
     * Get the {@code image_url} value.
     * Can be {@code null}.
     */
    @Nullable
    String getImageUrl();

    /**
     * Get the {@code website} value.
     * Can be {@code null}.
     */
    @Nullable
    String getWebsite();

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    @Nullable
    String getName();

    /**
     * Get the {@code country} value.
     * Can be {@code null}.
     */
    @Nullable
    String getCountry();
}
