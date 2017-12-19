package com.example.toby.gymapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


     //same as the previous Logo screen, we make some buttons
     final Button btnSignIn = findViewById(R.id.btnSignIn);
     final Button btnCreateAccount = findViewById(R.id.btnCreateAccount);

        // If the user is logged in, it redirects him to Main Activity
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }

        // Set on click listener for the login button
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = new Intent(LoginActivity.this, LoginUserActivity.class);
                LoginActivity.this.startActivity(signInIntent);
            }
        });

        // Set on click listener for the crete account button
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent accountIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(accountIntent);
            }
        });


    }
}
