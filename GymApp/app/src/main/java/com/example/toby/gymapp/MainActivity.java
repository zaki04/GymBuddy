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

    //private static final String URL_GET_EVENTS = "http://gymapp.cba.pl/getAllEvents.php";

    List<Event> eventList;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventList = new ArrayList<>();

        loadEvents();

        //creating buttons and referencing them to the XML
        final Button btnAllEvents = (Button) findViewById(R.id.btnAllEvents);
        final Button btnMyEvents = (Button) findViewById(R.id.btnMyEvents);
        final Button btnShowEvent = (Button) findViewById(R.id.btnShowEvent);

        final ImageButton ImageButtonProfile = (ImageButton) findViewById(R.id.imageButtonProfile);
        final ImageButton ImageButtonCalendar = (ImageButton) findViewById(R.id.imageButtonCalendar);
        final ImageButton ImageButtonMenu = (ImageButton) findViewById(R.id.imageButtonMenu);
        final ImageButton ImageButtonCreateEvent = (ImageButton) findViewById(R.id.imageButtonCreateEvent);

        //making the btnShowEvent go to the ShowEventActivity on click
        btnShowEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent showEventIntent = new Intent(MainActivity.this, ShowEventActivity.class);
                MainActivity.this.startActivity(showEventIntent);

            }
        });

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

    private void loadEvents(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_GET_EVENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++){

                                JSONObject event = array.getJSONObject(i);

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

        Volley.newRequestQueue(this).add(stringRequest);
    }
}
