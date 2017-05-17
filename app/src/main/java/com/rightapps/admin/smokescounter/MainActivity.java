package com.rightapps.admin.smokescounter;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.view.View;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Counter counter = new Counter(0,0,0);
    private Timer daytimer, weektimer;
    private SharedPreferences sf;
    private SharedPreferences.Editor ed;
    private int oldday = 0;
    private TextView today;
    private TextView week;
    private TextView alltime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        daytimer = new Timer();
        weektimer = new Timer();
        today = (TextView) findViewById(R.id.today);
        week = (TextView) findViewById(R.id.week);
        alltime = (TextView) findViewById(R.id.alltime);
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.HOUR_OF_DAY, 23);
        cal1.set(Calendar.MINUTE, 59);
        cal1.set(Calendar.SECOND, 59);
        cal1.set(Calendar.MILLISECOND, 59);
        sf = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ed = sf.edit();
        daytimer.schedule(new DayCountTT(), cal1.getTime(), 24 * 60 * 60 * 1000);
        //daytimer.schedule(new DayCountTT(), cal1.getTime(), 2000);
        weektimer.schedule(new WeekCountTT(), cal1.getTime(), 7 * 24 * 60 * 60 * 1000);
        if (sf.contains("launched") && sf.getBoolean("launched", false)) {
            Log.d("SharedPrefCheck", "Contains Launch "+Integer.toString(sf.getInt("d", 0)));
            oldday = sf.getInt("startday", 0);
            counter = new Counter(sf.getInt("d", 0), sf.getInt("w", 0),
                    sf.getInt("a", 0));
        } else {
            Log.d("SharedPrefCheck", "Does not contains Launch");
            ed.clear();
            ed.commit();
            ed.putBoolean("launched", true);
            Calendar cal = Calendar.getInstance();
            oldday = cal.get(Calendar.DAY_OF_YEAR);
            ed.putInt("startday", oldday);
            ed.commit();
            counter = new Counter(0, 0, 0);
            ed.putInt("d", 0);
            ed.putInt("w", 0);
            ed.putInt("a", 0);
            ed.commit();
        }
    }

    public void updateCnt(View view){
        counter.increment();
        today.setText(Integer.toString(counter.getDaycount()));
        week.setText(Integer.toString(counter.getDaycount()));
        alltime.setText(Integer.toString(counter.getDaycount()));

    }

    public void decreCnt(View view){
        counter.decrement();
        today.setText(Integer.toString(counter.getDaycount()));
        week.setText(Integer.toString(counter.getDaycount()));
        alltime.setText(Integer.toString(counter.getDaycount()));
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        today.setText(Integer.toString(counter.getDaycount()));
        week.setText(Integer.toString(counter.getWeektotal()));
        alltime.setText(Integer.toString(counter.getAlltimetotal()));
    }

    @Override
    protected void onStop() {
        super.onStop();
        sf = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ed = sf.edit();
        ed.putInt("d", counter.getDaycount());
        ed.putInt("w", counter.getWeektotal());
        ed.putInt("a", counter.getAlltimetotal());
        ed.commit();
    }

    private class DayCountTT extends TimerTask {

        @Override
        public void run() {
            sf = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            ed = sf.edit();
            counter.daycountholder.add(counter.getDaycount());
            counter = new Counter(0, counter.getWeektotal(),
                    counter.getAlltimetotal());
            ed.putInt("d", counter.getDaycount());
            ed.putInt("w", counter.getWeektotal());
            ed.putInt("a", counter.getAlltimetotal());
            ed.commit();
        }

    }

    private class WeekCountTT extends TimerTask {

        @Override
        public void run() {
            sf = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            ed = sf.edit();
            counter.weekcountholder.add(counter.getWeektotal());
            counter = new Counter(counter.getDaycount(), 0,
                    counter.getAlltimetotal());
            ed.putInt("d", counter.getDaycount());
            ed.putInt("w", counter.getWeektotal());
            ed.putInt("a", counter.getAlltimetotal());
            ed.commit();
        }

    }
}


