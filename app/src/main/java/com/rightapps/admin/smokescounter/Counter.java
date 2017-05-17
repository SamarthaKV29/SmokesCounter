package com.rightapps.admin.smokescounter;
import java.util.ArrayList;
/**
 * Created by Admin on 5/12/2017.
 */

public class Counter {
    private int daycount;
    private int weektotal;
    private int alltimetotal;
    public ArrayList<Integer> daycountholder;
    public ArrayList<Integer> weekcountholder;

    public Counter(int c, int w, int a) {
        alltimetotal = a;
        weektotal = w;
        daycount = c;
        daycountholder = new ArrayList<Integer>();
        weekcountholder = new ArrayList<Integer>();
    }

    public void increment() {
        daycount+=1;
        weektotal+=1;
        alltimetotal+=1;
    }

    public void decrement() {
        if(daycount > 0){
            daycount-=1;
            weektotal-=1;
            alltimetotal-=1;
        }
    }

    public int getDaycount() {
        return daycount;
    }

    public void setDaycount(int daycount) {
        this.daycount = daycount;
    }

    public int getWeektotal() {
        return weektotal;
    }

    public void setWeektotal(int weektotal) {
        this.weektotal = weektotal;
    }

    public int getAlltimetotal() {
        return alltimetotal;
    }

    public void setAlltimetotal(int alltimetotal) {
        this.alltimetotal = alltimetotal;
    }
}
