package io.aggreg.app.provider.publishercategory;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import io.aggreg.app.provider.base.AbstractSelection;
import io.aggreg.app.provider.publisher.*;
import io.aggreg.app.provider.category.*;

/**
 * Selection for the {@code publisher_category} table.
 */
public class PublisherCategorySelection extends AbstractSelection<PublisherCategorySelection> {
    @Override
    protected Uri baseUri() {
        return PublisherCategoryColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code PublisherCategoryCursor} object, which is positioned before the first entry, or null.
     */
    public PublisherCategoryCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new PublisherCategoryCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public PublisherCategoryCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public PublisherCategoryCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public PublisherCategorySelection id(long... value) {
        addEquals("publisher_category." + PublisherCategoryColumns._ID, toObjectArray(value));
        return this;
    }

    public PublisherCategorySelection publisherId(long... value) {
        addEquals(PublisherCategoryColumns.PUBLISHER_ID, toObjectArray(value));
        return this;
    }

    public PublisherCategorySelection publisherIdNot(long... value) {
        addNotEquals(PublisherCategoryColumns.PUBLISHER_ID, toObjectArray(value));
        return this;
    }

    public PublisherCategorySelection publisherIdGt(long value) {
        addGreaterThan(PublisherCategoryColumns.PUBLISHER_ID, value);
        return this;
    }

    public PublisherCategorySelection publisherIdGtEq(long value) {
        addGreaterThanOrEquals(PublisherCategoryColumns.PUBLISHER_ID, value);
        return this;
    }

    public PublisherCategorySelection publisherIdLt(long value) {
        addLessThan(PublisherCategoryColumns.PUBLISHER_ID, value);
        return this;
    }

    public PublisherCategorySelection publisherIdLtEq(long value) {
        addLessThanOrEquals(PublisherCategoryColumns.PUBLISHER_ID, value);
        return this;
    }

    public PublisherCategorySelection publisherImageUrl(String... value) {
        addEquals(PublisherColumns.IMAGE_URL, value);
        return this;
    }

    public PublisherCategorySelection publisherImageUrlNot(String... value) {
        addNotEquals(PublisherColumns.IMAGE_URL, value);
        return this;
    }

    public PublisherCategorySelection publisherImageUrlLike(String... value) {
        addLike(PublisherColumns.IMAGE_URL, value);
        return this;
    }

    public PublisherCategorySelection publisherImageUrlContains(String... value) {
        addContains(PublisherColumns.IMAGE_URL, value);
        return this;
    }

    public PublisherCategorySelection publisherImageUrlStartsWith(String... value) {
        addStartsWith(PublisherColumns.IMAGE_URL, value);
        return this;
    }

    public PublisherCategorySelection publisherImageUrlEndsWith(String... value) {
        addEndsWith(PublisherColumns.IMAGE_URL, value);
        return this;
    }

    public PublisherCategorySelection publisherWebsite(String... value) {
        addEquals(PublisherColumns.WEBSITE, value);
        return this;
    }

    public PublisherCategorySelection publisherWebsiteNot(String... value) {
        addNotEquals(PublisherColumns.WEBSITE, value);
        return this;
    }

    public PublisherCategorySelection publisherWebsiteLike(String... value) {
        addLike(PublisherColumns.WEBSITE, value);
        return this;
    }

    public PublisherCategorySelection publisherWebsiteContains(String... value) {
        addContains(PublisherColumns.WEBSITE, value);
        return this;
    }

    public PublisherCategorySelection publisherWebsiteStartsWith(String... value) {
        addStartsWith(PublisherColumns.WEBSITE, value);
        return this;
    }

    public PublisherCategorySelection publisherWebsiteEndsWith(String... value) {
        addEndsWith(PublisherColumns.WEBSITE, value);
        return this;
    }

    public PublisherCategorySelection publisherName(String... value) {
        addEquals(PublisherColumns.NAME, value);
        return this;
    }

    public PublisherCategorySelection publisherNameNot(String... value) {
        addNotEquals(PublisherColumns.NAME, value);
        return this;
    }

    public PublisherCategorySelection publisherNameLike(String... value) {
        addLike(PublisherColumns.NAME, value);
        return this;
    }

    public PublisherCategorySelection publisherNameContains(String... value) {
        addContains(PublisherColumns.NAME, value);
        return this;
    }

    public PublisherCategorySelection publisherNameStartsWith(String... value) {
        addStartsWith(PublisherColumns.NAME, value);
        return this;
    }

    public PublisherCategorySelection publisherNameEndsWith(String... value) {
        addEndsWith(PublisherColumns.NAME, value);
        return this;
    }

    public PublisherCategorySelection publisherCountry(String... value) {
        addEquals(PublisherColumns.COUNTRY, value);
        return this;
    }

    public PublisherCategorySelection publisherCountryNot(String... value) {
        addNotEquals(PublisherColumns.COUNTRY, value);
        return this;
    }

    public PublisherCategorySelection publisherCountryLike(String... value) {
        addLike(PublisherColumns.COUNTRY, value);
        return this;
    }

    public PublisherCategorySelection publisherCountryContains(String... value) {
        addContains(PublisherColumns.COUNTRY, value);
        return this;
    }

    public PublisherCategorySelection publisherCountryStartsWith(String... value) {
        addStartsWith(PublisherColumns.COUNTRY, value);
        return this;
    }

    public PublisherCategorySelection publisherCountryEndsWith(String... value) {
        addEndsWith(PublisherColumns.COUNTRY, value);
        return this;
    }

    public PublisherCategorySelection publisherTagLine(String... value) {
        addEquals(PublisherColumns.TAG_LINE, value);
        return this;
    }

