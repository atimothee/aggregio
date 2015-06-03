package io.aggreg.app.provider.article;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import io.aggreg.app.provider.base.AbstractSelection;
import io.aggreg.app.provider.category.*;
import io.aggreg.app.provider.publisher.*;

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

    public ArticleSelection publisherId(long... value) {
        addEquals(ArticleColumns.PUBLISHER_ID, toObjectArray(value));
        return this;
    }

    public ArticleSelection publisherIdNot(long... value) {
        addNotEquals(ArticleColumns.PUBLISHER_ID, toObjectArray(value));
        return this;
    }

    public ArticleSelection publisherIdGt(long value) {
        addGreaterThan(ArticleColumns.PUBLISHER_ID, value);
        return this;
    }

    public ArticleSelection publisherIdGtEq(long value) {
        addGreaterThanOrEquals(ArticleColumns.PUBLISHER_ID, value);
        return this;
    }

    public ArticleSelection publisherIdLt(long value) {
        addLessThan(ArticleColumns.PUBLISHER_ID, value);
        return this;
    }

    public ArticleSelection publisherIdLtEq(long value) {
        addLessThanOrEquals(ArticleColumns.PUBLISHER_ID, value);
        return this;
    }

    public ArticleSelection publisherImageUrl(String... value) {
        addEquals(PublisherColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleSelection publisherImageUrlNot(String... value) {
        addNotEquals(PublisherColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleSelection publisherImageUrlLike(String... value) {
        addLike(PublisherColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleSelection publisherImageUrlContains(String... value) {
        addContains(PublisherColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleSelection publisherImageUrlStartsWith(String... value) {
        addStartsWith(PublisherColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleSelection publisherImageUrlEndsWith(String... value) {
        addEndsWith(PublisherColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleSelection publisherFollowing(Boolean value) {
        addEquals(PublisherColumns.FOLLOWING, toObjectArray(value));
        return this;
    }

    public ArticleSelection publisherWebsite(String... value) {
        addEquals(PublisherColumns.WEBSITE, value);
        return this;
    }

    public ArticleSelection publisherWebsiteNot(String... value) {
        addNotEquals(PublisherColumns.WEBSITE, value);
        return this;
    }

    public ArticleSelection publisherWebsiteLike(String... value) {
        addLike(PublisherColumns.WEBSITE, value);
        return this;
    }

    public ArticleSelection publisherWebsiteContains(String... value) {
        addContains(PublisherColumns.WEBSITE, value);
        return this;
    }

    public ArticleSelection publisherWebsiteStartsWith(String... value) {
        addStartsWith(PublisherColumns.WEBSITE, value);
        return this;
    }

    public ArticleSelection publisherWebsiteEndsWith(String... value) {
        addEndsWith(PublisherColumns.WEBSITE, value);
        return this;
    }

    public ArticleSelection publisherName(String... value) {
        addEquals(PublisherColumns.NAME, value);
        return this;
    }

    public ArticleSelection publisherNameNot(String... value) {
        addNotEquals(PublisherColumns.NAME, value);
        return this;
    }

    public ArticleSelection publisherNameLike(String... value) {
        addLike(PublisherColumns.NAME, value);
        return this;
    }

    public ArticleSelection publisherNameContains(String... value) {
        addContains(PublisherColumns.NAME, value);
        return this;
    }

    public ArticleSelection publisherNameStartsWith(String... value) {
        addStartsWith(PublisherColumns.NAME, value);
        return this;
    }

    public ArticleSelection publisherNameEndsWith(String... value) {
        addEndsWith(PublisherColumns.NAME, value);
        return this;
    }

    public ArticleSelection publisherCountry(String... value) {
        addEquals(PublisherColumns.COUNTRY, value);
        return this;
    }

    public ArticleSelection publisherCountryNot(String... value) {
        addNotEquals(PublisherColumns.COUNTRY, value);
        return this;
    }

    public ArticleSelection publisherCountryLike(String... value) {
        addLike(PublisherColumns.COUNTRY, value);
        return this;
    }

    public ArticleSelection publisherCountryContains(String... value) {
        addContains(PublisherColumns.COUNTRY, value);
        return this;
    }

    public ArticleSelection publisherCountryStartsWith(String... value) {
        addStartsWith(PublisherColumns.COUNTRY, value);
        return this;
    }

    public ArticleSelection publisherCountryEndsWith(String... value) {
        addEndsWith(PublisherColumns.COUNTRY, value);
        return this;
    }
}
