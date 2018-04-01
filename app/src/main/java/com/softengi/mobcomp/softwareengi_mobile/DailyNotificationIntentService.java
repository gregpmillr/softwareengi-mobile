package com.softengi.mobcomp.softwareengi_mobile;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

/**
 * Used to broadcast a daily notification.
 * from https://stackoverflow.com/questions/34517520/how-to-give-notifications-on-android-on-specific-time.
 */
public class DailyNotificationIntentService extends IntentService {

    private static final int NOTIFICATION_ID = 3;

    /**
     * Constructor
     */
    public DailyNotificationIntentService() {
        super("DailyNotificationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Get moving!");
        builder.setContentText("Get on out there and moooove!!!");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        Intent notifyIntent = new Intent(this, LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //to be able to launch your activity from the notification
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);
    }
}
