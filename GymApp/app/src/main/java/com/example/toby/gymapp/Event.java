package com.example.toby.gymapp;

/**
 * Created by aleks on 08.12.2017.
 *
 * This is a class that stores the information about the event
 *
 */

public class Event {

    // Declare variables
    private int id;
    private String title, description, date, time, gym, creator;

    // Create a constructor for the objects
    public Event(int id, String title, String description, String date, String time, String gym, String creator){
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.gym = gym;
        this.creator = creator;
    }

    // Getter method for event's id
    public int getId(){
        return id;
    }

    // Getter method for event's title
    public String getTitle(){
        return title;
    }

    // Getter method for event's description
    public String getDescription(){
        return description;
    }

    // Getter method for event's date
    public String getDate(){
        return date;
    }

    // Getter method for event's time
    public String getTime(){
        return time;
    }

    // Getter method for event's gym
    public String getGym(){
        return gym;
    }

    // Getter method for event's creator
    public String getCreator(){
        return creator;
    }
}

