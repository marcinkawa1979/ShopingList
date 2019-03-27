package com.example.android.shopinglist;

public class Product {

    private String productName;
    private float productCost;

    public Product(){}

    public Product(String productName, int productCost) {
        this.productName = productName;
        this.productCost = productCost;
    }

    public Product(String productName){
        this.productName = productName;
        productCost = 5.00F;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductCost() {
        return productCost;
    }

    public void setProductCost(int productCost) {
        this.productCost = productCost;
    }
}
