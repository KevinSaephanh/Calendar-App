package com.calendar;

import java.util.Arrays;

public class Month {
    private static final int[] DAYS_PER_MONTH = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    private String currMonth;

    //Constructor
    public Month() {
        setCurrMonth(java.util.Calendar.getInstance().get(java.util.Calendar.MONTH));
    }

    public void setCurrMonth(int index) {
        currMonth = months[index];
    }

    public String getCurrMonth() {
        return currMonth;
    }

    public int getCurrMonthNum() {
        return Arrays.asList(months).indexOf(getCurrMonth());
    }

    //Return number of days of given year and month
    public int getDaysPerMonth(int year, int month) {
        //If leap year, add an extra day to February
        if (new Year().isLeapYear(year) && month == 2)
            return DAYS_PER_MONTH[month - 1] + 1;

        return DAYS_PER_MONTH[month - 1];
    }
}
