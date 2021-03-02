package com.example.finalproject;

public class Date {
    String year;
    String month;
    String day;
    String hour;
    String minute;

    public Date() {
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String transformTime(){ //Transform selected time to SQLite DATETIME format HH:mm:ss
        String StringHour = this.getHour();
        String StringMinute = this.getMinute();
        if( StringHour.length() == 1){//if hour = 1  then add 0 to have 01
            StringHour = "0"+StringHour;
        }
        if(StringMinute.length() == 1){//if minute = 1 then add 0 to have 01
            StringMinute = "0"+StringMinute; }




        return StringHour + ":" + StringMinute+":00";
    }

    public String toSQLformat(){//yyyy-DD-MM
        return this.getYear()+"-"+this.getMonth() +"-"+this.getDay();
    }
}
