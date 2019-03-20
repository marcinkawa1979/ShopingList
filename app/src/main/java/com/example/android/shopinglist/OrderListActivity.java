package com.example.android.shopinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderListActivity extends AppCompatActivity {

    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.order_list_rv) RecyclerView recyclerView;
    @BindView(R.id.empty_view) RelativeLayout emptyView;

    private ArrayList<Order> orderArrayList = new ArrayList<>();
    private MyOrderAdapter mAdapter;

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


        Date date = new Date();
        orderArrayList.add(new Order("Biedronka", "someAddress", date));
        orderArrayList.add(new Order("Lidl", "fghfdsf", date));
        orderArrayList.add(new Order("Biedronka", "someAddress", date));

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

}
