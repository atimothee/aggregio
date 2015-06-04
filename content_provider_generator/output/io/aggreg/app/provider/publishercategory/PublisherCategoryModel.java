package io.aggreg.app.provider.publishercategory;

import io.aggreg.app.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Data model for the {@code publisher_category} table.
 */
public interface PublisherCategoryModel extends BaseModel {

    /**
     * Get the {@code publisher_id} value.
     */
    long getPublisherId();

    /**
     * Get the {@code category_id} value.
     */
    long getCategoryId();
}
