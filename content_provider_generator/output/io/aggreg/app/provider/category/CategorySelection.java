package io.aggreg.app.provider.category;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import io.aggreg.app.provider.base.AbstractSelection;

/**
 * Selection for the {@code category} table.
 */
public class CategorySelection extends AbstractSelection<CategorySelection> {
    @Override
    protected Uri baseUri() {
        return CategoryColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code CategoryCursor} object, which is positioned before the first entry, or null.
     */
    public CategoryCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new CategoryCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public CategoryCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public CategoryCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public CategorySelection id(long... value) {
        addEquals("category." + CategoryColumns._ID, toObjectArray(value));
        return this;
    }

    public CategorySelection name(String... value) {
        addEquals(CategoryColumns.NAME, value);
        return this;
    }

    public CategorySelection nameNot(String... value) {
        addNotEquals(CategoryColumns.NAME, value);
        return this;
    }

    public CategorySelection nameLike(String... value) {
        addLike(CategoryColumns.NAME, value);
        return this;
    }

    public CategorySelection nameContains(String... value) {
        addContains(CategoryColumns.NAME, value);
        return this;
    }

    public CategorySelection nameStartsWith(String... value) {
        addStartsWith(CategoryColumns.NAME, value);
        return this;
    }

    public CategorySelection nameEndsWith(String... value) {
        addEndsWith(CategoryColumns.NAME, value);
        return this;
    }

    public CategorySelection imageUrl(String... value) {
        addEquals(CategoryColumns.IMAGE_URL, value);
        return this;
    }

    public CategorySelection imageUrlNot(String... value) {
        addNotEquals(CategoryColumns.IMAGE_URL, value);
        return this;
    }

    public CategorySelection imageUrlLike(String... value) {
        addLike(CategoryColumns.IMAGE_URL, value);
        return this;
    }

    public CategorySelection imageUrlContains(String... value) {
        addContains(CategoryColumns.IMAGE_URL, value);
        return this;
    }

    public CategorySelection imageUrlStartsWith(String... value) {
        addStartsWith(CategoryColumns.IMAGE_URL, value);
        return this;
    }

    public CategorySelection imageUrlEndsWith(String... value) {
        addEndsWith(CategoryColumns.IMAGE_URL, value);
        return this;
    }
}
