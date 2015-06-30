package io.aggreg.app.sync;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Date;

import io.aggreg.app.R;
import io.aggreg.app.provider.article.ArticleSelection;
import io.aggreg.app.utils.References;

/**
 * Created by Timo on 6/29/15.
 */
public class ArticleDeleteService extends IntentService{

    public ArticleDeleteService(String name) {
        super(name);
    }

    public ArticleDeleteService(){
        super("ArticleDeleteService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ArticleSelection articleSelection = new ArticleSelection();
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        SharedPreferences prefs = getSharedPreferences(References.KEY_PREFERENCES, MODE_PRIVATE);
        int deleteDays = prefs.getInt(getString(R.string.pref_key_delete_stale_articles), 7);
        articleSelection.pubDateBeforeEq(new Date(System.currentTimeMillis() - (deleteDays * DAY_IN_MS)));
        articleSelection.or();
        articleSelection.publisherFollowing(false);
        articleSelection.delete(getContentResolver());

    }
}
