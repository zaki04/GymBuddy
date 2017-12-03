package com.example.toby.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    // Define View objects
    private TextView textViewCreateAccount;
    private EditText editTextName, editTextAge, editTextEmail, editTextEmailConfirm, editTextPassword, editTextPasswordConfirm;
    private CheckBox checkBox;
    private ProgressBar progressBarRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // If user is already logged in, display user profile
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, UserAccountActivity.class));
            return;
        }

        // Get previously defined views from xml
        textViewCreateAccount = (TextView) findViewById(R.id.textViewCreateAccount);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextEmailConfirm = (EditText) findViewById(R.id.editTextEmailConfirm);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPasswordConfirm = (EditText) findViewById(R.id.editTextPasswordConfirm);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        progressBarRegister = (ProgressBar) findViewById(R.id.progressBarRegister);

        // Set OnClickListener for the button
        // After clicking the button the user will be registered to the server
        findViewById(R.id.btnSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    // Create a method for registering user
    // Inside this method we call the script registerUser.php
    private void registerUser(){

        // Get the values from edit text
        final String name = editTextName.getText().toString().trim();
        final String birthdate = editTextAge.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        String emailConfirm = editTextEmailConfirm.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        String passwordConfirm = editTextPasswordConfirm.getText().toString().trim();
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);

        // Validate field name
        if(TextUtils.isEmpty(name)){
            editTextName.setError("Please enter name");
            editTextName.requestFocus();
            return;
        }

        // Validate field date of birth
        if(TextUtils.isEmpty(birthdate)){
            editTextAge.setError("Please enter date of birth");
            editTextAge.requestFocus();
            return;
        }

        // Validate field email
        if(TextUtils.isEmpty(email)){
            editTextEmail.setError("Please enter email");
            editTextEmail.requestFocus();
            return;
        }

        // Validate email confirmation
        if(!TextUtils.equals(email, emailConfirm)){
            editTextEmailConfirm.setError("Email not matching");
            editTextEmailConfirm.requestFocus();
            return;
        }

        // Validate field password
        if(TextUtils.isEmpty(password)){
            editTextPassword.setError("Please enter password");
            editTextPassword.requestFocus();
            return;
        }

        // Validate password confirmation
        if(!TextUtils.equals(password, passwordConfirm)){
            editTextPasswordConfirm.setError("Email not matching");
            editTextPasswordConfirm.requestFocus();
            return;
        }

        // Validate checkbox
        if(!checkBox.isChecked()){
            checkBox.setError("Checkbox must be checked");
            checkBox.requestFocus();
            return;
        }

        // Set progress bar to visible
        progressBarRegister.setVisibility(View.VISIBLE);


        // If the validation is passed
        // Create a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER,
                // Pass a new on response listener
                // If there is no error, this method will br executed
                // In the response we will get json object
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Set the progress bar to gone
                       progressBarRegister.setVisibility(View.GONE);

                        try {
                            // Convert response to json object
                            JSONObject obj = new JSONObject(response);

                            // If there is no error in response
                            if (!obj.getBoolean("error")) {
                                // Display a toast with message from json object
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                // Get the user from the response
                                JSONObject userJson = obj.getJSONObject("user");

                                // Creating a new user object
                                User user = new User(
                                        userJson.getInt("id"),
                                        userJson.getString("name"),
                                        userJson.getString("birthdate"),
                                        userJson.getString("email")
                                );

                                // Store the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                // Start the profile activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), UserAccountActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
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
                        progressBarRegister.setVisibility(View.GONE);
                        // Display the error here if there is one
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
                params.put("name", name);
                params.put("birthdate", birthdate);
                params.put("email", email);
                params.put("password", password);
                // Return parameters
                return params;
            }
        };

        // Add the request to request queue object
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

}