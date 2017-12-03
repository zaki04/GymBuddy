package com.example.toby.gymapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class LoginUserActivity extends AppCompatActivity {

    // Define View objects
    ImageView imageViewSignIn;
    TextView textViewSignIn;
    EditText editTextLoginEmail, editTextLoginPassword;
    CheckBox checkBoxSignIn;
    ProgressBar progressBarUserLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        // Initialize view objects
        editTextLoginEmail = (EditText) findViewById(R.id.editTextLoginEmail);
        editTextLoginPassword = (EditText) findViewById(R.id.editTextLoginPassword);
        checkBoxSignIn = (CheckBox) findViewById(R.id.checkBoxSignIn);
        progressBarUserLogin = (ProgressBar) findViewById(R.id.progressBarUserLogin);

        // If user is already logged in, display user profile
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, UserAccountActivity.class));
            return;
        }

        // Call userLogin method when user presses login button
        findViewById(R.id.btnUserSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
    }

    // Create userLogin method
    private void userLogin(){

        // Get values from the fields email and password
        final String email = editTextLoginEmail.getText().toString();
        final String password = editTextLoginPassword.getText().toString();

        // Validate field email
        if(TextUtils.isEmpty(email)){
            editTextLoginEmail.setError("Please enter your email");
            editTextLoginEmail.requestFocus();
            return;
        }

        // Validate field password
        if(TextUtils.isEmpty(password)){
            editTextLoginPassword.setError("Please enter your password");
            editTextLoginPassword.requestFocus();
            return;
        }

        progressBarUserLogin.setVisibility(View.VISIBLE);
        // If the validation is ok

        // Create a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBarUserLogin.setVisibility(View.GONE);

                        try {
                            // Convert the response to json object
                            JSONObject obj = new JSONObject(response);

                            // If there is no error in response
                            if (!obj.getBoolean("error")) {
                                // Display a toast with message from json object
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                // Get the user from the response
                                JSONObject userJson = obj.getJSONObject("user");

                                // Create a new user object
                                User user = new User(
                                        userJson.getInt("id"),
                                        userJson.getString("name"),
                                        userJson.getString("birthdate"),
                                        userJson.getString("email")
                                );

                                // Store the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                // Start the user account activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), UserAccountActivity.class));
                            } else {
                                // Display a toast with message coming from the server
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
                        // Set progress bar visibility to gone
                        progressBarUserLogin.setVisibility(View.GONE);
                        // Display a toast with error message
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override // Override getParams method
            protected Map<String, String> getParams() throws AuthFailureError {
                // Define a map object
                Map<String, String> params = new HashMap<>();
                // Put all the parameters required in this hash map
                // The 1st parameter is the text we've written in the POST variable of the php script
                // The 2nd parameter is the actual value that needs to be sent
                params.put("email", email);
                params.put("password", password);
                // Return parameters
                return params;
            }
        };

        // Add the string request to request queue
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}