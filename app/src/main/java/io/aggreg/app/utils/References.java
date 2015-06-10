package io.aggreg.app.utils;

import io.aggreg.app.BuildConfig;

public class References {
    public static final int PUBLISHER_LOADER = 0;
    public static final int ARTICLES_LOADER = 1;
    public static final int ARTICLE_DETAIL_LOADER = 5;
    public static final int BOOKMARKS_LOADER = 6;

    public static final String KEY_TOGGLE_GRID = "toggle_grid";
    public static final String KEY_PREFERENCES = "preferences";

    public static String ARG_KEY_CATEGORY_ID = "category_id";
    public static final String ARG_KEY_ARTICLE_LINK = "article_id";
    public static final String ARG_KEY_SYNC_TYPE = "sync_type";
    public static final String SYNC_TYPE_ARTICLE = "sync_type_article";
    public static final String SYNC_TYPE_CATEGORY = "sync_type_category";
    public static final String SYNC_TYPE_PUBLISHER = "sync_type_publisher";

    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";
}
