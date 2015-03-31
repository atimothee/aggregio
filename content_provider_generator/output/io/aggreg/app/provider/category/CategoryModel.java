package io.aggreg.app.provider.category;

import io.aggreg.app.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Data model for the {@code category} table.
 */
public interface CategoryModel extends BaseModel {

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    @Nullable
    String getName();

    /**
     * Get the {@code image_url} value.
     * Can be {@code null}.
     */
    @Nullable
    String getImageUrl();
}
