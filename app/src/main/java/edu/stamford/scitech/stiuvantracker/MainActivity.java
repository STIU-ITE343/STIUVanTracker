package edu.stamford.scitech.stiuvantracker;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    boolean NotificationEnabled = false;
    boolean BSMT = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a toolbar which will replace the action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup the viewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        // Setup the Tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        // By using this method the tabs will be populated according to viewPager's count and
        // with the name from the pagerAdapter getPageTitle()
        tabLayout.setTabsFromPagerAdapter(pagerAdapter);
        // This method ensures that tab selection events update the ViewPager and page changes update the selected tab.
        tabLayout.setupWithViewPager(viewPager);


        //Notification
        if (NotificationEnabled) {
            if (BSMT) {
                //7.30AM Van from Baan Suan/MT to STIU
                Calendar bsmt730 = Calendar.getInstance();
                bsmt730.set(Calendar.HOUR_OF_DAY, 7);
                bsmt730.set(Calendar.MINUTE, 25);
                bsmt730.set(Calendar.SECOND, 0);
                //Pass it to AlarmReceiver
                Intent intent_bsmt730 = new Intent(MainActivity.this, AlarmReceiver.class);
                intent_bsmt730.putExtra("VanName", "Baan Suan/MT");
                intent_bsmt730.putExtra("VanTime", "7.30am");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent_bsmt730, PendingIntent.FLAG_UPDATE_CURRENT);
               // AlarmManager am_bsmt730 = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
               // am_bsmt730.setRepeating(AlarmManager.RTC_WAKEUP, bsmt730.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return Fragment1.newInstance();
                case 1: return Fragment2.newInstance();
                case 2: return Fragment3.newInstance();
                default: return Fragment1.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String tabName = "";
            switch(position) {
                case 0: tabName = "Schedule"; break;
                case 1: tabName = "Map"; break;
                case 2: tabName = "Settings"; break;
            }
           // return "Tab " + (position + 1);
            return tabName;
        }


    }

    public boolean getNotificationEnabled() {
        return NotificationEnabled;
    }

    public void setNotificationEnabled() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}



/*

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
*/
