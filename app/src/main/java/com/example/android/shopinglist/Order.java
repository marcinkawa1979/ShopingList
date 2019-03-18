package com.example.android.shopinglist;

import java.util.ArrayList;
import java.util.Date;

public class Order {

    private String shopName;
    private String shopAddress;
    private Date receptionData;
    private ArrayList<Product> productList;
    private float price;


    public Order(String shopName, String shopAddress, Date receptionData) {
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.receptionData = receptionData;
        this.productList = null;
        this.price = 0.00F;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public Date getReceptionData() {
        return receptionData;
    }

    public void setReceptionData(Date receptionData) {
        this.receptionData = receptionData;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
