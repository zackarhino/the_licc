package com.example.fall2020androidproject.items;

import java.util.Date;

/**
 * An Event Item for the event list
 */
public class EventItem {
    private String eventName;
    private String eventDate;

    // Constructor
    public EventItem(String eventName, String eventDate){
        this.eventName = eventName;
        this.eventDate = eventDate;
    }

    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public String getEventDate() {
        return eventDate;
    }
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
}
