package com.example.toby.gymapp;

/**
 * Created by aleks on 08.12.2017.
 */

public class Event {

    private int id;
    private String uniqueid, title, description, date, time, gym, creator;

    public Event(int id, String uniqueid, String title, String description, String date, String time, String gym, String creator){
        this.id = id;
        this.uniqueid = uniqueid;
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.gym = gym;
        this.creator = creator;
    }

    public int getId(){
        return id;
    }

    public String getUniqueid(){
        return uniqueid;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getDate(){
        return date;
    }

    public String getTime(){
        return time;
    }

    public String getGym(){
        return gym;
    }

    public String getCreator(){
        return creator;
    }
}

