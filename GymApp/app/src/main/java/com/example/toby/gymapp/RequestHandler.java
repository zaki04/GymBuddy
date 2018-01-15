package com.example.toby.gymapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by aleks on 26.11.2017.
 *
 * This is a singleton class to handle all the network request.
 * It will create an instance of the request queue object
 * an that object will by lying as long as the app runs
 */

public class RequestHandler {

    private static RequestHandler mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    // Create a constructor
    private RequestHandler(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    // Create a synchronized constructor
    public static synchronized RequestHandler getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RequestHandler(context);
        }
        return mInstance;
    }

    // This method will give the request queue
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    // Method to add request object to the request queue
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}