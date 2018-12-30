package com.dzulkarnain_inc.pulsa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //public static final String ROOT_URL = "http://192.168.43.215/Android%20Studio/PulsaKu/";
    //public static final String ROOT_URL = "http://100.66.8.27/Android%20Studio/PulsaKu/";
    public static final String ROOT_URL = "http://192.168.43.215/Laravel/PulsaKu/public/api/pulsa/";

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        new android.os.Handler().postDelayed(new Runnable(){
            public void run(){
                Intent home = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(home);
                MainActivity.this.overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
                finish();
            }
        },SPLASH_TIME_OUT);

        String title = "";
        setActionBarTitle(title);
    }

    private void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }
}
