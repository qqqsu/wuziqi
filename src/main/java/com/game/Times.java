package com.game;

public class Times {
    private Time btTime;
    private Time bsTime;
    private Time wtTime;
    private Time wsTime;

    public Times() {
        this.btTime = new Time(0,10,0);
        this.bsTime = new Time(0,0,30);
        this.wtTime = new Time(0,10,0);
        this.wsTime = new Time(0,0,30);
    }

    public Time getBtTime() {
        return btTime;
    }

    public void setBtTime(Time btTime) {
        this.btTime = btTime;
    }

    public Time getBsTime() {
        return bsTime;
    }

    public void setBsTime(Time bsTime) {
        this.bsTime = bsTime;
    }

    public Time getWtTime() {
        return wtTime;
    }

    public void setWtTime(Time wtTime) {
        this.wtTime = wtTime;
    }

    public Time getWsTime() {
        return wsTime;
    }

    public void setWsTime(Time wsTime) {
        this.wsTime = wsTime;
    }

    public int TimeOutColor(){
        int ret =0;
        if(this.getWtTime().isTimeOut() || this.getWsTime().isTimeOut()) ret =-1;
        if(this.getBsTime().isTimeOut() || this.getBtTime().isTimeOut()) ret =1;
        return ret;
    }
}
