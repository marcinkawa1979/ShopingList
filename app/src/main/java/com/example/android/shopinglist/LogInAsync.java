package com.example.android.shopinglist;

import android.os.AsyncTask;
import android.util.Log;

public class LogInAsync extends AsyncTask<String, Void, String> {


    /** Tag for log messages */
    private static final String LOG_TAG = LogInAsync.class.getName();

    /** Query URL */
    private String mUrl;
    private String email;
    private String password;

    public LogInAsync(String url, String email, String password){

        this.mUrl = url;
        this.email = email;
        this.password = password;
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.i(LOG_TAG, "In LogIn loadInBackground method check point");

        // Don't perform the request if there are no URLs, or the first URL is null.
        if(mUrl== null){
            return null;
        }

        // Perform the HTTP request for earthquake data and process the response.
        String result = QueryUtils.logToServer(mUrl, email, password);
        Log.i(LOG_TAG, "In loadInBackground method check point " + result);
        return result;
    }

}

