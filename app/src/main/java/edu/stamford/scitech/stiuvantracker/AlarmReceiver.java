package edu.stamford.scitech.stiuvantracker;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.EventListener;


public class AlarmReceiver extends BroadcastReceiver {

    int MID = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        String VanTitle;
        String VanDescription;
        String VanTime;
        



        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, EventListener.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        VanTitle = notificationIntent.getStringExtra("VanName");
        VanTime = notificationIntent.getStringExtra("VanTime");

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                context)
                .setContentTitle(VanTitle + " " + VanTime + " Van leaving soon.")
                .setContentText(VanTitle + " " + VanTime + " Van leaving in 5 minutes.")
                .setAutoCancel(true).setWhen(when)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        notificationManager.notify(MID, mNotifyBuilder.build());
        MID++;

    }

}