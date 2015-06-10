package io.aggreg.app.provider.selectpublisher;

import io.aggreg.app.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Data model for the {@code select_publisher} table.
 */
public interface SelectPublisherModel extends BaseModel {

    /**
     * Get the {@code image_url} value.
     * Can be {@code null}.
     */
    @Nullable
    String getImageUrl();

    /**
     * Get the {@code selected} value.
     * Can be {@code null}.
     */
    @Nullable
    Boolean getSelected();

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

    /**
     * Get the {@code tag_line} value.
     * Can be {@code null}.
     */
    @Nullable
    String getTagLine();
}
