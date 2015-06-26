package io.aggreg.app.gcm;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import io.aggreg.app.R;

/**
 * Created by Timo on 6/26/15.
 */
public class InstanceIDListenerService extends com.google.android.gms.iid.InstanceIDListenerService{


    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        //TODO: Invoke sync adapter
    }
}
