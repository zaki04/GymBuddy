package com.example.toby.gymapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CreateEvent extends AppCompatActivity {

    // Define view objects
    TextView tvCreateEventWelcome, tvCreateEventGym, tvCreateEventCreator, tvCreateEventCreatorValue;
    EditText etCreateEventTitle, etCreateEventDescription, etCreateEventDate, etCreateEventTime, etCreateEventSelectedGym;
    ImageButton ibtnCreateEventDatePicker, ibCreateEventTimePicker;
    Button btnCancelCreateEvent, btnCreateEventSubmit;

    // Define a calendar object
    final Calendar myCalendar = Calendar.getInstance();

    //ArrayList<Gym> gymArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        // Set OnClick Listener for cancel button
        findViewById(R.id.btnCancelCreateEvent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateEvent.this, MainActivity.class));
            }
        });

        // Initialize view objects
        tvCreateEventWelcome = findViewById(R.id.tvCreateEventWelcome);
        etCreateEventTitle = findViewById(R.id.etCreateEventTitle);
        etCreateEventDescription = findViewById(R.id.etCreateEventDescription);

        // Get the current user from Shared Preferences
        User user = SharedPrefManager.getInstance(this).getUser();

        // Initialize view objects
        tvCreateEventCreator = findViewById(R.id.tvCreateEventCreator);
        tvCreateEventCreatorValue = findViewById(R.id.tvCreateEventCreatorValue);

        // Set value for the text view
        // Sets the currently logged in user
        tvCreateEventCreatorValue.setText(String.valueOf(user.getEmail()));

        // Initialize view object
        etCreateEventDate = findViewById(R.id.etCreateEventDate);
        // Set the edit text unclickable
        etCreateEventDate.setClickable(false);
        // Define that the edit text is not focusable
        etCreateEventDate.setFocusable(false);

        // Initialize view object
        etCreateEventSelectedGym = findViewById(R.id.etCreateEventSelectedGym);

        // Set the listener to call when the user sets the date
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                // Set the year
                myCalendar.set(Calendar.YEAR, year);
                // Set the month
                myCalendar.set(Calendar.MONTH, monthOfYear);
                // Set the day
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                // Call method updateLabel
                updateLabel();
            }
        };

        // Set OnClick Listener for the date picker button
        findViewById(R.id.ibtnCreateEventDatePicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create a date picker dialog
                new DatePickerDialog(CreateEvent.this, date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Initialize the view object
        etCreateEventTime = findViewById(R.id.etCreateEventTime);
        // Define the edit text as not clickable
        etCreateEventTime.setClickable(false);
        // Define the edit text as not focusable
        etCreateEventTime.setFocusable(false);

        // ???
        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {

                // Set the hour
                myCalendar.set(Calendar.HOUR_OF_DAY, hours);
                // Set minutes
                myCalendar.set(Calendar.MINUTE, minutes);
                // Call method updateTimeLabel
                updateTimeLabel();
            }
        };

        // Set OnClick Listener for the time picker dialog
        findViewById(R.id.ibCreateEventTimePicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create a time picker dialog
                new TimePickerDialog(CreateEvent.this, time, myCalendar.get(Calendar.HOUR_OF_DAY),
                        myCalendar.get(Calendar.MINUTE), false).show();
            }
        });

        // Instantiate an AlertDialog.Builder with its constructor
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Set the title for alert dialog
        builder.setTitle("Pick a gym");

        // Create an array adapter
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CreateEvent.this,
                android.R.layout.select_dialog_singlechoice);
        // Add the objects at the end of the array
        arrayAdapter.add("Gym 1");
        arrayAdapter.add("Gym 2");
        arrayAdapter.add("Gym 3");

        // Set a listener to be invoked when the negative button of the dialog is clicked
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        });

        // Set a list of items, to be displayed in the dialog as the content
        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {

                // Get the data item associated with the specified position in the data set
                final String selectedGym = arrayAdapter.getItem(which);
                // Set text for the edit text with selected gym
                etCreateEventSelectedGym.setText(selectedGym);

            }
        });

        // Set OnClick Listener for the text view pick a gym
        findViewById(R.id.tvCreateEventGym).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an AlertDialog with the arguments supplied to this builder and immediately display the dialog
                builder.show();
            }
        });

        // Set OnClick Listener for the submit button
        findViewById(R.id.btnCreateEventSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Call the method registerEvent
                registerEvent();
            }
        });
    }

    // Create a method for updating the edit text with chosen date
    private void updateLabel(){

        // Define the pattern for date
        String myFormat = "dd-MM-yyyy";
        // Construct a simple date format
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        // Set text for the edit text with chosen date in the defined format
        etCreateEventDate.setText(sdf.format(myCalendar.getTime()));
    }

    // Create a method for updating the edit text with chosen time
    private void updateTimeLabel(){

        // Define the pattern for time
        String myTimeFormat = "h:mm a";
        // Construct a simple date format
        SimpleDateFormat tf = new SimpleDateFormat(myTimeFormat, Locale.US);

        // Set text for the edit text with chosen time in the defined format
        etCreateEventTime.setText(tf.format(myCalendar.getTime()));
    }

    // Create a method for creating event
    // Inside this method we call the script createEvent.php
    private void registerEvent() {

        // Get the values from edit text and text view
        final String title = etCreateEventTitle.getText().toString().trim();
        final String description = etCreateEventDescription.getText().toString().trim();
        final String date = etCreateEventDate.getText().toString().trim();
        final String time = etCreateEventTime.getText().toString().trim();
        final String gym = etCreateEventSelectedGym.getText().toString().trim();
        final String creator = tvCreateEventCreatorValue.getText().toString().trim();

        // Validate field title
        if (TextUtils.isEmpty(title)) {
            etCreateEventTitle.setError("Please fill out title");
            etCreateEventTitle.requestFocus();
            return;
        }

        // Validate field description
        if (TextUtils.isEmpty(description)) {
            etCreateEventDescription.setError("Please fill out description");
            etCreateEventDescription.requestFocus();
        }

        // Validate field date
        if (TextUtils.isEmpty(date)) {
            etCreateEventDate.setError("Please pick a date");
            return;
        }

        // Validate field time
        if (TextUtils.isEmpty(time)) {
            etCreateEventTime.setError("Please pick time");
            return;
        }

        // Validate field gym
        if(TextUtils.isEmpty(gym)){
            etCreateEventSelectedGym.setError("Please pick a gym");
            etCreateEventSelectedGym.requestFocus();
            return;
        }

        // If the validation is passed
        // Create a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_CREATE_EVENT,
                // Pass a new on response listener
                // If there is no error, this method will be executed
                // In the response we will get json object
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            // Convert response to json object
                            JSONObject jsonObject = new JSONObject(response);

                            // If there is no error in response
                            if (!jsonObject.getBoolean("error")) {
                                // Display a toast with message from json object
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"),
                                        Toast.LENGTH_SHORT).show();

                                // Start the main activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {

                                // If there is an error in response
                                // Display a toast with message from json object
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"),
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                // Pass a new error listener
                // If there is an error, this method will be executed
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Display a toast with error message
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override // override getParams method
            protected Map<String, String> getParams() throws AuthFailureError {
                // Define a map object
                Map<String, String> params = new HashMap<>();
                // Put all the parameters required in this hash map
                // The 1st parameter is the text we've written in the POST variable of the php script
                // The 2nd parameter is the actual value that needs to be sent
                params.put("title", title);
                params.put("description", description);
                params.put("date", date);
                params.put("time", time);
                params.put("gym", gym);
                params.put("creator", creator);
                // Return parameters
                return params;
            }
        };

        // Add the request to request queue object
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        }

    }

