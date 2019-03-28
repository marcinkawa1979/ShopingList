package com.example.android.shopinglist;

import android.os.AsyncTask;

public class OrderDetailsAsync extends AsyncTask<String, Void, String> {

    /** Tag for log messages */
    private static final String LOG_TAG = LogInAsync.class.getName();

    /** Query URL */
    private String mUrl;
    private String token;
    private String id;

    public OrderDetailsAsync(String mUrl, String token, String id) {
        this.mUrl = mUrl;
        this.token = token;
        this.id = id;
    }

    @Override
    protected String doInBackground(String... strings) {
//       Log.i(LOG_TAG, "In LogIn loadInBackground method check point");

        // Don't perform the request if there are no URLs, or the first URL is null.
        if(mUrl== null){
            return null;
        }

        // Perform the HTTP request for earthquake data and process the response.
        String result = QueryUtils.fetchLink(mUrl, token, id);
//        Log.i(LOG_TAG, "In loadInBackground method check point " + result);
        return result;
    }
}
