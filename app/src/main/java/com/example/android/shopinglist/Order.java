package com.example.android.shopinglist;

import java.util.ArrayList;
import java.util.Date;

public class Order {

    private String shopName;
    private String shopAddress;

    private Date receptionData;

    private String date;
    private ArrayList<Product> productList;
    private float price;
    private int id;


    public Order(String shopName, String shopAddress, Date receptionData) {
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.receptionData = receptionData;
        this.productList = new ArrayList<>();
        this.price = 0.00F;
    }

    // this constructor is used when the list of orders is created
    public Order(String shopName, String shopAddress, Date date, float price, int id, ArrayList<Product> productList) {
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.receptionData = date;
        this.price = price;
        this.id = id;
        this.productList = productList;
    }

    public Order(Date receptionData, String shopName, String shopAddress, Float price, ArrayList<Product> productList){
        this.receptionData = receptionData;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.price = price;
        this.productList= productList;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
