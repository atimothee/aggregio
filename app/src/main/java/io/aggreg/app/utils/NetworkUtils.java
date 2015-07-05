package io.aggreg.app.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by Timo on 6/6/15.
 */
public class NetworkUtils {
    private Context mContext;
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    public NetworkUtils(Context context) {
        this.mContext = context;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public boolean isWIFIAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWifi.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isInternetAvailable() {
        Boolean isNetworkAvailable = isNetworkAvailable();
        if(isNetworkAvailable) {
            return isOnline();
        }else {
            Log.d(LOG_TAG, "network off");
            return false;

        }
    }

    private boolean isOnline() {

//        Runtime runtime = Runtime.getRuntime();
//        try {
//
//            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
//            int     exitValue = ipProcess.waitFor();
//            return (exitValue == 0);
//
//        } catch (IOException e)          { e.printStackTrace(); }
//        catch (InterruptedException e) { e.printStackTrace(); }
//
//        return false;
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name
            Log.d(LOG_TAG, "ip address "+ipAddr);

            if (ipAddr.equals("")) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
