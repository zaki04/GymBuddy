package com.example.toby.gymapp;

/**
 * Created by aleks on 27.11.2017.
 *
 * This is a class that stores the information about the user
 */

public class User {

// Declare variables
    private int id;
    private String email, birthdate, name;

// Create a constructor for the objects
    public User(int id, String email, String birthdate, String name){
        this.id = id;
        this.email = email;
        this.birthdate = birthdate;
        this.name = name;
    }

// Getter method for user's id
    public int getId(){
        return id;
    }

// Getter method for user's email
    public String getEmail(){
        return email;
    }

// Getter method for user's birthdate
    public String getBirthdate(){
        return birthdate;
    }

// Getter method for the user's name
    public String getName(){
      return name;
    }

}
