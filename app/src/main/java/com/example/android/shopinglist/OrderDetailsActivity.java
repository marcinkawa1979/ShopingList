package com.example.android.shopinglist;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

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
    @BindView(R.id.order_details_button)
    Button mSendLink;

    public static final String LOG_TAG = OrderDetailsActivity.class.getName();
    private static final String URL = "https://test.elementzone.uk/generate";

    private static final String E_MAIL = "emailKey";
    private static final String PASSWORD = "passwordKey";
    private static final String TOKEN = "token";

    private String token;
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        ButterKnife.bind(this);

        SharedPreferences preferences = getSharedPreferences("myPreference", Activity.MODE_PRIVATE);
        token = preferences.getString(TOKEN, "");

        Intent intent = getIntent();
        final String json = intent.getStringExtra("Json object");

        Order order = HelperMethods.changeStringToOrder(json);

        Integer order_id = order.getId();
        final String idString = order_id.toString();

        String shopName = order.getShopName();
        mOrderName.setText(shopName);

        String shopAddress = order.getShopAddress();
        mShopAddress.setText(shopAddress);

        String date = order.getDate();
        mOrderDate.setText(date);

        Date receptionDate = order.getReceptionData();
        mOrderDate.setText(HelperMethods.dateToString(receptionDate));

        ArrayList<Product> productList;
        productList = order.getProductList();
        if(!productList.isEmpty() || productList != null)
        mProductsList.setText(buildProductList(productList));

        float price = order.getPrice();
        mPrice.setText(formatPrice(price));

        mSendLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLink(URL, token, idString);

                Toast.makeText(OrderDetailsActivity.this, "This is link to order: "  + link, Toast.LENGTH_SHORT).show();
            }
        });

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

    //TODO
    private void getLink(String url, String token, String idString){
        try {
            link = new OrderDetailsAsync(url, token, idString).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.i(LOG_TAG, "getLink method loader check point 3 " + url);
    }


}
