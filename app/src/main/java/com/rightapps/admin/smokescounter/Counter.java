package com.rightapps.admin.smokescounter;

import java.util.ArrayList;

public class Counter {
    private int daycount;
    private int weektotal;
    private int alltimetotal;

    public Counter() {
        alltimetotal = 0;
        weektotal = 0;
        daycount = 0;

    }
    public Counter(int dc,int wc,int at){
        alltimetotal = at;
        weektotal = wc;
        daycount = dc;
    }

    public void increment() {
        daycount += 1;
        weektotal += 1;
        alltimetotal += 1;
    }

    public void decrement() {
        if (daycount > 0 && weektotal > 0 && alltimetotal > 0) {
            daycount -= 1;
            weektotal -= 1;
            alltimetotal -= 1;
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