    public PublisherCategorySelection publisherTagLineNot(String... value) {
        addNotEquals(PublisherColumns.TAG_LINE, value);
        return this;
    }

    public PublisherCategorySelection publisherTagLineLike(String... value) {
        addLike(PublisherColumns.TAG_LINE, value);
        return this;
    }

    public PublisherCategorySelection publisherTagLineContains(String... value) {
        addContains(PublisherColumns.TAG_LINE, value);
        return this;
    }

    public PublisherCategorySelection publisherTagLineStartsWith(String... value) {
        addStartsWith(PublisherColumns.TAG_LINE, value);
        return this;
    }

    public PublisherCategorySelection publisherTagLineEndsWith(String... value) {
        addEndsWith(PublisherColumns.TAG_LINE, value);
        return this;
    }

    public PublisherCategorySelection publisherFollowing(boolean value) {
        addEquals(PublisherColumns.FOLLOWING, toObjectArray(value));
        return this;
    }

    public PublisherCategorySelection publisherOrder(Integer... value) {
        addEquals(PublisherColumns.ORDER, value);
        return this;
    }

    public PublisherCategorySelection publisherOrderNot(Integer... value) {
        addNotEquals(PublisherColumns.ORDER, value);
        return this;
    }

    public PublisherCategorySelection publisherOrderGt(int value) {
        addGreaterThan(PublisherColumns.ORDER, value);
        return this;
    }

    public PublisherCategorySelection publisherOrderGtEq(int value) {
        addGreaterThanOrEquals(PublisherColumns.ORDER, value);
        return this;
    }

    public PublisherCategorySelection publisherOrderLt(int value) {
        addLessThan(PublisherColumns.ORDER, value);
        return this;
    }

    public PublisherCategorySelection publisherOrderLtEq(int value) {
        addLessThanOrEquals(PublisherColumns.ORDER, value);
        return this;
    }

    public PublisherCategorySelection categoryId(long... value) {
        addEquals(PublisherCategoryColumns.CATEGORY_ID, toObjectArray(value));
        return this;
    }

    public PublisherCategorySelection categoryIdNot(long... value) {
        addNotEquals(PublisherCategoryColumns.CATEGORY_ID, toObjectArray(value));
        return this;
    }

    public PublisherCategorySelection categoryIdGt(long value) {
        addGreaterThan(PublisherCategoryColumns.CATEGORY_ID, value);
        return this;
    }

    public PublisherCategorySelection categoryIdGtEq(long value) {
        addGreaterThanOrEquals(PublisherCategoryColumns.CATEGORY_ID, value);
        return this;
    }

    public PublisherCategorySelection categoryIdLt(long value) {
        addLessThan(PublisherCategoryColumns.CATEGORY_ID, value);
        return this;
    }

    public PublisherCategorySelection categoryIdLtEq(long value) {
        addLessThanOrEquals(PublisherCategoryColumns.CATEGORY_ID, value);
        return this;
    }

    public PublisherCategorySelection categoryName(String... value) {
        addEquals(CategoryColumns.NAME, value);
        return this;
    }

    public PublisherCategorySelection categoryNameNot(String... value) {
        addNotEquals(CategoryColumns.NAME, value);
        return this;
    }

    public PublisherCategorySelection categoryNameLike(String... value) {
        addLike(CategoryColumns.NAME, value);
        return this;
    }

    public PublisherCategorySelection categoryNameContains(String... value) {
        addContains(CategoryColumns.NAME, value);
        return this;
    }

    public PublisherCategorySelection categoryNameStartsWith(String... value) {
        addStartsWith(CategoryColumns.NAME, value);
        return this;
    }

    public PublisherCategorySelection categoryNameEndsWith(String... value) {
        addEndsWith(CategoryColumns.NAME, value);
        return this;
    }

    public PublisherCategorySelection categoryImageUrl(String... value) {
        addEquals(CategoryColumns.IMAGE_URL, value);
        return this;
    }

    public PublisherCategorySelection categoryImageUrlNot(String... value) {
        addNotEquals(CategoryColumns.IMAGE_URL, value);
        return this;
    }

    public PublisherCategorySelection categoryImageUrlLike(String... value) {
        addLike(CategoryColumns.IMAGE_URL, value);
        return this;
    }

    public PublisherCategorySelection categoryImageUrlContains(String... value) {
        addContains(CategoryColumns.IMAGE_URL, value);
        return this;
    }

    public PublisherCategorySelection categoryImageUrlStartsWith(String... value) {
        addStartsWith(CategoryColumns.IMAGE_URL, value);
        return this;
    }

    public PublisherCategorySelection categoryImageUrlEndsWith(String... value) {
        addEndsWith(CategoryColumns.IMAGE_URL, value);
        return this;
    }

    public PublisherCategorySelection categoryOrder(Integer... value) {
        addEquals(CategoryColumns.ORDER, value);
        return this;
    }

    public PublisherCategorySelection categoryOrderNot(Integer... value) {
        addNotEquals(CategoryColumns.ORDER, value);
        return this;
    }

    public PublisherCategorySelection categoryOrderGt(int value) {
        addGreaterThan(CategoryColumns.ORDER, value);
        return this;
    }

    public PublisherCategorySelection categoryOrderGtEq(int value) {
        addGreaterThanOrEquals(CategoryColumns.ORDER, value);
        return this;
    }

    public PublisherCategorySelection categoryOrderLt(int value) {
        addLessThan(CategoryColumns.ORDER, value);
        return this;
    }

    public PublisherCategorySelection categoryOrderLtEq(int value) {
        addLessThanOrEquals(CategoryColumns.ORDER, value);
        return this;
    }
}
