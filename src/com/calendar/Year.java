package com.calendar;

/*
* This class contains only the current year
* Created for future scalable implementation
* */

public class Year {
    private int year;

    //Constructor
    public Year() {
        setYear(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR));
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    //Check if current year is a leap year
    public boolean isLeapYear(int year) {
        return year % 4 == 0;
    }
}
