package com.example.toby.gymapp;

/**
 * Created by aleks on 27.11.2017.
 */

public class User {

    private int id;
    private String name, birthdate, email;

    public User(int id, String name, String birthdate, String email){
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.email = email;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getBirthdate(){
        return birthdate;
    }

    public String getEmail(){
        return email;
    }
}
