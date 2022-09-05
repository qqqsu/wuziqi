package com.game;

public class Time {
    private int h;
    private int m;
    private int s;

    public Time(int h, int m, int s) {
        this.h = h;
        this.m = m;
        this.s = s;
    }

    public void setTime(int h, int m, int s) {
        this.h = h;
        this.m = m;
        this.s = s;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public void timeGo() {
        if (s > 0) {
            s--;
        } else if (m > 0) {
            s = 59;
            m--;
        } else {
            s=59;
            m=59;
            h--;
        }
    }
    public boolean isTimeOut(){
        if(h==0 && m==0 && s==0) return true;
        else return false;
    }

}
