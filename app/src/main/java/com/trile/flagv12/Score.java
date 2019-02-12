package com.trile.flagv12;


public class Score {
    private int id,kq,date,hour,minute,second,month,sl;
    private String level;



    public Score(int id, int kq, int date, int month , int hour, int minute, int second, String level,int sl) {
        this.id = id;
        this.kq = kq;
        this.date = date;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.month=month;
        this.level=level;
        this.sl=sl;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public int getId() {
        return id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKq() {
        return kq;
    }

    public void setKq(int kq) {
        this.kq = kq;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
