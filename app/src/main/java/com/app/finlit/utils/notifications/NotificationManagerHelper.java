package com.app.finlit.utils.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.app.finlit.R;
import com.app.finlit.ui.notification.NotificationActivity;

public class NotificationManagerHelper {
    private final static int NOTIFICATION_ID = NotificationManagerHelper.class.hashCode();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void sendNotificationEvent(Context context, NotificationEvent notificationEvent) {
//        Intent intent = SystemUtils.getPreviousIntent(context);
//        if (intent == null) {
        Intent intent = new Intent(context, NotificationActivity.class);
//        }
        sendNotificationEvent(context, intent, notificationEvent);
    }

    /**
     * This function is used to create notification
     * */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void sendNotificationEvent(Context context, Intent intent, NotificationEvent notificationEvent) {
        int notifyID = 1;
        String CHANNEL_ID = "Finlit";// The id of the channel.
        CharSequence name = "Finlit";// The user-visible name of the channel.

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setColor(context.getResources().getColor(R.color.white))
                .setSmallIcon(getNotificationIcon())
                .setContentTitle(notificationEvent.getTitle())
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationEvent.getSubject()))
                .setContentText(notificationEvent.getBody())
                .setSound(uri)
                .setContentIntent(contentIntent)
                .setPriority(Notification.PRIORITY_HIGH)
                .setChannelId(CHANNEL_ID);

        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults = Notification.DEFAULT_ALL;
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    private static int getNotificationIcon() {
        boolean whiteIcon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
        // TODO need to add other icon
        return whiteIcon ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;
    }

    public static void clearNotificationEvent(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
    }
}
