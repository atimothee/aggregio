package io.aggreg.app.sync;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Timo on 7/2/15.
 */
public class PeriodicalSyncService extends IntentService{
    public PeriodicalSyncService(String name) {
        super(name);
    }
    public PeriodicalSyncService() {
        super(PeriodicalSyncService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
