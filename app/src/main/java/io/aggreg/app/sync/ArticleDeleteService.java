package io.aggreg.app.sync;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Date;

import io.aggreg.app.R;
import io.aggreg.app.provider.article.ArticleSelection;
import io.aggreg.app.utils.References;

/**
 * Created by Timo on 6/29/15.
 */
public class ArticleDeleteService extends IntentService{
    private String LOG_TAG = ArticleDeleteService.class.getSimpleName();

    public ArticleDeleteService(String name) {
        super(name);
    }

    public ArticleDeleteService(){
        super(ArticleDeleteService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String deleteDaysString = prefs.getString(getApplicationContext().getString(R.string.pref_key_delete_stale_articles), "14");
        //Log.d(LOG_TAG, "delete days")
        Integer deleteDays = Integer.valueOf(deleteDaysString);
        if(deleteDays != -1) {
            ArticleSelection articleSelection = new ArticleSelection();
            long DAY_IN_MS = 1000 * 60 * 60 * 24;
            articleSelection.pubDateBeforeEq(new Date(System.currentTimeMillis() - (deleteDays * DAY_IN_MS)));
            articleSelection.delete(getContentResolver());
            //Log.d(LOG_TAG, "articles deleting");
        }

    }
}
