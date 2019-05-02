package com.app.finlit.fcm;

import android.util.Log;

import com.app.finlit.utils.SharedPreferenceHelper;
import com.app.finlit.utils.notifications.NotificationHelper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class NotificationService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.e("NotificationService" , "received");

        if (remoteMessage == null)
            return;

        SharedPreferenceHelper preferenceHelper = SharedPreferenceHelper.getInstance();
        if (!preferenceHelper.getNotification()){
            return;
        }

        new NotificationHelper(this).parseMessage(remoteMessage);
    }
}