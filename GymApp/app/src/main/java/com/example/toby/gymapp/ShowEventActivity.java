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

    TextView tvShowEventTitle, tvShowEventCreator, tvShowEventPlace, tvShowEventDescription, tvShowEventDate, tvShowEventTime;

    List participants;
    ListView participantList;
    ArrayAdapter arrayAdapter;

    User user = SharedPrefManager.getInstance(this).getUser();
    String participantName = user.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);

        tvShowEventTitle = (TextView) findViewById(R.id.tvShowEventTitle);
        tvShowEventCreator = (TextView) findViewById(R.id.tvShowEventCreator);
        tvShowEventPlace = (TextView) findViewById(R.id.tvShowEventPlace);
        tvShowEventDescription = (TextView) findViewById(R.id.tvShowEventDescription);
        tvShowEventDate = (TextView) findViewById(R.id.tvShowEventDate);
        tvShowEventTime = (TextView) findViewById(R.id.tvShowEventTime);

        Intent intent = getIntent();

        String clickedTitle = intent.getStringExtra("clickedTitle");
        String clickedDescription = intent.getStringExtra("clickedDescription");
        String clickedDate = intent.getStringExtra("clickedDate");
        String clickedTime = intent.getStringExtra("clickedTime");
        String clickedGym = intent.getStringExtra("clickedGym");
        String clickedCreator = intent.getStringExtra("clickedCreator");

        tvShowEventTitle.setText(clickedTitle);
        tvShowEventCreator.setText("Created by " + clickedCreator);
        tvShowEventPlace.setText(clickedGym);
        tvShowEventDate.setText(clickedDate);
        tvShowEventTime.setText(clickedTime);
        tvShowEventDescription.setText(clickedDescription);

        initParticipants();

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, participants);
        participantList = findViewById(R.id.participantList);
        participantList.setAdapter(arrayAdapter);
    }

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
