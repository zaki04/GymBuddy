package com.example.toby.gymapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


//The LogoActivity is our first screen of the app, showing the logo and title of the app.
//It's main function is to send the user on to the login screen with a single press.
public class LogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

     //adding our very first button, which is an invisible button filling the entire screen
        final Button btnInvisible = (Button) findViewById(R.id.btnInvisible);

     //Now we want to send users to the LoginActivity with a simple push anywhere on the screen
        btnInvisible.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View v) {

            Intent firstIntent = new Intent(LogoActivity.this, LoginActivity.class);
            LogoActivity.this.startActivity(firstIntent);

        }
    });
}
}
