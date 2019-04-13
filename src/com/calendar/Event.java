package com.calendar;

import com.datastorage.EventJSON;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Event {
    private String name;
    private String description;
    private String date;
    private String start;
    private String end;

    private DateTimeFormatter dtf;

    //Constructor
    public Event(String name, LocalDate date, LocalTime start, LocalTime end, String description) {
        setName(name);
        setDate(date);
        setStart(start);
        setEnd(end);
        setDescription(description);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(LocalDate d) {
        dtf = DateTimeFormatter.ofPattern("M-d-uuuu");
        this.date = dtf.format(d);
    }

    public void setStart(LocalTime s) {
        dtf = DateTimeFormatter.ofPattern("h:mm a");
        this.start = dtf.format(s);
    }

    public void setEnd(LocalTime e) {
        dtf = DateTimeFormatter.ofPattern("h:mm a");
        this.end = dtf.format(e);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEvent() throws Exception {
        if(name.equals(""))
            throw new Exception("MISSING EVENT NAME");
        else {
            EventJSON jEvent = new EventJSON();
            jEvent.addJSON(name, date, start, end, description);
        }
    }
}
