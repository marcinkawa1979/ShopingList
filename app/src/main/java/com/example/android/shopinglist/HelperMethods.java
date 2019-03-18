package com.example.android.shopinglist;

import com.google.gson.Gson;

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
}
