package com.example.toby.gymapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        final ImageButton ImageButtonProfile = findViewById(R.id.imageButtonProfile);
        final ImageButton ImageButtonCalendar = findViewById(R.id.imageButtonCalendar);
        final ImageButton ImageButtonMenu = findViewById(R.id.imageButtonMenu);


        //making the ImageButtonProfile go to the UserAccountActivity on click, note that if not logged
        //in it will just go to the LoginActivity.
        ImageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent showProfileIntent = new Intent(CalendarActivity.this, UserAccountActivity.class);
                CalendarActivity.this.startActivity(showProfileIntent);

            }
        });
        //making the ImageButtonMenu go to the MainActivity on click, of course we're already within
        //this activity because for now the button is only used here.
        ImageButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent showMenuIntent = new Intent(CalendarActivity.this, MainActivity.class);
                CalendarActivity.this.startActivity(showMenuIntent);

            }
        });
        //Last but not least, the ImageButtonCalendar to sent the user to the calendar activity.
        ImageButtonCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent showCalendarIntent = new Intent(CalendarActivity.this, CalendarActivity.class);
                CalendarActivity.this.startActivity(showCalendarIntent);

            }
        });
    }
}
