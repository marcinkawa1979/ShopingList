package com.example.android.shopinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogInActivity extends AppCompatActivity {

    @BindView(R.id.e_mail_et) EditText mEmailTV;
    @BindView(R.id.password_et) EditText mPasswordTV;
    @BindView(R.id.log_in_button) Button mLogInButton;
    @BindView(R.id.show_password_button) ImageButton mShowPasswordButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        ButterKnife.bind(this);
        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, OrderList.class);
                startActivity(intent);

            }
        });
    }
}
