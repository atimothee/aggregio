//package io.aggreg.app.ui;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.PersistableBundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//
//import com.google.android.gms.appinvite.AppInviteInvitation;
//
///**
// * Created by Timo on 6/11/15.
// */
//public class InvitesActivity extends AppCompatActivity{
//    private static String TAG = InvitesActivity.class.getSimpleName();
//
//    @Override
//    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState, persistentState);
//    }
//
//
//    private void onInviteClicked() {
////        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
////                .setMessage(getString(R.string.invitation_message))
////                .setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
////                .build();
//        //startActivityForResult(intent, REQUEST_INVITE);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);
//
////        if (requestCode == REQUEST_INVITE) {
////            if (resultCode == RESULT_OK) {
////                // Check how many invitations were sent and show message to the user
////                // The ids array contains the unique invitation ids for each invitation sent
////                // (one for each contact select by the user). You can use these for analytics
////                // as the ID will be consistent on the sending and receiving devices.
////                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
////                showMessage(getString(R.string.sent_invitations_fmt, ids.length));
////            } else {
////                // Sending failed or it was canceled, show failure message to the user
////                showMessage(getString(R.string.send_failed));
////            }
////        }
//    }
//}
