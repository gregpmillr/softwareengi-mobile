package com.softengi.mobcomp.softwareengi_mobile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Used to broadcast notifcations for the user's device.
 * from https://stackoverflow.com/questions/34517520/how-to-give-notifications-on-android-on-specific-time.
 */
public class DailyNotificationReceiver extends BroadcastReceiver {

    /**
     * Default empty constructor
     */
    public DailyNotificationReceiver() {}

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intenty = new Intent(context, DailyNotificationIntentService.class);
        context.startService(intenty);
    }
}
