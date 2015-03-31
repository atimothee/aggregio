package io.aggreg.app.provider.article;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import io.aggreg.app.provider.base.AbstractSelection;

/**
 * Selection for the {@code article} table.
 */
public class ArticleSelection extends AbstractSelection<ArticleSelection> {
    @Override
    protected Uri baseUri() {
        return ArticleColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code ArticleCursor} object, which is positioned before the first entry, or null.
     */
    public ArticleCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new ArticleCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public ArticleCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public ArticleCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public ArticleSelection id(long... value) {
        addEquals("article." + ArticleColumns._ID, toObjectArray(value));
        return this;
    }

    public ArticleSelection title(String... value) {
        addEquals(ArticleColumns.TITLE, value);
        return this;
    }

    public ArticleSelection titleNot(String... value) {
        addNotEquals(ArticleColumns.TITLE, value);
        return this;
    }

    public ArticleSelection titleLike(String... value) {
        addLike(ArticleColumns.TITLE, value);
        return this;
    }

    public ArticleSelection titleContains(String... value) {
        addContains(ArticleColumns.TITLE, value);
        return this;
    }

    public ArticleSelection titleStartsWith(String... value) {
        addStartsWith(ArticleColumns.TITLE, value);
        return this;
    }

    public ArticleSelection titleEndsWith(String... value) {
        addEndsWith(ArticleColumns.TITLE, value);
        return this;
    }

    public ArticleSelection link(String... value) {
        addEquals(ArticleColumns.LINK, value);
        return this;
    }

    public ArticleSelection linkNot(String... value) {
        addNotEquals(ArticleColumns.LINK, value);
        return this;
    }

    public ArticleSelection linkLike(String... value) {
        addLike(ArticleColumns.LINK, value);
        return this;
    }

    public ArticleSelection linkContains(String... value) {
        addContains(ArticleColumns.LINK, value);
        return this;
    }

    public ArticleSelection linkStartsWith(String... value) {
        addStartsWith(ArticleColumns.LINK, value);
        return this;
    }

    public ArticleSelection linkEndsWith(String... value) {
        addEndsWith(ArticleColumns.LINK, value);
        return this;
    }

    public ArticleSelection pubDate(Date... value) {
        addEquals(ArticleColumns.PUB_DATE, value);
        return this;
    }

    public ArticleSelection pubDateNot(Date... value) {
        addNotEquals(ArticleColumns.PUB_DATE, value);
        return this;
    }

    public ArticleSelection pubDate(Long... value) {
        addEquals(ArticleColumns.PUB_DATE, value);
        return this;
    }

    public ArticleSelection pubDateAfter(Date value) {
        addGreaterThan(ArticleColumns.PUB_DATE, value);
        return this;
    }

    public ArticleSelection pubDateAfterEq(Date value) {
        addGreaterThanOrEquals(ArticleColumns.PUB_DATE, value);
        return this;
    }

    public ArticleSelection pubDateBefore(Date value) {
        addLessThan(ArticleColumns.PUB_DATE, value);
        return this;
    }

    public ArticleSelection pubDateBeforeEq(Date value) {
        addLessThanOrEquals(ArticleColumns.PUB_DATE, value);
        return this;
    }

    public ArticleSelection text(String... value) {
        addEquals(ArticleColumns.TEXT, value);
        return this;
    }

    public ArticleSelection textNot(String... value) {
        addNotEquals(ArticleColumns.TEXT, value);
        return this;
    }

    public ArticleSelection textLike(String... value) {
        addLike(ArticleColumns.TEXT, value);
        return this;
    }

    public ArticleSelection textContains(String... value) {
        addContains(ArticleColumns.TEXT, value);
        return this;
    }

    public ArticleSelection textStartsWith(String... value) {
        addStartsWith(ArticleColumns.TEXT, value);
        return this;
    }

    public ArticleSelection textEndsWith(String... value) {
        addEndsWith(ArticleColumns.TEXT, value);
        return this;
    }

    public ArticleSelection image(String... value) {
        addEquals(ArticleColumns.IMAGE, value);
        return this;
    }

    public ArticleSelection imageNot(String... value) {
        addNotEquals(ArticleColumns.IMAGE, value);
        return this;
    }

    public ArticleSelection imageLike(String... value) {
        addLike(ArticleColumns.IMAGE, value);
        return this;
    }

    public ArticleSelection imageContains(String... value) {
        addContains(ArticleColumns.IMAGE, value);
        return this;
    }

    public ArticleSelection imageStartsWith(String... value) {
        addStartsWith(ArticleColumns.IMAGE, value);
        return this;
    }

    public ArticleSelection imageEndsWith(String... value) {
        addEndsWith(ArticleColumns.IMAGE, value);
        return this;
    }

    public ArticleSelection categoryId(String... value) {
        addEquals(ArticleColumns.CATEGORY_ID, value);
        return this;
    }

    public ArticleSelection categoryIdNot(String... value) {
        addNotEquals(ArticleColumns.CATEGORY_ID, value);
        return this;
    }

    public ArticleSelection categoryIdLike(String... value) {
        addLike(ArticleColumns.CATEGORY_ID, value);
        return this;
    }

    public ArticleSelection categoryIdContains(String... value) {
        addContains(ArticleColumns.CATEGORY_ID, value);
        return this;
    }

    public ArticleSelection categoryIdStartsWith(String... value) {
        addStartsWith(ArticleColumns.CATEGORY_ID, value);
        return this;
    }

    public ArticleSelection categoryIdEndsWith(String... value) {
        addEndsWith(ArticleColumns.CATEGORY_ID, value);
        return this;
    }

    public ArticleSelection newsSourceId(String... value) {
        addEquals(ArticleColumns.NEWS_SOURCE_ID, value);
        return this;
    }

    public ArticleSelection newsSourceIdNot(String... value) {
        addNotEquals(ArticleColumns.NEWS_SOURCE_ID, value);
        return this;
    }

    public ArticleSelection newsSourceIdLike(String... value) {
        addLike(ArticleColumns.NEWS_SOURCE_ID, value);
        return this;
    }

    public ArticleSelection newsSourceIdContains(String... value) {
        addContains(ArticleColumns.NEWS_SOURCE_ID, value);
        return this;
    }

    public ArticleSelection newsSourceIdStartsWith(String... value) {
        addStartsWith(ArticleColumns.NEWS_SOURCE_ID, value);
        return this;
    }

    public ArticleSelection newsSourceIdEndsWith(String... value) {
        addEndsWith(ArticleColumns.NEWS_SOURCE_ID, value);
        return this;
    }
}
