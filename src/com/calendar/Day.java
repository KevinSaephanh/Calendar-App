package com.calendar;

public class Day {
    private static final int[] DAYS = new int[31];
    private static final String[] DAYS_OF_WEEK = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    private int currDay;

    private String currCalDay;

    //Constructor
    public Day() {
        DAYS[0] = 1;
        for(int i = 1; i < DAYS.length; i++)
            DAYS[i] = i + 1;

        setCurrDay(java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH));
    }

    public void setCurrDay(int index) {
        currDay = DAYS[index - 1];
    }

    public void setCurrCalDay(int index) {
        currCalDay = DAYS_OF_WEEK[index];
    }

    public int getCurrDay() {
        return currDay;
    }

    public String getCurrCalDay() {
        return currCalDay;
    }

    public String getCalDays(int index) {
        return DAYS_OF_WEEK[index];
    }
}
