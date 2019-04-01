package com.example.android.shopinglist;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;

/**
 * Helper methods related to requesting, sending and receiving data from server.
 */
public final class QueryUtils {
    /** Tag for log messages */
    private static final String LOG_TAG = LogInAsync.class.getName();
    private static final String LOG_TAG_SEND = ProductListActivity.class.getName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     *
     */
    static String logToServer(String requestUrl, String email, String password) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeLoginRequest(url, email, password);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create token
        String response = extractToken(jsonResponse);

        return response;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(OrderListActivity.LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeLoginRequest(URL url, String email, String password) throws IOException {
        String jsonResponse = "";

        // If the url is null, than return earlier
        if(url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);

            //Add POST data in JSON format
            JSONObject jsonParam = new JSONObject();
            try {
                jsonParam.put("email", email);
                jsonParam.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Create a writer object and make the request
            OutputStreamWriter outputStream = new OutputStreamWriter(urlConnection.getOutputStream());
            outputStream.write(jsonParam.toString());
            outputStream.flush();
            outputStream.close();

            urlConnection.connect();

            //If the response was successful(code 200)
            //then read the input stream and parse the response
            if (urlConnection.getResponseCode() == 200) {
                Log.e(LogInActivity.LOG_TAG, "OK, response code: " + urlConnection.getResponseCode());
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LogInActivity.LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LogInActivity.LOG_TAG, "Problem can't connect", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Return a String api_token
     */
    private static String extractToken(String jsonResponse) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        String token = "";

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            //Create new JSONObject that holds data from SAMPLE_JSON_RESPONSE
            JSONObject jObj = new JSONObject(jsonResponse);

            JSONObject data = jObj.getJSONObject("data");
            token = data.getString("api_token");

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return token;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    static Boolean refresh(String requestUrl, String token) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeRefreshRequest(url, token);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create token
        Boolean response = extractRefreshResponse(jsonResponse);

        return response;
    }

    private static Boolean extractRefreshResponse(String jsonResponse) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        Boolean response = null;
        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            //Create new JSONObject that holds data from SAMPLE_JSON_RESPONSE
            JSONObject jObj = new JSONObject(jsonResponse);
            response = jObj.getBoolean("data");

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return response;
    }

    private static String makeRefreshRequest(URL url, String token) throws IOException {
        String jsonResponse = "";

        // If the url is null, than return earlier
        if(url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);

            //Add POST data in JSON format
            JSONObject jsonParam = new JSONObject();
            try {
                jsonParam.put("api_token", token);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Create a writer object and make the request
            OutputStreamWriter outputStream = new OutputStreamWriter(urlConnection.getOutputStream());
            outputStream.write(jsonParam.toString());
            outputStream.flush();
            outputStream.close();

            urlConnection.connect();

            //If the response was successful(code 200)
            //then read the input stream and parse the response
            if (urlConnection.getResponseCode() == 200) {
                Log.e(LogInActivity.LOG_TAG, "OK, response code: " + urlConnection.getResponseCode());
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LogInActivity.LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LogInActivity.LOG_TAG, "Problem can't connect", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     *
     */
    static String addOrder(String requestUrl, Order order, String token) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeSendOrderRequest(url, order, token);
        } catch (IOException e) {
            Log.e(LOG_TAG_SEND, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create token
        //TODO check output json
        // String response = extractSendingResponse(jsonResponse);
        Log.e(LOG_TAG_SEND, "Response after sending Order " + jsonResponse);
        return jsonResponse;
    }

    private static JSONObject prepareObjectToSend(Order order, String token){
        //Add POST data in JSON format
        JSONObject jsonOrder = new JSONObject();
        JSONArray items = new JSONArray();


        try {
            jsonOrder.put("api_token", token);

            String dateToString = HelperMethods.dateToString(order.getReceptionData());
            jsonOrder.put("date", dateToString);
            jsonOrder.put("shop_name", order.getShopName());
            jsonOrder.put("location", order.getShopAddress());
            jsonOrder.put("price", order.getPrice());

            for(int i = 0; i<order.getProductList().size();i++){
                Product prod = order.getProductList().get(i);
                items.put(prod.getProductName());
            }
            jsonOrder.put("items", items);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonOrder;
    }
    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeSendOrderRequest(URL url, Order order, String token) throws IOException {
        String jsonResponse = "";
        JSONObject jsonOrder;

        // If the url is null, than return earlier
        if(url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);

             jsonOrder = prepareObjectToSend(order, token);

            //Create a writer object and make the request
            OutputStreamWriter outputStream = new OutputStreamWriter(urlConnection.getOutputStream());
            outputStream.write(jsonOrder.toString());
            outputStream.flush();
            outputStream.close();

            urlConnection.connect();

            //If the response was successful(code 200)
            //then read the input stream and parse the response
            if (urlConnection.getResponseCode() == 200) {
                Log.e(LOG_TAG_SEND, "OK, response code: " + urlConnection.getResponseCode());
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG_SEND, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG_SEND, "Problem can't connect", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Return a String api_token
     */
    private static String extractSendingResponse(String jsonResponse) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        String token = "";

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            //Create new JSONObject that holds data from SAMPLE_JSON_RESPONSE
            JSONObject jObj = new JSONObject(jsonResponse);

            JSONObject data = jObj.getJSONObject("data");
            token = data.getString("api_token");

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return token;
    }

    /**
     * Gets the list of orders from server
     * @param requestUrl to send request
     * @param token for user
     * @return list of orders
     */
    static ArrayList<Order> fetchOrders(String requestUrl, String token) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeGetOrdersRequest(url, token);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create token
        ArrayList<Order> response = extractOrder(jsonResponse);

        return response;
    }

    private static ArrayList<Order> extractOrder(String jsonResponse) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding objects to
        ArrayList<Order> ordersList = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            //Create new JSONObject that holds data from SAMPLE_JSON_RESPONSE
            JSONObject jObj = new JSONObject(jsonResponse);

            JSONArray dataArray = jObj.getJSONArray("data");


            for (int i = 0; i < dataArray.length(); i++){
                JSONObject orderJson = dataArray.getJSONObject(i);
                String stringDate = orderJson.getString("date");
                Date receptiondate = HelperMethods.convertStringToDate(stringDate);
                String nameOfShop = orderJson.getString("shop_name");
                String address = orderJson.getString("location");
                float price = Float.valueOf(orderJson.getString("price"));
                int order_id = orderJson.getInt("id");

                ArrayList<Product> productArrayList = new ArrayList<>();
                JSONArray itemsArray = orderJson.getJSONArray("items");
                for(int j = 0;j < itemsArray.length(); j++ ){
                    JSONObject item = itemsArray.getJSONObject(j);
                    String itemName = item.getString("item");
                    productArrayList.add(new Product(itemName));
                }
                // Add new order to ArrayList<Order>
                ordersList.add(new Order(nameOfShop, address, receptiondate, price, order_id, productArrayList));
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
            // Return the list of orders
        return ordersList;
    }

    /**
     * Make an HTTP request to the given URL and token. Return a String as the response.
     */
    private static String makeGetOrdersRequest(URL url, String token) throws IOException {
        String jsonResponse = "";

        // If the url is null, than return earlier
        if(url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);

            //Add POST data in JSON format
            JSONObject jsonParam = new JSONObject();
            try {
                jsonParam.put("api_token", token);
                jsonParam.put("offset", 70);
                jsonParam.put("limit", 6);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Create a writer object and make the request
            OutputStreamWriter outputStream = new OutputStreamWriter(urlConnection.getOutputStream());
            outputStream.write(jsonParam.toString());
            outputStream.flush();
            outputStream.close();

            urlConnection.connect();

            //If the response was successful(code 200)
            //then read the input stream and parse the response
            if (urlConnection.getResponseCode() == 200) {
                Log.e(OrderListActivity.LOG_TAG, "OK, response code: " + urlConnection.getResponseCode());
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(OrderListActivity.LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(OrderListActivity.LOG_TAG, "Problem can't connect", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    static String fetchLink(String requestUrl, String token , String id) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeLinkRequest(url, token, id);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create token
        String response = extractLink(jsonResponse);

        return response;
    }

    private static String extractLink(String jsonResponse) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        String link = "";

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            //Create new JSONObject that holds data from SAMPLE_JSON_RESPONSE
            JSONObject jObj = new JSONObject(jsonResponse);

            JSONObject data = jObj.getJSONObject("data");
            link = data.getString("link");

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return link;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeLinkRequest(URL url, String token, String id) throws IOException {
        String jsonResponse = "";

        // If the url is null, than return earlier
        if(url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);

            //Add POST data in JSON format
            JSONObject jsonParam = new JSONObject();
            try {
                jsonParam.put("api_token", token);
                jsonParam.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Create a writer object and make the request
            OutputStreamWriter outputStream = new OutputStreamWriter(urlConnection.getOutputStream());
            outputStream.write(jsonParam.toString());
            outputStream.flush();
            outputStream.close();

            urlConnection.connect();

            //If the response was successful(code 200)
            //then read the input stream and parse the response
            if (urlConnection.getResponseCode() == 200) {
                Log.e(LogInActivity.LOG_TAG, "OK, response code: " + urlConnection.getResponseCode());
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LogInActivity.LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LogInActivity.LOG_TAG, "Problem can't connect", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

}
