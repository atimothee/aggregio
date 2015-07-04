package io.aggreg.app.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import io.aggreg.app.R;
import io.aggreg.app.provider.AggregioProvider;
import io.aggreg.app.sync.ArticleDeleteService;

/**
 * Created by Timo on 4/6/15.
 */
public class GeneralUtils {
    private Context mContext;
    private String accountType;
    private String accountName;
    private static final String LOG_TAG = GeneralUtils.class.getSimpleName();


    public GeneralUtils(Context mContext) {
        this.mContext = mContext;
        this.accountName = mContext.getString(R.string.account_name);
        this.accountType = mContext.getString(R.string.account_type);
    }

    public Account getSyncAccount() {
        Account newAccount = new Account(
                accountName, accountType);
        AccountManager accountManager =
                (AccountManager) mContext.getSystemService(
                        mContext.ACCOUNT_SERVICE);
        Log.d(LOG_TAG, "account is " + newAccount.toString());

        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            return newAccount;
        } else {
            Log.e(LOG_TAG, "Account exists or some other error occured");
        }
        return newAccount;

    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }



    public void SyncRefreshArticles() {

        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        settingsBundle.putString(References.ARG_KEY_SYNC_TYPE, References.SYNC_TYPE_ARTICLE_REFRESH);
        Account account = getSyncAccount();
        ContentResolver.requestSync(account, AggregioProvider.AUTHORITY, settingsBundle);
    }

    public void deleteArticles(){
        Intent mServiceIntent = new Intent(mContext, ArticleDeleteService.class);
        mContext.startService(mServiceIntent);
    }



}
