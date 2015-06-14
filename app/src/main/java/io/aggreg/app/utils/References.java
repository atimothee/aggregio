package io.aggreg.app.utils;

import io.aggreg.app.BuildConfig;

public class References {
    public static final int PUBLISHER_LOADER = 0;
    public static final int ARTICLES_LOADER = 1;
    public static final int ARTICLE_DETAIL_LOADER = 5;
    public static final int BOOKMARKS_LOADER = 6;

    public static final String KEY_TOGGLE_GRID = "toggle_grid";
    public static final String KEY_PREFERENCES = "preferences";
    public static final String KEY_APP_SETUP = "first_time";
    public static final String KEY_LAST_SYNC = "last_sync";
    public static final String ARG_IS_FIRST_TIME = "is_first_time";
    public static final String KEY_INTRO_SHOWN = "into_shown";

    public static String ARG_KEY_CATEGORY_ID = "category_id";
    public static String ARG_KEY_PUBLISHER_ID = "publisher_id";
    public static final String ARG_KEY_ARTICLE_LINK = "article_id";
    public static final String ARG_KEY_SYNC_TYPE = "sync_type";
    public static final String SYNC_TYPE_ARTICLE_REFRESH = "sync_type_article_refresh";
    public static final String SYNC_TYPE_CATEGORY = "sync_type_category";
    public static final String SYNC_TYPE_PUBLISHER = "sync_type_publisher";
    public static final String SYNC_TYPE_FIRST_TIME = "sync_type_first_time";
    public static final String ARG_KEY_PUBLISHER_ACTIVITY_TYPE = "activity_type";
    public static final String ACTIVITY_TYPE_SETUP_PUBLISHERS = "activity_type_setup_publishers";
    public static final String ACTIVITY_TYPE_MANAGE_PUBLISHERS = "activity_type_manage_publishers";

    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";
}
