package io.aggreg.app.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.sql.Ref;

import io.aggreg.app.R;
import io.aggreg.app.ui.MainActivity;

/**
 * Created by Timo on 4/6/15.
 */
public class AccountUtils {
    private Context mContext;
    private String accountType;
    private String accountName;
    private static final String LOG_TAG = AccountUtils.class.getSimpleName();


    public AccountUtils(Context mContext){
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

    public static String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(References.PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(LOG_TAG, "Registration not found.");
            return registrationId;
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(References.PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {

            Log.i(LOG_TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }



    public static void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(LOG_TAG, "Saving regId "+regId+" on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(References.PROPERTY_REG_ID, regId);
        editor.putInt(References.PROPERTY_APP_VERSION, appVersion);
        editor.commit();
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

    public static SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return context.getSharedPreferences(References.KEY_PREFERENCES,
                Context.MODE_PRIVATE);
    }

}
