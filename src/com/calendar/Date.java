package com.calendar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
* This class sets the current date according to host system settings
* Day, month, year, and local date time are included
* */

public class Date {
    private Year year;
    private Month month;
    private Day day;

    private DateTimeFormatter dtf;

    private StringBuffer date;
    private String time;

    //Constructor
    public Date() {
        init();
        setTime(LocalDateTime.now());
        setDate(day.getCurrCalDay());
        setDate(month.getCurrMonth());
        setDate(String.valueOf(day.getCurrDay()));
        setDate(String.valueOf(year.getYear()));
    }

    private void init() {
        year = new Year();
        month = new Month();
        date = new StringBuffer();

        day = new Day();
        day.setCurrCalDay(java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_WEEK) - 1);
    }

    //Set time format: hr:min am/pm
    public void setTime(LocalDateTime ldt) {
        dtf = DateTimeFormatter.ofPattern("h:mm:ss a");
        this.time = ldt.format(dtf);
    }

    //Append all attributes into StringBuffer date
    public void setDate(String s) {
        date.append(s);
        date.append(" ");
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date.toString();
    }
}
