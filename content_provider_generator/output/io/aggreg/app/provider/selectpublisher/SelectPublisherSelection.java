package io.aggreg.app.provider.selectpublisher;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import io.aggreg.app.provider.base.AbstractSelection;

/**
 * Selection for the {@code select_publisher} table.
 */
public class SelectPublisherSelection extends AbstractSelection<SelectPublisherSelection> {
    @Override
    protected Uri baseUri() {
        return SelectPublisherColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code SelectPublisherCursor} object, which is positioned before the first entry, or null.
     */
    public SelectPublisherCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new SelectPublisherCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public SelectPublisherCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public SelectPublisherCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public SelectPublisherSelection id(long... value) {
        addEquals("select_publisher." + SelectPublisherColumns._ID, toObjectArray(value));
        return this;
    }

    public SelectPublisherSelection imageUrl(String... value) {
        addEquals(SelectPublisherColumns.IMAGE_URL, value);
        return this;
    }

    public SelectPublisherSelection imageUrlNot(String... value) {
        addNotEquals(SelectPublisherColumns.IMAGE_URL, value);
        return this;
    }

    public SelectPublisherSelection imageUrlLike(String... value) {
        addLike(SelectPublisherColumns.IMAGE_URL, value);
        return this;
    }

    public SelectPublisherSelection imageUrlContains(String... value) {
        addContains(SelectPublisherColumns.IMAGE_URL, value);
        return this;
    }

    public SelectPublisherSelection imageUrlStartsWith(String... value) {
        addStartsWith(SelectPublisherColumns.IMAGE_URL, value);
        return this;
    }

    public SelectPublisherSelection imageUrlEndsWith(String... value) {
        addEndsWith(SelectPublisherColumns.IMAGE_URL, value);
        return this;
    }

    public SelectPublisherSelection selected(Boolean value) {
        addEquals(SelectPublisherColumns.SELECTED, toObjectArray(value));
        return this;
    }

    public SelectPublisherSelection website(String... value) {
        addEquals(SelectPublisherColumns.WEBSITE, value);
        return this;
    }

    public SelectPublisherSelection websiteNot(String... value) {
        addNotEquals(SelectPublisherColumns.WEBSITE, value);
        return this;
    }

    public SelectPublisherSelection websiteLike(String... value) {
        addLike(SelectPublisherColumns.WEBSITE, value);
        return this;
    }

    public SelectPublisherSelection websiteContains(String... value) {
        addContains(SelectPublisherColumns.WEBSITE, value);
        return this;
    }

    public SelectPublisherSelection websiteStartsWith(String... value) {
        addStartsWith(SelectPublisherColumns.WEBSITE, value);
        return this;
    }

    public SelectPublisherSelection websiteEndsWith(String... value) {
        addEndsWith(SelectPublisherColumns.WEBSITE, value);
        return this;
    }

    public SelectPublisherSelection name(String... value) {
        addEquals(SelectPublisherColumns.NAME, value);
        return this;
    }

    public SelectPublisherSelection nameNot(String... value) {
        addNotEquals(SelectPublisherColumns.NAME, value);
        return this;
    }

    public SelectPublisherSelection nameLike(String... value) {
        addLike(SelectPublisherColumns.NAME, value);
        return this;
    }

    public SelectPublisherSelection nameContains(String... value) {
        addContains(SelectPublisherColumns.NAME, value);
        return this;
    }

    public SelectPublisherSelection nameStartsWith(String... value) {
        addStartsWith(SelectPublisherColumns.NAME, value);
        return this;
    }

    public SelectPublisherSelection nameEndsWith(String... value) {
        addEndsWith(SelectPublisherColumns.NAME, value);
        return this;
    }

    public SelectPublisherSelection country(String... value) {
        addEquals(SelectPublisherColumns.COUNTRY, value);
        return this;
    }

    public SelectPublisherSelection countryNot(String... value) {
        addNotEquals(SelectPublisherColumns.COUNTRY, value);
        return this;
    }

    public SelectPublisherSelection countryLike(String... value) {
        addLike(SelectPublisherColumns.COUNTRY, value);
        return this;
    }

    public SelectPublisherSelection countryContains(String... value) {
        addContains(SelectPublisherColumns.COUNTRY, value);
        return this;
    }

    public SelectPublisherSelection countryStartsWith(String... value) {
        addStartsWith(SelectPublisherColumns.COUNTRY, value);
        return this;
    }

    public SelectPublisherSelection countryEndsWith(String... value) {
        addEndsWith(SelectPublisherColumns.COUNTRY, value);
        return this;
    }

    public SelectPublisherSelection tagLine(String... value) {
        addEquals(SelectPublisherColumns.TAG_LINE, value);
        return this;
    }

    public SelectPublisherSelection tagLineNot(String... value) {
        addNotEquals(SelectPublisherColumns.TAG_LINE, value);
        return this;
    }

    public SelectPublisherSelection tagLineLike(String... value) {
        addLike(SelectPublisherColumns.TAG_LINE, value);
        return this;
    }

    public SelectPublisherSelection tagLineContains(String... value) {
        addContains(SelectPublisherColumns.TAG_LINE, value);
        return this;
    }

    public SelectPublisherSelection tagLineStartsWith(String... value) {
        addStartsWith(SelectPublisherColumns.TAG_LINE, value);
        return this;
    }

    public SelectPublisherSelection tagLineEndsWith(String... value) {
        addEndsWith(SelectPublisherColumns.TAG_LINE, value);
        return this;
    }
}
