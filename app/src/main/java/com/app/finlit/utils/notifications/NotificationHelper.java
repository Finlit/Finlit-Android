package com.app.finlit.utils.notifications;

import android.content.Context;
import android.util.Log;

import com.app.finlit.utils.SystemUtils;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


public class NotificationHelper {
    private static final String TAG = NotificationHelper.class.getSimpleName();

    private Context context;

    public NotificationHelper(Context context) {
        this.context = context;
    }

    public void parseMessage(RemoteMessage remoteMessage) {

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            parseNotificationMessage(remoteMessage);
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            parseDataMessage(remoteMessage);
        }
    }

    /**
     * This function is used to parse notification message
     * */
    public void parseNotificationMessage(RemoteMessage remoteMessage) {

        NotificationEvent event = new NotificationEvent();
        event.setBody(remoteMessage.getNotification().getBody());
        event.setSubject(remoteMessage.getNotification().getBody());
        event.setTitle(remoteMessage.getNotification().getBody());

        if (!SystemUtils.isAppIsInBackground(context)) {
            // app is in foreground, broadcast the push message

            NotificationManagerHelper.sendNotificationEvent(context, event);
        }
    }

    /**
     * This function is used to parse Data message
     * */
    public void parseDataMessage(RemoteMessage remoteMessage) {

        Map<String, String> data = null;

        try {
            data = remoteMessage.getData();
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }

        if (data == null) {
            return;
        }

        try {
            NotificationEvent event = new NotificationEvent();

            String title = data.get("title");
            String message = data.get("content");

            event.setTitle(title);
            event.setSubject(message);
            event.setBody(message);

            if (!SystemUtils.isAppIsInBackground(context)) {
                // app is in foreground, broadcast the push message

                NotificationManagerHelper.sendNotificationEvent(context, event);
            } else {
                // app is in background, show the notification in notification tray

                NotificationManagerHelper.sendNotificationEvent(context, event);
            }

        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }


}
