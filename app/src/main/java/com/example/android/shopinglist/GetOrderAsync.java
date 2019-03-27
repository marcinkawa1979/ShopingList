package com.example.android.shopinglist;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

public class GetOrderAsync extends AsyncTask<String, Void, ArrayList<Order>> {
    /** Tag for log messages */
    private static final String LOG_TAG = ProductListActivity.class.getName();

    private String mUrl;
    private String mToken;

    public GetOrderAsync(String url, String token){
        this.mUrl = url;
        this.mToken = token;
    }

    @Override
    protected ArrayList<Order> doInBackground(String... strings) {
        Log.i(LOG_TAG, "In SendOrderAsync loadInBackground method check point 1 "+ mToken + mUrl);

        // Don't perform the request if there are no URLs, or the first URL is null.
        if(mUrl== null){
            return null;
        }

        // Perform the HTTP request for earthquake data and process the response.
        ArrayList<Order> result = QueryUtils.fetchOrders(mUrl, mToken);
        Log.i(LOG_TAG, "In SendOrderAsync loadInBackground method check point 2 ");
        return result;
    }
}
