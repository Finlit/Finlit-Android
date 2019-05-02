package com.app.finlit.fcm;

import android.util.Log;

import com.app.finlit.utils.SharedPreferenceHelper;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "onTokenRefresh: " + refreshedToken);
        // sending reg id to your server
        sendRegistrationToServer(refreshedToken);
        // store to sharedPreferences
        SharedPreferenceHelper.getInstance().saveFCMToken(refreshedToken);
    }
    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        Log.e(TAG, "fcmToken: " + token);
    }
}
