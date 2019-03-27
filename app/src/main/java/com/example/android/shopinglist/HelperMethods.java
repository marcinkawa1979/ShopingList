package com.example.android.shopinglist;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * This HelperMethod class contains methods used in project
 */

public final class HelperMethods {

    /**
     * Create a private constructor because no one should ever create a {@link HelperMethods} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     *  directly from the class name HelperMethods (and an object instance of QueryUtils is not needed).
     */
    private HelperMethods() {
    }

    /**
     * This method changes Order type object to string. So to send object via Activities.
     * @param order object to change
     * @return String representation of object
     */
    public static String changeOrderToString(Order order){
        String json = new Gson().toJson(order);
        return json;
    }

    /**
     * This method changes String type object to Order object.
     * @param json string representation of Order object
     * @return
     */
    public static Order changeStringToOrder(String json){

        Order order = new Gson().fromJson(json, Order.class);
        return order;
    }

    /**
     * Converts String date chosen by user to Date object
     * @param stringDate to convert
     * @return Date object
     */
    public static Date convertStringToDate(String stringDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date myDate = new Date();
        try {
            myDate = dateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return myDate;
    }

    /**
     * Convert Date object to String date
     * @param date object to convert
     * @return date as String
     */
    public static String dateToString(Date date){

        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String stringDate = DATE_FORMAT.format(date);
        return stringDate;

    }
}
