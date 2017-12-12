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

    // implements AdapterView.OnItemSelectedListener

    TextView tvCreateEventWelcome, tvCreateEventGym, tvCreateEventCreator, tvCreateEventCreatorValue;
    EditText etCreateEventTitle, etCreateEventDescription, etCreateEventDate, etCreateEventTime, etCreateEventSelectedGym;
    //Spinner spinnerCreateEventGym;
    ImageButton ibtnCreateEventDatePicker, ibCreateEventTimePicker;
    Button btnCancelCreateEvent, btnCreateEventSubmit;


    final Calendar myCalendar = Calendar.getInstance();

    //ArrayList<Gym> gymArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);


        //Gym gymA = new Gym("Gym1", "Address1");
        //Gym gymB = new Gym("Gym2", "Address2");
        //Gym gymC = new Gym("Gym3", "Address3");

        //gymArrayList.add(gymA);
        //gymArrayList.add(gymB);
        //gymArrayList.add(gymC);

        findViewById(R.id.btnCancelCreateEvent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateEvent.this, MainActivity.class));
            }
        });

        tvCreateEventWelcome = (TextView) findViewById(R.id.tvCreateEventWelcome);
        etCreateEventTitle = (EditText) findViewById(R.id.etCreateEventTitle);
        etCreateEventDescription = (EditText) findViewById(R.id.etCreateEventDescription);

        User user = SharedPrefManager.getInstance(this).getUser();

        tvCreateEventCreator = (TextView) findViewById(R.id.tvCreateEventCreator);
        tvCreateEventCreatorValue = (TextView) findViewById(R.id.tvCreateEventCreatorValue);

        tvCreateEventCreatorValue.setText(String.valueOf(user.getEmail()));

        etCreateEventDate = (EditText) findViewById(R.id.etCreateEventDate);
        etCreateEventDate.setClickable(false);
        etCreateEventDate.setFocusable(false);

        etCreateEventSelectedGym = (EditText) findViewById(R.id.etCreateEventSelectedGym);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };


        findViewById(R.id.ibtnCreateEventDatePicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(CreateEvent.this, date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etCreateEventTime = (EditText) findViewById(R.id.etCreateEventTime);
        etCreateEventTime.setClickable(false);
        etCreateEventTime.setFocusable(false);

        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hours);
                myCalendar.set(Calendar.MINUTE, minutes);
                updateTimeLabel();
            }
        };

        findViewById(R.id.ibCreateEventTimePicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(CreateEvent.this, time, myCalendar.get(Calendar.HOUR_OF_DAY),
                        myCalendar.get(Calendar.MINUTE), false).show();
            }
        });


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a gym");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CreateEvent.this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Gym 1");
        arrayAdapter.add("Gym 2");
        arrayAdapter.add("Gym 3");

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        });


        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                final String selectedGym = arrayAdapter.getItem(which);
                etCreateEventSelectedGym.setText(selectedGym);

            }
        });

        findViewById(R.id.tvCreateEventGym).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.show();
            }
        });



        //Spinner spinnerCreateEventGym = (Spinner) findViewById(R.id.spinnerCreateEventGym);
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gymsArray,
        //        android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinnerCreateEventGym.setAdapter(adapter);

        //spinnerCreateEventGym.setOnItemSelectedListener(CreateEvent.this);

        findViewById(R.id.btnCreateEventSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerEvent();
            }
        });

    }

    private void updateLabel(){
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etCreateEventDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateTimeLabel(){
        String myTimeFormat = "h:mm a";
        SimpleDateFormat tf = new SimpleDateFormat(myTimeFormat, Locale.US);
        etCreateEventTime.setText(tf.format(myCalendar.getTime()));
    }

    //public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
     //   parent.getItemAtPosition(pos);
    //}

    //@Override
    //public void onNothingSelected(AdapterView<?> adapterView) {

    //}

    public void returnDialog(String msg){
        tvCreateEventGym.setText(msg);
    }

    private void registerEvent() {
        final String title = etCreateEventTitle.getText().toString().trim();
        final String description = etCreateEventDescription.getText().toString().trim();
        final String date = etCreateEventDate.getText().toString().trim();
        final String time = etCreateEventTime.getText().toString().trim();
        final String gym = etCreateEventSelectedGym.getText().toString().trim();
        final String creator = tvCreateEventCreatorValue.getText().toString().trim();

        if (TextUtils.isEmpty(title)) {
            etCreateEventTitle.setError("Please fill out title");
            etCreateEventTitle.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(description)) {
            etCreateEventDescription.setError("Please fill out description");
            etCreateEventDescription.requestFocus();
        }

        if (TextUtils.isEmpty(date)) {
            etCreateEventDate.setError("Please pick a date");
            return;
        }

        if (TextUtils.isEmpty(time)) {
            etCreateEventTime.setError("Please pick time");
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_CREATE_EVENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (!jsonObject.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                                JSONObject eventJson = jsonObject.getJSONObject("event");

                                Event event = new Event(
                                        eventJson.getInt("id"),
                                        eventJson.getString("uniqueid"),
                                        eventJson.getString("title"),
                                        eventJson.getString("description"),
                                        eventJson.getString("date"),
                                        eventJson.getString("time"),
                                        eventJson.getString("gym"),
                                        eventJson.getString("creator")
                                );

                                SharedPrefManager.getInstance(getApplicationContext()).createEvent(event);

                                finish();
                                startActivity(new Intent(getApplicationContext(), ShowEventActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        }

    }

