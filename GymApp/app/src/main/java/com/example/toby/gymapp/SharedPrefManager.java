package com.example.toby.gymapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


/**
 * Created by aleks on 26.11.2017.
 *
 * This class uses a singleton pattern
 * There is created a a login session using Shared Preferences
 * which allows to store primitive data,
 * that will persist across user session
 */

public class SharedPrefManager {

    // Declare constants
    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_NAME = "keyname";
    private static final String KEY_BIRTHDATE = "keybirthdate";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_ID = "keyid";

    private static final String KEY_EVENT_ID = "keyeventid";
    private static final String KEY_UNIQUEID = "keyuniqueid";
    private static final String KEY_TITLE = "keytitle";
    private static final String KEY_DESCRIPTION = "keydescription";
    private static final String KEY_DATE = "keydate";
    private static final String KEY_TIME = "keytime";
    private static final String KEY_GYM = "keygym";
    private static final String KEY_CREATOR = "keyemail";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }


    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    // Create method for login
    // This method stores the user data in Shared Preferences
    public void userLogin(User user){
        // Create Shared Preferences; Context.MODE_PRIVATE means only this application can acces this Shared Preferences
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        // Create an editor
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Put all the values from the object user to the editor
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_BIRTHDATE, user.getBirthdate());
        editor.putString(KEY_EMAIL, user.getEmail());
        // Commit the preferences changes back form the editor to the Shared Preferences objects it's editing
        editor.apply();
    }

    // Create a boolean method that checks if the user is already logged in
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null) != null;
    }

    // Create a method that gives the logged in user
    public User getUser(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_NAME, null),
                sharedPreferences.getString(KEY_BIRTHDATE, null),
                sharedPreferences.getString(KEY_EMAIL, null)
        );
    }

    // Create a method that will allow the user to log out
    public void logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Mark in the editor to remove all values form the preferences
        editor.clear();
        editor.apply();
        // Create a new Intent that redirects the user to login screen after logging out
        Intent i = new Intent(mCtx, LoginUserActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.getApplicationContext().startActivity(i);
    }

    public Event getEvent(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Event(
                sharedPreferences.getInt(KEY_EVENT_ID, -1),
                sharedPreferences.getString(KEY_UNIQUEID, null),
                sharedPreferences.getString(KEY_TITLE, null),
                sharedPreferences.getString(KEY_DESCRIPTION, null),
                sharedPreferences.getString(KEY_DATE, null),
                sharedPreferences.getString(KEY_TIME, null),
                sharedPreferences.getString(KEY_GYM, null),
                sharedPreferences.getString(KEY_CREATOR, null)
        );
    }

    public void createEvent(Event event){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        // Create an editor
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Put all the values from the object user to the editor
        editor.putInt(KEY_EVENT_ID, event.getId());
        editor.putString(KEY_UNIQUEID, event.getUniqueid());
        editor.putString(KEY_TITLE, event.getTitle());
        editor.putString(KEY_DESCRIPTION, event.getDescription());
        editor.putString(KEY_DATE, event.getTime());
        editor.putString(KEY_TIME, event.getTime());
        editor.putString(KEY_GYM, event.getGym());
        editor.putString(KEY_CREATOR, event.getCreator());
        // Commit the preferences changes back form the editor to the Shared Preferences objects it's editing
        editor.apply();
    }

    public void getOneEvent(Event event){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        // Create an editor
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Put all the values from the object user to the editor
        editor.putInt(KEY_EVENT_ID, event.getId());
        editor.putString(KEY_UNIQUEID, event.getUniqueid());
        editor.putString(KEY_TITLE, event.getTitle());
        editor.putString(KEY_DESCRIPTION, event.getDescription());
        editor.putString(KEY_DATE, event.getDate());
        editor.putString(KEY_TIME, event.getTime());
        editor.putString(KEY_GYM, event.getGym());
        editor.putString(KEY_CREATOR, event.getCreator());
        // Commit the preferences changes back form the editor to the Shared Preferences objects it's editing
        editor.apply();
    }
}
