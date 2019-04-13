package com.datastorage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/*
* Class that creates json file to store events
* User can add to and delete events from the file
* */

public class EventJSON {
    private JSONArray list;

    private File f;

    //Constructor to create file
    public EventJSON() {
        this.f = new File("Events.json");
    }

    @SuppressWarnings("unchecked")
    public void addJSON(String name, String date, String start, String end, String description) {
        try {
            //If file is not empty, add file contents to json array
            if (!emptyFile(f))
                list = (JSONArray) new JSONParser().parse(new FileReader(f.getName()));

            //Add new elements to json object
            JSONObject event = new JSONObject();
            event.put("Event", name);
            event.put("Date", date);
            event.put("Start Time", start);
            event.put("End Time", end);
            event.put("Description", description);

            //Add json object to json array
            list.add(event);

            //Sort json array after adding new json object
            sortJSON();

            updateJSON();
        } catch(IOException | ParseException e) {
            e.printStackTrace();
        } catch(IndexOutOfBoundsException i) {
            System.out.println(i);
        }
    }

    //Delete event from file using event name as key
    public void deleteFromJSON(String key) {
        //Iterate through json array
        for (int i = 0; i < list.size(); i++) {
            JSONObject jsonObject = (JSONObject) list.get(i);

            //If json object has key, remove it from array
            if(jsonObject.get("Event").equals(key))
                list.remove(i);
        }

        //update file
        updateJSON();
    }

    //Sort json by date and time
    @SuppressWarnings("unchecked")
    private void sortJSON() {
        //Convert json array to array list of json objects
        List<JSONObject> jsonValues = new ArrayList<JSONObject>();
        for(int i = 0; i < list.size(); i++)
            jsonValues.add((JSONObject) list.get(i));

        //Use Collections class to sort list
        Collections.sort(jsonValues, new Comparator<JSONObject>() {
            //Key by which array is sorted
            private static final String KEY_DATE = "Date";
            private static final String KEY_TIME = "Start Time";

            //Compare json objects by date
            public int compare(JSONObject a, JSONObject b) {
                //Format and dates for both json objects
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M-d-uuuu");
                LocalDate date1 = LocalDate.parse(a.get(KEY_DATE).toString(), dtf);
                LocalDate date2 = LocalDate.parse(b.get(KEY_DATE).toString(), dtf);

                //If dates are equal, compare by start time
                if(date1.equals(date2)) {
                    //Format and start times for both json objects
                    dtf = DateTimeFormatter.ofPattern("h:mm a");
                    LocalTime time1 = LocalTime.parse(a.get(KEY_TIME).toString(), dtf);
                    LocalTime time2 = LocalTime.parse(b.get(KEY_TIME).toString(), dtf);

                    return time1.compareTo(time2);
                }

                return date1.compareTo(date2);
            }
        });

        //Add json objects to sorted array
        JSONArray sortedArray = new JSONArray();
        for(int i = 0; i < list.size(); i++)
            sortedArray.add(jsonValues.get(i));

        //Set json array list to sorted array
        list = sortedArray;
    }

    //Write json array to json file
    private void updateJSON() {
        try {
            //Create object mapper to pretty print list
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            //Clear file to add new json array
            PrintWriter writer = new PrintWriter(f.getName());
            writer.println("");
            writer.close();

            //Write to json file
            FileWriter fileWriter = new FileWriter(f.getName(), true);
            fileWriter.write(mapper.writeValueAsString(list));
            fileWriter.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    //Return list of events for the current date
    public ArrayList<String> getTodayEvents() {
        try {
            //If file is not empty, add file contents to json array
            if (!emptyFile(f))
                list = (JSONArray) new JSONParser().parse(new FileReader(f.getName()));

            //Array list of events
            ArrayList<String> eventList = new ArrayList<String>();

            //Iterate through json file
            for(Object o : list) {
                //Set json object equal to file objects
                JSONObject event = (JSONObject) o;

                //Date time formatter and local date variable for current date
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M-d-uuuu");
                LocalDate localDate = LocalDate.now();

                //If date of json object is current date, add event title to array list
                if(event.get("Date").equals(dtf.format(localDate)))
                    eventList.add(event.get("Event").toString());
            }

            return eventList;
        } catch(IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getTodayEventDetails(String key) {
        try {
            //If file is not empty, add file contents to json array
            if (!emptyFile(f))
                list = (JSONArray) new JSONParser().parse(new FileReader(f.getName()));
        } catch(IOException | ParseException e) {
            e.printStackTrace();
        }

        //Iterate through json array
        for (int i = 0; i < list.size(); i++) {
            JSONObject jsonObject = (JSONObject) list.get(i);

            //Check if json object contains key
            if (jsonObject.get("Event").equals(key))
                //Return json object elements
                return jsonObject.get("Start Time") + "-" +
                        jsonObject.get("End Time") + "\n" +
                        jsonObject.get("Description");
        }

        return "";
    }

    //Check if file is empty
    private boolean emptyFile(File f) {
        return f.length() == 0;
    }
}
