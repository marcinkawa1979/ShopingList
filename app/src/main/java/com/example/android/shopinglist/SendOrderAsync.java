package com.example.android.shopinglist;

import android.os.AsyncTask;
import android.util.Log;

public class SendOrderAsync extends AsyncTask<Order, Void, String> {

    /** Tag for log messages */
    private static final String LOG_TAG = ProductListActivity.class.getName();

    private String mUrl;
    private Order mOrderToAdd;
    private String mToken;

    public SendOrderAsync(String url,Order order, String token){
        this.mUrl = url;
        this.mOrderToAdd = order;
        this.mToken = token;

    }

    @Override
    protected String doInBackground(Order... orders) {
        Log.i(LOG_TAG, "In SendOrderAsync loadInBackground method check point 1 "+ mToken + mUrl);

        // Don't perform the request if there are no URLs, or the first URL is null.
        if(mUrl== null){
            return null;
        }

        // Perform the HTTP request for earthquake data and process the response.
        String result = QueryUtils.addOrder(mUrl, mOrderToAdd, mToken);
        Log.i(LOG_TAG, "In SendOrderAsync loadInBackground method check point 2 " + result);
        return result;
    }
}
