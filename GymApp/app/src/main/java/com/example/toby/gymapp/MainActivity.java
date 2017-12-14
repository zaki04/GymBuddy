package com.example.toby.gymapp;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Define a list to store all the events
    List<Event> eventList;

    // Define a RecyclerView
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the RecyclerView from xml
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the events
        eventList = new ArrayList<>();

        // This method fetches and parses json to display it in RecyclerView
        loadEvents();

        // Creating buttons and referencing them to the XML
        final Button btnAllEvents = (Button) findViewById(R.id.btnAllEvents);
        final Button btnMyEvents = (Button) findViewById(R.id.btnMyEvents);

        final ImageButton ImageButtonProfile = (ImageButton) findViewById(R.id.imageButtonProfile);
        final ImageButton ImageButtonCalendar = (ImageButton) findViewById(R.id.imageButtonCalendar);
        final ImageButton ImageButtonMenu = (ImageButton) findViewById(R.id.imageButtonMenu);
        final ImageButton ImageButtonCreateEvent = (ImageButton) findViewById(R.id.imageButtonCreateEvent);


        //making the ImageButtonProfile go to the UserAccountActivity on click, note that if not logged
        //in it will just go to the LoginActivity.
        ImageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent showProfileIntent = new Intent(MainActivity.this, UserAccountActivity.class);
                MainActivity.this.startActivity(showProfileIntent);

            }
        });
        //making the ImageButtonMenu go to the MainActivity on click, of course we're already within
        //this activity because for now the button is only used here.
        ImageButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent showMenuIntent = new Intent(MainActivity.this, MainActivity.class);
                MainActivity.this.startActivity(showMenuIntent);

            }
        });

        // Set OnClick listener for the create event button
        ImageButtonCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showCreateEventIntent = new Intent(MainActivity.this, CreateEvent.class);
                MainActivity.this.startActivity(showCreateEventIntent);
            }
        });
        //Last but not least, the ImageButtonCalendar to sent the user to the calendar activity.
        ImageButtonCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent showCalendarIntent = new Intent(MainActivity.this, CalendarActivity.class);
                MainActivity.this.startActivity(showCalendarIntent);

            }
        });

    }

    // Create a method that will fetch the events form the database
    private void loadEvents(){

        // Create a string request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_GET_EVENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            // Convert the string to json array object
                            JSONArray array = new JSONArray(response);

                            // Traversing through all the objects
                            for (int i = 0; i < array.length(); i++){

                                // Get the event object from json array
                                JSONObject event = array.getJSONObject(i);

                                // Add the event to the event list
                                eventList.add(new Event(
                                        event.getInt("id"),
                                        event.getString("uniqueid"),
                                        event.getString("title"),
                                        event.getString("description"),
                                        event.getString("date"),
                                        event.getString("time"),
                                        event.getString("gym"),
                                        event.getString("creator")
                                ));
                            }

                            // Create adapter object and set it to RecyclerView
                            EventsAdapter adapter = new EventsAdapter(MainActivity.this, eventList);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        // Add the request to request queue object
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        //Volley.newRequestQueue(this).add(stringRequest); ???
    }
}
