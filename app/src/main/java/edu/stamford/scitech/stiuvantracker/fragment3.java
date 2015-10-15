package edu.stamford.scitech.stiuvantracker;

import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;


public class fragment3 extends Fragment {

    private ListPreference mListPreference;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     @return A new instance of fragment Fragment3.
     */


    public static fragment3 newInstance() {
        return new fragment3();
    }

    public fragment3() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_fragment3,
                container, false);

      Button testbutton = (Button) view.findViewById(R.id.button_test);
        testbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Test button firing.
                //Toast.makeText(getActivity(), "clicked", Toast.LENGTH_LONG).show();

                //Test notification with AlarmManager.
                scheduleNotification(getNotification("Test Van is leaving in 5 minutes."), 3000);

                //Test notification without scheduling.
                //testNotification();
            }
        });
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_fragment3, container, false);
        return view;
    }


    /*
     *   Method for testing the notification without AlarmManager.
     */
    public void testNotification() {
/*        PendingIntent pi = PendingIntent.getActivity(fragment3.this.getActivity(),
                0, new Intent(fragment3.this.getActivity(),MainActivity.class), 0);*/

        PendingIntent pi = PendingIntent.getActivity(fragment3.this.getActivity(),
                0, new Intent(fragment3.this.getActivity(),MainActivity.class), 0);


        Notification note = new NotificationCompat.Builder(fragment3.this.getActivity())
                .setTicker("Test Van leaving!")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("Test Van leaving!")
                .setContentText("The Test Van is leaving now.")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) fragment3.this.getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, note);
    }


    /*
     *   Method for testing scheduled notifications with AlarmManager.
     */
    private void scheduleNotification(Notification notification, int delay) {

        Toast.makeText(getActivity(),
                "Notification incoming in " + delay, Toast.LENGTH_LONG)
                .show();

       Intent notificationIntent = new Intent(fragment3.this.getActivity(), NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(fragment3.this.getActivity(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Calculate the time from the current time of the system.
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }



    /*
        Create the notification.
     */
     private Notification getNotification(String content) {

         /*Notification.Builder builder = new Notification.Builder(fragment3.this.getActivity());
         builder.setContentTitle("Test Van Leaving");
         builder.setContentText(content);*/

         Notification note = new NotificationCompat.Builder(fragment3.this.getActivity())
                 .setTicker("Test Van leaving!")
                 .setSmallIcon(android.R.drawable.ic_menu_report_image)
                 .setContentTitle("Test Van leaving!")
                 .setContentText(content)
                 .build();

        //builder.setSmallIcon(R.drawable.ic_launcher);

        //return builder.build();
         return note;
    }



}