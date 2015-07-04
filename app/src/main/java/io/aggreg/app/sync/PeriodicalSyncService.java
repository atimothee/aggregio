package io.aggreg.app.sync;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import io.aggreg.app.utils.GeneralUtils;

/**
 * Created by Timo on 7/2/15.
 */
public class PeriodicalSyncService extends IntentService{
    private static String LOG_TAG = PeriodicalSyncService.class.getSimpleName();
    public PeriodicalSyncService(String name) {
        super(name);
    }
    public PeriodicalSyncService() {
        super(PeriodicalSyncService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        new GeneralUtils(getApplicationContext()).SyncRefreshArticles();

    }
}
