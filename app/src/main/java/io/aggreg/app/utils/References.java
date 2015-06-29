package io.aggreg.app.utils;

import io.aggreg.app.BuildConfig;

public class References {
    public static final int PUBLISHER_LOADER = 0;
    public static final int ARTICLES_LOADER = 1;
    public static final int ARTICLE_DETAIL_LOADER = 2;
    public static final int ARTICLE_RELATED_LOADER = 3;
    public static final int BOOKMARKS_LOADER = 4;

    public static final String KEY_TOGGLE_GRID = "toggle_grid";
    public static final String KEY_PREFERENCES = "preferences";
    public static final String KEY_LAST_SYNC = "last_sync";
    public static final String ARG_IS_FIRST_TIME = "is_first_time";
    public static final String KEY_INTRO_SHOWN = "into_shown";
    public static final String FIRST_SYNC_COMPLETE = "first_sync_complete";
//    public static final String ARG_KEY_NOTIFICATION_TITLE = "title";
//    public static final String ARG_GCM_ID_KEY = "id";
//    public static final String GCM_KEY_TYPE = "type";
//    public static final String GCM_TYPE_TICKLE = "tickle";
//    public static final String GCM_TYPE_NOTIFICATION = "notification";
    public static final String SYNC_TYPE_GCM_REGISTER_DEVICE = "register_device";

    public static final String ARG_KEY_CATEGORY_ID = "category_id";
    public static final String ARG_KEY_PUBLISHER_ID = "publisher_id";
    public static final String ARG_KEY_ARTICLE_ID = "article_id";
    public static final String ARG_KEY_ARTICLE_LINK = "article_link";
    public static final String ARG_KEY_SYNC_TYPE = "sync_type";
    public static final String SYNC_TYPE_ARTICLE_REFRESH = "sync_type_article_refresh";
    public static final String SYNC_TYPE_CATEGORY = "sync_type_category";
    public static final String SYNC_TYPE_PUBLISHER = "sync_type_publisher";
    public static final String SYNC_TYPE_FIRST_TIME = "sync_type_first_time";
    public static final String ARG_KEY_PUBLISHER_ACTIVITY_TYPE = "activity_type";
    public static final String ACTIVITY_TYPE_SETUP_PUBLISHERS = "activity_type_setup_publishers";
    public static final String ACTIVITY_TYPE_MANAGE_PUBLISHERS = "activity_type_manage_publishers";

//    public final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
//    public static final String PROPERTY_REG_ID = "registration_id";
//    public static final String PROPERTY_APP_VERSION = "appVersion";

    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";
}
