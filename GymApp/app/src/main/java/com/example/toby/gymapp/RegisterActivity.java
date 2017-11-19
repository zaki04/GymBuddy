package com.example.toby.gymapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //adding the sign up button which sends the user to the main screen of the app
        final Button btnSignUp = (Button) findViewById(R.id.btnSignUp);

        //and the intent to change the screen
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(RegisterActivity.this, MainActivity.class);
                RegisterActivity.this.startActivity(signupIntent);
            }
        });
    }
}