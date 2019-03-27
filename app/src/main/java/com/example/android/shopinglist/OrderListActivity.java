package com.example.android.shopinglist;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderListActivity extends AppCompatActivity {

    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.order_list_rv) RecyclerView recyclerView;
    @BindView(R.id.empty_view) RelativeLayout emptyView;

    public static final String LOG_TAG = OrderListActivity.class.getName();

    private static final String requestedUrl = "https://test.elementzone.uk/orders";
    private ArrayList<Order> orderArrayList = new ArrayList<>();
    private MyOrderAdapter mAdapter;
    private String token;
    private String response;

    private static final String TOKEN = "token";

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // This viewHolder will have all required values.
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            Order thisItem = orderArrayList.get(position);

            String json = HelperMethods.changeOrderToString(thisItem);
            Intent intent = new Intent(OrderListActivity.this, OrderDetailsActivity.class);
            intent.putExtra("Json object", json);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        SharedPreferences preferences = getSharedPreferences("myPreference", Activity.MODE_PRIVATE);
        token = preferences.getString(TOKEN, "");

        /*Date date = new Date();
        orderArrayList.add(new Order("Biedronka", "someAddress", date));
        orderArrayList.add(new Order("Lidl", "fghfdsf", date));
        orderArrayList.add(new Order("Biedronka", "someAddress", date));*/

        orderArrayList = connect();


        ButterKnife.bind(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderListActivity.this, ShopDataActivity.class);
                startActivity(intent);
            }
        });

        if(!(orderArrayList.size()<=0)){

            emptyView.setVisibility(View.GONE);
            recyclerView.setHasFixedSize(true);

            mAdapter = new MyOrderAdapter(orderArrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(OrderListActivity.this));
            recyclerView.setAdapter(mAdapter);

            mAdapter.setOnItemClickListener(onItemClickListener);

        } else {
            emptyView.setVisibility(View.VISIBLE);
        }
        mAdapter.setOnItemClickListener(onItemClickListener);
    }

    private ArrayList<Order> connect(){
        ArrayList <Order> response = null;

        try {
            response = new GetOrderAsync(requestedUrl, token).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.i(LOG_TAG, "connect method loader check point 3 " + requestedUrl);


        return response;

    }

}
