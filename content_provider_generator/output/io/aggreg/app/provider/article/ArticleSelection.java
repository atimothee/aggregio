package io.aggreg.app.provider.article;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import io.aggreg.app.provider.base.AbstractSelection;
import io.aggreg.app.provider.category.*;
import io.aggreg.app.provider.newssource.*;

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

    public ArticleSelection categoryId(long... value) {
        addEquals(ArticleColumns.CATEGORY_ID, toObjectArray(value));
        return this;
    }

    public ArticleSelection categoryIdNot(long... value) {
        addNotEquals(ArticleColumns.CATEGORY_ID, toObjectArray(value));
        return this;
    }

    public ArticleSelection categoryIdGt(long value) {
        addGreaterThan(ArticleColumns.CATEGORY_ID, value);
        return this;
    }

    public ArticleSelection categoryIdGtEq(long value) {
        addGreaterThanOrEquals(ArticleColumns.CATEGORY_ID, value);
        return this;
    }

    public ArticleSelection categoryIdLt(long value) {
        addLessThan(ArticleColumns.CATEGORY_ID, value);
        return this;
    }

    public ArticleSelection categoryIdLtEq(long value) {
        addLessThanOrEquals(ArticleColumns.CATEGORY_ID, value);
        return this;
    }

    public ArticleSelection categoryName(String... value) {
        addEquals(CategoryColumns.NAME, value);
        return this;
    }

    public ArticleSelection categoryNameNot(String... value) {
        addNotEquals(CategoryColumns.NAME, value);
        return this;
    }

    public ArticleSelection categoryNameLike(String... value) {
        addLike(CategoryColumns.NAME, value);
        return this;
    }

    public ArticleSelection categoryNameContains(String... value) {
        addContains(CategoryColumns.NAME, value);
        return this;
    }

    public ArticleSelection categoryNameStartsWith(String... value) {
        addStartsWith(CategoryColumns.NAME, value);
        return this;
    }

    public ArticleSelection categoryNameEndsWith(String... value) {
        addEndsWith(CategoryColumns.NAME, value);
        return this;
    }

    public ArticleSelection categoryImageUrl(String... value) {
        addEquals(CategoryColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleSelection categoryImageUrlNot(String... value) {
        addNotEquals(CategoryColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleSelection categoryImageUrlLike(String... value) {
        addLike(CategoryColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleSelection categoryImageUrlContains(String... value) {
        addContains(CategoryColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleSelection categoryImageUrlStartsWith(String... value) {
        addStartsWith(CategoryColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleSelection categoryImageUrlEndsWith(String... value) {
        addEndsWith(CategoryColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleSelection newsSourceId(long... value) {
        addEquals(ArticleColumns.NEWS_SOURCE_ID, toObjectArray(value));
        return this;
    }

    public ArticleSelection newsSourceIdNot(long... value) {
        addNotEquals(ArticleColumns.NEWS_SOURCE_ID, toObjectArray(value));
        return this;
    }

    public ArticleSelection newsSourceIdGt(long value) {
        addGreaterThan(ArticleColumns.NEWS_SOURCE_ID, value);
        return this;
    }

    public ArticleSelection newsSourceIdGtEq(long value) {
        addGreaterThanOrEquals(ArticleColumns.NEWS_SOURCE_ID, value);
        return this;
    }

    public ArticleSelection newsSourceIdLt(long value) {
        addLessThan(ArticleColumns.NEWS_SOURCE_ID, value);
        return this;
    }

    public ArticleSelection newsSourceIdLtEq(long value) {
        addLessThanOrEquals(ArticleColumns.NEWS_SOURCE_ID, value);
        return this;
    }

    public ArticleSelection newsSourceImageUrl(String... value) {
        addEquals(NewsSourceColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleSelection newsSourceImageUrlNot(String... value) {
        addNotEquals(NewsSourceColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleSelection newsSourceImageUrlLike(String... value) {
        addLike(NewsSourceColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleSelection newsSourceImageUrlContains(String... value) {
        addContains(NewsSourceColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleSelection newsSourceImageUrlStartsWith(String... value) {
        addStartsWith(NewsSourceColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleSelection newsSourceImageUrlEndsWith(String... value) {
        addEndsWith(NewsSourceColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleSelection newsSourceWebsite(String... value) {
        addEquals(NewsSourceColumns.WEBSITE, value);
        return this;
    }

    public ArticleSelection newsSourceWebsiteNot(String... value) {
        addNotEquals(NewsSourceColumns.WEBSITE, value);
        return this;
    }

    public ArticleSelection newsSourceWebsiteLike(String... value) {
        addLike(NewsSourceColumns.WEBSITE, value);
        return this;
    }

    public ArticleSelection newsSourceWebsiteContains(String... value) {
        addContains(NewsSourceColumns.WEBSITE, value);
        return this;
    }

    public ArticleSelection newsSourceWebsiteStartsWith(String... value) {
        addStartsWith(NewsSourceColumns.WEBSITE, value);
        return this;
    }

    public ArticleSelection newsSourceWebsiteEndsWith(String... value) {
        addEndsWith(NewsSourceColumns.WEBSITE, value);
        return this;
    }

    public ArticleSelection newsSourceName(String... value) {
        addEquals(NewsSourceColumns.NAME, value);
        return this;
    }

    public ArticleSelection newsSourceNameNot(String... value) {
        addNotEquals(NewsSourceColumns.NAME, value);
        return this;
    }

    public ArticleSelection newsSourceNameLike(String... value) {
        addLike(NewsSourceColumns.NAME, value);
        return this;
    }

    public ArticleSelection newsSourceNameContains(String... value) {
        addContains(NewsSourceColumns.NAME, value);
        return this;
    }

    public ArticleSelection newsSourceNameStartsWith(String... value) {
        addStartsWith(NewsSourceColumns.NAME, value);
        return this;
    }

    public ArticleSelection newsSourceNameEndsWith(String... value) {
        addEndsWith(NewsSourceColumns.NAME, value);
        return this;
    }

    public ArticleSelection newsSourceCountry(String... value) {
        addEquals(NewsSourceColumns.COUNTRY, value);
        return this;
    }

    public ArticleSelection newsSourceCountryNot(String... value) {
        addNotEquals(NewsSourceColumns.COUNTRY, value);
        return this;
    }

    public ArticleSelection newsSourceCountryLike(String... value) {
        addLike(NewsSourceColumns.COUNTRY, value);
        return this;
    }

    public ArticleSelection newsSourceCountryContains(String... value) {
        addContains(NewsSourceColumns.COUNTRY, value);
        return this;
    }

    public ArticleSelection newsSourceCountryStartsWith(String... value) {
        addStartsWith(NewsSourceColumns.COUNTRY, value);
        return this;
    }

    public ArticleSelection newsSourceCountryEndsWith(String... value) {
        addEndsWith(NewsSourceColumns.COUNTRY, value);
        return this;
    }
}
