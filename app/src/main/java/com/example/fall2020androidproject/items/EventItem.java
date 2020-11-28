package com.example.fall2020androidproject.items;

/**
 * An Event Item for the event list
 */
public class EventItem {
    private String eventName;

    // Constructor
    public EventItem(String eventName){
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
