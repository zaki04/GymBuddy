package com.example.toby.gymapp;

/**
 * Created by aleks on 10.12.2017.
 */

public class Gym {

    private String gymName, gymAddress;

    public Gym(String gymName, String gymAddress){
        this.gymName = gymName;
        this.gymAddress = gymAddress;
    }

    public String toString(){
        String result = gymName + ", " + gymAddress;
        return result;
    }

    public String getGymName(){
        return gymName;
    }

    public String getGymAddress(){
        return gymAddress;
    }
}
