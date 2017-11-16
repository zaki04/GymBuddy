package com.example.toby.moviestrike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //references to IDs!
        final EditText username = (EditText) findViewById(R.id.password);
        final EditText password = (EditText) findViewById(R.id.username);


        //declaring some buttons!
        final Button btnLogin = (Button) findViewById(R.id.btnLogin);
        final Button btnSignUp = (Button) findViewById(R.id.btnSignUp);

        //the OnClickListener handles what should happen when the button is clicked
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //this if statement takes the input given to username and password, converts to strings and checks if they are correct
                //if they are correct, the "Intent" changes the screen from this one, MainActivity, to LoggedinActivity
                if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                    Intent loginIntent = new Intent(MainActivity.this, LoggedinActivity.class);
                    MainActivity.this.startActivity(loginIntent);



                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //this code changes the screen from this one, MainActivity, to RegisterActivity
                {
                    Intent loginIntent = new Intent(MainActivity.this, RegisterActivity.class);
                    MainActivity.this.startActivity(loginIntent);



                }
            }
        });
    }
}