package com.example.android.shopinglist;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopDataActivity extends AppCompatActivity {

    @BindView(R.id.shop_next_button)
    Button mNextButton;
    @BindView(R.id.shop_name)
    EditText mShopNameET;
    @BindView(R.id.shop_address)
    EditText mShopAddressET;
    @BindView(R.id.shopping_date)
    EditText mShoppingDateET;

    Order order;

    Calendar calender;
    DatePickerDialog datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_data);

        ButterKnife.bind(this);

        mShoppingDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String shopName = mShopNameET.getText().toString();
                String shopAddress = mShopAddressET.getText().toString();
                String shoppingDate =  mShoppingDateET.getText().toString();

                if(shopName.equals("") || shopAddress.equals("") || shoppingDate.equals("")){
                    Toast.makeText(getApplicationContext(), "Uzupełnij dane.", Toast.LENGTH_LONG).show();
                } else {

                    order = new Order(shopName, shopAddress, convertStringToDate(shoppingDate));

                    Intent intent = new Intent(ShopDataActivity.this, ProductListActivity.class);
                    intent.putExtra("Json string", HelperMethods.changeOrderToString(order));
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * Converts String date chosen by user to Date object
     * @param stringDate to convert
     * @return Date object
     */
    private Date convertStringToDate(String stringDate) {
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd", Locale.getDefault());
        Date t = null;
        try {
            t = ft.parse(stringDate);
        } catch (ParseException e) {
        }
        return t;
    }

    /**
     * Use custom datepicker to set date of reception shopping
     */
    private void chooseDate(){
        calender = Calendar.getInstance();
        int day = calender.get(Calendar.DAY_OF_MONTH);
        int month = calender.get(Calendar.MONTH);
        int year = calender.get(Calendar.YEAR);

        datePicker = new DatePickerDialog(ShopDataActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String s = year + "-" + month + "-" + dayOfMonth;
                mShoppingDateET.setText(s);
            }
        },year,month,day);
        datePicker.show();
    }
}
