package com.example.toby.gymapp;

/**
 * Created by aleks on 27.11.2017.
 */

public class User {

    private int id;
    private String email, birthdate, name;

    public User(int id, String email, String birthdate, String name){
        this.id = id;
        this.email = email;
        this.birthdate = birthdate;
        this.name = name;
    }

    public int getId(){
        return id;
    }


    public String getEmail(){
        return email;
    }

    public String getBirthdate(){
        return birthdate;
    }

    public String getName(){
      return name;
    }

}
