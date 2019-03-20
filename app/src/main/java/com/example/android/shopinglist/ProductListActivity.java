package com.example.android.shopinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListActivity extends AppCompatActivity {

    @BindView(R.id.product_list_button)
    Button mConfirmButton;
    @BindView(R.id.products_new_product_ev)
    EditText mProductName;
    @BindView(R.id.add_product_bt)
    FloatingActionButton mAddProduct;
    @BindView(R.id.products_price_ev)
    EditText mPrice;

    private ArrayList<Product> productArrayList = new ArrayList<>();
    private MyProductAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        final String json = intent.getStringExtra("Json string");

        if(!(productArrayList == null)) {
            RecyclerView recyclerView = findViewById(R.id.products_lv);
            recyclerView.setHasFixedSize(true);

            mAdapter = new MyProductAdapter(productArrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(ProductListActivity.this));

            recyclerView.setAdapter(mAdapter);
        }

        mAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product = mProductName.getText().toString();

                if(product.equals("")){
                    Toast.makeText(getApplicationContext(), "Podaj nazwę produktu", Toast.LENGTH_LONG).show();
                } else{
                    addProduct(product);
                    mAdapter.notifyDataSetChanged();
                    mProductName.setText("");
                }
            }
        });

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order order = HelperMethods.changeStringToOrder(json);
                order.setProductList(productArrayList);

                String insertedPrice = mPrice.getText().toString();

                if(insertedPrice.equals("")){
                    Toast.makeText(getApplicationContext(), "Wprowadz kwotę.", Toast.LENGTH_LONG).show();
                } else {
                    float price = Float.valueOf(insertedPrice);
                    order.setPrice(price);

                    //TODO here object should be send to server and open OrderList Activity
                    String json = HelperMethods.changeOrderToString(order);
                    Intent intent = new Intent(ProductListActivity.this, OrderDetailsActivity.class);
                    intent.putExtra("Json object", json);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * Adds new product to list.
     * @param productName inserted by user
     */
    private void addProduct(String productName){

        Product product = new Product(productName);
        productArrayList.add(product);
    }
}
