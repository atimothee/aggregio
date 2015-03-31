package io.aggreg.app.provider.newssource;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import io.aggreg.app.provider.base.AbstractSelection;

/**
 * Selection for the {@code news_source} table.
 */
public class NewsSourceSelection extends AbstractSelection<NewsSourceSelection> {
    @Override
    protected Uri baseUri() {
        return NewsSourceColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code NewsSourceCursor} object, which is positioned before the first entry, or null.
     */
    public NewsSourceCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new NewsSourceCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public NewsSourceCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public NewsSourceCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public NewsSourceSelection id(long... value) {
        addEquals("news_source." + NewsSourceColumns._ID, toObjectArray(value));
        return this;
    }

    public NewsSourceSelection imageUrl(String... value) {
        addEquals(NewsSourceColumns.IMAGE_URL, value);
        return this;
    }

    public NewsSourceSelection imageUrlNot(String... value) {
        addNotEquals(NewsSourceColumns.IMAGE_URL, value);
        return this;
    }

    public NewsSourceSelection imageUrlLike(String... value) {
        addLike(NewsSourceColumns.IMAGE_URL, value);
        return this;
    }

    public NewsSourceSelection imageUrlContains(String... value) {
        addContains(NewsSourceColumns.IMAGE_URL, value);
        return this;
    }

    public NewsSourceSelection imageUrlStartsWith(String... value) {
        addStartsWith(NewsSourceColumns.IMAGE_URL, value);
        return this;
    }

    public NewsSourceSelection imageUrlEndsWith(String... value) {
        addEndsWith(NewsSourceColumns.IMAGE_URL, value);
        return this;
    }

    public NewsSourceSelection website(String... value) {
        addEquals(NewsSourceColumns.WEBSITE, value);
        return this;
    }

    public NewsSourceSelection websiteNot(String... value) {
        addNotEquals(NewsSourceColumns.WEBSITE, value);
        return this;
    }

    public NewsSourceSelection websiteLike(String... value) {
        addLike(NewsSourceColumns.WEBSITE, value);
        return this;
    }

    public NewsSourceSelection websiteContains(String... value) {
        addContains(NewsSourceColumns.WEBSITE, value);
        return this;
    }

    public NewsSourceSelection websiteStartsWith(String... value) {
        addStartsWith(NewsSourceColumns.WEBSITE, value);
        return this;
    }

    public NewsSourceSelection websiteEndsWith(String... value) {
        addEndsWith(NewsSourceColumns.WEBSITE, value);
        return this;
    }

    public NewsSourceSelection name(String... value) {
        addEquals(NewsSourceColumns.NAME, value);
        return this;
    }

    public NewsSourceSelection nameNot(String... value) {
        addNotEquals(NewsSourceColumns.NAME, value);
        return this;
    }

    public NewsSourceSelection nameLike(String... value) {
        addLike(NewsSourceColumns.NAME, value);
        return this;
    }

    public NewsSourceSelection nameContains(String... value) {
        addContains(NewsSourceColumns.NAME, value);
        return this;
    }

    public NewsSourceSelection nameStartsWith(String... value) {
        addStartsWith(NewsSourceColumns.NAME, value);
        return this;
    }

    public NewsSourceSelection nameEndsWith(String... value) {
        addEndsWith(NewsSourceColumns.NAME, value);
        return this;
    }

    public NewsSourceSelection country(String... value) {
        addEquals(NewsSourceColumns.COUNTRY, value);
        return this;
    }

    public NewsSourceSelection countryNot(String... value) {
        addNotEquals(NewsSourceColumns.COUNTRY, value);
        return this;
    }

    public NewsSourceSelection countryLike(String... value) {
        addLike(NewsSourceColumns.COUNTRY, value);
        return this;
    }

    public NewsSourceSelection countryContains(String... value) {
        addContains(NewsSourceColumns.COUNTRY, value);
        return this;
    }

    public NewsSourceSelection countryStartsWith(String... value) {
        addStartsWith(NewsSourceColumns.COUNTRY, value);
        return this;
    }

    public NewsSourceSelection countryEndsWith(String... value) {
        addEndsWith(NewsSourceColumns.COUNTRY, value);
        return this;
    }
}
