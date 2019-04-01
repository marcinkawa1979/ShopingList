package com.example.android.shopinglist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogInActivity extends AppCompatActivity{

    /** Tag for log messages */
    public static final String LOG_TAG = LogInActivity.class.getName();

    private static final String E_MAIL = "emailKey";
    private static final String PASSWORD = "passwordKey";
    private static final String TOKEN = "token";

    private static final String BASE_URL = "https://test.elementzone.uk/";
    private static final String LOG_IN = "login";
    private static final String REFRESH = "refresh";

    String eMail;
    String password;
    String token;
    private String requestedUrl;

    private SharedPreferences preferences;

    @BindView(R.id.token) TextView mTokenTV;
    @BindView(R.id.e_mail_et) EditText mEmailET;
    @BindView(R.id.password_et) EditText mPasswordET;
    @BindView(R.id.log_in_button) Button mLogInButton;
    @BindView(R.id.show_password_button) ImageButton mShowPasswordButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        ButterKnife.bind(this);
        preferences = getSharedPreferences("myPreference", Activity.MODE_PRIVATE);

        // make password visible or hidden
        mShowPasswordButton.setOnClickListener(new View.OnClickListener() {
            boolean isPasswordVisible = false;
            @Override
            public void onClick(View v) {
                if(!isPasswordVisible){
                    mPasswordET.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mPasswordET.setSelection(mPasswordET.length());
                    isPasswordVisible = true;
                }else if(isPasswordVisible){
                    mPasswordET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mPasswordET.setSelection(mPasswordET.length());
                    isPasswordVisible = false;
                }
            }
        });

        if(preferences.contains(E_MAIL)){
            eMail = preferences.getString(E_MAIL,"");
            mEmailET.setText(eMail);}
        if(preferences.contains(PASSWORD)){
            password = preferences.getString(PASSWORD,"");
            mPasswordET.setText(password);}

        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preferences.contains(TOKEN) && verification(eMail, password)) {

                    requestedUrl = BASE_URL + REFRESH;
                    token = preferences.getString(TOKEN,"");

                    if(refresh()){
                        Intent intent = new Intent(LogInActivity.this, OrderListActivity.class);
                        startActivity(intent);
                  } else {
                         Toast.makeText(LogInActivity.this, getString(R.string.log_in_toast_1), Toast.LENGTH_SHORT).show();
                    }
                } else{
                    requestedUrl = BASE_URL + LOG_IN;
                    mLogInButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            eMail = mEmailET.getText().toString();
                            password = mPasswordET.getText().toString();

                            if (eMail.isEmpty()) {
                                Toast.makeText(LogInActivity.this, getString(R.string.log_in_input_email_toast), Toast.LENGTH_SHORT).show();
                            } else {
                                connect();
                            }

                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(E_MAIL, eMail);
                            editor.putString(PASSWORD, password);
                            editor.putString(TOKEN, token);
                            editor.apply();

                            Intent intent = new Intent(LogInActivity.this, OrderListActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }

    /**
     * Checks if device is connected to net and connects with server log in
     */
    private void connect(){
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            try {
                token = new LogInAsync(requestedUrl, eMail, password).execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            Log.i(LOG_TAG, "connect method loader check point 3 " + requestedUrl);

        }else{
            Toast.makeText(this, getString(R.string.login_no_connect_toast), Toast.LENGTH_SHORT).show();
        }

    }

    private Boolean refresh(){

        Boolean isRefreshed = null;

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            try {
                isRefreshed = new RefreshAsync(requestedUrl, token).execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            Log.i(LOG_TAG, "refresh method loader check point 4 " + isRefreshed + requestedUrl);

        }else{
            Toast.makeText(this, R.string.login_no_connect_toast, Toast.LENGTH_SHORT).show();
        }
        return isRefreshed;
    }

    /**
     * This method checks if email and password are valid
     *
     */
    private boolean verification(String mail, String pass){

        if(mEmailET.getText().toString().equals(mail) && mPasswordET.getText().toString().equals(pass)) {

            return true;

        } else {
            Toast.makeText(LogInActivity.this, getString(R.string.login_activ_validation_toast), Toast.LENGTH_SHORT).show();
            return false;
        }

    }

}
