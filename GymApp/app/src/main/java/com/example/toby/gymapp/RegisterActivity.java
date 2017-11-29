package com.example.toby.gymapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SyncStateContract;
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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    // Define View objects
    Button btnSignUp;
    TextView textViewCreateAccount;
    EditText editTextName, editTextAge, editTextEmail, editTextEmailConfirm, editTextPassword, editTextPasswordConfirm;
    CheckBox checkBox;

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

        //final Calendar calendar = Calendar.getInstance();
        //final DatePickerDialog

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
    private void registerUser(){
        final String name = editTextName.getText().toString().trim();
        final String birthdate = editTextAge.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

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

        // Validate field password
        if(TextUtils.isEmpty(password)){
            editTextPassword.setError("Please enter password");
            editTextPassword.requestFocus();
            return;
        }

        // If the validation is passed
        // Create an async task to create a new user account
        class RegisterUser extends AsyncTask<Void, Void, String>{

            //private ProgressBar progressBar;

            protected String doInBackground(Void... voids){

                // Create a Request Handler
                RequestHandler requestHandler = new RequestHandler();

                // Create a HashMap with request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("birthdate", birthdate);
                params.put("email", email);
                params.put("password", password);

                // Return the response
                return requestHandler.sendPostRequest(Constants.URL_REGISTER, params);
            }

            // Here the user is registered to the server
            protected void onPostExecute(String response) {
                super.onPostExecute(response);
                //hiding the progressbar after completion
                //progressBar.setVisibility(View.GONE);

                try {
                    // Converting response to json object
                    JSONObject obj = new JSONObject(response);

                    // If there is no error in response
                    if (!obj.getBoolean("error")) {
                        // Display a toast with message taken from php file
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        // Get the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

                        // Create a new user object
                        User user = new User(
                                userJson.getInt("id"),
                                userJson.getString("email"),
                                userJson.getString("birthdate"),
                                userJson.getString("name")
                        );

                        // Store the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        // Start the user account activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), UserAccountActivity.class));
                    } else {
                        // If there ia an error, display a toast with message from php file
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        // Execute the async task
        RegisterUser ru = new RegisterUser();
        ru.execute();
    }

}