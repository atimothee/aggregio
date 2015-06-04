package io.aggreg.app.provider.articleimage;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import io.aggreg.app.provider.base.AbstractSelection;
import io.aggreg.app.provider.article.*;
import io.aggreg.app.provider.category.*;
import io.aggreg.app.provider.publisher.*;

/**
 * Selection for the {@code article_image} table.
 */
public class ArticleImageSelection extends AbstractSelection<ArticleImageSelection> {
    @Override
    protected Uri baseUri() {
        return ArticleImageColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code ArticleImageCursor} object, which is positioned before the first entry, or null.
     */
    public ArticleImageCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new ArticleImageCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public ArticleImageCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public ArticleImageCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public ArticleImageSelection id(long... value) {
        addEquals("article_image." + ArticleImageColumns._ID, toObjectArray(value));
        return this;
    }

    public ArticleImageSelection imageUrl(String... value) {
        addEquals(ArticleImageColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleImageSelection imageUrlNot(String... value) {
        addNotEquals(ArticleImageColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleImageSelection imageUrlLike(String... value) {
        addLike(ArticleImageColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleImageSelection imageUrlContains(String... value) {
        addContains(ArticleImageColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleImageSelection imageUrlStartsWith(String... value) {
        addStartsWith(ArticleImageColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleImageSelection imageUrlEndsWith(String... value) {
        addEndsWith(ArticleImageColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleImageSelection articleId(Long... value) {
        addEquals(ArticleImageColumns.ARTICLE_ID, value);
        return this;
    }

    public ArticleImageSelection articleIdNot(Long... value) {
        addNotEquals(ArticleImageColumns.ARTICLE_ID, value);
        return this;
    }

    public ArticleImageSelection articleIdGt(long value) {
        addGreaterThan(ArticleImageColumns.ARTICLE_ID, value);
        return this;
    }

    public ArticleImageSelection articleIdGtEq(long value) {
        addGreaterThanOrEquals(ArticleImageColumns.ARTICLE_ID, value);
        return this;
    }

    public ArticleImageSelection articleIdLt(long value) {
        addLessThan(ArticleImageColumns.ARTICLE_ID, value);
        return this;
    }

    public ArticleImageSelection articleIdLtEq(long value) {
        addLessThanOrEquals(ArticleImageColumns.ARTICLE_ID, value);
        return this;
    }

    public ArticleImageSelection articleTitle(String... value) {
        addEquals(ArticleColumns.TITLE, value);
        return this;
    }

    public ArticleImageSelection articleTitleNot(String... value) {
        addNotEquals(ArticleColumns.TITLE, value);
        return this;
    }

    public ArticleImageSelection articleTitleLike(String... value) {
        addLike(ArticleColumns.TITLE, value);
        return this;
    }

    public ArticleImageSelection articleTitleContains(String... value) {
        addContains(ArticleColumns.TITLE, value);
        return this;
    }

    public ArticleImageSelection articleTitleStartsWith(String... value) {
        addStartsWith(ArticleColumns.TITLE, value);
        return this;
    }

    public ArticleImageSelection articleTitleEndsWith(String... value) {
        addEndsWith(ArticleColumns.TITLE, value);
        return this;
    }

    public ArticleImageSelection articleLink(String... value) {
        addEquals(ArticleColumns.LINK, value);
        return this;
    }

    public ArticleImageSelection articleLinkNot(String... value) {
        addNotEquals(ArticleColumns.LINK, value);
        return this;
    }

    public ArticleImageSelection articleLinkLike(String... value) {
        addLike(ArticleColumns.LINK, value);
        return this;
    }

    public ArticleImageSelection articleLinkContains(String... value) {
        addContains(ArticleColumns.LINK, value);
        return this;
    }

    public ArticleImageSelection articleLinkStartsWith(String... value) {
        addStartsWith(ArticleColumns.LINK, value);
        return this;
    }

    public ArticleImageSelection articleLinkEndsWith(String... value) {
        addEndsWith(ArticleColumns.LINK, value);
        return this;
    }

    public ArticleImageSelection articleImage(String... value) {
        addEquals(ArticleColumns.IMAGE, value);
        return this;
    }

    public ArticleImageSelection articleImageNot(String... value) {
        addNotEquals(ArticleColumns.IMAGE, value);
        return this;
    }

    public ArticleImageSelection articleImageLike(String... value) {
        addLike(ArticleColumns.IMAGE, value);
        return this;
    }

    public ArticleImageSelection articleImageContains(String... value) {
        addContains(ArticleColumns.IMAGE, value);
        return this;
    }

    public ArticleImageSelection articleImageStartsWith(String... value) {
        addStartsWith(ArticleColumns.IMAGE, value);
        return this;
    }

    public ArticleImageSelection articleImageEndsWith(String... value) {
        addEndsWith(ArticleColumns.IMAGE, value);
        return this;
    }

    public ArticleImageSelection articlePubDate(Date... value) {
        addEquals(ArticleColumns.PUB_DATE, value);
        return this;
    }

    public ArticleImageSelection articlePubDateNot(Date... value) {
        addNotEquals(ArticleColumns.PUB_DATE, value);
        return this;
    }

    public ArticleImageSelection articlePubDate(Long... value) {
        addEquals(ArticleColumns.PUB_DATE, value);
        return this;
    }

    public ArticleImageSelection articlePubDateAfter(Date value) {
        addGreaterThan(ArticleColumns.PUB_DATE, value);
        return this;
    }

    public ArticleImageSelection articlePubDateAfterEq(Date value) {
        addGreaterThanOrEquals(ArticleColumns.PUB_DATE, value);
        return this;
    }

    public ArticleImageSelection articlePubDateBefore(Date value) {
        addLessThan(ArticleColumns.PUB_DATE, value);
        return this;
    }

    public ArticleImageSelection articlePubDateBeforeEq(Date value) {
        addLessThanOrEquals(ArticleColumns.PUB_DATE, value);
        return this;
    }

    public ArticleImageSelection articleText(String... value) {
        addEquals(ArticleColumns.TEXT, value);
        return this;
    }

    public ArticleImageSelection articleTextNot(String... value) {
        addNotEquals(ArticleColumns.TEXT, value);
        return this;
    }

    public ArticleImageSelection articleTextLike(String... value) {
        addLike(ArticleColumns.TEXT, value);
        return this;
    }

    public ArticleImageSelection articleTextContains(String... value) {
        addContains(ArticleColumns.TEXT, value);
        return this;
    }

    public ArticleImageSelection articleTextStartsWith(String... value) {
        addStartsWith(ArticleColumns.TEXT, value);
        return this;
    }

    public ArticleImageSelection articleTextEndsWith(String... value) {
        addEndsWith(ArticleColumns.TEXT, value);
        return this;
    }

    public ArticleImageSelection articleBookMarked(Boolean value) {
        addEquals(ArticleColumns.BOOK_MARKED, toObjectArray(value));
        return this;
    }

    public ArticleImageSelection articleCategoryId(Long... value) {
        addEquals(ArticleColumns.CATEGORY_ID, value);
        return this;
    }

    public ArticleImageSelection articleCategoryIdNot(Long... value) {
        addNotEquals(ArticleColumns.CATEGORY_ID, value);
        return this;
    }

    public ArticleImageSelection articleCategoryIdGt(long value) {
        addGreaterThan(ArticleColumns.CATEGORY_ID, value);
        return this;
    }

    public ArticleImageSelection articleCategoryIdGtEq(long value) {
        addGreaterThanOrEquals(ArticleColumns.CATEGORY_ID, value);
        return this;
    }

    public ArticleImageSelection articleCategoryIdLt(long value) {
        addLessThan(ArticleColumns.CATEGORY_ID, value);
        return this;
    }

    public ArticleImageSelection articleCategoryIdLtEq(long value) {
        addLessThanOrEquals(ArticleColumns.CATEGORY_ID, value);
        return this;
    }

    public ArticleImageSelection articleCategoryName(String... value) {
        addEquals(CategoryColumns.NAME, value);
        return this;
    }

    public ArticleImageSelection articleCategoryNameNot(String... value) {
        addNotEquals(CategoryColumns.NAME, value);
        return this;
    }

    public ArticleImageSelection articleCategoryNameLike(String... value) {
        addLike(CategoryColumns.NAME, value);
        return this;
    }

    public ArticleImageSelection articleCategoryNameContains(String... value) {
        addContains(CategoryColumns.NAME, value);
        return this;
    }

    public ArticleImageSelection articleCategoryNameStartsWith(String... value) {
        addStartsWith(CategoryColumns.NAME, value);
        return this;
    }

    public ArticleImageSelection articleCategoryNameEndsWith(String... value) {
        addEndsWith(CategoryColumns.NAME, value);
        return this;
    }

    public ArticleImageSelection articleCategoryImageUrl(String... value) {
        addEquals(CategoryColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleImageSelection articleCategoryImageUrlNot(String... value) {
        addNotEquals(CategoryColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleImageSelection articleCategoryImageUrlLike(String... value) {
        addLike(CategoryColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleImageSelection articleCategoryImageUrlContains(String... value) {
        addContains(CategoryColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleImageSelection articleCategoryImageUrlStartsWith(String... value) {
        addStartsWith(CategoryColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleImageSelection articleCategoryImageUrlEndsWith(String... value) {
        addEndsWith(CategoryColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleImageSelection articlePublisherId(long... value) {
        addEquals(ArticleColumns.PUBLISHER_ID, toObjectArray(value));
        return this;
    }

    public ArticleImageSelection articlePublisherIdNot(long... value) {
        addNotEquals(ArticleColumns.PUBLISHER_ID, toObjectArray(value));
        return this;
    }

    public ArticleImageSelection articlePublisherIdGt(long value) {
        addGreaterThan(ArticleColumns.PUBLISHER_ID, value);
        return this;
    }

    public ArticleImageSelection articlePublisherIdGtEq(long value) {
        addGreaterThanOrEquals(ArticleColumns.PUBLISHER_ID, value);
        return this;
    }

    public ArticleImageSelection articlePublisherIdLt(long value) {
        addLessThan(ArticleColumns.PUBLISHER_ID, value);
        return this;
    }

    public ArticleImageSelection articlePublisherIdLtEq(long value) {
        addLessThanOrEquals(ArticleColumns.PUBLISHER_ID, value);
        return this;
    }

    public ArticleImageSelection articlePublisherImageUrl(String... value) {
        addEquals(PublisherColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleImageSelection articlePublisherImageUrlNot(String... value) {
        addNotEquals(PublisherColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleImageSelection articlePublisherImageUrlLike(String... value) {
        addLike(PublisherColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleImageSelection articlePublisherImageUrlContains(String... value) {
        addContains(PublisherColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleImageSelection articlePublisherImageUrlStartsWith(String... value) {
        addStartsWith(PublisherColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleImageSelection articlePublisherImageUrlEndsWith(String... value) {
        addEndsWith(PublisherColumns.IMAGE_URL, value);
        return this;
    }

    public ArticleImageSelection articlePublisherFollowing(Boolean value) {
        addEquals(PublisherColumns.FOLLOWING, toObjectArray(value));
        return this;
    }

    public ArticleImageSelection articlePublisherWebsite(String... value) {
        addEquals(PublisherColumns.WEBSITE, value);
        return this;
    }

    public ArticleImageSelection articlePublisherWebsiteNot(String... value) {
        addNotEquals(PublisherColumns.WEBSITE, value);
        return this;
    }

    public ArticleImageSelection articlePublisherWebsiteLike(String... value) {
        addLike(PublisherColumns.WEBSITE, value);
        return this;
    }

    public ArticleImageSelection articlePublisherWebsiteContains(String... value) {
        addContains(PublisherColumns.WEBSITE, value);
        return this;
    }

    public ArticleImageSelection articlePublisherWebsiteStartsWith(String... value) {
        addStartsWith(PublisherColumns.WEBSITE, value);
        return this;
    }

    public ArticleImageSelection articlePublisherWebsiteEndsWith(String... value) {
        addEndsWith(PublisherColumns.WEBSITE, value);
        return this;
    }

    public ArticleImageSelection articlePublisherName(String... value) {
        addEquals(PublisherColumns.NAME, value);
        return this;
    }

    public ArticleImageSelection articlePublisherNameNot(String... value) {
        addNotEquals(PublisherColumns.NAME, value);
        return this;
    }

    public ArticleImageSelection articlePublisherNameLike(String... value) {
        addLike(PublisherColumns.NAME, value);
        return this;
    }

    public ArticleImageSelection articlePublisherNameContains(String... value) {
        addContains(PublisherColumns.NAME, value);
        return this;
    }

    public ArticleImageSelection articlePublisherNameStartsWith(String... value) {
        addStartsWith(PublisherColumns.NAME, value);
        return this;
    }

    public ArticleImageSelection articlePublisherNameEndsWith(String... value) {
        addEndsWith(PublisherColumns.NAME, value);
        return this;
    }

    public ArticleImageSelection articlePublisherCountry(String... value) {
        addEquals(PublisherColumns.COUNTRY, value);
        return this;
    }

    public ArticleImageSelection articlePublisherCountryNot(String... value) {
        addNotEquals(PublisherColumns.COUNTRY, value);
        return this;
    }

    public ArticleImageSelection articlePublisherCountryLike(String... value) {
        addLike(PublisherColumns.COUNTRY, value);
        return this;
    }

    public ArticleImageSelection articlePublisherCountryContains(String... value) {
        addContains(PublisherColumns.COUNTRY, value);
        return this;
    }

    public ArticleImageSelection articlePublisherCountryStartsWith(String... value) {
        addStartsWith(PublisherColumns.COUNTRY, value);
        return this;
    }

    public ArticleImageSelection articlePublisherCountryEndsWith(String... value) {
        addEndsWith(PublisherColumns.COUNTRY, value);
        return this;
    }
}
