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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        // Initialize view objects
        editTextLoginEmail = (EditText) findViewById(R.id.editTextLoginEmail);
        editTextLoginPassword = (EditText) findViewById(R.id.editTextLoginPassword);
        checkBoxSignIn = (CheckBox) findViewById(R.id.checkBoxSignIn);

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

        // If the validation is ok

        // Create an async task
        class UserLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String response) {
                super.onPostExecute(response);

                progressBar.setVisibility(View.GONE);

                try {
                    // Convert response json object
                    JSONObject obj = new JSONObject(response);

                    // If there is no error in the response
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

                        // Strore the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        // Start the user account activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), UserAccountActivity.class));
                    } else {
                        // Display a toast if the email or password are invalid
                        Toast.makeText(getApplicationContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                // Create a request handler object
                RequestHandler requestHandler = new RequestHandler();

                // Create a HashMap
                HashMap<String, String> params = new HashMap<>();
                // Put the parameters to the HashMap
                params.put("email", email);
                params.put("password", password);

                // Return the response
                return requestHandler.sendPostRequest(Constants.URL_LOGIN, params);
            }
        }

        // Execute the async task
        UserLogin ul = new UserLogin();
        ul.execute();
    }
}