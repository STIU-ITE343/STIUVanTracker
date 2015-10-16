package edu.stamford.scitech.stiuvantracker;

import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Calendar;


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


        final CheckBox ch_arl = (CheckBox)view.findViewById(R.id.ch_arl);
        final CheckBox ch_bs = (CheckBox)view.findViewById(R.id.ch_bs);
        CheckBox ch_mrt = (CheckBox)view.findViewById(R.id.ch_mrt);
        CheckBox ch_lumpini = (CheckBox)view.findViewById(R.id.ch_bs);
        Switch toggle = (Switch) view.findViewById(R.id.switch_notification);
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

        /*  @Niko
         *   Example implementation of hardcoded scheduled notifications
         *   using Calendar to set the time of the alarm and AlarmManager to
         *   run it in a set interval (daily).
         */

        // TODO: 16.10.2015  Check for Saturday/Sunday

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled

                    //Ban suan van
                    if (ch_bs.isChecked()) {

                        //7.30AM Van from Baan Suan/MT to STIU
                        Calendar bsmt730 = Calendar.getInstance();
                        bsmt730.set(Calendar.HOUR_OF_DAY, 7);
                        bsmt730.set(Calendar.MINUTE, 25);
                        bsmt730.set(Calendar.SECOND, 0);
                        //Pass it to AlarmReceiver
                        Intent intent_bsmt730 = new Intent(fragment3.this.getActivity(), AlarmReceiver.class);
                        intent_bsmt730.putExtra("VanName", "Baan Suan/MT");
                        intent_bsmt730.putExtra("VanTime", "7.30");
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(fragment3.this.getActivity(), 0, intent_bsmt730, PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager am_bsmt730 = (AlarmManager) fragment3.this.getActivity().getSystemService(fragment3.this.getActivity().ALARM_SERVICE);
                        am_bsmt730.setRepeating(AlarmManager.RTC_WAKEUP, bsmt730.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);


                        //10AM Van from Baan Suan/MT to STIU
                        Calendar bsmt10 = Calendar.getInstance();
                        bsmt10.set(Calendar.HOUR_OF_DAY, 9);
                        bsmt10.set(Calendar.MINUTE, 55);
                        bsmt10.set(Calendar.SECOND, 0);
                        //Pass it to AlarmReceiver
                        Intent intent_bsmt10 = new Intent(fragment3.this.getActivity(), AlarmReceiver.class);
                        intent_bsmt10.putExtra("VanName", "Baan Suan/MT");
                        intent_bsmt10.putExtra("VanTime", "10.00");
                        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(fragment3.this.getActivity(), 0, intent_bsmt10, PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager am_bsmt10 = (AlarmManager) fragment3.this.getActivity().getSystemService(fragment3.this.getActivity().ALARM_SERVICE);
                        am_bsmt10.setRepeating(AlarmManager.RTC_WAKEUP, bsmt730.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent2);
                    }

                    //Airport link van
                    if (ch_arl.isChecked()) {

                        //16.30AM Van from STIU to ARL
                        Calendar arl1630 = Calendar.getInstance();
                        arl1630.set(Calendar.HOUR_OF_DAY, 16);
                        arl1630.set(Calendar.MINUTE, 25);
                        arl1630.set(Calendar.SECOND, 0);
                        //Pass it to AlarmReceiver
                        Intent intent_arl1630 = new Intent(fragment3.this.getActivity(), AlarmReceiver.class);
                        intent_arl1630.putExtra("VanName", "Airport Link");
                        intent_arl1630.putExtra("VanTime", "16.30");
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(fragment3.this.getActivity(), 0, intent_arl1630, PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager am_arl1630 = (AlarmManager) fragment3.this.getActivity().getSystemService(fragment3.this.getActivity().ALARM_SERVICE);
                        am_arl1630.setRepeating(AlarmManager.RTC_WAKEUP, arl1630.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

                    }
                } else {
                    // The toggle is disabled
                }
            }
        });


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

        int color = 0xff00A0E3;
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_icon_small);

        Notification note = new NotificationCompat.Builder(fragment3.this.getActivity())
                .setTicker("Test Van leaving!")
                .setSmallIcon(R.drawable.ic_icon_small)
                .setLargeIcon(bm)
                //.setSmallIcon()
                .setColor(color)
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
                "Notification incoming in " + delay / 1000 + " seconds.", Toast.LENGTH_LONG)
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