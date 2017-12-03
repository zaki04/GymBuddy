package com.example.toby.gymapp;

/**
 * Created by aleks on 27.11.2017.
 *
 * This is a class that stores the information about the user
 */

public class User {

    // Declare variables
    private int id;
    private String name, birthdate, email;

    // Create a constructor for the objects
    public User(int id, String name, String birthdate, String email){
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.email = email;
    }

    // Getter method for user's id
    public int getId(){
        return id;
    }

    // Getter method for user's email
    public String getName(){
        return name;
    }

    // Getter method for user's birthdate
    public String getBirthdate(){
        return birthdate;
    }

    // Getter method for the user's name
    public String getEmail(){
      return email;
    }

}
