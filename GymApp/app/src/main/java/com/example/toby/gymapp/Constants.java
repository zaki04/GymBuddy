package com.example.toby.gymapp;

/**
 * Created by aleks on 26.11.2017.
 *
 * This class contains constants
 * It contains URLs of the php files that are uploaded to an external web hosting server
 * The php files allow the application to communicate with the database
 */

public class Constants {

    private static final String ROOT_URL = "http://gymapp.cba.pl/Api.php?apicall=";

    public static final String URL_REGISTER = ROOT_URL+"signup";
    public static final String URL_LOGIN = ROOT_URL+"login";
}
