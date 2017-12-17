package com.example.toby.gymapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowEventActivity extends AppCompatActivity {

    // Define view objects
    TextView tvShowEventTitle, tvShowEventCreator, tvShowEventPlace, tvShowEventDescription,
            tvShowEventDate, tvShowEventTime;

    // Define a list
    List participants;

    // Define a list view
    ListView participantList;

    // Define an array adapter
    ArrayAdapter arrayAdapter;

    // Get currently logged in user from shared preferences
    User user = SharedPrefManager.getInstance(this).getUser();
    // Get the user's name from shared preferences
    String participantName = user.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);

        // Initialize view objects
        tvShowEventTitle = findViewById(R.id.tvShowEventTitle);
        tvShowEventCreator = findViewById(R.id.tvShowEventCreator);
        tvShowEventPlace = findViewById(R.id.tvShowEventPlace);
        tvShowEventDescription = findViewById(R.id.tvShowEventDescription);
        tvShowEventDate = findViewById(R.id.tvShowEventDate);
        tvShowEventTime = findViewById(R.id.tvShowEventTime);

        // Return the intent that started this activity
        Intent intent = getIntent();

        // Retrieve extended data from the intent
        String clickedTitle = intent.getStringExtra("clickedTitle");
        String clickedDescription = intent.getStringExtra("clickedDescription");
        String clickedDate = intent.getStringExtra("clickedDate");
        String clickedTime = intent.getStringExtra("clickedTime");
        String clickedGym = intent.getStringExtra("clickedGym");
        String clickedCreator = intent.getStringExtra("clickedCreator");

        // Set values for the text views
        tvShowEventTitle.setText(clickedTitle);
        tvShowEventCreator.setText("Created by " + clickedCreator);
        tvShowEventPlace.setText(clickedGym);
        tvShowEventDate.setText(clickedDate);
        tvShowEventTime.setText(clickedTime);
        tvShowEventDescription.setText(clickedDescription);

        // Call method initParticipants
        initParticipants();

        // Create an array adapter
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, participants);
        // Initialize list view
        participantList = findViewById(R.id.participantList);
        // ???
        participantList.setAdapter(arrayAdapter);
    }

    // Checks if the toggleButton is checked.
    // If the button is checked the user joins the event and the user's name is added to the array.
    // Otherwise the user leaves the event  and the user's name is removed from the array.
    public void changeJoinState(View view) {

        boolean checked = ((ToggleButton) view).isChecked();

        if(checked) {
            participants.add(participantName);
            arrayAdapter.notifyDataSetChanged();

            Toast.makeText(ShowEventActivity.this, "Event joined", Toast.LENGTH_SHORT).show();

        }else{
            participants.remove(participantName);
            arrayAdapter.notifyDataSetChanged();

            Toast.makeText(ShowEventActivity.this, "Event left", Toast.LENGTH_SHORT).show();

        }
    }

    void initParticipants(){
        participants = new ArrayList<String>();
        participants.add("Alex");
    }




}
