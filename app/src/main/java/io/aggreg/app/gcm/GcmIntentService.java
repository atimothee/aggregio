//package io.aggreg.app.gcm;
//
//import android.app.IntentService;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.ContentResolver;
//import android.content.Context;
//import android.content.Intent;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.support.v4.app.NotificationCompat;
//import android.support.v4.app.TaskStackBuilder;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.google.android.gms.gcm.GoogleCloudMessaging;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import io.aggreg.app.R;
//import io.aggreg.app.provider.AggregioProvider;
//import io.aggreg.app.ui.ArticleDetailActivity;
//import io.aggreg.app.utils.AccountUtils;
//import io.aggreg.app.utils.References;
//
///**
// * Created by Timo on 12/22/14.
// */
//public class GcmIntentService extends IntentService {
//
//    public static final int NOTIFICATION_ID = 1;
//    private static final String TAG = GcmIntentService.class.getSimpleName();
//
//    private NotificationManager mNotificationManager;
//
//
//    public GcmIntentService() {
//        super("GcmIntentService");
//    }
//
//    @Override
//    protected void onHandleIntent(Intent intent) {
//        Bundle extras = intent.getExtras();
//        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
//        String messageType = gcm.getMessageType(intent);
//
//        if (extras != null && !extras.isEmpty()) {
//
//            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
//                Logger.getLogger("GCM_RECEIVED").log(Level.INFO, extras.toString());
//
//                Log.d(TAG, "Request sync called via GCM");
//                if(extras.containsKey(References.GCM_KEY_TYPE) && extras.getString(References.GCM_KEY_TYPE).equalsIgnoreCase(References.GCM_TYPE_NOTIFICATION)){
//                    extras.putLong(References.ARG_GCM_ID_KEY, Long.valueOf(extras.getString(References.ARG_GCM_ID_KEY)));
//                    sendNotification(extras);
//                    Log.d(TAG, "send notification called");
//
//                }else if(extras.containsKey(References.GCM_KEY_TYPE) && extras.getString(References.GCM_KEY_TYPE).equalsIgnoreCase(References.GCM_TYPE_TICKLE)){
//                    extras.putLong(References.ARG_GCM_ID_KEY, Long.valueOf(extras.getString(References.ARG_GCM_ID_KEY)));
//                    ContentResolver.requestSync(new AccountUtils(getApplicationContext()).getSyncAccount(), AggregioProvider.AUTHORITY, extras);
//                }
//            }
//        }
//        GcmBroadcastReceiver.completeWakefulIntent(intent);
//    }
//
//    private void sendNotification(Bundle extras) {
//        mNotificationManager = (NotificationManager)
//                this.getSystemService(Context.NOTIFICATION_SERVICE);
//        Intent resultIntent = new Intent(getApplicationContext(), ArticleDetailActivity.class);
//        resultIntent.putExtra(References.ARG_KEY_ARTICLE_LINK, extras.getLong(References.ARG_KEY_ARTICLE_LINK));
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(getApplicationContext())
//                        .setSmallIcon(R.drawable.ic_launcher)
//                        .setContentTitle(extras.getString(References.ARG_KEY_NOTIFICATION_TITLE))
//                        .setStyle(new NotificationCompat.BigTextStyle()
//                                .bigText(extras.getString(References.ARG_KEY_NOTIFICATION_TITLE)))
//                        .setAutoCancel(true)
//                        .setSound(uri)
//                        .setContentText(extras.getString(References.ARG_KEY_NOTIFICATION_TITLE));
//
//        mBuilder.setContentIntent(resultPendingIntent);
//        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
//    }
//
//
//    protected void showToast(final String message) {
//        new Handler(Looper.getMainLooper()).post(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//}
