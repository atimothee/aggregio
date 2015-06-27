package io.aggreg.app.ui;

import android.accounts.Account;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.SyncInfo;
import android.content.SyncStatusObserver;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.aggreg.app.provider.AggregioProvider;

/**
 * Created by Timo on 6/27/15.
 */
public abstract class SyncActivity extends AppCompatActivity{
    private static final String CONTENT_AUTHORITY = AggregioProvider.AUTHORITY;
    private Object syncHandle;
    private SyncStatusObserver observer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        observer = new SyncStatusObserver()
        {
            @Override
            public void onStatusChanged(int which)
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Account account = getAccount();
                        boolean isSynchronizing =
                                isSyncActive(account, CONTENT_AUTHORITY);
                        updateState(isSynchronizing);
                    }
                });
            }
        };
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // Refresh synchronization status
        observer.onStatusChanged(0);

        // Watch for synchronization status changes
        final int mask = ContentResolver.SYNC_OBSERVER_TYPE_PENDING |
                ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE;
        syncHandle = ContentResolver.addStatusChangeListener(mask, observer);
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        // Remove our synchronization listener if registered
        if (syncHandle != null)
        {
            ContentResolver.removeStatusChangeListener(syncHandle);
            syncHandle = null;
        }
    }

    private static boolean isSyncActive(Account account, String authority)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            return isSyncActiveHoneycomb(account, authority);
        } else
        {
            SyncInfo currentSync = ContentResolver.getCurrentSync();
            return currentSync != null && currentSync.account.equals(account)
                    && currentSync.authority.equals(authority);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static boolean isSyncActiveHoneycomb(Account account,
                                                 String authority)
    {
        for(SyncInfo syncInfo : ContentResolver.getCurrentSyncs())
        {
            if(syncInfo.account.equals(account) &&
                    syncInfo.authority.equals(authority))
            {
                return true;
            }
        }
        return false;
    }

    protected abstract Account getAccount();
    protected abstract void updateState(boolean isSynchronizing);
}
