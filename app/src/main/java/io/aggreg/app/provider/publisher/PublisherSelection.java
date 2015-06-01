package io.aggreg.app.provider.publisher;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import io.aggreg.app.provider.base.AbstractSelection;

/**
 * Selection for the {@code publisher} table.
 */
public class PublisherSelection extends AbstractSelection<PublisherSelection> {
    @Override
    protected Uri baseUri() {
        return PublisherColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code PublisherCursor} object, which is positioned before the first entry, or null.
     */
    public PublisherCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new PublisherCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public PublisherCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public PublisherCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public PublisherSelection id(long... value) {
        addEquals("publisher." + PublisherColumns._ID, toObjectArray(value));
        return this;
    }

    public PublisherSelection imageUrl(String... value) {
        addEquals(PublisherColumns.IMAGE_URL, value);
        return this;
    }

    public PublisherSelection imageUrlNot(String... value) {
        addNotEquals(PublisherColumns.IMAGE_URL, value);
        return this;
    }

    public PublisherSelection imageUrlLike(String... value) {
        addLike(PublisherColumns.IMAGE_URL, value);
        return this;
    }

    public PublisherSelection imageUrlContains(String... value) {
        addContains(PublisherColumns.IMAGE_URL, value);
        return this;
    }

    public PublisherSelection imageUrlStartsWith(String... value) {
        addStartsWith(PublisherColumns.IMAGE_URL, value);
        return this;
    }

    public PublisherSelection imageUrlEndsWith(String... value) {
        addEndsWith(PublisherColumns.IMAGE_URL, value);
        return this;
    }

    public PublisherSelection website(String... value) {
        addEquals(PublisherColumns.WEBSITE, value);
        return this;
    }

    public PublisherSelection websiteNot(String... value) {
        addNotEquals(PublisherColumns.WEBSITE, value);
        return this;
    }

    public PublisherSelection websiteLike(String... value) {
        addLike(PublisherColumns.WEBSITE, value);
        return this;
    }

    public PublisherSelection websiteContains(String... value) {
        addContains(PublisherColumns.WEBSITE, value);
        return this;
    }

    public PublisherSelection websiteStartsWith(String... value) {
        addStartsWith(PublisherColumns.WEBSITE, value);
        return this;
    }

    public PublisherSelection websiteEndsWith(String... value) {
        addEndsWith(PublisherColumns.WEBSITE, value);
        return this;
    }

    public PublisherSelection name(String... value) {
        addEquals(PublisherColumns.NAME, value);
        return this;
    }

    public PublisherSelection nameNot(String... value) {
        addNotEquals(PublisherColumns.NAME, value);
        return this;
    }

    public PublisherSelection nameLike(String... value) {
        addLike(PublisherColumns.NAME, value);
        return this;
    }

    public PublisherSelection nameContains(String... value) {
        addContains(PublisherColumns.NAME, value);
        return this;
    }

    public PublisherSelection nameStartsWith(String... value) {
        addStartsWith(PublisherColumns.NAME, value);
        return this;
    }

    public PublisherSelection nameEndsWith(String... value) {
        addEndsWith(PublisherColumns.NAME, value);
        return this;
    }

    public PublisherSelection country(String... value) {
        addEquals(PublisherColumns.COUNTRY, value);
        return this;
    }

    public PublisherSelection countryNot(String... value) {
        addNotEquals(PublisherColumns.COUNTRY, value);
        return this;
    }

    public PublisherSelection countryLike(String... value) {
        addLike(PublisherColumns.COUNTRY, value);
        return this;
    }

    public PublisherSelection countryContains(String... value) {
        addContains(PublisherColumns.COUNTRY, value);
        return this;
    }

    public PublisherSelection countryStartsWith(String... value) {
        addStartsWith(PublisherColumns.COUNTRY, value);
        return this;
    }

    public PublisherSelection countryEndsWith(String... value) {
        addEndsWith(PublisherColumns.COUNTRY, value);
        return this;
    }
}
