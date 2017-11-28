package com.example.toby.gymapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class UserAccountActivity extends AppCompatActivity {

    TextView textViewAccountId, textViewAccountName, textViewAccountAge, textViewAccountEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginUserActivity.class));
        }

        textViewAccountId = (TextView) findViewById(R.id.textViewAccountId);
        textViewAccountName = (TextView) findViewById(R.id.textViewAccountName);
        textViewAccountAge = (TextView) findViewById(R.id.textViewAccountAge);
        textViewAccountEmail = (TextView) findViewById(R.id.textViewAccountEmail);

        User user = SharedPrefManager.getInstance(this).getUser();

        textViewAccountId.setText(String.valueOf(user.getId()));
        textViewAccountName.setText(String.valueOf(user.getName()));
        textViewAccountAge.setText(String.valueOf(user.getBirthdate()));
        textViewAccountEmail.setText(String.valueOf(user.getEmail()));

        findViewById(R.id.buttonAccountLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });
    }
}
