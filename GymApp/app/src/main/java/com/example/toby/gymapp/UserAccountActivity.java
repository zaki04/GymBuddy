package com.example.toby.gymapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class UserAccountActivity extends AppCompatActivity {

    // Define View objects
    TextView textViewAccountId, textViewAccountName, textViewAccountAge, textViewAccountEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        // If the user is not logged in, start Main Activity
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        // Initialize view objects
        textViewAccountId = findViewById(R.id.textViewAccountId);
        textViewAccountName = findViewById(R.id.textViewAccountName);
        textViewAccountAge = findViewById(R.id.textViewAccountAge);
        textViewAccountEmail = findViewById(R.id.textViewAccountEmail);

        // Get the current user from shared preferences
        User user = SharedPrefManager.getInstance(this).getUser();

        // Set the values to text views
        textViewAccountId.setText(String.valueOf(user.getId()));
        textViewAccountName.setText(String.valueOf(user.getName()));
        textViewAccountAge.setText(String.valueOf(user.getBirthdate()));
        textViewAccountEmail.setText(String.valueOf(user.getEmail()));

        final ImageButton ImageButtonProfile = findViewById(R.id.imageButtonProfile);
        final ImageButton ImageButtonMenu = findViewById(R.id.imageButtonMenu);
        final ImageButton ImageButtonCreateEvent = findViewById(R.id.imageButtonCreateEvent);


        //making the ImageButtonProfile go to the UserAccountActivity on click, note that if not logged
        //in it will just go to the LoginActivity.
        ImageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent showProfileIntent = new Intent(UserAccountActivity.this, UserAccountActivity.class);
                UserAccountActivity.this.startActivity(showProfileIntent);

            }
        });
        //making the ImageButtonMenu go to the MainActivity on click, of course we're already within
        //this activity because for now the button is only used here.
        ImageButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent showMenuIntent = new Intent(UserAccountActivity.this, MainActivity.class);
                UserAccountActivity.this.startActivity(showMenuIntent);

            }
        });

        // Set OnClick listener for the create event button
        ImageButtonCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showCreateEventIntent = new Intent(UserAccountActivity.this, CreateEvent.class);
                UserAccountActivity.this.startActivity(showCreateEventIntent);
            }
        });




        // Call the logout method when the user presses logout button
        findViewById(R.id.buttonAccountLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });


    }
}