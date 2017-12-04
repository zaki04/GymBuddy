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
     final Button btnSignIn = (Button) findViewById(R.id.btnSignIn);
     final Button btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
     final Button btnFacebook = (Button) findViewById(R.id.btnFacebook);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, UserAccountActivity.class));
            return;
        }

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = new Intent(LoginActivity.this, LoginUserActivity.class);
                LoginActivity.this.startActivity(signInIntent);
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent accountIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(accountIntent);
            }
        });

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebookIntent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(facebookIntent);
            }
        });
    }


}
