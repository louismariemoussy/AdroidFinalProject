package com.example.finalproject;

public class Date {
    int year;
    int month;
    int day;
    int hour;
    int minute;

    public Date() {
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
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

    public String transformTime(){ //Transform selected time to SQLite DATETIME format HH:mm:ss
        String StringHour = Integer.toString(this.getHour());
        String StringMinute = Integer.toString(this.getMinute());
        if( StringHour.length() == 1){//if hour = 1  then add 0 to have 01
            StringHour = "0"+StringHour;
        }
        if(StringMinute.length() == 1){//if minute = 1 then add 0 to have 01
            StringMinute = "0"+StringMinute; }




        return StringHour + ":" + StringMinute+":00";
    }
}
