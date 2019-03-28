package com.example.android.shopinglist;

import android.os.AsyncTask;
import android.util.Log;

public class RefreshAsync extends AsyncTask<String,Void,Boolean> {
    /** Tag for log messages */
    private static final String LOG_TAG = RefreshAsync.class.getName();

    /** Query URL */
    private String mUrl;
    private String token;

    public RefreshAsync(String url, String token){

        this.mUrl = url;
        this.token = token;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        Log.i(LOG_TAG, "In LogIn loadInBackground method check point");

        // Don't perform the request if there are no URLs, or the first URL is null.
        if(mUrl== null){
            return null;
        }
        // Perform the HTTP request for earthquake data and process the response.
        Boolean result = QueryUtils.refresh(mUrl, token);
//        Log.i(LOG_TAG, "In loadInBackground method check point " + result);
        return result;
    }
}
