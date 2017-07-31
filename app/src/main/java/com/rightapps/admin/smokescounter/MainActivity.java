package com.rightapps.admin.smokescounter;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Counter counter;
    private SharedPreferences sf;
    private SharedPreferences.Editor ed;
    private TextView today;
    private TextView week;
    private TextView alltime;
    private Context ctx;
    private TextView debuginf;

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
    public String getInstallDate(Context ctx) {
        // get app installation date

        PackageManager packageManager =  ctx.getPackageManager();
        long installTimeInMilliseconds; // install time is conveniently provided in milliseconds

        Date installDate;
        String installDateString = "";
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(ctx.getPackageName(), 0);
            installTimeInMilliseconds = packageInfo.firstInstallTime;
            installDateString  = getDate(installTimeInMilliseconds, "MM/dd/yyyy");
        }
        catch (PackageManager.NameNotFoundException e) {
            // an error occurred, so display the Unix epoch
            installDate = new Date(0);
            installDateString = installDate.toString();
        }

        return installDateString;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = getApplicationContext();
        setContentView(R.layout.activity_main);
        sf = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ed = sf.edit();
        try{
            if(!sf.getBoolean("savedInstallDate", false)){
                ed.putBoolean("savedInstallDate", true);
                ed.putString("installDate", getInstallDate(ctx));
                ed.commit();
            }
            today = (TextView) findViewById(R.id.today);
            week = (TextView) findViewById(R.id.week);
            alltime = (TextView) findViewById(R.id.alltime);
            counter = new Counter();
        }catch(Exception e){
            catch_a(e);
        }
    }

    public void updateCnt(View view){
        if(today == null || week == null || alltime == null)
            return;
        try{
            counter.increment();
            today.setText(Integer.toString(counter.getDaycount()));
            week.setText(Integer.toString(counter.getDaycount()));
            alltime.setText(Integer.toString(counter.getDaycount()));
        }catch(Exception e){
            catch_a(e);
        }

    }

    public void catch_a(Exception e){
        Toast t = Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG);
        t.show();
    }

    public void decreCnt(View view){
        if(today == null || week == null || alltime == null)
            return;
        try{
            counter.decrement();
            today.setText(Integer.toString(counter.getDaycount()));
            week.setText(Integer.toString(counter.getDaycount()));
            alltime.setText(Integer.toString(counter.getDaycount()));
        }catch(Exception e){
            catch_a(e);
        }
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        try{
            if(sf.getBoolean("savedInstallDate", false)){
                Toast t = Toast.makeText(ctx, sf.getString("installDate", "Not Saved"), Toast.LENGTH_LONG);
            }
            //
            if(sf.contains("a") && sf.contains("w") && sf.contains("d")){
                counter = new Counter(sf.getInt("d", 0), sf.getInt("w", 0), sf.getInt("a", 0));
            }
            today.setText(Integer.toString(counter.getDaycount()));
            week.setText(Integer.toString(counter.getWeektotal()));
            alltime.setText(Integer.toString(counter.getAlltimetotal()));
        }catch(Exception e){
            catch_a(e);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        try{
            sf = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            ed = sf.edit();
            ed.putInt("d", counter.getDaycount());
            ed.putInt("w", counter.getWeektotal());
            ed.putInt("a", counter.getAlltimetotal());
            ed.commit();
        }
        catch (Exception e){
            catch_a(e);
        }
    }
}


