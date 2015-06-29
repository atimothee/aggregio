//package io.aggreg.app.utils;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.util.Log;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesUtil;
//
//import java.io.IOException;
//
//import io.aggreg.app.R;
//
///**
// * Created by Timo on 6/29/15.
// */
//public class GCMUtils {
//
//    private boolean checkPlayServices(Activity activity) {
//        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
//        if (resultCode != ConnectionResult.SUCCESS) {
//            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
//                GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
//                        References.PLAY_SERVICES_RESOLUTION_REQUEST).show();
//            } else {
//                Log.i(LOG_TAG, "This device is not supported.");
//                activity.finish();
//            }
//            return false;
//        }
//        return true;
//    }
//
//
//    private void GCM() {
//        String msg = "";
//
//
//        try {
//            InstanceID instanceID = InstanceID.getInstance(mContext);
//            String regId = instanceID.getToken(mContext.getString(R.string.gcm_defaultSenderId),
//                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
//            msg = "Device registered, registration ID=" + regId;
//            sendRegistrationId(regId);
//            storeRegistrationId(mContext, regId);
//
//
//        } catch (IOException ex) {
//            msg = "Error :" + ex.getMessage();
//        }
//    }
//
//    private void sendRegistrationId(String regId) {
//
//    }
//
//    public static SharedPreferences getGCMPreferences(Context context) {
//        // This sample app persists the registration ID in shared preferences, but
//        // how you store the regID in your app is up to you.
//        return context.getSharedPreferences(References.KEY_PREFERENCES,
//                Context.MODE_PRIVATE);
//    }
//
//    public static String getRegistrationId(Context context) {
//        final SharedPreferences prefs = getGCMPreferences(context);
//        String registrationId = prefs.getString(References.PROPERTY_REG_ID, "");
//        if (registrationId.isEmpty()) {
//            Log.i(LOG_TAG, "Registration not found.");
//            return registrationId;
//        }
//        // Check if app was updated; if so, it must clear the registration ID
//        // since the existing regID is not guaranteed to work with the new
//        // app version.
//        int registeredVersion = prefs.getInt(References.PROPERTY_APP_VERSION, Integer.MIN_VALUE);
//        int currentVersion = getAppVersion(context);
//        if (registeredVersion != currentVersion) {
//
//            Log.i(LOG_TAG, "App version changed.");
//            return "";
//        }
//        return registrationId;
//    }
//
//
//    public static void storeRegistrationId(Context context, String regId) {
//        final SharedPreferences prefs = getGCMPreferences(context);
//        int appVersion = getAppVersion(context);
//        Log.i(LOG_TAG, "Saving regId " + regId + " on app version " + appVersion);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(References.PROPERTY_REG_ID, regId);
//        editor.putInt(References.PROPERTY_APP_VERSION, appVersion);
//        editor.commit();
//    }
//}
