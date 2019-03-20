package com.example.android.shopinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailsActivity extends AppCompatActivity {

    @BindView(R.id.order_details_shop_name)
    TextView mOrderName;
    @BindView(R.id.order_details_shop_address)
    TextView mShopAddress;
    @BindView(R.id.order_details_date)
    TextView mOrderDate;
    @BindView(R.id.order_details_price)
    TextView mPrice;
    @BindView(R.id.order_details_products_tv)
    TextView mProductsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        final String json = intent.getStringExtra("Json object");

        Order order = HelperMethods.changeStringToOrder(json);

        String shopName = order.getShopName();
        mOrderName.setText(shopName);

        String shopAddress = order.getShopAddress();
        mShopAddress.setText(shopAddress);

        Date receptionDate = order.getReceptionData();
        mOrderDate.setText(dateToString(receptionDate));

        ArrayList<Product> productList;
        productList = order.getProductList();
        if(!productList.isEmpty())
        mProductsList.setText(buildProductList(productList));

        float price = order.getPrice();
        mPrice.setText(formatPrice(price));
    }

    private String dateToString(Date date){
        Format form = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return form.format(date);
    }

    private String buildProductList (ArrayList<Product> list){
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).getProductName());
                sb.append("\n");
        }
        return sb.toString();
    }

    private String formatPrice(Float price){
        NumberFormat formatter = new DecimalFormat("#0.00");
        return formatter.format(price) + " zł";
    }
}
